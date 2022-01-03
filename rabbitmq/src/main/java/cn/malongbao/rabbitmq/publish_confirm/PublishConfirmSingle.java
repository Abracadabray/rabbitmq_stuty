package cn.malongbao.rabbitmq.publish_confirm;

import cn.malongbao.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * Description:发布确认
 * date: 2022/1/3 21:52
 * @author Hy
 * @since JDK 1.8
 */
public class PublishConfirmSingle {
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        String queue = channel.queueDeclare().getQueue();
        //开启发布确认
        channel.confirmSelect();
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            String msg = "消息" + i;
            channel.basicPublish("", queue, null, msg.getBytes());
            //服务端返回false或者超时未返回，生产者可以消息重发
            if (channel.waitForConfirms()) {
                continue;
            }
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - beginTime) + "ms");
    }

}
