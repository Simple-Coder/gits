package com.sohu.gray.config;

import com.sohu.gray.filter.GrayLoadBalancerClientFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by dongxie on 2022/3/17.
 */
@Configuration
@ConditionalOnProperty(value = "gray.enabled")
public class GrayGatewayAutoConfiguration {
    @Bean
    @ConditionalOnBean(LoadBalancerClient.class)
    public LoadBalancerClientFilter loadBalancerClientFilter(LoadBalancerClient client) {
        return new GrayLoadBalancerClientFilter(client);
    }
}
