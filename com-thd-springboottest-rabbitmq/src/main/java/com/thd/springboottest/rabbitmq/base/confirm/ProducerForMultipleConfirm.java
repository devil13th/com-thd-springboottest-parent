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
public class ProducerForMultipleConfirm {
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

        System.out.println("channelNumber:" + channel.getChannelNumber());


        int base = 10;

        for (int i = base; i < base+5; i++) {
            String msg = "Hello world.I love you forever ===>"  + i;
//            System.out.println("channel.NextPublishSeqNo:" + channel.getNextPublishSeqNo());
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
            System.out.println(" ==== > 发送 ...");



        }


        /*
         * 批量confirm模式是每发送一批消息后，调用channel.waitForConfirms()方法，等待服务器的确认返回，因此相比于普通confirm模式，性能更好。
         * 但是不好的地方在于，如果出现返回Basic.Nack或者超时情况，生产者客户端需要将这一批次的消息全部重发，这样会带来明显的重复消息数量，
         * 如果消息经常丢失，批量confirm模式的性能应该是不升反降的。
         */

//        channel.waitForConfirmsOrDie();
        if (!channel.waitForConfirms()) {
            System.out.println("confirm message send failed");
        } else {
            System.out.println("confirm message send ok");
        }




        //关闭信道和连接
        channel.close();
        connection.close();
    }
}
