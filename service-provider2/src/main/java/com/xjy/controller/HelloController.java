package com.xjy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bjxujiayi
 * @create 2019-03-07 20:09
 **/
@RestController
public class HelloController {
    @GetMapping("/hello/{name}")
    public String hello(@PathVariable String name) {
        return "2:"+name;
    }
}
