package com.thd.springboottest.redisson.utils;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * com.thd.springboottest.redisson.utils.RedissLockUtil
 *
 * @author: wanglei62
 * @DATE: 2020/3/13 12:27
 **/
@Component
public class DistributeLockUtil {
    @Autowired
    private  RedissonClient redissonClient;

    public void setRedissonClient(RedissonClient locker) {
        redissonClient = locker;
    }

    private Logger logger = LoggerFactory.getLogger(DistributeLockUtil.class);


    // 加锁
    public Boolean lockFor10S(String lockName) {
        try {
            if (redissonClient == null) {
                logger.info("DistributedRedisLock redissonClient is null");
                return false;
            }

            RLock lock = redissonClient.getLock(lockName);
            // 锁10秒后自动释放，防止死锁
            lock.lock(10, TimeUnit.SECONDS);

            logger.info("Thread [{}] DistributedRedisLock lock [{}] success", Thread.currentThread().getName(), lockName);
            // 加锁成功
            return true;
        } catch (Exception e) {
            logger.error("DistributedRedisLock lock [{}] Exception:", lockName, e);
            return false;
        }
    }

    // 释放锁
    public Boolean unlockFor10S(String lockName) {
        try {
            if (redissonClient == null) {
                logger.info("DistributedRedisLock redissonClient is null");
                return false;
            }

            RLock lock = redissonClient.getLock(lockName);
            lock.unlock();
            logger.info("Thread [{}] DistributedRedisLock unlock [{}] success", Thread.currentThread().getName(), lockName);
            // 释放锁成功
            return true;
        } catch (Exception e) {
            logger.error("DistributedRedisLock unlock [{}] Exception:", lockName, e);
            return false;
        }
    }



}
