package com.imooc.mvcdemo.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.imooc.mvcdemo.dao.SeckillDao;
import com.imooc.mvcdemo.dao.SuccessKilledDao;
import com.imooc.mvcdemo.dao.cache.RedisDao;
import com.imooc.mvcdemo.dto.Exposer;
import com.imooc.mvcdemo.dto.SeckillExcution;
import com.imooc.mvcdemo.enums.SeckillStateEnum;
import com.imooc.mvcdemo.exception.RepeatKillException;
import com.imooc.mvcdemo.exception.SeckillCloseException;
import com.imooc.mvcdemo.exception.SeckillException;
import com.imooc.mvcdemo.model.Seckill;
import com.imooc.mvcdemo.model.SuccessKilled;
import com.imooc.mvcdemo.service.SeckillService;
@Service
public class SeckillServiceImpl implements SeckillService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillDao seckillDao;
	
	@Autowired
	private SuccessKilledDao successKilledDao;
	
	@Autowired
	private RedisDao redisDao;
	
	//md5盐值字符串，混淆MD5
	private final String slat = "cc454cggfdgdfgg54g6f34@#$%~~fdgfdggfd45";
	
	@Override
	public List<Seckill> getSeckillList() {
		// TODO Auto-generated method stub
		return seckillDao.queryAll(0, 4);
	}

	@Override
	public Seckill getById(long seckillId) {
		// TODO Auto-generated method stub
		return seckillDao.queryById(seckillId);
	}

	@Override
	public Exposer exportSeckillUrl(long seckillId) {
		//缓存优化
		//1:访问redis
		Seckill seckill = redisDao.getSeckill(seckillId);
		if (seckill == null) {
			//2:访问数据库
			seckill = seckillDao.queryById(seckillId);
			if (seckill == null) {
				return new Exposer(false, seckillId);
			}else {
				//3:放入redis
				redisDao.putSeckill(seckill);
			}
		}

		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		//系统当前时间
		Date nowTime = new Date();
		if (nowTime.getTime() < startTime.getTime() 
				|| nowTime.getTime() > endTime.getTime()) {
			return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
		}
		//转换字符串过程，不可逆
		String md5 = getMD5(seckillId);
		return new Exposer(true, md5, seckillId);
	}
	
	private String getMD5(long seckillId){
		String base = seckillId + "/" + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}


	/**
	 * 使用注解控制事务方法的优点：
	 * 1：开发团队达成一致约定，明确标注事务方法的编程风格。
	 * 2：保证事务方法的执行时间尽可能短，不要穿插其他的网络操作，RPC/HTTP请求或者剥离到事务方法外部
	 * 3：不是所有的方法都需要事务，如只有一条修改操作，或者只读操作，不需要事务控制
	 */
	@Override
	@Transactional
	public SeckillExcution excuteSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {
		// TODO Auto-generated method stub
		if (md5 == null || !md5.equals(getMD5(seckillId))) {
			throw new SeckillException("seckill data rewrite");
		}
		//执行秒杀逻辑：减库存+记录购买记录
		Date nowTime = new Date();
				
		try {
			//记录购买行为
			int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
			if (insertCount <= 0) {
				throw new RepeatKillException("seckill repeated");
			}else {
				//减库存
				int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
				if (updateCount <= 0) {
					throw new SeckillCloseException("seckill is closed");
				} else {
					//秒杀成功
					SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExcution(seckillId, SeckillStateEnum.SUCCESS, successKilled);					
				}
			}
			
			//抛出运行期异常，会触发事务回滚
		} catch(SeckillCloseException e1){
			throw e1;
		} catch (RepeatKillException e2) {
			throw e2;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			//所有编译期异常 转化为运行期异常
			throw new SeckillException("seckill inner error:" + e.getMessage());
		}

	}

	
	//通过存储过程实现秒杀执行
	@Override
	public SeckillExcution excuteSeckillProcedure(long seckillId, long userPhone, String md5){
		// TODO Auto-generated method stub
		if (md5 == null || !md5.equals(getMD5(seckillId))) {
			return new SeckillExcution(seckillId, SeckillStateEnum.DATA_REWRITE);
		}
		Date killTime = new Date();
		Map<String, Object> map =  new HashMap<String, Object>();
		map.put("seckillId", seckillId);
		map.put("phone", userPhone);
		map.put("killTime", killTime);
		map.put("result", null);
		try {
			//执行存储过程，result被赋值
			seckillDao.killByProcedure(map);
			//获取result
			int result = MapUtils.getInteger(map, "result",-2);
			if (result==1) {
				SuccessKilled sk = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
				return new SeckillExcution(seckillId, SeckillStateEnum.SUCCESS, sk);
			}else {
				return new SeckillExcution(seckillId, SeckillStateEnum.stateOf(result));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return new SeckillExcution(seckillId, SeckillStateEnum.INNER_ERROR);
		}
	}
	
}
