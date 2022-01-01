package com.qiuqiu.rabbit.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Description:
 * date: 2021/12/21 23:24
 *
 * @author Hy
 * @since JDK 1.8
 */
public class RabbitMQUtils {
    //得到一个连接的 channel
    public static Channel getChannel() throws Exception{
//创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("121.4.249.70");
        factory.setUsername("admin");
        factory.setPassword("admin");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        return channel;
    }
}