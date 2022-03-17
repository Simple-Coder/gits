package com.sohu.gray.rule;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * Created by dongxie on 2022/3/17.
 * 灰度路由
 */
public interface GrayLoadBalancer {

    /**
     * 根据serviceId 筛选可用服务
     * @param serviceId 服务ID
     * @param request 当前请求
     * @return
     */
    ServiceInstance choose(String serviceId, ServerHttpRequest request);

}