package com.sohu.gray.controller;

import cn.hutool.json.JSONObject;
import com.sohu.gray.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: UserController
 * @author: xiedong
 * @date 2022/3/16 22:17
 * @Desc:
 */
@RestController
@Slf4j
@RequestMapping("/user/a")
public class UserController {
    @Autowired
    private UserService userService;

    //http://localhost:8001/user/a/get/88888
    @GetMapping("/get/{id}")
    public String getById(@PathVariable("id") String id) {
        log.info("service a accept id:【{}】", id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("a", userService.getById(id));
        return jsonObject.toString();
    }
}
