package com.thd.springboottest.rabbitmq.base.transaction;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.thd.springboottest.rabbitmq.base.ConnectionUtil;

/**
 * com.thd.springboottest.rabbitmq.base.direct.Producer
 *
 * @author: wanglei62
 * @DATE: 2020/2/18 13:57
 **/
public class Producer {
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


        /**
         * channel.txSelect(); 开启事务
         * channel.txCommit(); 提交事务
         * channel.txRollback(); 事务回滚
         *
         * 声明式事务开启:channel.txSelect();  如果开启了事务必须要用channel.txCommit(); 提交事务，否则消息不会被发送
         * 如果调用channel.txRollback();则事务回滚
         *
         * 事务的规则同数据库事务
         * 例如：
         *   ------> 开启事务 channel.txSelect()
         *   生产者发送第一条消息到mq
         *   生产者发送第二条消息到mq
         *   生产者发送第三条消息到mq
         *   ------> 提交事务 channel.txRollback()
         *
         *   保证三条消息要发送都发送，要不发送都不发送
         *   如果调用了事务回滚方法，则从开启事务到事务回滚之间发送的消息都不会被发送
         *
         *  开了事务就要提交或者回滚，否则消息不会被发送
         *  可以不开启事务(非事务模式)，消息可以随时被发送，但不带有事务
         *
         *  开启事务会消耗性能
         *  -- 事务模式，结果如下：
         *  事务模式，发送1w条数据，执行花费时间：14197s
         *  事务模式，发送1w条数据，执行花费时间：13597s
         *  事务模式，发送1w条数据，执行花费时间：14216s
         *  -- 非事务模式，结果如下：
         *  非事务模式，发送1w条数据，执行花费时间：101s
         *  非事务模式，发送1w条数据，执行花费时间：77s
         *  非事务模式，发送1w条数据，执行花费时间：106s
         */

        // 开启事务
        channel.txSelect();
        for (int i = 0; i < 5; i++) {
            String msg = "Hello world.I love you forever ===>"  + i;
            // 发布消息，需要参数：交换器，路由键。最后一个参数为消息内容
            // 注意：RabbitMQ的消息类型只有一种，那就是byte[]
            channel.basicPublish(EXCHANGE_NAME, ROUTE_KEY, null, msg.getBytes("utf-8"));

            System.out.println("send:" + msg);
        }
        // 提交事务
        channel.txCommit();


        //关闭信道和连接
        channel.close();
        connection.close();
    }
}
