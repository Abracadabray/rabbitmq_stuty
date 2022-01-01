package cn.malongbao.rabbitmq.hello_world;

import cn.malongbao.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;

/**
 * @param
 * @author Hy
 * @date 2021/12/29 10:03
 * @return 生产者和消费者的配置需要一致
 */
public class Customer {

    public static void main(String[] args) throws Exception {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        String[] queues = new String[]{"hello1", "hello2", "hello3", "hello4"};
        //虽然可以重新绑定队列，但是实际上每次绑定的都是一个队列，不会绑定多个队列；
        for (String queue : queues) {
            channel.queueDeclare(queue, false, false, false, null);
            channel.basicConsume(queue, true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("new String(body) = " + new String(body));
                }
            });
        }
    }

    //要使用main方法才可以消费消息
    //Junit测试不支持多线程模型，以junit形式运行的话，没有办法一直监听消息
    @Test
    public void getMsg() throws Exception {
        Connection connection = RabbitMQUtils.getConnection();

        //创建通道
        Channel channel = connection.createChannel();
        //通道绑定对象
        channel.queueDeclare("hello", false, false, false, null);

        /**
         * 消费消息：
         * Junit不支持多线程模型，主方法运行完之后，会直接杀死子线程；
         * 所以这里会导致，消费完消息之后，子线程还没有触发回调，主线程已经结束了。
         * 解决方法：写在main方法中
         */
        channel.basicConsume("hello", true, new DefaultConsumer(channel) {
            //这里是消费完消息之后的回调
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("new String(body) = " + new String(body));
            }
        });

        /**
         * 一般customer是一直与队列建立连接的,需要一直监听
         * 如果直接关掉channel和connection的话，会出现初次可以消费消息，但是之后队列中再有消息的话，消费者就不会消费了；
         */
//        channel.close();
//        connection.close();
    }
}
