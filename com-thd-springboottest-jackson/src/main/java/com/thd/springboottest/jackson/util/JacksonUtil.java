package com.thd.springboottest.jackson.util;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;

/**
 * com.thd.springboottest.jackson.util.JacksonUtil
 *
 * @author: wanglei62
 * @DATE: 2020/3/31 8:21
 **/
public class JacksonUtil {
    private static Logger log = LoggerFactory.getLogger(JacksonUtil.class);
    private static ObjectMapper objectMapper = new ObjectMapper();
//
//    static {
//        //该属性设置主要是将对象的所有字段全部列入，若有特殊需求，可以进入JsonSerialize.Inclusion该枚举类查看
//        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.ALWAYS);
//
//        //该属性设置主要是取消将对象的时间默认转换timesstamps(时间戳)形式
//        objectMapper.configure(SerializationConfig.Feature.WRI.TE_DATES_AS_TIMESTAMPS, false);
//
//        //该属性设置主要是将忽略空bean转json错误
//        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
//
//        //忽略在json字符串中存在，在java类中不存在字段，防止错误。
//        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//        //所有日期都统一为以下样式：yyyy-MM-dd HH:mm:ss，这里可以不用我的DateTimeUtil.DATE_FORMAT，手动添加
//        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.DATE_FORMAT));
//    }

    /**
     * 封装序列化方法
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String objToJson(T obj) {
        if (obj == null) {
            return null;
        }

        try {
            return obj instanceof String ? (String)obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("obj To json is error", e);
            return null;
        }
    }
    /**
     *  封装反序列化方法
     */
    public static <T> T json2Object(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json) || clazz == null) {
            return null;
        }

        try {
            return clazz.equals(String.class) ? (T)json : objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            log.warn("json To obj is error", e);
            return null;
        }
    }
}
