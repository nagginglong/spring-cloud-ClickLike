package com.nagginglong.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * description:创建队列
 *
 * @author: nagginglong
 * @CreateTime: 2020/11/3   16:10
 */


@Component
public class RabbitMQConfig {

    public static String qName_clickLike = "clickLike_sync";

    @Bean
    public Queue createQueue(){

        return new Queue(qName_clickLike);

    }


}
