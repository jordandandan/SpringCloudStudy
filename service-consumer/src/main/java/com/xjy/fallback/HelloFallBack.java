package com.xjy.fallback;

import com.xjy.controller.TestFeign;

import org.springframework.stereotype.Component;

/**
 * @author bjxujiayi
 * @create 2019-03-08 13:51
 **/
@Component
public class HelloFallBack implements TestFeign {

    @Override
    public String hello(String name) {
        return "fall back";
    }
}
