package com.thd.springboottest.rabbitmq.base.direct;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * com.thd.springboottest.rabbitmq.base.direct.Consumer2
 *
 * @author: wanglei62
 * @DATE: 2020/2/18 15:21
 **/
public class Consumer2 {
    public static void main(String[] args) throws IOException, TimeoutException {
        String QUEUE_NAME = "queue-devil13th";// 队列名称
        String ROUTE_KEY = "routekey-devil13th";// 路由键名称
        String EXCHANGE_NAME = "exchange-devil13th";// 交换器名称


        Connection conn = ConnectionUtil.getConnection();
        Channel channel = conn.createChannel();


        //开始消费，第二个参数表示自动确认
        channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel) {
            // 当消息到达时执行回调方法
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
                System.out.println(" consumer ...");
                String message = new String(body, "utf-8");
                System.out.println("[Receive]：" + message);
            }
        });

        System.out.println("已经启动消费者");

    }
}
