package com.qidong.service.impl;

import com.qidong.dao.SeckillDao;
import com.qidong.dao.SuccessKilledDao;
import com.qidong.dao.cache.RedisDao;
import com.qidong.dto.Exposer;
import com.qidong.dto.SeckillExecution;
import com.qidong.entity.Seckill;
import com.qidong.entity.SuccessKilled;
import com.qidong.enums.SeckillStatEnum;
import com.qidong.exception.RepeatKillException;
import com.qidong.exception.SeckillCloseException;
import com.qidong.exception.SeckillException;
import com.qidong.service.SeckillService;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SeckillServiceImpl implements SeckillService {
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;
    @Autowired
    private RedisDao redisDao;

    private Logger logger = LoggerFactory.getLogger(SeckillServiceImpl.class);

    //md5盐值字符串，用于混淆MD5
    private final String slat = "asfdsffretrgfsddfDEFE!@$";

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,4);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        //优化点：redis缓存秒杀单
        /*Seckill seckill = seckillDao.queryById(seckillId);
        if (seckill == null){
            return new Exposer(false, seckillId);
        }*/

        Seckill seckill = redisDao.getSeckill(seckillId);
        if (seckill == null){
            seckill = seckillDao.queryById(seckillId);
            if (seckill == null){
                return new Exposer(false,seckillId);
            }else {
                redisDao.putSeckill(seckill);
            }
        }

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()){
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }

        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    @Transactional
    /**
     * 优点：
     * 1 达成一致约定，明确事务方法的编程风格
     * 2 保证事务方法的执行时间尽可能短，不要穿插其他网络操作（redis缓存等RPC/HTTP请求或者玻璃到事务方法外部）
     * 3 不是所有的方法都需要事务，如只有一条修改操作
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if (md5 == null || !md5.equals(getMD5(seckillId))){
            throw new SeckillException("seckill data rewrite");
        }
        try{
            //优化：先插入，再update
            //执行秒杀逻辑
            Date nowTime = new Date();
            //记录购买行为
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            if (insertCount <= 0){
                throw new RepeatKillException("seckill repeated");
            }else {
                int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
                if (updateCount <= 0){
                    throw new SeckillCloseException("seckill is closed");
                }else {
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS,successKilled);
                }
            }
        } catch (SeckillCloseException e){
            throw e;
        } catch (RepeatKillException e){
            throw e;
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            //把编译期异常转换为运行期异常，这样spring声明事务能做rollback
            throw new SeckillException("seckill inner error：" + e.getMessage());
        }
    }

    public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5){
        if (md5 == null || !md5.equals(getMD5(seckillId))){
            return new SeckillExecution(seckillId, SeckillStatEnum.DATA_REWRITE);
        }
        Date killTime = new Date();
        Map<String, Object> map = new HashMap();
        map.put("seckillId", seckillId);
        map.put("userPhone", userPhone);
        map.put("killTime", killTime);
        map.put("result", null);
        try {
            seckillDao.killByProcedure(map);
            Integer result = MapUtils.getInteger(map, "result", -2);//如果没有值就-2表示出错了
            if (result == 1){
                SuccessKilled sk = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, sk);
            }else {
                return new SeckillExecution(seckillId, SeckillStatEnum.stateOf(result));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
        }
    }

    private String getMD5(long seckillId){
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());//不可逆
        return md5;
    }

}
