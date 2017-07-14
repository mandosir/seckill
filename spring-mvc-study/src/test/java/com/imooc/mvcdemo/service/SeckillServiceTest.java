package com.imooc.mvcdemo.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.imooc.mvcdemo.dto.Exposer;
import com.imooc.mvcdemo.dto.SeckillExcution;
import com.imooc.mvcdemo.exception.RepeatKillException;
import com.imooc.mvcdemo.exception.SeckillCloseException;
import com.imooc.mvcdemo.model.Seckill;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-service.xml","classpath:spring/spring-dao.xml"})
public class SeckillServiceTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillService seckillService;
	
	@Test
	public void testGetSeckillList() {
		List<Seckill> list = seckillService.getSeckillList();
		logger.info("list={}", list);
	}

	@Test
	public void testGetById() {
		long id = 1000L;
		Seckill seckill = seckillService.getById(id);
		logger.info("seckill={}", seckill);
		
	}

	@Test
	public void testExportSeckillUrl() throws Exception {
		long id = 1001;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		if (exposer.isExposed()) {
			logger.info("exposer={}",exposer);
			long phone=15111975296L;
			String md5 = exposer.getMd5();
			try {
				SeckillExcution excution = seckillService.excuteSeckill(id, phone, md5);
				logger.info("result{}",excution);
				
			} catch(RepeatKillException e){
				logger.error(e.getMessage());
			} catch (SeckillCloseException e) {
				logger.error(e.getMessage());
				// TODO: handle exception
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	@Test
	public void testExcuteSeckill() {
		long seckillId=1002;
		long phone=13654298531L;
		Exposer exposer = seckillService.exportSeckillUrl(seckillId);
		if (exposer.isExposed()) {
			String md5 = exposer.getMd5();
			SeckillExcution excution = seckillService.excuteSeckillProcedure(seckillId, phone, md5);
			logger.info(excution.getStateInfo());
		}
	}

}
