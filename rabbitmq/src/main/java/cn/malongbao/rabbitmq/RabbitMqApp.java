package cn.malongbao.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"cn.malongbao.rabbitmq.springboot"})
public class RabbitMqApp {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMqApp.class,args);

//        这个不能写在Main方法中，写在main中肯定是空的
//        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
//        System.out.println(webApplicationContext);
//        Provider bean = webApplicationContext.getBean("provider", Provider.class);
//        System.out.println(bean);
    }
}
