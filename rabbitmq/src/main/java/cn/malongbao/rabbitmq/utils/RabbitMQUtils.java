package cn.malongbao.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 这块之后可以使用配置的方式来获取
 */
public class RabbitMQUtils {

    //static：所有对象共享
    private static ConnectionFactory connectionFactory;

    //可以使用单例模式
    //重量级资源，需要结合单例模式进行整合。在类加载的时候执行，只执行一次
    //这样的好处是，连接功能之后只创建一次，但是每次拿到的都是一个新的链接
    static {
        connectionFactory = new ConnectionFactory();
        //设置地址和端口
        connectionFactory.setHost("121.4.249.70");
        connectionFactory.setPort(5672);

        //设置访问哪个虚拟主机
        connectionFactory.setVirtualHost("/test");

        //设置用户名和密码
        connectionFactory.setUsername("testUser");
        connectionFactory.setPassword("testUser");
    }

    public static Connection getConnection() {
        try {
            return connectionFactory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭通道和关闭连接工具方法
     *
     * @param channel
     * @param conn
     */
    public static void closeConnection(Connection conn, Channel channel) {
        try {
            if (channel != null) {
                channel.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
