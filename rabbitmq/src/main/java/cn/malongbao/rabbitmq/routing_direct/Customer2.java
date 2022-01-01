package cn.malongbao.rabbitmq.routing_direct;

import cn.malongbao.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Customer2 {
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        //通道声明交换机和交换的类型
        channel.exchangeDeclare("logs_direct", "direct");

        //创建一个临时队列
        String queue = channel.queueDeclare().getQueue();

        //基于route key绑定队列和交换机
        channel.queueBind(queue, "logs_direct", "info");

        //获取消费的消息
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("Customer2:" + new String(body));
            }
        });

    }
}
