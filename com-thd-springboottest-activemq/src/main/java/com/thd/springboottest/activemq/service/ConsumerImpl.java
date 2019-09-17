package com.thd.springboottest.activemq.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author devil13th
 **/
@Component
public class ConsumerImpl implements Consumer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @JmsListener(destination="order.queue")
    public void receiveQueue(String text) {
        System.out.println(text);
    }


    @JmsListener(destination = "order.topic",containerFactory = "jmsListenerContainerTopic")
    public void onTopicMessage(String msg) {
        logger.info("接收到topic消息：{}",msg);
    }

    @JmsListener(destination = "order.topic",containerFactory = "jmsListenerContainerTopic")
    public void onTopicMessage2(String msg) {
        logger.info("接收到topic消息2：{}",msg);
    }

    @JmsListener(destination = "order.queue",containerFactory = "jmsListenerContainerQueue")
    public void onQueueMessage(String msg) {
        logger.info("接收到queue消息：{}",msg);
    }


}
