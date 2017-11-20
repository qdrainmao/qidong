package com.qidong.service;

import com.qidong.dto.Exposer;
import com.qidong.dto.SeckillExecution;
import com.qidong.entity.Seckill;
import com.qidong.exception.RepeatKillException;
import com.qidong.exception.SeckillCloseException;
import com.qidong.exception.SeckillException;

import java.util.List;

/**
 * 站在使用者角度设计接口
 * 三个方面：方法定义粒度，参数，返回类型
 */
public interface SeckillService {
    List<Seckill> getSeckillList();

    Seckill getById(long seckillId);

    /**
     * 秒杀开启时输出秒杀接口地址，否则输出系统时间和秒杀时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException;

    /**
     * 执行秒杀by存储过程
     * @param seckillId
     * @param userPhone
     * @param md5
     */

    SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5);


}
