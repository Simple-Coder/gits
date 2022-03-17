package com.sohu.gray.ribbon;

import com.google.common.base.Optional;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;

/**
 * Created by dongxie on 2022/3/17.
 */
@Slf4j
public class GrayChooserRule extends ZoneAvoidanceRule {
    @Autowired
    private DiscoveryClient discoveryClient;

    @Override
    public Server choose(Object key) {
        try {
            //获取所有服务实例
            List<Server> allServers = getLoadBalancer().getAllServers();
            Optional<Server> server = getPredicate().chooseRoundRobinAfterFiltering(allServers, key);
            return server.isPresent() ? server.get() : null;
        } catch (Exception e) {
            log.warn("gray choose server occur exception:{}, execute super method.", e.getMessage(), e);
            return super.choose(key);
        }
    }
}
