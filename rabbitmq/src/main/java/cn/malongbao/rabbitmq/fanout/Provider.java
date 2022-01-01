package cn.malongbao.rabbitmq.fanout;

import cn.malongbao.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.Test;

/**
 * @param
 * @author Hy
 * @date 2021/12/26 20:57
 * @return
 */
public class Provider {
    @Test
    public void sendMsg2() throws Exception {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机类型
        channel.exchangeDeclare("logs", "fanout");
        channel.queueDeclare("hello", false, false, false, null);
        //发布消息
        for (int i = 1; i <= 10; i++) {
            channel.basicPublish("logs", "", null, ("fanout" + i).getBytes());
        }
        RabbitMQUtils.closeConnection(connection, channel);
    }
}
