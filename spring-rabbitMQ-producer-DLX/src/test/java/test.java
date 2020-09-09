import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:rabbitmq.xml")
public class test {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendMsg(){

        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("confrim回调函数执行成功------->");
                if (ack) {
                    System.out.println("消息推送成功");
                }else{
                    System.out.println("消息推送失败");
                }
            }
        });
        //消费到达超时时间未被消费
//        rabbitTemplate.convertAndSend("normal_exchange_DLX","normal.test","死信消息测试------>");

        //队列中的消息数达到最大时，其他的消息成为死信队列
//        for (int i = 0; i < 20; i++) {
//
//            rabbitTemplate.convertAndSend("normal_exchange_DLX","normal.test","死信消息测试------>");
//        }

        //消费者拒收消费消息，并且不重回队列时，会成为死信队列
        rabbitTemplate.convertAndSend("normal_exchange_DLX","normal.test","死信消息测试------>");

    }
}
