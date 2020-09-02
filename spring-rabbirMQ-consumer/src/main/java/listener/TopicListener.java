package listener;


import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class TopicListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        byte[] messageBody = message.getBody();
        System.out.println(new String(messageBody));
    }
}
