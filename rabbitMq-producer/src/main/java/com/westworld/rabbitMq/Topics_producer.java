package com.westworld.rabbitMq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 通配符分发模式
 */
public class Topics_producer {
    public static void main(String[] args) {
        try {
            //1.创建连接工厂
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("127.0.0.1");   //ip
            connectionFactory.setPort(5672);    //端口
            connectionFactory.setVirtualHost("/westworld");   //rabbitMQ的虚拟机
            connectionFactory.setUsername("luqifan");   //用户名
            connectionFactory.setPassword("luqifan");   //密码
            //2.创建connection连接
            Connection connection = connectionFactory.newConnection();
            //3.创建channel通道
            Channel channel = connection.createChannel();
            //4.创建exchange交换机
            /**
             * exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, Map<String, Object> arguments)
             * 参数：
             *      exchange  交换机名称
             *      type      交换机类型
             *          DIRECT("direct"), 定向
             *          FANOUT("fanout"), 广播
             *          TOPIC("topic"),   通配符
             *          HEADERS("headers"); 参数匹配
             *
             *      durable  是否持久化
             *      autoDelete  是否自动删除
             *      arguments  参数
             */
            String exchangeName = "topic";
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC,false,false,null);
            //5.创建queue队列
            /**
             * queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
             * 参数：
             *      queue  队列名称
             *      durable  是否持久化
             *      exclusive  是否独占一个消费者
             *      autoDelete 是否自动删除
             *      arguments   参数
             */
            String queueName1 = "topic_queue1";
            String queueName2 = "topic_queue2";

            channel.queueDeclare(queueName1,false,false,false,null);  //创建queue队列1
            channel.queueDeclare(queueName2,false,false,false,null);  //创建queue队列2

            //6.exchang交换机和queue队列进行绑定
            /**
             * queueBind(String queue, String exchange, String routingKey, Map<String, Object> arguments)
             * 参数：
             *      queue  队列名称
             *      exchange  交换机名称
             *      routingKey  路由键，绑定规则（如果交换机的类型为fanout时，routingKey设置为""）
             *      arguments   参数
             */
            channel.queueBind(queueName1,exchangeName,"*.error",null);   //通配符 *为一个单词，#为0个或者多个单词
            channel.queueBind(queueName2,exchangeName,"order.#",null);
            channel.queueBind(queueName2,exchangeName,"*.*",null);
            //7.发送消息
            /**
             * basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
             * 参数：
             *      exchange  交换机
             *      routingKey  路由规则
             *      props    配置信息
             *      body     发送的消息
             */
            String msg = "topic通配符方式发送的测试消息";
            channel.basicPublish(exchangeName,"order.test",null,msg.getBytes());
            //8.断开连接
            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
