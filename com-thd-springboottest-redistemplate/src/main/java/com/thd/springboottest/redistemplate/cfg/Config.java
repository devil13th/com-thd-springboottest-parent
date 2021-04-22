package com.thd.springboottest.redistemplate.cfg;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.thd.springboottest.redistemplate.bean.Item;
import com.thd.springboottest.redistemplate.serializer.*;
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
import org.springframework.http.MediaType;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author devil13th
 **/
@Configuration
public class Config {
    private Logger log = LoggerFactory.getLogger(this.getClass());


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

        // JdkSerializationRedisSerializer  JDK序列化器
         JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();


        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class); // 默认的使用JdkSerializationRedisSerializer


        ObjectMapper objectMapper = new ObjectMapper();


        /**
         * 特别注意这个配置
         * 此项必须配置，否则反序列化会报java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to XXX
         * 因为反序列化时候都是转换成Map类型
         *
         * 加了此配置，序列化的时候会将数据类型写到json中,才可以进行正常json转对象的反序列化
         *
         * 加了改配置，如果有自定义的序列化器，不仅要重写JsonSerializer.serialize方法还要重写JsonSerializer.serializeWithType方法
         * 否则会报错 Type id handling not implemented for type XXX
         */
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);




        // null属性不进行序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 设置在反序列化时忽略在JSON字符串中存在，而在Java中不存在的属性
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 设置日期格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));

        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        //Date序列化和反序列化
        javaTimeModule.addSerializer(Date.class,new JsonDateSerializer());
        javaTimeModule.addDeserializer(Date.class,new JsonDateDeserializer());
        // timestamp的序列化和反序列话
        javaTimeModule.addSerializer(Timestamp.class,new JsonTimestampSerializer());
        javaTimeModule.addDeserializer(Timestamp.class,new JsonTimestampDeserializer());

        objectMapper.registerModule(javaTimeModule);

        // 创建某种自定义类型转换的模块
        SimpleModule itemModule = new SimpleModule();
        //设置Item对象的 序列化器和反序列化器
        itemModule.addSerializer(Item.class,new ItemSerializer());
        itemModule.addDeserializer(Item.class,new ItemDeserializer());
        // 注册模块到objectMapper中
        // objectMapper.registerModule(itemModule);


        // Jackson2JsonRedisSerializer内部是通过ObjectMapper来进行序列化和反序列化的,所以要设置ObjectMapper
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);




        // =================== 创建 StringRedisSerializer 序列化器 使用String进行序列化和反序列化=================== //
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // key采用String的序列化方式
        redisTemplate.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
        // hash的value序列化方式采用jackson
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.setHashValueSerializer(jdkSerializationRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;

    }




    @Bean("normalRedisTemplate")
    public RedisTemplate<Object, Object> normalRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // =================== 创建Jackson2JsonRedisSerialize 序列化器  使用ObjectMapper进行序列化和反序列化=================== //

       // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class); // 默认的使用JdkSerializationRedisSerializer
        ObjectMapper objectMapper = new ObjectMapper();


        /**
         * 特别注意这个配置
         * 此项必须配置，否则反序列化会报java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to XXX
         * 因为反序列化时候都是转换成Map类型
         *
         * 加了此配置，序列化的时候会将数据类型写到json中,才可以进行正常json转对象的反序列化
         *
         * 加了改配置，如果有自定义的序列化器，不仅要重写JsonSerializer.serialize方法还要重写JsonSerializer.serializeWithType方法
         * 否则会报错 Type id handling not implemented for type XXX
         */
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        // 解决localdate反序列化报错
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());

        // null属性不进行序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 设置在反序列化时忽略在JSON字符串中存在，而在Java中不存在的属性
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

        // Jackson2JsonRedisSerializer内部是通过ObjectMapper来进行序列化和反序列化的,所以要设置ObjectMapper
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // =================== 创建 StringRedisSerializer 序列化器 使用String进行序列化和反序列化=================== //
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        redisTemplate.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
        // hash的value序列化方式采用jackson
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.setHashValueSerializer(jdkSerializationRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;

    }

}
