package cn.malongbao.rabbitmq.dead_queue.max_lenght;

import cn.malongbao.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * Description:
 * date: 2022/1/4 10:05
 *
 * @author Hy
 * @since JDK 1.8
 */
public class Producer {
    private static final String NORMAL_EXCHANGE = "normal_exchange";

    public static void main(String[] argv) throws Exception {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);

        //该信息是用作演示队列个数限制
        for (int i = 1; i < 20; i++) {
            String message = "info" + i;
            channel.basicPublish(NORMAL_EXCHANGE, "normal", null, message.getBytes());
            System.out.println("生产者发送消息:" + message);
        }
    }
}
