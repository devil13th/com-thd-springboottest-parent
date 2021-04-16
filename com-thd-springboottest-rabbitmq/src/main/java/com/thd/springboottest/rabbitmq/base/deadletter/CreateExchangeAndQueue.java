package com.thd.springboottest.rabbitmq.base.deadletter;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.thd.springboottest.rabbitmq.base.ConnectionUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * com.thd.springboottest.rabbitmq.base.direct.CreateExchangeAndQueue
 *
 * @author: wanglei62
 * @DATE: 2020/2/18 17:42
 **/
public class CreateExchangeAndQueue {
    public static void main(String[] args) throws Exception{
        String QUEUE_NAME = "dlx-test-queue";// 队列名称
        String ROUTE_KEY = "dlx-test-routekey";// 路由键名称
        String EXCHANGE_NAME = "dlx-test-exchange";// 交换器名称

        String DLX_QUEUE_NAME = "dlx-queue";// 队列名称
        String DLX_ROUTE_KEY = "dlx-routekey";// 路由键名称
        String DLX_EXCHANGE_NAME = "dlx-exchange";// 交换器名称

        Connection conn = ConnectionUtil.getConnection();
        Channel channel = conn.createChannel();




        // ====================== 创建私信队列、交换机、并绑定 ===================
        // 创建一个死信队列(就是一个普通的队列)
        channel.queueDeclare(DLX_QUEUE_NAME, true, false, false, null);
        // 创建一个死信交换机
        channel.exchangeDeclare(DLX_EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true, false, false, null);
        // 绑定死信队列和私信交换机
        channel.queueBind(DLX_QUEUE_NAME, DLX_EXCHANGE_NAME, DLX_ROUTE_KEY);



        // channel.queueDeclare ： 创建一个队列
        /*
         * 声明（创建）队列
         * 参数1：队列名称
         * 参数2：消息是否持久化 为true时server重启队列不会消失
         * 参数3：队列是否是独占的，如果为true只能被一个connection使用，其他连接建立时会抛出异常
         * 参数4：队列不再使用时是否自动删除（没有连接，并且没有未处理的消息)
         * 参数5：建立队列时的其他参数(创建死信队列、延时队列使用)
         */

        // 设置死信队列
        Map<String , Object> arguments = new HashMap<String , Object>();
        arguments.put("x-dead-letter-exchange" , DLX_EXCHANGE_NAME); // 设置死信交换机的名称
        arguments.put("x-dead-letter-routing-key", DLX_ROUTE_KEY); // 这个DLX指定routing key，如果没有特殊指定，则使用原队列的routing key
        channel.queueDeclare(QUEUE_NAME, true, false, false, arguments);

        // 创建交换器
        /*
         * 参数1：exchange 交换机名称
         * 参数2：type 交换机类型
         * 参数3：durable 此交换机是否持久化,durable设置为true表示持久化,反之是非持久化,持久化的可以将交换器存盘,在服务器重启的时候不会丢失信息
         * 参数4：autoDelete 是否自动删除,当最后一个绑定到 exchange 上的队列被删除后，exchange 没有绑定的队列了，自动删除该 exchange
         * 参数5：internal 是否内置,如果设置 为true,则表示是内置的交换器,客户端程序无法直接发送消息到这个交换器中,只能通过交换器路由到交换器的方式
         * 参数6：arguments 其它一些结构化参数比如:alternate-exchange
         */
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true, false, false, null);

        // 将队列和交换器通过路由键进行绑定
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTE_KEY);




        System.out.println("已创建交换器和队列并已绑定");
        channel.close();
        conn.close();
    }
}
