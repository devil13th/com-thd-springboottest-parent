package com.thd.springboottest.rabbitmq.base.confirm;

import com.rabbitmq.client.*;
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




        /*
           设置Return Listener监听 如果找不到routing key 则调用该回调
           开启该回调方法： 设置mandatory为true (第三个参数)
           channel.basicPublish(EXCHANGE_NAME, ROUTE_KEY, true , true ,p, msg.getBytes());

         */

        channel.addReturnListener(new ReturnListener() {
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("--------------Return Listener--------------");
                System.out.println(replyCode);
                System.out.println(replyText);
                System.out.println(exchange);
                System.out.println(routingKey);
                System.out.println(properties);
                System.out.println(new String(body));
            }
        });



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
                .correlationId(String.valueOf(Math.random() * 100))
                .headers(m) // 设置头信息
                .build();


//        channel.basicPublish(EXCHANGE_NAME, ROUTE_KEY, p, msg.getBytes());

        // 第三个参数是 mandatory , 如果找不到routingkey 则会调用return listener回调
        channel.basicPublish(EXCHANGE_NAME, ROUTE_KEY, true,p, msg.getBytes());
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
