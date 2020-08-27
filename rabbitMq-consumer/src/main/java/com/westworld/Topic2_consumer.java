package com.westworld;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 通配符topic的消费者2
 */
public class Topic2_consumer {
    public static void main(String[] args) {
        try {
            //1.创建连接工厂
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("127.0.0.1");  //ip
            connectionFactory.setPort(5672);   //端口
            connectionFactory.setVirtualHost("/westworld");   //rabbitMQ的虚拟机
            connectionFactory.setUsername("luqifan");   //用户名
            connectionFactory.setPassword("luqifan");   //密码
            //2.创建connection连接
            Connection connection = connectionFactory.newConnection();
            //3.创建channel通道
            Channel channel = connection.createChannel();

            Consumer callback = new DefaultConsumer(channel){
                /**
                 *
                 * @param consumerTag  标识
                 * @param envelope  获取一些信息：交换机、路由key...
                 * @param properties  配置信息
                 * @param body    消费的消息
                 * @throws IOException
                 */
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("consumerTag = " + consumerTag);
                    System.out.println("routingKey = " + envelope.getRoutingKey());
                    System.out.println("body = " + new String(body));
                }
            };

            //4消费数据
            /**
             * basicConsume(String queue, boolean autoAck, Consumer callback)
             * 参数：
             *      queue     队列
             *      autoAck   是否指定确认回值
             *      callback  回调函数
             */
            String queueName = "topic_queue2";
            channel.basicConsume(queueName,true,callback);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
