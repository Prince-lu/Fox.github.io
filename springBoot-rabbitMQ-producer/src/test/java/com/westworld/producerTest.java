package com.westworld;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class producerTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendMssage(){
        rabbitTemplate.convertAndSend("springboot_topic_exchange","order.hh","发送给routingKey为order.#的topic类型的消息.....");
    }

    @Test
    public void sendMssage2(){
        rabbitTemplate.convertAndSend("springboot_topic_exchange","luqifan.hh","发送给routingKey为luqifan.*的topic类型的消息.....");
    }
}
