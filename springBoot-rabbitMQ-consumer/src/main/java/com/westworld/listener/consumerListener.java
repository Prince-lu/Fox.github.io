package com.westworld.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class consumerListener{

    @RabbitListener(queues = "springboot_topic_queue1")
    public void ListenerQueue1(Message message){
        byte[] body = message.getBody();
        System.out.println("message = " + new String(body));
    }

    @RabbitListener(queues = "springboot_topic_queue2")
    public void ListenerQueue2(Message message){
        byte[] body = message.getBody();
        System.out.println("message = " + new String(body));
    }

}
