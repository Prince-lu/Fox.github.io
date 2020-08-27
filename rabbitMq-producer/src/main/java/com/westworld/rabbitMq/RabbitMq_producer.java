package com.westworld.rabbitMq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发送消息
 */
public class RabbitMq_producer {

    private static String QUEUE_NAME = "hello";


    public static void main(String[] args) {
        String message = "luqifan";
        try {
            //1.创建连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");   //ip
            factory.setPort(5672);    //端口
            factory.setVirtualHost("/westworld");   //rabbit的虚拟机
            factory.setUsername("luqifan");    //用户名
            factory.setPassword("luqifan");    //密码

            //2.建立connection连接
            Connection connection = factory.newConnection();
            //3.创建channel管道连接到RabbitMq的Virtual虚拟机上
            Channel channel = connection.createChannel();
            /**
             * 4、创建Queue队列
             * queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
             * 参数
             *  1、queue: 队列名称
             *  2、durable：是否要持久化， 当mq重启之后存在
             *  3、exclusive：
             *       是否独占，只能有一个消费者
             *      当connection关闭时，是否删除队列
             *  4、autoDelete:是否自动删除。当没有connection时，自动删除
             *  5、arguments： 参数
             */
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            //5、发送消息
            /**
             * basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
             * 参数：
             *  1、exchange：交换机名称
             *  2、routingKey：路由名称
             *  3、props： 配置信息
             *  4、body： 真实发送的消息数据，字节数组信息
             */
            for (int i = 0; i < 100 ; i++) {
                channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            }


            channel.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
