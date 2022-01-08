package cn.malongbao.rabbitmq.delay_queue;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.io.IOException;
import java.util.Date;

/**
 * Description:
 * date: 2022/1/5 18:10
 *
 * @author Hy
 * @since JDK 1.8
 */

public class DeadLetterQueueConsumer {
    Logger logger = LoggerFactory.getLogger(DeadLetterQueueConsumer.class);

    @RabbitListener(queues = "QD")
    public void receiveD(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        logger.info("当前时间： {},收到死信队列信息{}", new Date().toString(), msg);
    }
}
