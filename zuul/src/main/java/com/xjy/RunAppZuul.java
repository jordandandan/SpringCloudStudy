package com.xjy;

import com.xjy.filter.TokenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
public class RunAppZuul {
    public static void main(String[] args) {
        SpringApplication.run(RunAppZuul.class, args);
    }
    @Bean
    public TokenFilter tokenFilter(){
        return new TokenFilter();
    }
}
