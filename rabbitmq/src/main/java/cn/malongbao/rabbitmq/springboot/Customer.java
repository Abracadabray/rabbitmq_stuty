package cn.malongbao.rabbitmq.springboot;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Customer {
    @Autowired
    RabbitTemplate rabbitTemplate;

    //hello_world
    @RabbitListener(queuesToDeclare = @Queue("hello_queue"))
    public void receive(String msg) {
        System.out.println("msg1 = " + msg);
    }

    //----------------------------------------------------------------------------
    //work
    @RabbitListener(queuesToDeclare = @Queue("work_queue"))
    public void receive2(String msg) {
        System.out.println("customer work1:" + msg);
    }

    @RabbitListener(queuesToDeclare = @Queue("work_queue"))
    public void receive3(String msg) {
        System.out.println("customer work2:" + msg);
    }

    //----------------------------------------------------------------------------
    //fanout
    @RabbitListener(bindings = @QueueBinding(value = @Queue, exchange = @Exchange(name = "fanout_exchange", type = "fanout")))
    public void receive4(String msg) {
        System.out.println("customer fanout1:" + msg);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue, exchange = @Exchange(name = "fanout_exchange", type = "fanout")))
    public void receive5(String msg) {
        System.out.println("customer fanout2:" + msg);
    }

    //----------------------------------------------------------------------------
    //routing_direct
    @RabbitListener(bindings = @QueueBinding(value = @Queue, exchange = @Exchange(name = "direct_exchange", type = "direct"), key = {"info"}))
    public void receive6(String msg) {
        System.out.println("customer direct1:" + msg);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue, exchange = @Exchange(name = "direct_exchange", type = "direct"), key = {"warning", "error"}))
    public void receive7(String msg) {
        System.out.println("customer direct2:" + msg);
    }

    //----------------------------------------------------------------------------
    //routing_topic
    @RabbitListener(bindings = @QueueBinding(value = @Queue, exchange = @Exchange(name = "topic_exchange", type = "topic"), key = {"info.*"}))
    public void receive8(String msg) {
        System.out.println("customer topic1:" + msg);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue, exchange = @Exchange(name = "topic_exchange", type = "topic"), key = {"info.#"}))
    public void receive9(String msg) {
        System.out.println("customer topic2:" + msg);
    }


}
