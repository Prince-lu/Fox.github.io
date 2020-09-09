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
    public void test(){
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             * 消息可靠性投递， confirm确认模式--生产者发送的消息到Exchange交换机后，会调用回调函数
             * @param correlationData 相关配置信息
             * @param ack      消息是否投递成功
             * @param cause    消息投递失败的原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("confirmCallBack回调函数执行.....");
                if(ack){
                    System.out.println("消息投递成功---->");
                }else{
                    System.out.println("消息投递失败------->");
                }
            }
        });

        rabbitTemplate.convertAndSend("order_exchange","order.aaa","延迟队列模式------下单成功.....");


    }
}
