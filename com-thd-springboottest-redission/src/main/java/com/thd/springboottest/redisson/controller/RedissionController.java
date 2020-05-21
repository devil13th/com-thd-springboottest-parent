package com.thd.springboottest.redisson.controller;

import com.thd.springboottest.redisson.utils.DistributeLockUtil;
import com.thd.springboottest.redisson.utils.RedissLockUtil;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * com.thd.springboottest.redisson.controller.RedissionController
 *
 * @author: wanglei62
 * @DATE: 2020/5/21 14:38
 **/
@RestController
@RequestMapping("/redission")
public class RedissionController {
    @Autowired
    private DistributeLockUtil distributeLockUtil;
    @Autowired
    private RedissonClient redissonClient;


    @GetMapping("/testList")
    public ResponseEntity testList() {
        RList rlist = redissonClient.getList("list");
        rlist.add(Math.random());


        List l = rlist.readAll();
        return ResponseEntity.ok(l);
    }


    @GetMapping("/testMap")
    public ResponseEntity testMap() {
        RMap map = redissonClient.getMap("map");
        map.put(Math.random(),Math.random());

        Map m = map.readAllMap();
        return ResponseEntity.ok(m);
    }

    @GetMapping("/testMapCache")
    public ResponseEntity testMapCache() {
        RMapCache mapCache = redissonClient.getMapCache("mapcache");
        mapCache.put(Math.random(),Math.random(),10, TimeUnit.SECONDS);
        Map m = mapCache.readAllMap();


        return ResponseEntity.ok(m);
    }

    @GetMapping("/testSet")
    public ResponseEntity testSet() {
        RSet set = redissonClient.getSet("set");
        set.add(Math.random());

        set.add(1);
        Set s = set.readAll();

        return ResponseEntity.ok(s);
    }


    @GetMapping("/testSetCache")
    public ResponseEntity testSetCache() {
        RSetCache setCache = redissonClient.getSetCache("setCache");
        setCache.add(Math.random(),5,TimeUnit.SECONDS);

        setCache.add(1);
        Set s = setCache.readAll();

        return ResponseEntity.ok(s);
    }



    // 测试分布式锁
    // url : http://127.0.0.1:8899/thd/redission/testLock
    @GetMapping("/testLock")
    public ResponseEntity testLock() {
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    distributeLockUtil.lockFor10S("LOCK");
                }
            }).start();
        }
        return ResponseEntity.ok("SUCCESS");
    }




}
