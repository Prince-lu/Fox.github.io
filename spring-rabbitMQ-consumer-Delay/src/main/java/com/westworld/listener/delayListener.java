package com.westworld.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

@Component
public class delayListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        //消息标识
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try{
            //1.获取订单消息
            System.out.println(new String(message.getBody()));

            //2.执行业务逻辑
            System.out.println("订单下单成功,订单号为'123456789',已通知库存系统指派商品------->");
            //3.消息手动签收
            channel.basicAck(deliveryTag,true);
        }catch (Exception e){
            //3.消息操作失败，通知broker重新发送消息回原队列
            channel.basicNack(deliveryTag,true,true);
        }

    }
}
