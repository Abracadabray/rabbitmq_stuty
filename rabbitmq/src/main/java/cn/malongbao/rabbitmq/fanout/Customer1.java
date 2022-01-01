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
public class Customer1 {
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        //绑定交换机，交换机名称是logs
        channel.exchangeDeclare("logs", "fanout");
        channel.basicQos(1);
        //创建临时队列，队列名称是临时的
        String queue = channel.queueDeclare().getQueue();
//        System.out.println(queue);
        //将临时队列绑定到交换机
        channel.queueBind(queue, "logs", "");
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
//        channel.basicConsume(queue, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    System.out.println("Customer1:" + new String(body));
                    Thread.sleep(100);
                    channel.basicAck(envelope.getDeliveryTag(),false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }


}
