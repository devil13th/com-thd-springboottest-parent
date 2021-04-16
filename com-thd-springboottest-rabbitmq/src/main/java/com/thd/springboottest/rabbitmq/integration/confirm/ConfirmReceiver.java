package com.thd.springboottest.rabbitmq.integration.confirm;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
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


    @RabbitListener(queues = "deal_queue_demo4")
    public void process(Message message, @Headers Map<String, Object> headers, Channel channel) throws IOException {

        // 获取消息Id
        String messageId = message.getMessageProperties().getMessageId();
        String msg = new String(message.getBody(), "UTF-8");
        System.out.println("死信邮件消费者获取生产者消息msg:"+msg+",消息id"+messageId);

        // 手动ack
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        // 手动签收
        channel.basicAck(deliveryTag, false);
        System.out.println("执行结束....");

    }
}
