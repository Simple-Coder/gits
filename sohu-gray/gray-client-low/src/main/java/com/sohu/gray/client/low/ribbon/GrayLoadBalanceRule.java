package com.sohu.gray.client.low.ribbon;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.base.Optional;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import com.sohu.gray.client.low.constant.CommonConstants;
import com.sohu.gray.client.low.support.NonWebVersionContextHolder;
import com.sohu.gray.client.low.support.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dongxie on 2022/3/18.
 */
@Slf4j
public class GrayLoadBalanceRule extends ZoneAvoidanceRule {
    @Override
    public Server choose(Object key) {
        ILoadBalancer lb = getLoadBalancer();
        List<Server> allServers = lb.getAllServers();
        String reqVersion = WebUtils.getRequest() != null ? WebUtils.getRequest().getHeader(CommonConstants.VERSION)
                : NonWebVersionContextHolder.getVersion();

        log.info("key:{}--reqversion:{}", key, reqVersion);
        if (StrUtil.isNotBlank(reqVersion)) {
//            // 遍历可以实例元数据，若匹配则返回此实例
//            allServers.stream()
//                    .filter(server -> reqVersion.equalsIgnoreCase(MapUtil.getStr(, CommonConstants.VERSION)))
//                    .collect(Collectors.toList());
        }
        Optional<Server> server = getPredicate().chooseRoundRobinAfterFiltering(allServers, key);
        if (server.isPresent()) {
            return server.get();
        }
        return super.choose(key);
    }
}
