package com.sohu.gray;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by dongxie on 2022/3/17.
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GrayGatewayServer {
    public static void main(String[] args) {
        SpringApplication.run(GrayGatewayServer.class, args);
    }
}
