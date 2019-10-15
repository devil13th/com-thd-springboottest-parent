package com.thd.springboottest.datetimestamp.controller;

import com.thd.springboottest.datetimestamp.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.Date;

/**
 * com.thd.springboottest.datetimestamp.controller.DateTimestampController
 *
 * @author: wanglei62
 * @DATE: 2019/10/15 21:38
 **/
@Controller
@RequestMapping("/datetimestamp")
public class DateTimestampController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 通过配置文件配置全局的日期类型属性序列化格式
     * 通过spring配置文件配置日期类型属性的格式化
     * 参见配置文件: application.yml
     * 配置项: spring.jackson.date-format: yyyy-MM-dd HH:mm:ss
     * 可修改上面配置进行测试,例如 spring.jackson.date-format: yyyy/MM/dd HH:mm:ss
     *
     * 测试url:http://127.0.0.1:8899/thd/datetimestamp/dateSerializerByConf
     *
     */
    @RequestMapping("/dateSerializerByConf")
    @ResponseBody
    public ResponseEntity dateSerializerByConf(){
        logger.info("dateSerializerByConf()");
        DateA entityA = new DateA();
        entityA.setName("devil13th");
        entityA.setDate(new Date());
        ResponseEntity r = ResponseEntity.ok(entityA);
        return r;
    }


    /**
     * 通过配置文件配置全局的日期类型属性序列化格式
     * 通过spring配置文件配置日期类型属性的格式化
     * 参见配置文件: application.yml
     * 配置项: spring.jackson.date-format: yyyy-MM-dd HH:mm:ss
     * 可修改上面配置进行测试,例如 spring.jackson.date-format: yyyy/MM/dd HH:mm:ss
     *
     * 测试url:http://127.0.0.1:8899/thd/datetimestamp/dateSerializerByConf
     *
     */
    @RequestMapping("/timestampSerializerByConf")
    @ResponseBody
    public ResponseEntity timestampSerializerByConf(){
        logger.info("timestampSerializerByConf()");
        TimestampA entityA = new TimestampA();
        entityA.setName("devil13th");
        entityA.setTimestamp(new Timestamp(new Date().getTime()));
        ResponseEntity r = ResponseEntity.ok(entityA);
        return r;
    }


    /**
     * 通过在实体的Date属性上添加@JsonFormat( pattern="yyyy/MM/dd HH:mm:ss",timezone = "GMT+8")进行序列化
     * 配置参见DateB.java
     * 测试 url:http://127.0.0.1:8899/thd/datetimestamp/dateSerializerByJsonformatAnnotation
     * @return
     */
    @RequestMapping("/dateSerializerByJsonformatAnnotation")
    @ResponseBody
    public ResponseEntity dateSerializerByJsonformatAnnotation(){
        logger.info("dateSerializerByJsonformatAnnotation()");
        DateB entityB = new DateB();
        entityB.setName("devil13th");
        entityB.setDate(new Date());
        ResponseEntity r = ResponseEntity.ok(entityB);
        return r;
    }


    /**
     * 通过在实体的Date属性上添加@JsonFormat( pattern="yyyy/MM/dd HH:mm:ss",timezone = "GMT+8")进行序列化
     * 配置参见TimestampB.java
     * 测试 url:http://127.0.0.1:8899/thd/datetimestamp/dateTimestampSerializerByJsonformatAnnotation
     * @return
     */
    @RequestMapping("/dateTimestampSerializerByJsonformatAnnotation")
    @ResponseBody
    public ResponseEntity dateTimestampSerializerByJsonformatAnnotation(){
        logger.info("dateTimestampSerializerByJsonformatAnnotation()");
        TimestampB entityB = new TimestampB();
        entityB.setName("devil13th");
        entityB.setTimestamp(new Timestamp(new Date().getTime()));
        ResponseEntity r = ResponseEntity.ok(entityB);
        return r;
    }

    /**
     * 通过在实体的Date属性上添加@JsonFormat进行序列化
     * 配置参见TimestampC.java
     * 测试 url:http://127.0.0.1:8899/thd/datetimestamp/timestampSerializerByJsonSerializeAnnotation
     * @return
     */
    @RequestMapping("/timestampSerializerByJsonSerializeAnnotation")
    @ResponseBody
    public ResponseEntity timestampSerializerByJsonSerializeAnnotation(){
        logger.info("timestampSerializerByJsonSerializeAnnotation()");
        TimestampC entityC = new TimestampC();
        entityC.setName("devil13th");
        entityC.setTimestamp(new Timestamp(new Date().getTime()));
        ResponseEntity r = ResponseEntity.ok(entityC);
        return r;
    }

    /**
     *  通过使用 @JsonFormat 和 @DateTimeFormat分别对Timestamp和Date进行格式化
     *  参见DateTimeStamp.java
     *  测试 url:
     *  http://127.0.0.1:8899/thd/datetimestamp/dateTimestampFormatByJsonFormatAnnotation?date=2019-01-01 23:23:23&timestamp=2010-02-02 01:01:01&name=devil13th
     *
     *  注意!!!!!!!!!：
     *  使用这种方式必须注释掉Spring配置文件中的spring.mvc.date-format属性 否则会报错,Spring配置优先!!!!
     */
    @RequestMapping("/dateTimestampFormatByJsonFormatAnnotation")
    @ResponseBody
    public ResponseEntity dateTimestampFormatByJsonFormatAnnotation(DateTimeStamp dateTimestamp){
        logger.info("dateTimestampFormatByJsonFormatAnnotation()");
        ResponseEntity r = ResponseEntity.ok(dateTimestamp);
        return r;
    }


}
