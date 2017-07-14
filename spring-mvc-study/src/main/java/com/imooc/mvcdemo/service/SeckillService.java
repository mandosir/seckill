package com.imooc.mvcdemo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.imooc.mvcdemo.dto.Exposer;
import com.imooc.mvcdemo.dto.SeckillExcution;
import com.imooc.mvcdemo.exception.RepeatKillException;
import com.imooc.mvcdemo.exception.SeckillCloseException;
import com.imooc.mvcdemo.exception.SeckillException;
import com.imooc.mvcdemo.model.Seckill;

/**
 * 业务接口：站在“使用者”角度設計接口
 * 三個方面：方法定义粒度，參數，返回類型（return 類型/異常）
 * @author 123
 *
 */
@Service
public interface SeckillService {

	/**
	 * 查询所有秒杀列表
	 * @return
	 */
	List<Seckill> getSeckillList();
	
	/**
	 * 查询单个秒杀列表
	 * @param seckillId
	 * @return
	 */
	Seckill getById(long seckillId);
	
	/**
	 * 秒杀开启时输出秒杀接口地址，
	 * 否则输出系统时间和秒杀时间
	 * @param seckillId
	 */
	Exposer exportSeckillUrl(long seckillId);
	
	/**
	 * 执行秒杀操作
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 */
	SeckillExcution excuteSeckill(long seckillId, long userPhone, String md5)
		throws SeckillException, RepeatKillException, SeckillCloseException;
	
	/**
	 * 执行秒杀操作by 存储过程
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 */
	SeckillExcution excuteSeckillProcedure(long seckillId, long userPhone, String md5);	
}
