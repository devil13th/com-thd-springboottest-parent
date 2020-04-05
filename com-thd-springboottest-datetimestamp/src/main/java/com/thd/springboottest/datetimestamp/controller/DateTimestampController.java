package com.thd.springboottest.datetimestamp.controller;

import com.thd.springboottest.datetimestamp.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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



    // ============================================ 参数中的转换 ============================================== //



    @RequestMapping("/dateFormatByParameter")
    @ResponseBody
    // http://127.0.0.1:8899/thd/datetimestamp/dateFormatByParameter?d=2019_11_21 2003:03:03
    // http://127.0.0.1:8899/thd/datetimestamp/dateFormatByParameter?d=2019_11_21
    // http://127.0.0.1:8899/thd/datetimestamp/dateFormatByParameter?d=1585875000
    public ResponseEntity dateFormatByParameter(@RequestParam  Date d){
        logger.info("dateFormatByParameter()");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(d));
        return ResponseEntity.ok(d);
        /**
         * 请求：http://127.0.0.1:8899/thd/datetimestamp/dateFormatByConfiguration?d=2019_11_21 2003:03:03
         *      2019_11_21 2003:03:03请求已经通过DateConverter转换成日期类型
         *
         * 响应：格式化是使用了jackson，调用了自定义的JsonDateSerializer进行的序列化
         * "2019||11||21 03:03:03"
         */

    }


    @RequestMapping("/dateFormatByPath/{d}")
    @ResponseBody
    // http://127.0.0.1:8899/thd/datetimestamp/dateFormatByPath/2019_11_21 2003:03:03
    // http://127.0.0.1:8899/thd/datetimestamp/dateFormatByPath/2019_11_21
    // http://127.0.0.1:8899/thd/datetimestamp/dateFormatByPath/1585875000
    public ResponseEntity dateFormatByPath(@PathVariable Date d){
        logger.info("dateFormatByPath()");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(d));
        return ResponseEntity.ok(d);
        /**
         * 请求：http://127.0.0.1:8899/thd/datetimestamp/dateFormatByConfiguration?d=2019_11_21 2003:03:03
         *      2019_11_21 2003:03:03请求已经通过DateConverter转换成日期类型
         *
         * 响应：格式化是使用了jackson，调用了自定义的JsonDateSerializer进行的序列化
         * "2019||11||21 03:03:03"
         */

    }


    @ResponseBody
    @RequestMapping(value="/dateFormatByObjectParameter",method=RequestMethod.GET)
    // url中?后的参数直接转对象接收
    //url : http://127.0.0.1:8899/thd/datetimestamp/dateFormatByObjectParameter?birthday=2019_11_21 2003:03:03&name=devil13th
    public MyBean dateFormatByObjectParameter(MyBean person ){
        System.out.println(person);
        return person;

        /**
         * 请求：http://127.0.0.1:8899/thd/datetimestamp/dateFormatByObjectParameter?birthday=2019_11_21 2003:03:03&name=devil13th
         *      2019_11_21 2003:03:03请求已经通过DateConverter转换成日期类型
         *
         * 响应：格式化是使用了jackson，调用了自定义的JsonDateSerializer进行的序列化
         * {"name":"devil13th","createTime":null,"birthday":"2020||02||12 11:03:03"}
         */
    }






    @RequestMapping("/timestampFormatByParameter")
    @ResponseBody
    // 默认的时间戳格式 http://127.0.0.1:8899/thd/datetimestamp/timestampFormatByParameter?d=2015-12-12 2023:23:23
    // 通过自定义时间戳的格式 http://127.0.0.1:8899/thd/datetimestamp/timestampFormatByParameter?d=1585877000
    public ResponseEntity timestampFormatByParameter(Timestamp d){
        logger.info("timestampFormatByParameter()");
        /**
         * 请求：http://127.0.0.1:8899/thd/datetimestamp/timestampFormatByConfiguration?d=1585877000
         *      1585877000请求已经通过TimestampConverter转换成日期类型
         *
         * 响应：格式化是使用了jackson，调用了自定义的JsonTimestampSerializer进行的序列化
         * "1970||01||19 16:31:17"
         */
        return ResponseEntity.ok(d);


    }









    @RequestMapping("/timestampFormatForRequestBody")
    @ResponseBody

    /*
url : post请求 http://127.0.0.1:8899/thd/datetimestamp/timestampFormatForRequestBody

请求的body内容：
{
	"name":"devil13th",
	"birthday":"2015_11_21 10:22:12",
	"createTime":"2012&&10&&10 10$$11$$22"
}
     */
    public ResponseEntity timestampFormatForRequestBody(@RequestBody MyBean myBean){
        logger.info("timestampFormatForRequestBody()");
        System.out.println(myBean);
        return ResponseEntity.ok(myBean);

        //响应内容：
        /*
        {
            "name":"devil13th",
            "birthday":"2015_11_21 10:22:12",
            "createTime":"2012&&10&&10 10$$11$$22"
        }
        json的序列化是用的jackson中设置的自定义序列化器

        */
    }


}
