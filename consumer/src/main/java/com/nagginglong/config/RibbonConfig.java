package com.nagginglong.config;

import com.netflix.loadbalancer.*;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * description:Ribbon远程调用组件
 *
 * @author: nagginglong
 * @CreateTime: 2020/10/30   16:15
 */


@Configuration
public class RibbonConfig {

    @Bean
    @LoadBalanced //启用负载均衡
    public RestTemplate createRT(){
        return new RestTemplate();
    }
    //负载均衡的分发策略
    @Bean
    public IRule createRule(){
        //最小并发分配
        return new BestAvailableRule();//
    }
}
