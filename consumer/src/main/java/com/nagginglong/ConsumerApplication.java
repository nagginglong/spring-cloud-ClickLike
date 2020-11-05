package com.nagginglong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;

/**
 * description:
 *
 * @author: nagginglong
 * @CreateTime: 2020/10/29   20:08
 */

@SpringBootApplication //SpringBoot 启动标记位
@EnableDiscoveryClient  //注册发现服务
@RibbonClients   //启动Ribbon组件
public class ConsumerApplication {

    public static void main(String[] args) {

        SpringApplication.run(ConsumerApplication.class,args);
    }

}
