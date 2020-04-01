package com.thd.springboottest.jackson.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.thd.springboottest.jackson.bean.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Controller
@RequestMapping("/jackson")
public class JacksonController {


    /**
     * 基本的序列化
     * Date和Timestamp被序列化成时间戳
     * java对象转json
     * @return
     * @throws Exception
     */
    @RequestMapping("/basicSerialize")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/jackson/basicSerialize
    public ResponseEntity basicSerialize() throws Exception{
        TestBean01 tb01 = new TestBean01();
        tb01.setName("thd");
        tb01.setAge(5);
        tb01.setBirthday(new Date());
        tb01.setCreateTime(new Timestamp(System.currentTimeMillis()));
        //先创建objmapper的对象
        ObjectMapper mapper=new ObjectMapper();
        // 序列化
        String str = mapper.writeValueAsString(tb01);
        System.out.println(String.format("TestBean01:%s",tb01));
        System.out.println(String.format("序列化TestBean01:%s",str));
        //输出：
        //TestBean01:TestBean01{name='thd', age=5, birthday=2020-04-01 11:00:00, createTime=2020-04-01 11:00:00}
        //序列化TestBean01:{"name":"thd","age":5,"birthday":1585710000639,"createTime":1585710000639}
        /**
         * 结论
         * 可以看出Date和Timestamp默认序列化成时间戳
         */
        return ResponseEntity.ok( str);
    }



