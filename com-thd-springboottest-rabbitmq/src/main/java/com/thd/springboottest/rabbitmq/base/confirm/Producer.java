package com.thd.springboottest.rabbitmq.base.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.thd.springboottest.rabbitmq.base.ConnectionUtil;

import java.io.IOException;

/**
 * com.thd.springboottest.rabbitmq.base.direct.Producer
 *
 * @author: wanglei62
 * @DATE: 2020/2/18 13:57
 **/
public class Producer {
    // 注：一定要先启动Consumer2，创建好交换器、队列 !!!
    private static final String ROUTE_KEY ="routekey-devil13th"; // 路由键名称
    private static final String EXCHANGE_NAME = "exchange-devil13th3";// 交换器名称

    public static void main(String[] args) throws Exception{
        // 获取连接
        Connection connection = ConnectionUtil.getConnection();
        System.out.println(connection);
        // 创建信道
        Channel channel = connection.createChannel();

        System.out.println(channel);

        // 开启发送方确认模式
        channel.confirmSelect();
        for (int i = 0; i < 5; i++) {
            String msg = "Hello world.I love you forever ===>"  + i;

            // 发布消息，需要参数：交换器，路由键。最后一个参数为消息内容
            // 注意：RabbitMQ的消息类型只有一种，那就是byte[]
            channel.basicPublish(EXCHANGE_NAME, ROUTE_KEY, null, msg.getBytes("utf-8"));

            System.out.println("send:" + msg);

//            if (channel.waitForConfirms()) {
//                System.out.println("消息发送成功" );
//            }else{
//                // 进行消息重发
//            }

        }

        //6 添加一个确认监听
        channel.addConfirmListener(new ConfirmListener() {
            //消息失败处理
            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                //deliveryTag；唯一消息标签
                //multiple：是否批量
                System.out.println(String.format("----no ack!---- %d  %b",deliveryTag,multiple));
            }
            //消息成功处理
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println(String.format("----  ack! ---- %d  %b",deliveryTag,multiple));
            }
        });


        //异步监听确认和未确认的消息
//        channel.addConfirmListener(new ConfirmListener() {
//            @Override
//            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
//                System.out.println(String.format("未确认消息，标识：%d，多个消息：%b", deliveryTag, multiple));
//            }
//            @Override
//            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
//                System.out.println(String.format("已确认消息，标识：%d，多个消息：%b", deliveryTag, multiple));
//            }
//        });




        //关闭信道和连接
        //channel.close();
        //connection.close();
    }
}
