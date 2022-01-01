package cn.malongbao.rabbitmq.routing_direct;

import cn.malongbao.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Customer1 {
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("logs_direct", "direct");

        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue, "logs_direct", "error");
        channel.queueBind(queue, "logs_direct", "warning");

        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("Customer1:" + new String(body));
            }
        });
    }
}
