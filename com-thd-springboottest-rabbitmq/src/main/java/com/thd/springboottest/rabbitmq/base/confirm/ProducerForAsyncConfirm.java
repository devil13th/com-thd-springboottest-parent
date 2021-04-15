package com.thd.springboottest.rabbitmq.base.confirm;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.thd.springboottest.rabbitmq.base.ConnectionUtil;

import java.io.IOException;
import java.util.*;

/**
 * com.thd.springboottest.rabbitmq.base.direct.Producer
 *
 * @author: wanglei62
 * @DATE: 2020/2/18 13:57
 **/
public class ProducerForAsyncConfirm {
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


        //未确认的消息标识
        SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());


        //6 添加一个确认监听
        channel.addConfirmListener(new ConfirmListener() {
            //消息失败处理
            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                //deliveryTag；唯一消息标签
                //multiple：是否批量

                if(multiple){
                    System.out.println(String.format("<< ----nack!---- %d  %b",deliveryTag,multiple));
                    confirmSet.headSet(deliveryTag+1).clear();
                }else {
                    System.out.println(String.format("<< ----nack!---- %d  %b",deliveryTag,multiple));
                    confirmSet.remove(deliveryTag);
                }


            }
            //消息成功处理
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {

                if(multiple){
                    System.out.println(String.format("<< ----  ack! ---- %d  %b",deliveryTag,multiple));
                    confirmSet.headSet(deliveryTag+1).clear();
                }else {
                    System.out.println(String.format("<< ----  ack! ---- %d  %b",deliveryTag,multiple));
                    confirmSet.remove(deliveryTag);
                }


            }
        });




        while (true){
            long seqNo = channel.getNextPublishSeqNo();
            String msg = "Hello world.I love you forever ===>"  +  seqNo;

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


            channel.basicPublish(EXCHANGE_NAME, ROUTE_KEY,p,msg.getBytes());
            confirmSet.add(seqNo);
            System.out.println("发送消息======>" + seqNo);
            Thread.sleep(2000);
        }

        //关闭信道和连接
        //channel.close();
        //connection.close();
    }
}
