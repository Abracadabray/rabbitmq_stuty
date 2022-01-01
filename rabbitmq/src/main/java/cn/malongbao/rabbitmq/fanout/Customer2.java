package cn.malongbao.rabbitmq.fanout;

import cn.malongbao.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @param
 * @author Hy
 * @date 2021/12/29 10:03
 * @return 生产者和消费者的配置需要一致
 */
public class Customer2 {

    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("logs","fanout");
        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue,"logs","");
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("Customer2:" + new String(body));
            }
        });
    }

}
