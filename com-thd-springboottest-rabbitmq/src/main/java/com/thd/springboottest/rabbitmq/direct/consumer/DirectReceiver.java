package com.thd.springboottest.rabbitmq.direct.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * com.thd.springboottest.rabbitmq.consumer.DirectReceiver
 *
 * @author: wanglei62
 * @DATE: 2020/2/14 16:16
 **/
@Component
@RabbitListener(queues = "TestDirectQueue")//监听的队列名称 TestDirectQueue
public class DirectReceiver {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    public void process(Map testMessage) {
        this.logger.info("DirectReceiver消费者收到消息  : " + testMessage.toString());
    }
}
