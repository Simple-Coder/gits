package com.sohu.gray.filter;

import com.sohu.gray.constant.CommonConstants;
import com.sohu.gray.rule.GrayLoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.gateway.config.GatewayLoadBalancerProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
import org.springframework.cloud.gateway.support.DelegatingServiceInstance;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.LoadBalancerProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalancerUriTools;
import org.springframework.cloud.gateway.config.GatewayLoadBalancerProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
import org.springframework.cloud.gateway.support.DelegatingServiceInstance;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URI;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName: GrayReactiveLoadBalancerClientFilter
 * @author: xiedong
 * @date 2022/3/17 22:51
 * @Desc:
 */
@Slf4j
public class GrayReactiveLoadBalancerClientFilter extends ReactiveLoadBalancerClientFilter {

    private static final int LOAD_BALANCER_CLIENT_FILTER_ORDER = 10150;

    private LoadBalancerClientFactory clientFactory;

    private GatewayLoadBalancerProperties loadBalancerProperties;

    private LoadBalancerProperties properties;

    private GrayLoadBalancer grayLoadBalancer;

    public GrayReactiveLoadBalancerClientFilter(GatewayLoadBalancerProperties loadBalancerProperties,
                                                LoadBalancerClientFactory clientFactory,
                                                LoadBalancerProperties properties, GrayLoadBalancer grayLoadBalancer) {
        super(clientFactory, loadBalancerProperties, properties);
        this.properties = properties;
        this.grayLoadBalancer = grayLoadBalancer;
        this.loadBalancerProperties = loadBalancerProperties;
    }

    @Override
    public int getOrder() {
        return LOAD_BALANCER_CLIENT_FILTER_ORDER;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI url = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        String schemePrefix = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_SCHEME_PREFIX_ATTR);
        if (url == null || (!"lb".equals(url.getScheme()) && !"lb".equals(schemePrefix))) {
            return chain.filter(exchange);
        }
        // preserve the original url
        ServerWebExchangeUtils.addOriginalRequestUrl(exchange, url);

        if (log.isTraceEnabled()) {
            log.trace(ReactiveLoadBalancerClientFilter.class.getSimpleName() + " url before: " + url);
        }

        DefaultRequest<RequestDataContext> lbRequest = new DefaultRequest(new RequestDataContext(new RequestData(exchange.getRequest()), "default"));
        return choose(exchange, lbRequest).doOnNext(response -> {

            if (!response.hasServer()) {
                throw NotFoundException.create(loadBalancerProperties.isUse404(),
                        "Unable to find instance for " + url.getHost());
            }

            URI uri = exchange.getRequest().getURI();

            // if the `lb:<scheme>` mechanism was used, use `<scheme>` as the default,
            // if the loadbalancer doesn't provide one.
            String overrideScheme = null;
            if (schemePrefix != null) {
                overrideScheme = url.getScheme();
            }

            DelegatingServiceInstance serviceInstance = new DelegatingServiceInstance(response.getServer(),
                    overrideScheme);

            URI requestUrl = LoadBalancerUriTools.reconstructURI(serviceInstance, uri);

            if (log.isTraceEnabled()) {
                log.trace("LoadBalancerClientFilter url chosen: " + requestUrl);
            }

            exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, requestUrl);
        }).then(chain.filter(exchange));
    }

    private Mono<Response<ServiceInstance>> choose(ServerWebExchange exchange, Request<RequestDataContext> lbRequest) {
        URI uri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        ServiceInstance serviceInstance = grayLoadBalancer.choose(uri.getHost(), exchange.getRequest());
        return Mono.just(new DefaultResponse(serviceInstance));
    }
}
