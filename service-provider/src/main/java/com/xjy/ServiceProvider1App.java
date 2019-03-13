package com.xjy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author bjxujiayi
 * @create 2019-03-07 20:11
 **/
@SpringBootApplication
@EnableEurekaClient
public class ServiceProvider1App {
    public static void main(String[] args) {
        SpringApplication.run(ServiceProvider1App.class, args);
    }
}
