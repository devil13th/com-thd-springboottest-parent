package com.thd.springboottest.rabbitmq.direct.producer.controller;

import com.thd.springboottest.rabbitmq.dto.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * com.thd.springboottest.rabbitmq.producer.controller.SendMessageController
 *
 * @author: wanglei62
 * @DATE: 2020/2/14 15:56
 **/
@Controller
@RequestMapping("/rabbitmq/direct/producer")
public class SendMessageController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法

    @RequestMapping("/test")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/rabbitmq/direct/producer/test
    public ResponseEntity test(){
        this.logger.info("test()");
        return ResponseEntity.ok("SUCCESS");
    }

    @RequestMapping("/sendDirectMessage")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/rabbitmq/direct/producer/sendDirectMessage
    public String sendDirectMessage() {
        this.logger.info("sendDirectMessage()");

        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message, hello!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        Map<String,Object> map=new HashMap<>();
//        map.put("messageId",messageId);
//        map.put("messageData",messageData);
//        map.put("createTime",createTime);

        Person p  = new Person("张三",5,new Date());
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", p);

        this.logger.info("SendMessageController 消息提供者发送消息：" + p);
        return "ok";
    }
}
