package com.xjy;

import com.xjy.config.RibbonRuleConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author bjxujiayi
 * @create 2019-03-07 20:32
 **/
@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "service-provider", configuration = RibbonRuleConfig.class)
@EnableFeignClients
public class ConsumerApp {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApp.class, args);
    }
}
