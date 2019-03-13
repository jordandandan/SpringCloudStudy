package com.xjy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author bjxujiayi
 * @create 2019-03-07 20:31
 **/
@RestController
@Slf4j
public class HelloController {

    @Autowired
    private RestTemplate restTemplate;
    @Resource
    private TestFeign testFeign;
    @GetMapping("/hello/{name}")
    @ResponseBody
    public String hello(@PathVariable String name){

        String url = "http://service-provider/hello/";
        String result = "";
        try {
            result = restTemplate.getForObject(url + name, String.class);
        }catch (Exception e){
            log.error("err:",e);
        }

        return result;
    }

    @GetMapping("/feign/{name}")
    @ResponseBody
    public String feiggn(@PathVariable String name){
       return testFeign.hello(name);
    }
}
