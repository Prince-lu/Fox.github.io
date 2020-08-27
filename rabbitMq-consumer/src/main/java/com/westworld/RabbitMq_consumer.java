package com.westworld;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费消息
 */
public class RabbitMq_consumer {
    public static void main(String[] args) {
        try {
             //1、创建连接工厂
             ConnectionFactory factory = new ConnectionFactory();
             factory.setHost("127.0.0.1");   //ip
             factory.setPort(5672);        //端口
             factory.setVirtualHost("/westworld");    //rabbitMq的虚拟机
             factory.setUsername("luqifan");    //用户名
             factory.setPassword("luqifan");    //密码
             //2、创建connection连接
            Connection connection = factory.newConnection();
            //3、创建channel管道
            Channel channel = connection.createChannel();

            //4、创建队列,如果已经有相同队列名称的队列，则不会重复创建队列
            /**
             * queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
             * 参数:
             *  1、queue：队列名称
             *  2、durable： 是否持久化
             *  3、exclusive： 是否独占
             *  4、autoDelete： connection关闭，是否删除queue
             *  5、arguments: 参数
             */
            channel.queueDeclare("hello",false,false,false,null);



            //6、回调函数
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
                    System.out.println("envelope = " + envelope.getExchange());
                    System.out.println("envelope = " + envelope.getRoutingKey());
                    System.out.println("properties = " + properties);
                    System.out.println("body = " + new String(body));
                }
            };

            //5、消费数据
            /**
             * basicConsume(String queue, boolean autoAck, Consumer callback)
             * 参数：
             *  1、queue：队列名称
             *  2、autoAck：是否指定确认回执
             *  3、callback： 回调函数
             */
            channel.basicConsume("hello",true,callback);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
