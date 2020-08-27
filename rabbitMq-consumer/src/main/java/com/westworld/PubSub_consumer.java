package com.westworld;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 广播模式的消息队列
 */
public class PubSub_consumer {
    public static void main(String[] args) {
        try {
            //1.创建连接工厂
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("127.0.0.1");  //ip
            connectionFactory.setPort(5672);     //端口
            connectionFactory.setVirtualHost("/westworld");    //rabbitMQ的虚拟机
            connectionFactory.setUsername("luqifan");    //用户名
            connectionFactory.setPassword("luqifan");    //密码
            //2.创建connection连接
            Connection connection = connectionFactory.newConnection();
            //3.创建channel管道
            Channel channel = connection.createChannel();

            Consumer callback = new DefaultConsumer(channel){
                /**
                 * 回调方法，收到消息之后会自动执行该方法
                 * handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
                 * 参数：
                 *  1、consumerTag: 标识
                 *  2、envelope： 获取一些信息：交换机、路由key...
                 *  3、properties：配置信息
                 *  4、body：消息队列中的消息数据
                 */
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("consumerTag = " + consumerTag);
                    System.out.println("envelope.getRoutingKey() = " + envelope.getRoutingKey());
                    System.out.println("envelope.getExchange() = " + envelope.getExchange());
                    System.out.println("properties = " + properties);
                    System.out.println("body = " + new String(body));
                }
            };
            //4.消费数据
            /**
             * basicConsume(String queue, boolean autoAck, Consumer callback)
             参数：
             *  1、queue：队列名称
             *  2、autoAck：是否指定确认回执
             *  3、callback： 回调函数
             */
            String queueName = "fanout-queue1";
            channel.basicConsume(queueName,true,callback);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
