Consumer消费端的保证消息的可靠性的方式： Ack 机制

ack指Acknowledge，确认。表示consumer消费端收到消息后的确认方式

有三种确认方式：
    -自动确认：acknowledge="none"
    -手动确认：acknowledge="manual"
    -根据异常情况确认：acknowledge="auto"   (麻烦，不常用)

   其中自动确认指，消息被consumer接收到，则自动确认，并将message从rabbitMQ的消息缓存中移除。
   但是实际业务中，会出现cosumer接收到消息后，在业务处理的时候出现异常，导致该消息丢失。
   所以需要使用手动确认方式，在业务处理成功后，调用channel.basicAck()进行手动签收。如果出现
   异常，则调用channel.basicNack()方法通知broker，让其broker重新发送消息到queue中。

Consumer 消费端消费限流的机制 Qos机制

    作用: 削峰填谷 防止瞬间访问量增大，系统去MQ中取出的数据过多，导致宕机。
          限流机制可以保证系统去MQ中消费消息的数量，消费完成签收后，才继续去queue队列中继续拉去消息。

    配置方式：
        1.消费端的消费确认模式开启手动确认， acknowledge="manual"
        2.在<rabbit:listener-container>中配置prefetch属性设置消费端一次拉去多少消息去消费。
          消费完成，签收确认后，才继续去queue对列中继续拉去消息