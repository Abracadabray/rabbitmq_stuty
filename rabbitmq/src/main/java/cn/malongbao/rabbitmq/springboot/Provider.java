package cn.malongbao.rabbitmq.springboot;

import cn.malongbao.rabbitmq.RabbitMqApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = RabbitMqApp.class)
@RunWith(SpringRunner.class)
@Service
public class Provider {
    @Autowired
    RabbitTemplate rabbitTemplate;

    //Hello_word模式
    @Test
    public void send() {
        //routingKey：对应队列的正则表达式，Object：具体要发送的内容
        rabbitTemplate.convertAndSend("hello_queue", "hello world!");
    }

    //------------------------------------------------------------------------------
    //Work模式
    @Test
    public void send1() {
        for (int i = 1; i <= 10; i++) {
            rabbitTemplate.convertAndSend("work_queue", "hello work:" + i);
        }
    }

    //------------------------------------------------------------------------------
    //fanout模式
    @Test
    public void send2() {
        rabbitTemplate.convertAndSend("fanout_exchange", "", "fanout!");
    }

    //------------------------------------------------------------------------------
    //routing-direct模式
    @Test
    public void send3() {
        rabbitTemplate.convertAndSend("direct_exchange", "info", "info相关信息");
        rabbitTemplate.convertAndSend("direct_exchange", "warning", "warning相关信息");
        rabbitTemplate.convertAndSend("direct_exchange", "error", "error相关信息");
    }

    //------------------------------------------------------------------------------
    //routing-topic模式
    @Test
    public void send4() {
        rabbitTemplate.convertAndSend("topic_exchange", "user", "user的信息");
        rabbitTemplate.convertAndSend("topic_exchange", "user.info", "user.info的信息");
        rabbitTemplate.convertAndSend("topic_exchange", "user.info.age", "user.info.age的信息");
    }
}
