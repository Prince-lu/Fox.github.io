package com.westworld.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * Consumer ACK的机制
 *  1、在监听容器中 设置手动签收： acknowledge="manual"       manual--手动签收，
 *  2、让监听器类实现ChannelAwareMessageListener，并复写onMessage方法
 *  3、如果消息成功处理，则调用channel的basicAck()签收
 *  3、如果消息处理失败，则调用channel的basicNack()拒绝签收，broker重新发送消息给consumer
 */
@Component
public class AckListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        //消费的消息标识
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try{

            //1.获取消息
            System.out.println(message.getBody().toString());

            //2.处理业务逻辑
            System.out.println("处理业务逻辑-------->");

            //3.发送ack确认回执
            channel.basicAck(deliveryTag,true);
        }catch (Exception e){
            //3.业务逻辑处理失败，通知broker重新发送消息给queue
            //requeue: 是否重新把消息发回队列
            channel.basicNack(deliveryTag,true,false);
        }

    }
}
