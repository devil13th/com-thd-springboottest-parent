package com.thd.springboottest.rabbitmq.exchange.fanout.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * com.thd.springboottest.rabbitmq.exchange.topic.consumer.TopicManReceiver
 *
 * @author: wanglei62
 * @DATE: 2020/2/14 16:32
 **/

@Component
@RabbitListener(queues = "fanoutQueue02")
public class FanoutSecondReceiver {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @RabbitHandler
    public void process(Map testMessage) {
        this.logger.info("FanoutReceive 2 消费者收到消息  : " + testMessage.toString());
    }
}