    /**
     * 序列化日期的格式方法一
     * 在实体类中的属性上添加@JsonFormat注释解决日期序列化的格式问题
     * 例如 @JsonFormat(pattern = "yyyy**MM**dd HH:mm:ss", timezone = "GMT+8")
     *
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/serializeDate01")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/jackson/serializeDate01
    public ResponseEntity serializeDate01() throws Exception{
        TestBean02 tb = new TestBean02();
        tb.setName("thd");
        tb.setAge(5);
        tb.setBirthday(new Date());
        tb.setCreateTime(new Timestamp(System.currentTimeMillis()));
        //先创建objmapper的对象
        ObjectMapper mapper=new ObjectMapper();
        // 序列化
        String str = mapper.writeValueAsString(tb);
        System.out.println(String.format("TestBean01:%s",tb));
        System.out.println(String.format("序列化TestBean01:%s",str));
        //输出：
//        TestBean01:TestBean01{name='thd', age=5, birthday=2020-04-01 11:08:36, createTime=2020-04-01 11:08:36}
//        序列化TestBean01:{"name":"thd","age":5,"birthday":"2020**04**01 11:08:36","createTime":"2020**04**01 11:08:36"}


        /**
         * 结论：
         * 1.在实体类上的Date或Timestamp类型的属性上添加@JsonFormat(pattern = "yyyy**MM**dd HH:mm:ss", timezone = "GMT+8")
         * 可以设置序列化的日期格式
         */
        return ResponseEntity.ok( str);
    }



    /**
     * 序列化日期的格式方法二
     * 在实体类中的属性上添加@JsonSerialize注释解决日期序列化的格式问题
     * 例如 @JsonSerialize(using = JsonDateSerializer.class)
     *
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/serializeDate02")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/jackson/serializeDate02
    public ResponseEntity serializeDate02() throws Exception{
        TestBean03 tb = new TestBean03();
        tb.setName("thd");
        tb.setAge(5);
        tb.setBirthday(new Date());
        tb.setCreateTime(new Timestamp(System.currentTimeMillis()));
        //先创建objmapper的对象
        ObjectMapper mapper=new ObjectMapper();
        // 序列化
        String str = mapper.writeValueAsString(tb);
        System.out.println(String.format("TestBean03:%s",tb));
        System.out.println(String.format("序列化TestBean01:%s",str));
        //输出：
//        TestBean03:TestBean03{name='thd', age=5, birthday=2020-04-01 11:22:17, createTime=2020-04-01 11:22:17}
//        序列化TestBean01:{"name":"thd","age":5,"birthday":"2020-04-01","createTime":"2020||04||01 11:22:17"}

        /**
         * 结论：
         * 1.在实体类上的Date或Timestamp类型的属性上添加@JsonSerialize(using = xxxx.class)
         * 2.然后自己定义该序列化器
         * 可以设置序列化的日期格式
         */
        return ResponseEntity.ok( str);
    }


    /**
     * 序列化日期的格式方法三
     * java对象转json日期属性的处理
     * 优先类中的注释@Annotation处理日期类型属性,然后再是配置mapper.setDateFormat
     * @return
     * @throws Exception
     */
    @RequestMapping("/serializeDate03")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/jackson/serializeDate03
    public ResponseEntity serializeDate03() throws Exception{
        TestBean01 tb = new TestBean01();
        tb.setName("thd");
        tb.setAge(5);
        tb.setBirthday(new Date());
        tb.setCreateTime(new Timestamp(System.currentTimeMillis()));

        ObjectMapper mapper=new ObjectMapper();//先创建objmapper的对象
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy//MM//dd HH:mm:ss");
        // 设置日期序列化的格式, 优先级低于Bean属性的@Annotation注释
        mapper.setDateFormat(formatter);
        String str = mapper.writeValueAsString(tb);
        System.out.println(String.format("TestBean01:%s",tb));
        System.out.println(String.format("序列化TestBean01:%s",str));
        //输出：
//        TestBean03:TestBean01{name='thd', age=5, birthday=2020-04-01 11:27:31, createTime=2020-04-01 11:27:31}
//        序列化TestBean01:{"name":"thd","age":5,"birthday":"2020//04//01 11:27:31","createTime":"2020//04//01 11:27:31"}

        /**
         * 结论
         * 可以使用ObjectMapper.setDateFormat()设置日期格式化规则
         * 前提是实体没有添加前面例子中提到的注释(@JsonFormat或@JsonSerialize)
         */
        return ResponseEntity.ok( str);
    }




    /**
     * 序列化日期的格式方法四
     * 使用ObjectMapper.registerModule()设置日期格式化规则
     * @return
     * @throws Exception
     */
    @RequestMapping("/serializeDate04")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/jackson/serializeDate04
    public ResponseEntity serializeDate04() throws Exception{
        TestBean01 tb = new TestBean01();
        tb.setName("thd");
        tb.setAge(5);
        tb.setBirthday(new Date());
        tb.setCreateTime(new Timestamp(System.currentTimeMillis()));

        ObjectMapper mapper=new ObjectMapper();//先创建objmapper的对象


        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(Date.class, new JsonDateSerializer());
        timeModule.addSerializer(Timestamp.class, new JsonTimestampSerializer());
        mapper.registerModule(timeModule);


        String str = mapper.writeValueAsString(tb);
        System.out.println(String.format("TestBean01:%s",tb));
        System.out.println(String.format("序列化TestBean01:%s",str));
        //输出：
//        TestBean01:TestBean01{name='thd', age=5, birthday=2020-04-01 11:33:00, createTime=2020-04-01 11:33:00}
//        序列化TestBean01:{"name":"thd","age":5,"birthday":"2020-04-01","createTime":"2020||04||01 11:33:00"}
        /**
         * 结论
         * 1.可以使用ObjectMapper.registerModule()设置日期格式化规则
         * 2.要提前编写序列化器
         *
         */
        return ResponseEntity.ok( str);
    }

    /**
     * 反序列化例子
     * 时间戳类型可以自动转换成Date或Timestamp
     */
    @RequestMapping("basicDeserialize")
    // url : http://127.0.0.1:8899/thd/jackson/basicDeserialize
    public ResponseEntity basicDeserialize() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String json = "{\"name\":\"thd\",\"age\":5,\"birthday\":1585710000639,\"createTime\":1585710000639}";
        TestBean01 tb = mapper.readValue(json, TestBean01.class);
        System.out.println(tb);
        // 输出
        // TestBean01{name='thd', age=5, birthday=2020-04-01 11:00:00, createTime=2020-04-01 11:00:00}

        /**
         * 结论：
         * 时间戳类型可以自动转换成Date或Timestamp
         */
        return ResponseEntity.ok(tb);
    }



    /**
     * 反序列化日期方法一
     * 在实体类上的Date或Timestamp类型的属性上添加@JsonFormat(pattern = "yyyy**MM**dd HH:mm:ss", timezone = "GMT+8")
     */
    @RequestMapping("deserializeDate01")
    // url : http://127.0.0.1:8899/thd/jackson/deserializeDate01
    public ResponseEntity deserializeDate01() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String json = "{\"name\":\"thd\",\"age\":5,\"birthday\":\"2020**04**01 11:08:36\",\"createTime\":\"2020**04**01 11:08:36\"}";
        TestBean02 tb = mapper.readValue(json, TestBean02.class);
        System.out.println(tb);
        // 输出
        // TestBean01{name='thd', age=5, birthday=2020-04-01 11:08:36, createTime=2020-04-01 11:08:36}

        /**
         * 结论：
         * 1.在实体类上的Date或Timestamp类型的属性上添加@JsonFormat(pattern = "yyyy**MM**dd HH:mm:ss", timezone = "GMT+8")实现反序列化日期格式的设置
         *
         */
        return ResponseEntity.ok(tb);
    }

    /**
     * 反序列化日期方法二
     * 在实体类中的属性上添加@JsonDeserialize注释解决日期序列化的格式问题
     * 例如 @JsonDeserialize(using = JsonDateDeserializer.class)
     */
    @RequestMapping("deserializeDate02")
    // url : http://127.0.0.1:8899/thd/jackson/deserializeDate02
    public ResponseEntity deserializeDate02() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String json = "{\"name\":\"thd\",\"age\":5,\"birthday\":\"2015-01-01\",\"createTime\":\"2020&&04&&01 11$$08$$36\"}";
        TestBean03 tb = mapper.readValue(json, TestBean03.class);
        System.out.println(tb);
        // 输出
        // TestBean03{name='thd', age=5, birthday=2015-01-01 00:00:00, createTime=2020-04-01 11:08:36}

        /**
         * 结论：
         * 1.在实体类上的Date或Timestamp类型的属性上添加@JsonDeserialize(using = xxxx.class)
         * 2.然后自己定义该反序列化器
         * 可以设置反序列化的日期格式
         */
        return ResponseEntity.ok(tb);
    }


    /**
     * 反序列化日期方法三
     * 在实体类中的属性上添加@JsonDeserialize注释解决日期序列化的格式问题
     * 例如 @JsonDeserialize(using = JsonDateDeserializer.class)
     */
    @RequestMapping("deserializeDate03")
    // url : http://127.0.0.1:8899/thd/jackson/deserializeDate03
    public ResponseEntity deserializeDate03() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String json = "{\"name\":\"thd\",\"age\":5,\"birthday\":\"2015//01//01 20:20:21\",\"createTime\":1585710000639}";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy//MM//dd HH:mm:ss");
        mapper.setDateFormat(formatter);

        TestBean01 tb = mapper.readValue(json, TestBean01.class);
        System.out.println(tb);
        // 输出
        // TestBean01{name='thd', age=5, birthday=2015-01-01 20:20:21, createTime=2020-04-01 11:00:00}

        /**
         * 结论：
         * 可以通过mapper.setDateFormat设置日期类型的反序列化格式
         */
        return ResponseEntity.ok(tb);
    }


    /**
     * 反序列化日期方法四
     * 使用ObjectMapper.registerModule设置反序列化器
     */
    @RequestMapping("deserializeDate04")
    // url : http://127.0.0.1:8899/thd/jackson/deserializeDate04
    public ResponseEntity deserializeDate04() throws Exception{


        ObjectMapper mapper=new ObjectMapper();//先创建objmapper的对象


        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addDeserializer(Date.class, new JsonDateDeserializer());
        timeModule.addDeserializer(Timestamp.class, new JsonTimestampDeserializer());
        mapper.registerModule(timeModule);

        String json = "{\"name\":\"thd\",\"age\":5,\"birthday\":\"2020-04-01\",\"createTime\":\"2020&&04&&01 11$$33$$00\"}";
        TestBean03 tb = mapper.readValue(json,TestBean03.class);
        System.out.println(String.format("TestBean03:%s",tb));
        //输出：
        // TestBean03:TestBean03{name='thd', age=5, birthday=2020-04-01 00:00:00, createTime=2020-04-01 11:33:00}

        /**
         * 结论：
         * 可以使用ObjectMapper.registerModule设置反序列化器
         */

        return ResponseEntity.ok( tb);
    }




    @RequestMapping("/testInput")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/jackson/testInput
    public ResponseEntity testInput(@RequestBody JacksonBean jb) throws Exception{
        System.out.println(jb);
        return ResponseEntity.ok( jb);
    }

    /**
     * 设置自定义的序列化和反序列化规则
     * @return
     * @throws Exception
     */
    @RequestMapping("/testCustomSerializa")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/jackson/testCustomSerializa
    public ResponseEntity testCustomSerializa() throws Exception{

        // 设置自定义的序列化器
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDateTime.class, new JsonLocalDateTimeSerializer());
        mapper.registerModule(timeModule);
