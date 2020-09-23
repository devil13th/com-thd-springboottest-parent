package com.thd.springboottest.rabbitmq.base.confirm;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.thd.springboottest.rabbitmq.base.ConnectionUtil;

import java.io.Console;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String,String> messageList = new HashMap<String,String>();
        // 开启发送方确认模式
        channel.confirmSelect();

        System.out.println("channelNumber:" + channel.getChannelNumber());

        //6 添加一个确认监听
        channel.addConfirmListener(new ConfirmListener() {
            //消息失败处理
            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                //deliveryTag；唯一消息标签
                //multiple：是否批量
                System.out.println(String.format("----nack!---- %d  %b",deliveryTag,multiple));

            }
            //消息成功处理
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println(String.format("----  ack! ---- %d  %b",deliveryTag,multiple));
                System.out.println(channel.getConnection());
            }
        });

        int base = 10;

        for (int i = base; i < base+5; i++) {
            String msg = "Hello world.I love you forever ===>"  + i;
            System.out.println("channel.NextPublishSeqNo:" + channel.getNextPublishSeqNo());
            // 头信息,任意键值对
            Map<String,Object> m = new HashMap<String,Object>();
            m.put("a","1");
            m.put("b","2");
            m.put("c","3");

            AMQP.BasicProperties p = new AMQP.BasicProperties().builder()
            // 1 代表非持久化消息，2 代表持久化消息。
            .deliveryMode(2)
            // 消息主键,应用级别
            .messageId(String.valueOf(i * 100))
                    .headers(m) // 设置头信息
                    .build();


            // 发布消息，需要参数：交换器，路由键。最后一个参数为消息内容
            // 注意：RabbitMQ的消息类型只有一种，那就是byte[]
            channel.basicPublish(EXCHANGE_NAME, ROUTE_KEY, p, msg.getBytes("utf-8"));
            if (channel.waitForConfirms()) {
                System.out.println("发送成功!!!!!!!!!!!!!!!!!!!");
            }else{
                // 进行消息重发
                System.out.println("发送失败,重新发送");
            }
            System.out.println("send:" + msg);
//            if (channel.waitForConfirms()) {
//                System.out.println("消息发送成功" + p.getMessageId() ) ;
//            }else{
//                // 进行消息重发
//            }

        }


        channel.waitForConfirmsOrDie();

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
        channel.close();
        connection.close();
    }
}
