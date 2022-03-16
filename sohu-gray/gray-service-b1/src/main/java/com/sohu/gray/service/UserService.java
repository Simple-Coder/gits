package com.sohu.gray.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @InterfaceName: UserService
 * @author: xiedong
 * @date 2022/3/16 22:09
 * @Desc:
 */
@Component
@FeignClient(value = "SERVICE-C")
public interface UserService {
    @GetMapping("/user/c/get/{id}")
    String getById(@PathVariable("id") String id);
}
