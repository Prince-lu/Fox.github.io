package com.westworld.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * Consumer 消费限流机制
 *      1.消费端的消费确认模式开启成手动确认， acknowledge="manual"
 *      2.在<rabbit:listener-container>中配置prefetch属性设置消费端一次拉去多少消息去消费。
 *        消费完成，签收确认后，才继续去queue对列中继续拉去消息
 *
 */
@Component
public class QosListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try{
            //1.取出消息
            System.out.println(new String(message.getBody()));
            //2.业务逻辑处理
            System.out.println("处理业务逻辑------>");
            //3.签收确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);
        }catch(Exception e){
            //4.消息消费后业务处理失败，通知broker重新将消息发送到队列中
            // 第三个参数： requeue  消息重回队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),true,true);
        }

    }

}
