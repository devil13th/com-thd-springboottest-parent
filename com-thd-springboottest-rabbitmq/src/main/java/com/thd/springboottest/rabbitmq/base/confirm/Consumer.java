package com.thd.springboottest.rabbitmq.base.confirm;


import com.rabbitmq.client.*;
import com.thd.springboottest.rabbitmq.base.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * com.thd.springboottest.rabbitmq.base.direct.Consumer2
 *
 * @author: wanglei62
 * @DATE: 2020/2/18 15:21
 **/
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        String QUEUE_NAME = "queue-devil13th";// 队列名称
        String ROUTE_KEY = "routekey-devil13th";// 路由键名称
        String EXCHANGE_NAME = "exchange-devil13th";// 交换器名称


        Connection conn = ConnectionUtil.getConnection();
        Channel channel = conn.createChannel();

        /**
         * 开始消费
         * 参数1：routingKey
         * 参数2：是否自动确认
         */
        boolean autoAsk = false;
        channel.basicConsume(QUEUE_NAME, autoAsk, new DefaultConsumer(channel) {
            // 当消息到达时执行回调方法
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(" 消费消息 ...");
                System.out.println(consumerTag);

                String message = new String(body, "utf-8");
                System.out.println("[Receive]：" + message);


                //发布的每一条消息都会获得一个唯一的deliveryTag，deliveryTag在channel范围内是唯一的
                long deliveryTag = envelope.getDeliveryTag();

                /**
                 * 确认消息已接收的应答
                 * 第一个参数：发布的每一条消息都会获得一个唯一的deliveryTag，deliveryTag在channel范围内是唯一的
                 * 第二个参数：批量确认标志。如果值为true，则执行批量确认，此deliveryTag之前收到的消息全部进行确认; 如果值为false，则只对当前收到的消息进行确认
                 */
                channel.basicAck(deliveryTag, true);

                // 下面是拒绝应答
                /**
                 * Reject a message.
                 * @param deliveryTag 发布的每一条消息都会获得一个唯一的deliveryTag，deliveryTag在channel范围内是唯一的
                 * @param requeue 是否重回队列 如果值为true，则重新放入RabbitMQ的发送队列，如果值为false，则通知RabbitMQ销毁这条消息
                 * @throws IOException if an error is encountered
                 */
                // void basicReject(long deliveryTag, boolean requeue) throws IOException;

                /**
                 * Reject one or several received messages.
                 * @param deliveryTag 发布的每一条消息都会获得一个唯一的deliveryTag，deliveryTag在channel范围内是唯一的
                 * @param multiple 批量确认标志。如果值为true，则执行批量确认，此deliveryTag之前收到的消息全部进行拒绝; 如果值为false，则只对当前收到的消息进行拒绝
                 * @param requeue 是否重回队列 如果值为true，则重新放入RabbitMQ的发送队列，如果值为false，则通知RabbitMQ销毁这条消息
                 * @throws IOException if an error is encountered
                 */
                 // void basicNack(long deliveryTag, boolean multiple, boolean requeue) throws IOException;
                 // 注意!!!!! 如果我们的队列目前只有一个消费者，请注意不要拒绝消息并放回队列导致消息在同一个消费者身上无限循环无法消费的情况发生。
            }
        });

        System.out.println("已经启动消费者");

    }
}
