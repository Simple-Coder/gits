package com.sohu.gray.client.low.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import com.sohu.gray.client.low.ribbon.GrayLoadBalanceRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by dongxie on 2022/3/18.
 */
@Configuration
public class GrayRibbonClientsConfiguration {

    @Bean
    @ConditionalOnProperty(value = "gray.rule.enabled", havingValue = "true")
    public IRule ribbonRule(
            @Autowired(required = false) IClientConfig config) {
        ZoneAvoidanceRule rule = new GrayLoadBalanceRule();
        rule.initWithNiwsConfig(config);
        return rule;
    }

}
