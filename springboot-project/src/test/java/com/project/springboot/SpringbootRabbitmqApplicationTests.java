package com.project.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootRabbitmqApplicationTests {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 1.点对点模型。
     */
    @Test
    void testHelloWorld() {
        String queueName = "CTF_Queue";
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend(queueName, "1hello world rabbitmq!!!");
        }

    }

    /**
     * 2.work 模型
     */
    @Test
    void testWorkMode() {
        String queueName = "work";
        for (int i = 1; i <= 10; i++) {
            rabbitTemplate.convertAndSend(queueName, "hello world work rabbitmq!!! " + i);
        }
    }

    /**
     * 3.fanout模型 扇出 也就是广播模型
     */
    @Test
    void testFanoutMode() {
        String exchangeName = "order";
        rabbitTemplate.convertAndSend(exchangeName, "", "hello fanout mode!!!");
    }

    /**
     * 4.routing模型 固定路由模式
     */
    @Test
    void testRoutingMode() {
        String exchangeName = "logs";
        String routingKey = "product";
        rabbitTemplate.convertAndSend(exchangeName, routingKey, "hello routing mode !!! routingKey = " + routingKey);
    }
    /**
     * topic模型 动态路由模式
     */
    @Test
    void testTopicMode(){
        String exchangeName = "regist";
        String routingKey = "user.save.delete";
        rabbitTemplate.convertAndSend(exchangeName, routingKey, "hello routing mode !!! routingKey = " + routingKey);
    }
}
