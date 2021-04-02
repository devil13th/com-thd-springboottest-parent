package com.thd.springboottest.rabbitmq.exchange.fanout.producer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author: wanglei62
 * @DATE: 2020/2/14 16:29
 **/
@Controller
@RequestMapping("/rabbitmq/fanout/producer")
public class FanoutMessageSendController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法

    @GetMapping("/sendFanoutMessage1")
    @ResponseBody
    // url : // http://127.0.0.1:8899/thd/rabbitmq/fanout/producer/sendFanoutMessage1
    public String sendTopicMessage1() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: M A N ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> manMap = new HashMap<>();
        manMap.put("messageId", messageId);
        manMap.put("messageData", messageData);
        manMap.put("createTime", createTime);
        rabbitTemplate.convertAndSend("fanoutExchange", "", manMap);
        this.logger.info("SendMessageController 消息提供者发送消息：" + manMap);
        return "ok";
    }

    @GetMapping("/sendFanoutMessage2")
    @ResponseBody
    // url : // http://127.0.0.1:8899/thd/rabbitmq/fanout/producer/sendFanoutMessage2
    public String sendTopicMessage2() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: woman is all ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> womanMap = new HashMap<>();
        womanMap.put("messageId", messageId);
        womanMap.put("messageData", messageData);
        womanMap.put("createTime", createTime);
        rabbitTemplate.convertAndSend("fanoutExchange", "", womanMap);
        this.logger.info("SendMessageController 消息提供者发送消息：" + womanMap);
        return "ok";
    }
}
