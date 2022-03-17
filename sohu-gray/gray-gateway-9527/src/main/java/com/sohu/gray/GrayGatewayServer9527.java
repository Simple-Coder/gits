package com.sohu.gray;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName: GrayGatewayServer9527
 * @author: xiedong
 * @date 2022/3/17 22:48
 * @Desc:
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GrayGatewayServer9527 {
    public static void main(String[] args) {
        SpringApplication.run(GrayGatewayServer9527.class, args);
    }
}
