package com.westworld.producer;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class producerConfig {

    private static final String EXCHANGE_NAME="springboot_topic_exchange";
    private static final String QUEUE_NAME1="springboot_topic_queue1";
    private static final String QUEUE_NAME2="springboot_topic_queue2";

    //1.创建exchange交换机
    @Bean("topicExchange")
    public Exchange creatExchange(){
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }

    //2.创建queue队列
    @Bean("topicQueue1")
    public Queue creatQueue1(){
        return QueueBuilder.durable(QUEUE_NAME1).build();
    }

    @Bean("topicQueue2")
    public Queue creatQueue2(){
        return QueueBuilder.durable(QUEUE_NAME2).build();
    }

    //3.进行exchange和queue的绑定
    @Bean
    public Binding bindingQueueExchange(@Qualifier("topicExchange") Exchange exchange, @Qualifier("topicQueue1") Queue queue1){
        return BindingBuilder.bind(queue1).to(exchange).with("order.#").noargs();
    }

    @Bean
    public Binding bindingQueueExchange2(@Qualifier("topicExchange") Exchange exchange, @Qualifier("topicQueue2") Queue queue1){
        return BindingBuilder.bind(queue1).to(exchange).with("name.#").noargs();
    }

    @Bean
    public Binding bindingQueueExchange3(@Qualifier("topicExchange") Exchange exchange, @Qualifier("topicQueue2") Queue queue1){
        return BindingBuilder.bind(queue1).to(exchange).with("luqifan.*").noargs();
    }

}
