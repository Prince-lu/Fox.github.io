package com.westworld.rabbitMq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 广播模式的生产者
 */
public class PubSub_producer {
    public static void main(String[] args) {
        try {
            //1.创建连接工厂
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("127.0.0.1");   //ip
            connectionFactory.setPort(5672);    //端口
            connectionFactory.setVirtualHost("/westworld");   //rabbit的虚拟机
            connectionFactory.setUsername("luqifan");   //用户
            connectionFactory.setPassword("luqifan");   //密码
            //2.创建Connection连接
            Connection connection = connectionFactory.newConnection();
            //3.创建channel管道
            Channel channel = connection.createChannel();
            //5.创建交换机
            /**
             * exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments)
             * 参数：
             *      1. exchange   交换机名称
             *      2. type       交换机类型
             *        DIRECT("direct"), 定向
             *        FANOUT("fanout"), 扇形（广播） 发送消息到每个队列中
             *        TOPIC("topic"),   通配符的方式
             *        HEADERS("headers");  参数匹配
             *
             *      3. durable    是否持久化
             *      4. autoDelete  是否自动删除
             *      5. internal  内部使用，一般为false
             *      6. arguments  参数
             */
            String exchangeName = "fanout";
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT,false,false,false,null);
            //6.创建队列
            /**
             *  queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
             *  参数
             *   1、queue: 队列名称
             *   2、durable：是否要持久化， 当mq重启之后存在
             *   3、exclusive：
             *        是否独占，只能有一个消费者
             *       当connection关闭时，是否删除队列
             *   4、autoDelete:是否自动删除。当没有connection时，自动删除
             *   5、arguments： 参数
             */
            String queueName1 = "fanout-queue1";
            String queueName2 = "fanout-queue2";
            channel.queueDeclare(queueName1,false,false,false,null);   //队列1
            channel.queueDeclare(queueName2,false,false,false,null);   //队列2
            //7.绑定队列和交换机
            /**
             * queueBind(String queue, String exchange, String routingKey)
             * 参数：
             *      1. queue  队列
             *      2. exchange  交换机
             *      3. routingKey 路由键 ，绑定规则
             *           如果交换机的类型为fanout时，routingKey设置为""
             */
            channel.queueBind(queueName1,exchangeName,"");  //队列1绑定到交换机上
            channel.queueBind(queueName2,exchangeName,"");  //队列2绑定到交换机上

            //8.发送消息
            /**
             * basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
             * 参数：
             *  1、exchange：交换机名称
             *  2、routingKey：路由名称
             *  3、props： 配置信息
             *  4、body： 真实发送的消息数据，字节数组信息
             */
            channel.basicPublish(exchangeName,"",null,"广播类型消息测试".getBytes());

            //9. 释放资源
            channel.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
