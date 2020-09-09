producer 生产端保证消息可靠投递的2种方式：
    方式一：  confirm 确认模式
        说明：
            生产者发送消息后，消息被送到了exchange交换机，则会调用confirmback回调函数，
            可重写confirmback回调函数处理上送消息失败或成功的情况
        配置方式：
            1.确认模式开启：ConnectionFactory中开启publisher-confirms="true"
            2.rabbitTemplate.setConfirmCallback()定义回调函数

    方式二：  return 回退模式
        说明：
            当消息从exchang交换机路由到queue队列时失败，
            会出发returnCallback回调函数来通知生产者，并在returnCallback中进行重发处理
        配置方式：
            1.确认模式开启：ConnectionFactory中开启publisher-returns="true"
            2.rabbitTemplate.setReturnCallback()定义回调函数
            3、设置Exchange处理消息的方式
                 1.如果消息没有路由到queue中，则丢弃消息
                 2.如果消息没有路由到queue中，则调用returnCallBcak方法进行消息返回生产者----Mandatory需要开启