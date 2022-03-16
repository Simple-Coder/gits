package com.micro.service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dongxie on 2022/3/3.
 */
@RestController
@Slf4j
public class ProviderController {
    /**
     * 查询
     * http://localhost:8001/payment/get?id=31
     *
     * @param id
     * @return
     */
    @GetMapping(value = "payment/get")
    public String getPaymentById(@RequestParam("id") Long id) {
        log.info("*****查询结果: " + id);
        return "success";
    }
}
