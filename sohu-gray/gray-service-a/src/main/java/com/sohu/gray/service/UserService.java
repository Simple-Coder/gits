package com.sohu.gray.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by dongxie on 2022/3/16.
 */
@Component
@FeignClient(value = "SERVICE-B")
public interface UserService {
    @GetMapping("/user/b/get/{id}")
    String getById(@PathVariable("id") String id);
}

