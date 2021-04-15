package com.thd.springboottest.rabbitmq.integration.confirm;

import org.springframework.amqp.core.Message;
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
public class ConfirmProducer implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback  {
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public ConfirmProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    public void send2() {
        for (int i = 0; i < 3; i++) {
            String context = "hi, i am messages " + i;
            System.out.println("Sender : " + context);
            String businessKey = UUID.randomUUID().toString();
            CorrelationData correlationData = new CorrelationData(businessKey);
            System.out.println("================== > send  UUID: " + correlationData.getId());

            if(i %2 == 0 ){
                // 成功发送
                this.rabbitTemplate.convertAndSend("TestIntegrationConfirmExchange", "ConfirmRouting", context, correlationData);
            }else{
                // 发送成功但故意将routekey写错,导致找不到Queue而调用ReturnCallback回调
                this.rabbitTemplate.convertAndSend("TestIntegrationConfirmExchange", "xxxConfirmRouting", context, correlationData);

            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        System.out.println(" |||||||||||||||  成功发送到交换机: " + correlationData.getId() + ",s=" + s + ",b:" + b);
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println(" |||||||||||||||  交换机未找到路由 : " + message.toString() + " || " + i + " || " + s + " || " + s1 + " || " + s2 );
    }
}
