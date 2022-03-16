package com.sohu.gray;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by dongxie on 2022/3/16.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class ServiceA1 {
    public static void main(String[] args) {
        SpringApplication.run(ServiceA1.class, args);
    }
}
