package com.thd.springboottest.rabbitmq.base.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * com.thd.springboottest.rabbitmq.base.direct.Producer
 *
 * @author: wanglei62
 * @DATE: 2020/2/18 13:57
 **/
public class Producer2 {
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



        // 创建一个交换器，参数为：交互器名称,交换器类型
        // 注意：其实这个交换器只需要声明一次就可以，但是由于无法保证交换器已经存在了，所以我们每次都要声明, 如果有了不要再次创建，否则报错
        // channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);


        // 开启事务
        //channel.txSelect();
        //for (int i = 0; i < 2; i++) {
        String msg = "Hello world.I love you forever ===>" ;
        // 发布消息，需要参数：交换器，路由键。最后一个参数为消息内容
        // 注意：RabbitMQ的消息类型只有一种，那就是byte[]
        channel.basicPublish(EXCHANGE_NAME, ROUTE_KEY, null, msg.getBytes("utf-8"));

        System.out.println("send:" + msg);
        //}
        // 提交事务
       // channel.txCommit();


        //关闭信道和连接
        channel.close();
        connection.close();
    }
}
