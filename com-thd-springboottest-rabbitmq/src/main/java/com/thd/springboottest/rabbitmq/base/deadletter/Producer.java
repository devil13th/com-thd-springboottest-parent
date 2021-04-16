package com.thd.springboottest.rabbitmq.base.deadletter;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.thd.springboottest.rabbitmq.base.ConnectionUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * com.thd.springboottest.rabbitmq.base.deadletter.DeadLetterProducer
 *
 * @author: wanglei62
 * @DATE: 2021/4/16 15:53
 **/
public class Producer {
    public static void main(String[] args) throws Exception{

        String QUEUE_NAME = "dlx-test-queue";// 队列名称
        String ROUTE_KEY = "dlx-test-routekey";// 路由键名称
        String EXCHANGE_NAME = "dlx-test-exchange";// 交换器名称

        String DLX_QUEUE_NAME = "dlx-queue";// 队列名称
        String DLX_ROUTE_KEY = "dlx-routekey";// 路由键名称
        String DLX_EXCHANGE_NAME = "dlx-exchange";// 交换器名称


        // 获取连接
        Connection connection = ConnectionUtil.getConnection();
        System.out.println(connection);
        // 创建信道
        Channel channel = connection.createChannel();



        AMQP.BasicProperties p = new AMQP.BasicProperties().builder()
                // 消息主键,应用级别
                .messageId(String.valueOf(Math.random() * 100))
                .correlationId(String.valueOf(Math.random() * 100))
                .build();

        String msg = " Hello , Dead Letter Exchange！";
        channel.basicPublish(EXCHANGE_NAME, ROUTE_KEY, p, msg.getBytes());
        System.out.println(" ---- 消息被发送 ---- ");

        //关闭信道和连接
        channel.close();
        connection.close();
    }
}
