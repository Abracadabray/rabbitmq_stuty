package cn.malongbao.rabbitmq.routing_topic;

import cn.malongbao.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Customer1 {
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        //交换机的名称topics
        channel.exchangeDeclare("topics", "topic");

        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue, "topics", "user.*");

        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("Customer1:" + new String(body));
            }
        });
    }
}
