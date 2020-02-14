package com.thd.springboottest.rabbitmq.topic.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * com.thd.springboottest.rabbitmq.topic.consumer.TopicManReceiver
 *
 * @author: wanglei62
 * @DATE: 2020/2/14 16:32
 **/

@Component
@RabbitListener(queues = "topic.man")
public class TopicManReceiver {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @RabbitHandler
    public void process(Map testMessage) {
        this.logger.info("TopicManReceiver消费者收到消息  : " + testMessage.toString());
    }
}
