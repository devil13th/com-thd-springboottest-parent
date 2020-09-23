package com.thd.springboottest.shiro.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * com.thd.springboottest.rediscache.utils.FastJsonRedisSerializer
 *
 * @author: wanglei62
 * @DATE: 2019/11/7 11:20
 **/
public class MyShiroRedisSerializer<T> implements RedisSerializer<T> {
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private Class<T> clazz;
    public MyShiroRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (null == t) {
            return new byte[0];
        }

        ObjectMapper mapper = new ObjectMapper();

        String json = null;
        try {
            json = mapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Jackson Serialize failed");
        }

        //return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
        return json.getBytes(DEFAULT_CHARSET);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (null == bytes || bytes.length <= 0) {
            return null;
        }
        String json = new String(bytes, DEFAULT_CHARSET);

        ObjectMapper mapper = new ObjectMapper();
        try {
            return (T) mapper.readValue(json,Object.class);
        } catch (IOException e) {
            throw new RuntimeException("Jackson deserialize failed");
        }
    }

}
