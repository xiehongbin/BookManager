package com.langlang.book.controller;

import com.langlang.book.model.entity.User;
import com.langlang.book.service.UserService;
import com.langlang.book.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: xiehongbin
 * @Date: 2018/12/20
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public Map register(@RequestBody User user){
        if (StringUtils.isEmpty(user.getName())){
            throw new RuntimeException("用户名不能为空");
        }
        if (StringUtils.isEmpty(user.getPassword())){
            throw new RuntimeException("密码不能为空");
        }
        if (StringUtils.isEmpty(user.getEmail())){
            throw new RuntimeException("邮箱不能为空");
        }
        userService.register(user);
        return ResultData.success(true, "注册成功");
    }

    @RequestMapping("/query_all_user")
    public List<User> queryAllUser(){
        return userService.listAllUser();
    }

}
