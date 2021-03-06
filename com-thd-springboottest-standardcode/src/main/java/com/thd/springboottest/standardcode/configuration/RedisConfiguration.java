package com.thd.springboottest.standardcode.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thd.springboottest.standardcode.utils.FastJsonRedisSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * com.thd.springboottest.standardcode.configuration.RedisConfiguration
 * User: devil13th
 * Date: 2019/11/24
 * Time: 21:49
 * Description: No Description
 */
@Configuration
public class RedisConfiguration {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWaitMillis;

//    @Value("${spring.redis.password}")
//    private String password;

    @Value("${spring.redis.block-when-exhausted}")
    private boolean  blockWhenExhausted;

    @Bean
    public JedisPool redisPoolFactory()  throws Exception{
        log.info("JedisPool注入成功！！");
        log.info("redis地址：" + host + ":" + port);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
        // 是否启用pool的jmx管理功能, 默认true
        jedisPoolConfig.setJmxEnabled(true);
//        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout);
        return jedisPool;
    }



    @Bean("myRedisTemplate")
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
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);



        // 设置redis key的序列化器 ( 一般情况下key使用字符串 ,所以用 stringRedisSerializer)
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        // 设置redis value的序列化器 (上面几种序列化器任选其一,推荐使用fastJsonRedisSerializer (json格式,序列化效率高且业界常用))
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);


        System.out.println(redisTemplate.getKeySerializer().toString());
        System.out.println(redisTemplate.getValueSerializer().toString());



        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


}
