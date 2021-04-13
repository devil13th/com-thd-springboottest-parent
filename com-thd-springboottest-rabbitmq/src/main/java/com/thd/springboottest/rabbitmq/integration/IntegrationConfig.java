package com.thd.springboottest.rabbitmq.integration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IntegrationConfig {
    @Bean
    public Queue TestIntegrationConfirmQueue() {
        return new Queue("TestIntegrationConfirmQueue",true);  //true 是否持久
    }

    @Bean
    DirectExchange TestIntegrationConfirmExchange() {return new DirectExchange("TestIntegrationConfirmExchange"); }

    //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
    @Bean
    Binding bindingConfirm() {
        return BindingBuilder.bind(TestIntegrationConfirmQueue()).to(TestIntegrationConfirmExchange()).with("ConfirmRouting");
    }
}
