package com.thd.springboottest.jsonmessageconverters.configuration;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * com.thd.springboottest.jsonmessageconverters.configuration.WebMvcConfig
 *
 * @author: wanglei62
 * @DATE: 2019/12/9 23:22
 **/

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 利用fastjson替换掉jackson,且解决中文乱码问题
     * @param  converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        System.out.println("================ 构建 jsonMessageConverter =====================");
        //1.构建了一个HttpMessageConverter  FastJson   消息转换器
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //2.定义一个配置，设置编码方式，和格式化的形式
        FastJsonConfig fastJsonConfig = new FastJsonConfig();

        //3.设置成了PrettyFormat格式
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        //4.处理中文乱码问题
        List<MediaType> fastMediaTypes =  new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);

        //5.将fastJsonConfig加到消息转换器中
        fastConverter.setFastJsonConfig(fastJsonConfig);
        //converters.add(fastConverter);
        /**
         * SpringBoot 2.0.1版本中加载WebMvcConfigurer
         * 的顺序发生了变动，故需使用converters.add(0, converter);指定
         * FastJsonHttpMessageConverter
         * 在converters内的顺序，否则在SpringBoot 2.0.1及之后的版本中将优先使用Jackson处理
         */
        converters.add(0,fastConverter);
    }


    /**
     * 配置FastJson为Spring Boot默认JSON解析框架
     * @return  HttpMessageConverters
     */
//    @Bean
//    public HttpMessageConverters fastJsonHttpMessageConverters() {
//        // 1.定义一个converters转换消息的对象
//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//        // 2.添加fastjson的配置信息，比如: 是否需要格式化返回的json数据
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
//        // 3.在converter中添加配置信息
//        fastConverter.setFastJsonConfig(fastJsonConfig);
//        // 4.将converter赋值给HttpMessageConverter
//        HttpMessageConverter<?> converter = fastConverter;
//        // 5.返回HttpMessageConverters对象
//        return new HttpMessageConverters(converter);
//    }

}
