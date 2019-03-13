package com.xjy.controller;

import com.xjy.fallback.HelloFallBack;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author bjxujiayi
 * @create 2019-03-08 13:45
 **/
@FeignClient(value = "service-provider", fallback = HelloFallBack.class)
public interface TestFeign {
    @RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
    public String hello(@PathVariable("name") String name);
}
