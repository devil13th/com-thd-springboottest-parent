package com.thd.springboottest.rabbitmq.base.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * com.thd.springboottest.rabbitmq.base.direct.Producer
 *
 * @author: wanglei62
 * @DATE: 2020/2/18 13:57
 **/
public class Producer {

    private static final String QUEUE_NAME="test_queue";

    public static void main(String[] args) throws Exception{
        // 获取连接
        Connection connection = ConnectionUtil.getConnection();
        System.out.println(connection);
        // 创建信道
        Channel channel = connection.createChannel(1);
        System.out.println(channel);
        // 创建队列
        /*
        * 声明（创建）队列
        * 参数1：队列名称
        * 参数2：消息是否持久化 为true时server重启队列不会消失
        * 参数3：队列是否是独占的，如果为true只能被一个connection使用，其他连接建立时会抛出异常
        * 参数4：队列不再使用时是否自动删除（没有连接，并且没有未处理的消息)
        * 参数5：建立队列时的其他参数
        */
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String message = "hello world!";

        for(int i = 0 ; i < 1 ; i++){
            message = message + 1;
            /**
             * 第一个参数为交换机名称
             * 第二个参数为队列映射的路由key
             * 第三个参数为消息的其他属性
             * 第四个参数为发送信息的主体
             */
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes("utf-8"));
        }
        System.out.println("Producer Send +'" + message + "'");
    }
}
