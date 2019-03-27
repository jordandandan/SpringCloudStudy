package com.xjy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;

@SpringBootApplication
@EnableSidecar
public class SideCarApp {
    public static void main(String[] args) {
        SpringApplication.run(SideCarApp.class, args); }
}
