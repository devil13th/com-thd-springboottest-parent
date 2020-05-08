package com.thd.springboottest.redis.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * com.thd.springboottest.rediscache.utils.FastJsonRedisSerializer
 *
 * @author: wanglei62
 * @DATE: 2019/11/7 11:20
 **/
public class MyFastJsonRedisSerializer<T> extends FastJsonRedisSerializer<T> implements RedisSerializer<T> {
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private Class<T> clazz;
    public MyFastJsonRedisSerializer(Class<T> clazz) {
        super(clazz);
        this.clazz = clazz;
    }

    public byte[] serialize(T t) throws SerializationException {
        if (null == t) {
            return new byte[0];
        }
//        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
        return JSON.toJSONString(t).getBytes(DEFAULT_CHARSET);
    }

    public T deserialize(byte[] bytes) throws SerializationException {
        if (null == bytes || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return (T) JSON.parseObject(str, clazz);
    }

}
