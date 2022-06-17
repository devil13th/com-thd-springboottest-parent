package com.thd.springboottest.rabbitmq.base.delay;


import com.rabbitmq.client.*;
import com.thd.springboottest.rabbitmq.base.ConnectionUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;


public class DelayConsumer {
    public static void main(String[] args) throws IOException, TimeoutException {

        String DELAY_QUEUE_NAME = "delay-queue";// 队列名称

        Connection conn = ConnectionUtil.getConnection();
        Channel channel = conn.createChannel();

        // 消费成功列表  避免重复消费  successList是模拟的数据库
        Map<String,Boolean> successList = new HashMap<String,Boolean>();

        /**
         * 开始消费
         * 参数1：routingKey
         * 参数2：是否自动确认
         */
        boolean autoAsk = false;
        channel.basicConsume(DELAY_QUEUE_NAME, autoAsk, new DefaultConsumer(channel) {
            // 当消息到达时执行回调方法
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String message = new String(body, "utf-8");
                System.out.println(" ---- 延时队列接收消息: ----" );
                System.out.println(message);

                //发布的每一条消息都会获得一个唯一的deliveryTag，deliveryTag在channel范围内是唯一的
                long deliveryTag = envelope.getDeliveryTag();

                /**
                 * deliveryTag:该消息的index
                 * multiple：是否批量.true:将一次性ack所有小于deliveryTag的消息。
                 */
                channel.basicAck(deliveryTag,false);

                System.out.println("  ---- 消息被延时队列消费者消费 ----  ");
            }
        });

        System.out.println("已经启动消费者");

    }
}