package cn.malongbao.rabbitmq.work_queue;

import cn.malongbao.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import org.junit.Test;

/**
 * @param
 * @author Hy
 * @date 2021/12/26 20:57
 * @return
 */

/**
 * 总结:
 * 1、默认情况下，RabbitMQ将按顺序将每个消息发送给下一个使用者。平均而言，每个消费者都会收到相同数量的消息。这种分发消息的方式称为循环
 * 2、消费者可以自行设置接受消息的策略，但是这个只是代表对单个消费者生效，而不是代表所以消费者；
 * 3、一次只接受一条未确认的消息：channel.basicQos(1)，channel.basicConsume("hello", false, new DefaultConsumer(channel)，关闭自动确认消息。
 * 消息实现“能者多劳”，是需要所有消费者共同配合的，所有消费者都需要配置以上的配置；
 */
public class Provider {
    @Test
    public void sendMsg2() throws Exception {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("hello", false, false, false, null);
        for (int i = 1; i <= 10; i++) {
            channel.basicPublish("", "hello", MessageProperties.PERSISTENT_TEXT_PLAIN, ("hello world" + i).getBytes());
        }
        RabbitMQUtils.closeConnection(connection, channel);
    }
}