//
//
//
//        JacksonBean jb = new JacksonBean();
//        jb.setName("devil13th");
//        jb.setBirthday(new Date());
//        jb.setDate1(new Date());
//        jb.setDate2(new Date());
//        jb.setCreateTime(new Timestamp(System.currentTimeMillis()));
//        jb.setLocalDateTime(LocalDateTime.now());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        // 设置日期序列化的格式, 优先级低于Bean属性的@Annotation注释
        mapper.setDateFormat(formatter);
//
//
//        String str = mapper.writeValueAsString(jb);
//        System.out.println(str);

        //设置自定义的反序列化器
        JavaTimeModule timeModule2 = new JavaTimeModule();
        timeModule.addDeserializer(LocalDateTime.class, new JsonLocalDateTimeDeserializer());
        mapper.registerModule(timeModule2);
        String json = "{\"name\":\"devil13th\",\"birthday\":\"2020-04-01 00:37:13\",\"date1\":\"2020-04-01 00:37:13\",\"date2\":\"2020-04-01\",\"localDateTime\":414899151000,\"date3\":null,\"createTime\":\"2020-04-01 00:37:13\"}";



//        String json = "{\"name\":\"devil13th\",\"birthday\":\"2020-04-01 00:37:13\",\"date1\":\"2020-04-01 00:37:13\",\"date2\":\"2020-03-31\",\"date3\":null,\"createTime\":\"2020-04-01 00:37:13\"}";

        JacksonBean jjb = mapper.readValue(json,JacksonBean.class);
        System.out.println(jjb);
        return ResponseEntity.ok( jjb);
    }

}
