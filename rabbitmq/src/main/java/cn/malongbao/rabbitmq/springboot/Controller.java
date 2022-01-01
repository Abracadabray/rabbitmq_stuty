package cn.malongbao.rabbitmq.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    public Provider provider;


    @GetMapping(value = "/test")
    public void test(){
        provider.send();
    }
}
