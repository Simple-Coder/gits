package com.sohu.gray.rule.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.sohu.gray.constant.CommonConstants;
import com.sohu.gray.rule.GrayLoadBalancer;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @ClassName: VersionGrayLoadBalancer
 * @author: xiedong
 * @date 2022/3/17 22:53
 * @Desc:
 */
@Slf4j
public class VersionGrayLoadBalancer implements GrayLoadBalancer {
    private final DiscoveryClient discoveryClient;
    private AtomicInteger position;

    public VersionGrayLoadBalancer(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
        this.position = new AtomicInteger(1);
    }

    private Integer getInstancesIndex(Integer instanceSize) {
        // TODO: enforce order?
        int pos = Math.abs(this.position.incrementAndGet());
        return pos == Integer.MAX_VALUE ? RandomUtil.randomInt(instanceSize) : pos % instanceSize;
    }

    /**
     * 根据serviceId 筛选可用服务
     *
     * @param serviceId 服务ID
     * @param request   当前请求
     * @return
     */
    @Override
    public ServiceInstance choose(String serviceId, ServerHttpRequest request) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);

        // 注册中心无实例 抛出异常
        if (CollUtil.isEmpty(instances)) {
            log.warn("No instance available for {}", serviceId);
            throw new NotFoundException("No instance available for " + serviceId);
        }
        // 获取请求version，无则随机返回可用实例
        String reqVersion = request.getHeaders().getFirst(CommonConstants.VERSION);
        if (StrUtil.isBlank(reqVersion)) {
            return instances.get(this.getInstancesIndex(instances.size()));
        }

        // 遍历可以实例元数据，若匹配则返回此实例
        List<ServiceInstance> availableList = instances.stream()
                .filter(instance -> reqVersion.equalsIgnoreCase(MapUtil.getStr(instance.getMetadata(), CommonConstants.VERSION)))
                .collect(Collectors.toList());

        if (CollUtil.isEmpty(availableList)) {
            return instances.get(this.getInstancesIndex(instances.size()));
        }
        return availableList.get(this.getInstancesIndex(availableList.size()));

    }
}
