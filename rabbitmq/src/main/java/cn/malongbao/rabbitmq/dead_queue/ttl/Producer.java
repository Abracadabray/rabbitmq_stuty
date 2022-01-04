package cn.malongbao.rabbitmq.dead_queue.ttl;

import cn.malongbao.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * Description:
 *      Producer需要设置TTL时间；
 *      Consumer1按实际速度处理消息，channel声明死信队列、普通队列；如果处理消息超时，那么将超时之后的消息传递到死信队列中；
 *      Consumer2专门处理死信队列中的消息；
 *
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

        //设置消息的 TTL 时间 10s
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().expiration("10000").build();

        //该信息是用作演示队列个数限制
        for (int i = 1; i < 11; i++) {
            String message = "info" + i;
            channel.basicPublish(NORMAL_EXCHANGE, "normal", properties, message.getBytes());
            System.out.println("生产者发送消息:" + message);
        }
    }
}
