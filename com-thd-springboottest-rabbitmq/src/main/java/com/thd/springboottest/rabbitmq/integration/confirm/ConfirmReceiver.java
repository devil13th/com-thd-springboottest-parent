package com.thd.springboottest.rabbitmq.integration.confirm;

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
@RabbitListener(queues = "TestIntegrationConfirmQueue")
public class ConfirmReceiver {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @RabbitHandler
    public void process(String testMessage) {
        this.logger.info("<=================== ConfirmReceiver消费者收到消息  : " + testMessage.toString());
    }
}
