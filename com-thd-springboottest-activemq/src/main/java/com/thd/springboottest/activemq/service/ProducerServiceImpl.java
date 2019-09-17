package com.thd.springboottest.activemq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;

/**
 * @author devil13th
 **/
@Component
public class ProducerServiceImpl implements ProducerService {

    @Autowired
    private JmsTemplate jmsTemplate; //用来发送消息到broker的对象

    @Override
    public void sendMessage(Destination destination, String message) {
        System.out.println(jmsTemplate.getConnectionFactory().getClass());
        jmsTemplate.convertAndSend(destination, message);
    }
}
