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
public class DLXConsumer {
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
        channel.basicConsume(DLX_QUEUE_NAME, autoAsk, new DefaultConsumer(channel) {
            // 当消息到达时执行回调方法
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String message = new String(body, "utf-8");
                System.out.println(" ---- DLX队列接收消息: ----" );
                System.out.println(message);

                //发布的每一条消息都会获得一个唯一的deliveryTag，deliveryTag在channel范围内是唯一的
                long deliveryTag = envelope.getDeliveryTag();

                /**
                 * deliveryTag:该消息的index
                 * multiple：是否批量.true:将一次性ack所有小于deliveryTag的消息。
                 */
                channel.basicAck(deliveryTag,false);



                System.out.println("  ---- 消息被死信队列消费者消费 ----  ");
            }
        });

        System.out.println("已经启动消费者");

    }
}
