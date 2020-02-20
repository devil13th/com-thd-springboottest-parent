package com.thd.springboottest.rabbitmq.base.transaction;


import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * com.thd.springboottest.rabbitmq.base.direct.Consumer2
 *
 * @author: wanglei62
 * @DATE: 2020/2/18 15:21
 **/
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        String QUEUE_NAME = "queue-devil13th";// 队列名称
        String ROUTE_KEY = "routekey-devil13th";// 路由键名称
        String EXCHANGE_NAME = "exchange-devil13th";// 交换器名称


        Connection conn = ConnectionUtil.getConnection();
        Channel channel = conn.createChannel();

        /**
         * 开始消费
         * 参数1：routingKey
         * 参数2：是否自动确认
         */
        boolean autoAsk = false;
        channel.basicConsume(QUEUE_NAME, autoAsk, new DefaultConsumer(channel) {
            // 当消息到达时执行回调方法
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(" consumer ...");
                System.out.println(consumerTag);

                String message = new String(body, "utf-8");
                System.out.println("[Receive]：" + message);


                //发布的每一条消息都会获得一个唯一的deliveryTag，deliveryTag在channel范围内是唯一的
                long deliveryTag = envelope.getDeliveryTag();
                //第二个参数是批量确认标志。如果值为true，则执行批量确认，此deliveryTag之前收到的消息全部进行确认; 如果值为false，则只对当前收到的消息进行确认
                channel.basicAck(deliveryTag, true);



            }
        });

        System.out.println("已经启动消费者");

    }
}
