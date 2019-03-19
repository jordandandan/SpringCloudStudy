package com.xjy.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author bjxujiayi
 * @create 2019-03-08 13:13
 **/
@Configuration
public class RibbonRuleConfig {
    @Bean
    public IRule ribbonRule() {
        return new RandomRule();
    }
}
