package cn.malongbao.rabbitmq.delay_queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;

/**
 * Description:
 * date: 2022/1/5 18:09
 *
 * @author Hy
 * @since JDK 1.8
 */
public class SendMsgController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    Logger logger = LoggerFactory.getLogger(DeadLetterQueueConsumer.class);

    @GetMapping("sendMsg/{message}")
    public void sendMsg(@PathVariable String message) {
        logger.info("当前时间： {},发送一条信息给两个 TTL 队列:{}", new Date(), message);
        rabbitTemplate.convertAndSend("X", "XA", "消息来自 ttl 为 10S 的队列: " + message);
        rabbitTemplate.convertAndSend("X", "XB", "消息来自 ttl 为 40S 的队列: " + message);
    }
}
