1. 运行 CreateExchangeAndQueue 创建MQ的交换机和队列并绑定
2. 运行 Producer 发送消息
3. 查看rabbitmq  队列信息 (队列名称 ttl-queue和dlx-ttl-queue ) , 2s后消息从ttl-queue 转向了dlx-ttl-queue
4. 运行 DLXConsumer 死信队列消费消息 