package com.westworld.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
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
public class queueListener implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();     //消息标识
        try{
            //1、取出消息
            System.out.println("message = " + new String(message.getBody()));

            //2、进行业务处理
            System.out.println("进行业务处理......");
//            int i = 3/0;
            //3、业务处理成功，进行手动签收
            /**
             * basicAck(long deliveryTag, boolean multiple)
             * 参数：
             *      1. deliveryTag   消息的标识
             *      2. multiple    是否多条消息的签收
             */
            channel.basicAck(deliveryTag,true);

        }catch (Exception e){
            //3、业务处理失败，通知broker重新发送消息
            //  第三个参数：requeue    消息是否需要重回队列
            channel.basicNack(deliveryTag,true,true);
        }


    }

}
