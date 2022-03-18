package com.sohu.gray.config;

import com.sohu.gray.filter.GrayHttpHeadersFilter;
import com.sohu.gray.filter.GrayReactiveLoadBalancerClientFilter;
import com.sohu.gray.rule.GrayLoadBalancer;
import com.sohu.gray.rule.impl.VersionGrayLoadBalancer;
import com.sohu.gray.util.NonWebVersionContextHolder;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestTransformer;
import org.springframework.cloud.gateway.config.GatewayLoadBalancerProperties;
import org.springframework.cloud.gateway.config.GatewayReactiveLoadBalancerClientAutoConfiguration;
import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
import org.springframework.cloud.gateway.filter.headers.HttpHeadersFilter;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.util.Arrays;

/**
 * Mica ribbon rule auto configuration.
 *
 * @author L.cm
 * @link https://github.com/lets-mica/mica
 */
@Configuration
@EnableConfigurationProperties(LoadBalancerProperties.class)
@ConditionalOnProperty(value = "gray.rule.enabled", havingValue = "true")
@AutoConfigureBefore(GatewayReactiveLoadBalancerClientAutoConfiguration.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class GrayLoadBalancerClientConfiguration {

    @Bean
    public ReactiveLoadBalancerClientFilter gatewayLoadBalancerClientFilter
            (GrayLoadBalancer grayLoadBalancer,
             LoadBalancerClientFactory clientFactory,
             LoadBalancerProperties properties,
             GatewayLoadBalancerProperties loadBalancerProperties) {
        return new GrayReactiveLoadBalancerClientFilter(loadBalancerProperties, clientFactory, properties, grayLoadBalancer);
    }

    @Bean
    public GrayLoadBalancer grayLoadBalancer(DiscoveryClient discoveryClient) {
        return new VersionGrayLoadBalancer(discoveryClient);
    }

    @Bean
    public HttpHeadersFilter httpHeadersFilter() {
        return new GrayHttpHeadersFilter();
    }

//    @Bean
//    public LoadBalancerRequestTransformer transformer() {
//        return new LoadBalancerRequestTransformer() {
//            @Override
//            public HttpRequest transformRequest(HttpRequest request, ServiceInstance instance) {
//                return new HttpRequestWrapper(request) {
//                    @Override
//                    public HttpHeaders getHeaders() {
//                        HttpHeaders headers = super.getHeaders();
//                        headers.put("version", Arrays.asList(NonWebVersionContextHolder.getVersion()));
//                        return headers;
//                    }
//                };
//            }
//        };
//    }
}