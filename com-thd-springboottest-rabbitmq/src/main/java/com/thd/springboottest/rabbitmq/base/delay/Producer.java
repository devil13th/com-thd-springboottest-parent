package com.thd.springboottest.rabbitmq.base.delay;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.thd.springboottest.rabbitmq.base.ConnectionUtil;

import java.util.HashMap;
import java.util.Map;

public class Producer {
    public static void main(String[] args) throws Exception{

        String QUEUE_NAME = "normal-queue";// 队列名称
        String ROUTE_KEY = "normal-routekey";// 路由键名称
        String EXCHANGE_NAME = "normal-exchange";// 交换器名称



        // 获取连接
        Connection connection = ConnectionUtil.getConnection();
        System.out.println(connection);
        // 创建信道
        Channel channel = connection.createChannel();



        AMQP.BasicProperties p = new AMQP.BasicProperties().builder()
                // 消息主键,应用级别
                .messageId(String.valueOf(Math.random() * 100))
                .correlationId(String.valueOf(Math.random() * 100))
                .expiration("5000")  // 有效期10秒钟
                .build();



        String msg = " Hello , TTL ！";
        channel.basicPublish(EXCHANGE_NAME, ROUTE_KEY, p, msg.getBytes());
        System.out.println(" ---- 消息被发送 ---- ");

        //关闭信道和连接
        channel.close();
        connection.close();
    }
}
