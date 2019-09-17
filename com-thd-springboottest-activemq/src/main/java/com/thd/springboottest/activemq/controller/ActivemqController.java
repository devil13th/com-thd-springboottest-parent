package com.thd.springboottest.activemq.controller;

import com.thd.springboottest.activemq.service.ProducerService;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

/**
 * @author devil13th
 **/
@Controller
@RequestMapping(value="/activemq")
public class ActivemqController {
    @Autowired
    private Environment env;
    @Autowired
    private ProducerService producerService;
    @Autowired
    private ConnectionFactory connectionFactory;
//    @Autowired
//    private JmsPoolConnectionFactory pooledJmsConnectionFactory;
    @Autowired
    private JmsProperties jmsProperties;
    @ResponseBody
    @RequestMapping(value="/index")
    // http://127.0.0.1:8899/thd/activemq/index
    public String index(){
        Logger log = LoggerFactory.getLogger(this.getClass());
        log.info("index");
        return "index";
    }
    @ResponseBody
    @RequestMapping(value="/poolInfo")
    // http://127.0.0.1:8899/thd/activemq/poolInfo
    public String poolInfo(){
        Logger log = LoggerFactory.getLogger(this.getClass());
        System.out.println(connectionFactory.getClass());
        return "index";
    }


    @ResponseBody
    @RequestMapping(value="/sendQueueMessage/{msg}")
    // http://127.0.0.1:8899/thd/activemq/sendQueueMessage/aaaaa
    public String sendQueueMessage(@PathVariable String msg){
        Destination destination = new ActiveMQQueue("order.queue");
        producerService.sendMessage(destination, msg);
        return "发送成功";
    }

    @ResponseBody
    @RequestMapping(value="/sendTopicMessage/{msg}")
    // http://127.0.0.1:8899/thd/activemq/sendTopicMessage?msg=666666
    public String sendQueueMessage2(@PathVariable String msg){
        Destination destination = new ActiveMQTopic("order.topic");
        producerService.sendMessage(destination, msg);
        return "发送成功";
    }
}




