package com.nagginglong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * description:
 *
 * @author: nagginglong
 * @CreateTime: 2020/10/30   20:47
 */

@SpringBootApplication
@EnableDiscoveryClient //注册和发现服务
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class,args);
    }
}
