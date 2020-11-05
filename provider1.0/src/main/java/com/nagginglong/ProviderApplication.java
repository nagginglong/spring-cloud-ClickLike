package com.nagginglong;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * description:
 *
 * @author: nagginglong
 * @CreateTime: 2020/10/29   19:51
 */

@SpringBootApplication
@EnableDiscoveryClient  //发行注册服务
@MapperScan(basePackages = "com.nagginglong.dao")
@EnableScheduling //启用定时任务
public class ProviderApplication {

    public static void main(String[] args) {

        SpringApplication.run(ProviderApplication.class,args);

    }
}
