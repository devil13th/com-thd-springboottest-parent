package com.thd.springboottest.rediscache.cfg;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thd.springboottest.rediscache.utils.FastJsonRedisSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author devil13th
 **/
@Configuration
public class CfgBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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



    //缓存管理器
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {


        //JacksonJsonRedisSerializer和GenericJackson2JsonRedisSerializer，两者都能系列化成json，
        //但是后者会在json中加入@class属性，类的全路径包名，方便反系列化。
        //前者如果存放了List则在反系列化的时候如果没指定TypeReference(指定泛型类型)则会报错java.util.LinkedHashMap cannot be cast to ...



        //Jackson2JsonRedisSerializer<Person> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Person>(Person.class);
        //RedisSerializationContext.SerializationPair<Person> jsonPair = RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer);

        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        RedisSerializationContext.SerializationPair<Object> jsonPair = RedisSerializationContext.SerializationPair.fromSerializer(genericJackson2JsonRedisSerializer);

        RedisSerializationContext.SerializationPair<String> stringPair = RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer());



        //使用默认缓存配置，并且该配置,  设置key的序列化器 - key转换为字符串 ,设置value的序列化器- value转换为java的序列化
        RedisCacheConfiguration defaultCacheConfig=RedisCacheConfiguration.defaultCacheConfig();


        //使用默认缓存配置，并且该配置  , 设置key的序列化器 - key转换为字符串 ,设置value的序列化器- value转换为json
        // RedisCacheConfiguration defaultCacheConfig=RedisCacheConfiguration.defaultCacheConfig().serializeKeysWith(keyPair).serializeValuesWith(valuePair);


        // 返回一个key的序列化器 - key转换为字符串
        //defaultCacheConfig.serializeKeysWith(keyPair);
        // 返回一个value的序列化器- value转换为json
        //defaultCacheConfig.serializeValuesWith(valuePair);



        // 设置一个初始化的缓存空间set集合    例如person是一个缓存空间名字(可以理解为名为perosn的一组缓存), user也是一个缓存空间的名字
        // person中可以通过id属性的值定义缓存的key
        // user中可以通过id属性的值定义缓存的key
        //cacheNames的使用参见RedisServiceImpl.java中 @CachePut @CacheEvict @Cacheable注释的value属性
        Set<String> cacheNames =  new HashSet<>();
        cacheNames.add("person");
        cacheNames.add("personList");

        // 对每个缓存空间应用不同的配置  可以为每一个缓存空间定义其使用的缓存配置(比如缓存配置中的key的序列化器，value的序列化器,缓存时间等内容)
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        // defaultCacheConfig.serializeKeysWith和serializeValuesWith方法均会返回一个新的RedisCacheConfiguration对象
        //这个配置定义了key和value的序列化器
        configMap.put("person", defaultCacheConfig.serializeKeysWith(stringPair).serializeValuesWith(jsonPair));
        // defaultCacheConfig.entryTtl(Duration.ofSeconds(120)) 会返回一个新的RedisCacheConfiguration对象
        //这个配置定义了缓存时间为120秒
        configMap.put("personList", defaultCacheConfig.serializeKeysWith(stringPair).serializeValuesWith(jsonPair).entryTtl(Duration.ofSeconds(120)));

        //可根据不同的缓存名称设置不同的缓存配置(上面cacheNames(缓存名称)中的每个元素与configMap(缓存配置)中的每个元素相对应)
        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)     // 使用自定义的缓存配置初始化一个cacheManager
                .initialCacheNames(cacheNames)  // 注意这两句的调用顺序，一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
                .withInitialCacheConfigurations(configMap)
                .build();
        return cacheManager;
    }
}
