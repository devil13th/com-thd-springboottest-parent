package com.thd.springboottest.rabbitmq.base.confirm;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.thd.springboottest.rabbitmq.base.ConnectionUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * com.thd.springboottest.rabbitmq.base.direct.Producer
 *
 * @author: wanglei62
 * @DATE: 2020/2/18 13:57
 **/
public class ProducerForOneConfirm {
    // 注：一定要先启动Consumer2，创建好交换器、队列 !!!
    private static final String ROUTE_KEY ="routekey-devil13th"; // 路由键名称
    private static final String EXCHANGE_NAME = "exchange-devil13th";// 交换器名称

    public static void main(String[] args) throws Exception{
        // 获取连接
        Connection connection = ConnectionUtil.getConnection();
        System.out.println(connection);
        // 创建信道
        Channel channel = connection.createChannel();

        System.out.println(channel);
        Map<String,String> messageList = new HashMap<String,String>();
        // 开启发送方确认模式
        channel.confirmSelect();

        String msg = "hello confirm message!";
        // 头信息,任意键值对
        Map<String,Object> m = new HashMap<String,Object>();
        m.put("a","1");
        m.put("b","2");
        m.put("c","3");
        AMQP.BasicProperties p = new AMQP.BasicProperties().builder()
                // 1 代表非持久化消息，2 代表持久化消息。
                .deliveryMode(2)
                // 消息主键,应用级别
                .messageId(String.valueOf(Math.random() * 100))
                .headers(m) // 设置头信息
                .build();


        channel.basicPublish(EXCHANGE_NAME, ROUTE_KEY, p, msg.getBytes());
        if (!channel.waitForConfirms()) {
            System.out.println("发送失败");
        } else {
            System.out.println("发送成功");
        }


        //关闭信道和连接
        channel.close();
        connection.close();
    }
}
