死信队列 （Dead Letter Exchange（死信交换机） 当消息成为Dead Message后，可以把该消息发送给其他交换机，该交换机就是DLX。）
    1.说明：
        当消息成为Dead Message的时候，原queue队列通过绑定的DLX死信交换机，把Dead Message 路由给死信交换机绑定的队列中去。

    2.消息成为死信（Dead Message）的三种情况:
        1.队列的消息容量达到最大；
        2.消费者拒接消息，basicNack/basicReject，并且不把消息重新放入到原queue队列中，requeue=false；
        3.原queue队列存在过期时长设置，消息达到超时时长未被消费；

    3.原队列如何绑定死信交换机：
        在原队列的<rabbitmq: queue/>中指定 x-dead-letter-exchange  死信交换机  和   x-dead-letter-routing-key  死信交换机绑定的队列的routing key

        1.设置正常的交换机和队列
        2.设置死信交换机和队列
        3.正常的队列绑定死信交换机并指定routing key
        4.在正常队列中设置Dead Message 的过期时间和队列最大容量

         ex：
         <!--3.普通队列绑定死信交换机-->
                <rabbit:queue-arguments>
                    <!--3.1 设置死信交换机的名称  x-dead-letter-exchange -->
                    <entry key="x-dead-letter-exchange" value="dlx_exchange_DLX"></entry>
                    <!--3.2 设置路由到死信队列的routingKey  x-dead-letter-routing-key    -->
                    <entry key="x-dead-letter-routing-key" value="dlx.haha"></entry>

                    <!--4.设置普通消息成为死信消息的条件-->
                    <!--4.1设置队列的过期时间 ttl-->
                    <entry key="x-message-ttl" value="10000" value-type="java.lang.Integer"></entry>
                    <!--4.2设置队列的长度限制 max-length-->
                    <entry key="x-max-length" value="10" value-type="java.lang.Integer"></entry>
                </rabbit:queue-arguments>