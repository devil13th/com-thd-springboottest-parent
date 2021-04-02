package com.thd.springboottest.rabbitmq.integration.confirm;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * com.thd.springboottest.rabbitmq.integration.confirm.Productor
 *
 * @author: wanglei62
 * @DATE: 2021/4/2 10:43
 **/
public class Producer implements RabbitTemplate.ConfirmCallback  {
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public Producer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this);
    }

    public void send2() {
        for (int i = 0; i < 1000; i++) {
            String context = "hi, i am messages " + i;
            System.out.println("Sender : " + context);
            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

            System.out.println("callbackSender UUID: " + correlationData.getId());

            this.rabbitTemplate.convertAndSend("topicExchange", "topic.messages", context, correlationData);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        System.out.println("confirm: " + correlationData.getId() + ",s=" + s + ",b:" + b);
    }
}
