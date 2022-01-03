package cn.malongbao.rabbitmq.publish_confirm;

import cn.malongbao.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * Description:发布确认
 * date: 2022/1/3 21:52
 *
 * @author Hy
 * @since JDK 1.8
 */
public class PublishConfirmBatch {
    //批量的发布确认：需要生产者自当写逻辑确认
    //channel.waitForConfirms();确认当前队列中的所有消息，有多少个就确认多少个
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        String queue = channel.queueDeclare().getQueue();
        //开启发布确认
        channel.confirmSelect();

        int batchSize = 100;
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            String msg = "消息" + i;
            channel.basicPublish("", queue, null, msg.getBytes());
            //服务端返回false或者超时未返回，生产者可以消息重发
            if (i % batchSize == 0 && channel.waitForConfirms()) {
                continue;
            }
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - beginTime) + "ms");
    }

}
