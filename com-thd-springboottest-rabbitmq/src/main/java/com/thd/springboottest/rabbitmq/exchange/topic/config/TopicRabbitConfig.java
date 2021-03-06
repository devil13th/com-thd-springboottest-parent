package com.thd.springboottest.rabbitmq.exchange.topic.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.thd.springboottest.rabbitmq.exchange.topic.config.TopicRabbitConfig
 *
 * @author: wanglei62
 * @DATE: 2020/2/14 16:27
 **/
@Configuration
public class TopicRabbitConfig {
    //绑定键 bingdingKey
    public final static String man = "topic.man";
    public final static String woman = "topic.woman";
    public final static String child = "topic.child";

    @Bean
    public Queue firstQueue() {
        return new Queue(TopicRabbitConfig.man);
    }

    @Bean
    public Queue secondQueue() {
        return new Queue(TopicRabbitConfig.woman);
    }

    @Bean
    public Queue thirdQueue() {
        return new Queue(TopicRabbitConfig.child);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("topicExchange");
    }


    //将firstQueue和topicExchange绑定,而且绑定的键值为topic.man
    //这样只要是消息携带的路由键是topic.man,才会分发到该队列
    @Bean
    Binding bindingExchangeMessage() {
        return BindingBuilder.bind(firstQueue()).to(exchange()).with(man);
    }

    //将secondQueue和topicExchange绑定,而且绑定的键值为用上通配路由键规则topic.#
    // 这样只要是消息携带的路由键是以topic.开头,都会分发到该队列
    @Bean
    Binding bindingExchangeMessage2() {
        return BindingBuilder.bind(secondQueue()).to(exchange()).with(woman);
    }

    //将thidQueue和topicExchange绑定,而且绑定的键值为用上通配路由键规则topic.main
    // 这样只要是消息携带的路由键是以topic.main开头,都会分发到该队列和firstqueue 达到分发的效果
    @Bean
    Binding bindingExchangeMessage3() {
        return BindingBuilder.bind(thirdQueue()).to(exchange()).with("topic.#");
    }

}
