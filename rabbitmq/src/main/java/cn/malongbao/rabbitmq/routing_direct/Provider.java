package cn.malongbao.rabbitmq.routing_direct;

import cn.malongbao.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 不同的消息被不同的消费者接收
 */
public class Provider {
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("logs_direct", "direct");

        String[] routingKeys = new String[]{"info", "error", "warning"};
        for (String routingKey : routingKeys) {
            channel.basicPublish("logs_direct", routingKey, null, routingKey.getBytes());
        }
        RabbitMQUtils.closeConnection(connection, channel);
    }
}
