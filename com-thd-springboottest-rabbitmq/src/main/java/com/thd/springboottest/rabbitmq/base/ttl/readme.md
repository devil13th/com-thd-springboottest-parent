1. 运行 CreateExchangeAndQueue 创建MQ的交换机和队列并绑定

2. 运行普通的消费者 TTLConsumer
3. 运行死信队列消费者 DLXConsumer
4. 运行 Producer 发送消息
5. 查看Producer的控制台，成功发送消息
   查看TTLConsumer的控制台，消息被nask，被放到了死信队列
   查看DLXConsumer的控制台，消息被死信队列消费掉

上面的过程是通过消费者nask将消息放到死信队列，也可以不运行TTLConsumer ，当消息超过2S过期后，自动发送到DLXConsumer