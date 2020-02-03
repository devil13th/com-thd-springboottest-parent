package com.thd.springboottest.shiro.configuration;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thd.springboottest.shiro.dao.MySessionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
@Configuration
public class RedisConfig {
    private static Logger logger = LoggerFactory.getLogger(MySessionDao.class);
    @Bean("redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);


        /**
         * 原理：
         * 1.序列化器 JdkSerializationRedisSerializer Jackson2JsonRedisSerializer StringRedisSerializer 都实现了RedisSerializer接口
         * 2.接口中的两个方法，序列化和反序列化
         * @Nullable
         * byte[] serialize(@Nullable T var1) throws SerializationException;
         * @Nullable
         * T deserialize(@Nullable byte[] var1) throws SerializationException;
         *
         * 3.Jackson2JsonRedisSerializer内部是通过ObjectMapper来进行序列化和反序列化的
         *   所以使用Jackson2JsonRedisSerializer要设置ObjectMapper
         *
         * 4.自定义RedisSerializer 可以实现RedisSerializer接口 就可以了，例如使用FastJson进行序列化
         */



        // =================== 创建Jackson2JsonRedisSerialize 序列化器  使用ObjectMapper进行序列化和反序列化=================== //
        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        // Jackson2JsonRedisSerializer内部是通过ObjectMapper来进行序列化和反序列化的,所以要设置ObjectMapper
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);


        // =================== 创建 JdkSerializationRedisSerializer 序列化器 使用jdk的serializer进行序列化和反序列化=================== //
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();

        // =================== 创建 StringRedisSerializer 序列化器 使用String进行序列化和反序列化=================== //
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // =================== 创建 FastJsonRedisSerializer 序列化器 使用fastjson进行序列化和反序列化=================== //
        MyFastJsonRedisSerializer  fastJsonRedisSerializer = new MyFastJsonRedisSerializer(Object.class);

        /**
         * keySerializer 字符串 哈希 列表 集合 有序集合的键的序列化策略。
         * valueSerializer 字符串 列表 集合 有序集合的值的序列化策略。
         * hashKeySerializer 哈希的小键的序列化策略
         * hashValueSerializer 哈希的值的序列化策略
         */

        // 设置redis key的序列化器 ( 一般情况下key使用字符串 ,所以用 stringRedisSerializer)
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // 设置redis value的序列化器 (上面几种序列化器任选其一,推荐使用fastJsonRedisSerializer (json格式,序列化效率高且业界常用))
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);


        logger.info(redisTemplate.getKeySerializer().toString());
        logger.info(redisTemplate.getValueSerializer().toString());

        //redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());


        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
