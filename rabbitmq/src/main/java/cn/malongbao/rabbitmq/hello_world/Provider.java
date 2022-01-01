package cn.malongbao.rabbitmq.hello_world;

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

public class Provider {


    /**
     * 一个通道可以重新绑定队列，但不意外着可以绑定多个队列
     * @throws Exception
     */
    @Test
    public void sendMsg2() throws Exception {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        String[] queues = new String[]{"hello1", "hello2", "hello3", "hello4"};
        for (String queue : queues) {
            channel.queueDeclare(queue, false, false, false, null);
            for (int i = 1; i <= 10; i++) {
                channel.basicPublish("", queue, MessageProperties.PERSISTENT_TEXT_PLAIN, ("hello world" + i ) .getBytes());
            }
        }
        RabbitMQUtils.closeConnection(connection, channel);
    }

    @Test
    public void sendMsg() throws Exception {
        //获取连接
        Connection connection = RabbitMQUtils.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
        /**
         * 将通道绑定队列
         * queue：队列名称，如果队列中不存在则自动创建
         * durable:用来定义队列特性是否要持久化 true：需要持久；
         *    如果队列不持久化，那么rabbitmq重启后，队列会丢失；即使队列持久化，重启之后消息也会丢失，要消息不丢失，需要手动的设置
         * exclusive:是否独占队列 true：是否独占队列
         * autoDelete：是否在消费完成之后并且没有监听者时会自动删除队列，true：自动删除
         * arguments：额外参数
         */
        channel.queueDeclare("hello", false, false, false, null);

        /**
         *  发布消息
         *  exchange:交换机名称
         *  basicProperties:传递消息的额外设置：（可以设置消息进行持久化）MessageProperties.PERSISTENT_TEXT_PLAIN——消息持久化
         *  content:具体消息内容
         */
        //TODO:真正发送消息的操作，而不是说绑定什么队列就往什么队列发送消息
        //绑定hello，这里写hello1，其实这里是会往hello1中写数据
        channel.basicPublish("", "hello", MessageProperties.PERSISTENT_TEXT_PLAIN, "hello rabbitmq".getBytes());

        RabbitMQUtils.closeConnection(connection, channel);
    }
}
