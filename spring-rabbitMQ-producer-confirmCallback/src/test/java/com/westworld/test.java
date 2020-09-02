package com.westworld;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 生产端发送消息的可靠性保障的两种方式-------confirms确认模式 和  return回退模式
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:rabbitmq.xml")
public class test {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 消息可靠性投递  confirms确认模式-----生产者发送消息后，消息如果被路由到了exchange交换机，则会调用confirmback回调函数
     * 确认模式：
     *   步骤：
     *      1.确认模式开启：ConnectionFactory中开启publisher-confirms="true"
     *      2.在rabbitTemplate定义ConfirmCallback函数
     */
    @Test
    public void testConfirm(){
        //定义回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             *
             * @param correlationData   相关配置信息
             * @param ack      消息是否发送给了exchange交换机，true--成功；false--失败
             * @param cause    失败原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("confirm方法被执行.......");

                if(ack){
                    System.out.println("消息发送成功" + cause);
                }else{
                    System.out.println("消息发送失败，失败原因为：" + cause);
                }
            }
        });

        rabbitTemplate.convertAndSend("topic_exchange","aa.luqifan","消息可投靠性的消息投递.....");
    }

    /**
     * 消息可靠投递---return回退模式-----当消息从exchang交换机路由到queue队列时失败，会出发returnCallback回调函数来通知生产者
     *      回退模式：
     *               1.确认模式开启：ConnectionFactory中开启publisher-returns="true"
     *               2.在rabbitTemplate定义returnCallback函数
     *               3、设置Exchange处理消息的方式
     *                      1.如果消息没有路由到queue中，则丢弃消息
     *                      2.如果消息没有路由到queue中，则调用returnCallBcak方法进行消息返回生产者----Mandatory需要开启
     */
    @Test
    public void testReturn(){

        //定义exchange交换机处理失败消息的模式，开启后才能执行returnCallback方法
        rabbitTemplate.setMandatory(true);

        //定义returnCallback函数
            rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
                @Override
                public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                    System.out.println("returnCallBack方法执行......");
                    System.out.println("message = " + message);
                    System.out.println("replyCode = " + replyCode);
                    System.out.println("replyText = " + replyText);
                    System.out.println("exchange = " + exchange);
                    System.out.println("routingKey = " + routingKey);
                }

                //消息rout路由失败之后，可以在该returnback方法中进行重发操作等
            });

            //发送消息
        rabbitTemplate.convertAndSend("topic_exchange","bb.luqifan","消息可投靠性的消息投递.....");
    }


}
