package com.micro.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by dongxie on 2022/3/3.
 */
@SpringBootApplication
@EnableEurekaServer
public class GrayEurekaServer7001 {
    public static void main(String[] args) {
        SpringApplication.run(GrayEurekaServer7001.class, args);
    }
}
