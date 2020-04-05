package com.thd.springboottest.requestparameter.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.thd.springboottest.requestparameter.converters.DateConverter;
import com.thd.springboottest.requestparameter.converters.TimestampConverter;
import com.thd.springboottest.requestparameter.serializer.JsonDateDeserializer;
import com.thd.springboottest.requestparameter.serializer.JsonDateSerializer;
import com.thd.springboottest.requestparameter.serializer.JsonTimestampDeserializer;
import com.thd.springboottest.requestparameter.serializer.JsonTimestampSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.sql.Timestamp;
import java.util.Date;

/**
 * com.thd.springboottest.datetimestamp.configuration.DateConfig
 *
 * @author: wanglei62
 * @DATE: 2020/4/3 8:14
 **/
@Configuration
public class DateTimestampConfig {



    /**
     * Date转换器, 用于转换@RequestParam和@PathVariable修饰的参数
     */
    @Bean
    public Converter<String, Date> dateConverter(){
        return new DateConverter();
    }

    /**
     * 时间戳转换器 , 用于转换@RequestParam和@PathVariable修饰的参数
     * @return
     */
    @Bean
    public Converter<String, Timestamp> timestampConverter(){
        return new TimestampConverter();
    }


    /**
     * 处理jackson反序列化转换器，
     * 用于转换Post请求体的json以及我们的对象序列化为返回响应的json
     * 使用@RequestBody注解的对象中的Date类型将从这里被转换
     */
    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
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
        return objectMapper;
    }

}
