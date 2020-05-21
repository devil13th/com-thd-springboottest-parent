package com.thd.springboottest.redisson.cfg;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.thd.springboottest.redisson.cfg.RedissonConfig
 *
 * @author: wanglei62
 * @DATE: 2020/5/21 14:09
 **/
@Configuration
public class RedissonConfig {
    @Autowired
    private RedisProperties redisProperties;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Bean
    public RedissonClient redissonClient(){
        RedissonClient redissonClient;

        Config config = new Config();
        String url = "redis://" + redisProperties.getHost() + ":" + redisProperties.getPort();
        config.useSingleServer().setAddress(url)
                //.setPassword(redisProperties.getPassword())
                .setDatabase(redisProperties.getDatabase());

        try {
            redissonClient = Redisson.create(config);
            return redissonClient;
        } catch (Exception e) {
            logger.error("RedissonClient init redis url:[{}], Exception:", url, e);
            return null;
        }
    }
}
