package com.sohu.gray.filter;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.sohu.gray.constant.CommonConstants;
import com.sohu.gray.util.NonWebVersionContextHolder;
import org.springframework.cloud.gateway.filter.headers.HttpHeadersFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by dongxie on 2022/3/18.
 */
public class GrayHttpHeadersFilter implements HttpHeadersFilter {
    @Override
    public HttpHeaders filter(HttpHeaders input, ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        headers.put("version", Arrays.asList(exchange.getRequest().getHeaders().getFirst(CommonConstants.VERSION)));
//        headers.put("version", Arrays.asList(NonWebVersionContextHolder.getVersion()));
        return headers;
    }
}
