package com.imooc.mvcdemo.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.imooc.mvcdemo.model.Seckill;

public interface SeckillDao {
	
	/**
	 * 减库存
	 * @param seckillId
	 * @param killTime
	 * @return 影响行数>1，表示更新的记录行数
	 */
	int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);
	
	/**
	 * 根据id查询秒杀商品
	 * @param seckillId
	 * @return
	 */
	Seckill queryById(long seckillId);
	
	/**
	 * 根据偏移量查询秒杀列表
	 * @param offet
	 * @param limit
	 * @return
	 */
	List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
	
	/**
	 * 使用存储过程执行秒杀
	 * @param paramMap
	 */
	void killByProcedure(Map<String, Object> paramMap);
}
