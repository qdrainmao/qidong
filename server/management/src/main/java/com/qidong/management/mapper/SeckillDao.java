package com.qidong.management.mapper;


import com.qidong.management.vo.entity.Seckill;

import java.util.Date;
import java.util.List;

public interface SeckillDao {
    int reduceNumber(long seckillId, Date killTime);
    Seckill queryById(Long seckillId);
    List<Seckill>  queryAll(int offset, int limit);
}
