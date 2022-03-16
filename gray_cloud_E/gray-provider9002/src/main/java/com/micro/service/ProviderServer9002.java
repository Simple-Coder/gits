package com.micro.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by dongxie on 2022/3/3.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class ProviderServer9002 {
    public static void main(String[] args) {
        SpringApplication.run(ProviderServer9002.class, args);
    }
}
