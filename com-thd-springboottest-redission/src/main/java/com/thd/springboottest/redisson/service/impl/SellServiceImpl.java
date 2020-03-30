package com.thd.springboottest.redisson.service.impl;

import com.thd.springboottest.redisson.utils.RedissLockUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * com.thd.springboottest.redisson.service.impl.SellService
 *
 * @author: wanglei62
 * @DATE: 2020/3/13 12:38
 **/
public class SellServiceImpl {
    @Override
    @Transactional
    public R startSeckilRedisLock(long seckillId, long userId) {
        boolean res=false;
        try {
            res = RedissLockUtil.tryLock(seckillId+"", TimeUnit.SECONDS, 3, 20);
            if(res){
                Seckill seckill = seckillService.selectById(seckillId);
                if(seckill.getNumber()>0){
                    SuccessKilled killed = new SuccessKilled();
                    killed.setSeckillId(seckillId);
                    killed.setUserId(userId);
                    killed.setState(0);
                    killed.setCreateTime(new Timestamp(new Date().getTime()));
                    successKilledService.save(killed);
                    Seckill temp = new Seckill();
                    temp.setId(seckillId);
                    temp.setNumber(0);
                    seckillMapper.updateNum(temp);
                }else{
                    return R.fail();
                }
            }else{
                return R.fail();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(res){//释放锁
                RedissLockUtil.unlock(seckillId+"");
            }
        }
        return R.ok();
    }
}
