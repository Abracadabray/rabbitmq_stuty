package cn.malongbao.rabbitmq.work_queue;

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
        channel.queueDeclare("hello", false, false, false, null);

        //一次只接受一条未确认的消息
        channel.basicQos(1);
        //参数2 false:关闭自动确认消息
        channel.basicConsume("hello", true, new DefaultConsumer(channel) {
//        channel.basicConsume("hello", false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(4000);   //处理消息比较慢 一秒处理一个消息
                    System.out.println("Customer2: " + new String(body));
//                    channel.basicAck(envelope.getDeliveryTag(),false);//手动确认消息
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


//        channel.basicConsume("hello", true, new DefaultConsumer(channel) {
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                try {
//                    Thread.sleep(4000);   //处理消息比较慢 一秒处理一个消息
//                    System.out.println("Customer2: " + new String(body));
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

}
