package com.qidong.dao;

import com.qidong.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SeckillDao {
    int reduceNumber(@Param("seckillId")long seckillId, @Param("killTime")Date killTime);
    Seckill queryById(Long seckillId);
    //java没有保存形参的记录，queryAll(int offset, int limit) -> queryAll(arg0, arg1)
    List<Seckill>  queryAll(@Param("offset") int offset, @Param("limit") int limit);

    //使用存储过程执行秒杀
    void killByProcedure(Map<String, Object> paramMap);
}
