package com.thd.springboottest.rabbitmq.base.deadletter;


import com.rabbitmq.client.*;
import com.thd.springboottest.rabbitmq.base.ConnectionUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * com.thd.springboottest.rabbitmq.base.direct.Consumer2
 *
 * @author: wanglei62
 * @DATE: 2020/2/18 15:21
 **/
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        String QUEUE_NAME = "dlx-test-queue";// 队列名称
        String ROUTE_KEY = "dlx-test-routekey";// 路由键名称
        String EXCHANGE_NAME = "dlx-test-exchange";// 交换器名称

        String DLX_QUEUE_NAME = "dlx-queue";// 队列名称
        String DLX_ROUTE_KEY = "dlx-routekey";// 路由键名称
        String DLX_EXCHANGE_NAME = "dlx-exchange";// 交换器名称


        Connection conn = ConnectionUtil.getConnection();
        Channel channel = conn.createChannel();

        // 消费成功列表  避免重复消费  successList是模拟的数据库
        Map<String,Boolean> successList = new HashMap<String,Boolean>();

        /**
         * 开始消费
         * 参数1：routingKey
         * 参数2：是否自动确认
         */
        boolean autoAsk = false;
        channel.basicConsume(QUEUE_NAME, autoAsk, new DefaultConsumer(channel) {
            // 当消息到达时执行回调方法
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String message = new String(body, "utf-8");
                System.out.println(" ---- 正常队列接收消息: ----" );
                System.out.println(message);

                //发布的每一条消息都会获得一个唯一的deliveryTag，deliveryTag在channel范围内是唯一的
                long deliveryTag = envelope.getDeliveryTag();


                // 消息投递到死信队列的条件
                /*
                    消息被拒绝(basic.reject / basic.nack)，并且requeue = false（不重回队列）。
                    消息TTL过期。
                    队列达到最大长度。
                 */



                // basic.reject方法拒绝deliveryTag对应的消息，第二个参数是否requeue，true则重新入队列，否则丢弃或者进入死信队列。
                // 该方法reject后，该消费者还是会消费到该条被reject的消息。
                channel.basicReject(deliveryTag,false);

                // basic.nack方法为不确认deliveryTag对应的消息，第二个参数是否应用于多消息，第三个参数是否requeue，与basic.reject区别就是同时支持多个消息，可以nack该消费者先前接收未ack的所有消息。nack后的消息也会被自己消费到
//                channel.basicNack(deliveryTag,false,false);

                // basic.recover是否恢复消息到队列，参数是是否requeue，true则重新入队列，并且尽可能的将之前recover的消息投递给其他消费者消费，而不是自己再次消费。false则消息会重新被投递给自己。
//                channel.basicReject(deliveryTag,false);
                System.out.println("  ---- 消息被消费者拒绝,将投递到死信队列 ---- ");
            }
        });

        System.out.println("已经启动消费者");

    }
}
