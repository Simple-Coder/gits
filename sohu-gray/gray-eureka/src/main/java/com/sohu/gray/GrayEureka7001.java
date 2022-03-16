package com.sohu.gray;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by dongxie on 2022/3/16.
 */
@EnableEurekaServer
@SpringBootApplication
public class GrayEureka7001 {
    public static void main(String[] args) {
        SpringApplication.run(GrayEureka7001.class, args);
    }
}
