package com.qidong.service;

import com.qidong.dto.Exposer;
import com.qidong.dto.SeckillExecution;
import com.qidong.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> seckillList = seckillService.getSeckillList();
        logger.info("list={}", seckillList);
    }

    @Test
    public void getById() throws Exception {
        Seckill seckill = seckillService.getById(1000L);
        logger.info(seckill.getName());
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        Exposer exposer = seckillService.exportSeckillUrl(1001);
        logger.info(exposer.toString());
    }

    @Test
    public void executeSeckill() throws Exception {
        SeckillExecution seckillExecution = seckillService.executeSeckill(1001, 18616512283L, "e4d4d92fe917b975f1484fec68a10ebd");
        logger.info(seckillExecution.getStateInfo());
    }

    @Test
    public void executeSeckillProcedure(){
        Exposer exposer = seckillService.exportSeckillUrl(1003);
        if (exposer.isExposed()){
            String md5 = exposer.getMd5();
            SeckillExecution ex = seckillService.executeSeckillProcedure(1003, 11111111111L, md5);
            logger.info(ex.getStateInfo());
        }
    }


}