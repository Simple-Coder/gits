package com.sohu.gray.controller;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: UserController
 * @author: xiedong
 * @date 2022/3/16 22:03
 * @Desc:
 */
@RestController
@Slf4j
@RequestMapping("/user/c")
public class UserController {
    //http://localhost:8006/user/c/get/88888
    @GetMapping("/get/{id}")
    public String getById(@PathVariable("id") String id) {
        log.info("service c accept id:【{}】", id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "c张三" + id);
        jsonObject.put("password", "pwd1234~~~" + id);
        return jsonObject.toString();
    }
}

