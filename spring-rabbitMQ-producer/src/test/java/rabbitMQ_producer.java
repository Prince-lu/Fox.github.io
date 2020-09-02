import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:rabbitmq.xml")
public class rabbitMQ_producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //基础队列
    @Test
    public void testHelloWorld(){
        rabbitTemplate.convertAndSend("spring_queue","helloworld spring rabbitMQ.....");
    }

    //广播队列
    @Test
    public  void  testFanout(){
        rabbitTemplate.convertAndSend("spring_fanout_exchange","","fanout广播模式消息测试.....");
    }

    //通配符队列
    @Test
    public void  testTopic(){
        rabbitTemplate.convertAndSend("spring_topic_exchange","user.haha.lala","topic模式发送的给队列2的消息.....");
    }
}
