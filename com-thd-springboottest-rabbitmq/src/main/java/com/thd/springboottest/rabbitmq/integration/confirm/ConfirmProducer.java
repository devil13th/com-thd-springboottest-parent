package com.thd.springboottest.rabbitmq.integration.confirm;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * com.thd.springboottest.rabbitmq.integration.confirm.Productor
 *
 * @author: wanglei62
 * @DATE: 2021/4/2 10:43
 **/
@Component
public class ConfirmProducer implements RabbitTemplate.ConfirmCallback  {
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public ConfirmProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this);
    }

    public void send2() {
        for (int i = 0; i < 5; i++) {
            String context = "hi, i am messages " + i;
            System.out.println("Sender : " + context);
            String businessKey = UUID.randomUUID().toString();
            CorrelationData correlationData = new CorrelationData(businessKey);
            System.out.println("================== > send  UUID: " + correlationData.getId());

            this.rabbitTemplate.convertAndSend("TestIntegrationConfirmExchange", "ConfirmRouting", context, correlationData);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        System.out.println(" |||||||||||||||  confirm: " + correlationData.getId() + ",s=" + s + ",b:" + b);
    }
}
