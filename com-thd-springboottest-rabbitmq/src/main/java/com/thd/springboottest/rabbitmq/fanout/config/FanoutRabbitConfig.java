package com.thd.springboottest.rabbitmq.fanout.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.thd.springboottest.rabbitmq.fanout.config.FanoutRabbitConfig
 *
 * @author: wanglei62
 * @DATE: 2020/8/4 14:35
 **/
@Configuration
public class FanoutRabbitConfig {
    /**
     * 创建广播形式的交换机
     *
     * @return 交换机实例
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        System.out.println("【【【交换机实例创建成功】】】");
        return new FanoutExchange("fanoutExchange");
    }


    /**
     * 测试队列一
     * @return 队列实例
     */
    @Bean
    public Queue queue1() {
        System.out.println("【【【测试队列一实例创建成功】】】");
        return new Queue("fanoutQueue01");
    }

    /**
     * 测试队列二
     * @return 队列实例
     */
    @Bean
    public Queue queue2() {
        System.out.println("【【【测试队列二实例创建成功】】】");
        return new Queue("fanoutQueue02");
    }

    /**
     * 绑定队列一到交换机
     * @return 绑定对象
     */
    @Bean
    public Binding bingQueue1ToExchange() {
        System.out.println("【【【绑定队列一到交换机成功】】】");
        return BindingBuilder.bind(queue1()).to(fanoutExchange());
    }

    /**
     * 绑定队列二到交换机
     * @return 绑定对象
     */
    @Bean
    public Binding bingQueue2ToExchange() {
        System.out.println("【【【绑定队列二到交换机成功】】】");
        return BindingBuilder.bind(queue2()).to(fanoutExchange());
    }
}
