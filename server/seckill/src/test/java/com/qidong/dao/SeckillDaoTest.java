package com.qidong.dao;

import com.qidong.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 配置spring和junit整合,junit启动时加载springIOC容器
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
    @Autowired
    private SeckillDao seckillDao;
    @Test
    public void reduceNumber() throws Exception {
        int i = seckillDao.reduceNumber(1000, new Date());
        System.out.println(i);
        int i1 = seckillDao.reduceNumber(1001, new Date());
        System.out.println(i1);
        int i2 = seckillDao.reduceNumber(1002, new Date());
        System.out.println(i2);
    }

    @Test
    public void queryById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill.getCreateTime());
    }

    @Test
    public void queryAll() throws Exception {
        List<Seckill> seckills = seckillDao.queryAll(0, 100);
        for (Seckill seckill : seckills){
            System.out.println(seckill.getName());
        }
    }

}