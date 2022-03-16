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
 * @date 2022/3/16 22:16
 * @Desc:
 */
@RestController
@Slf4j
@RequestMapping("/user/b")
public class UserController {
    @Autowired
    private UserService userService;

    //http://localhost:8003/user/b/get/88888
    @GetMapping("/get/{id}")
    public String getById(@PathVariable("id") String id) {
        log.info("service b1 accept id:【{}】", id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("b1", userService.getById(id));
        return jsonObject.toString();
    }

}
