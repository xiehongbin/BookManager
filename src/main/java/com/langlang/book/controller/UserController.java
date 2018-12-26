package com.langlang.book.controller;

import com.alibaba.fastjson.JSONObject;
import com.langlang.book.mapper.UserMapper;
import com.langlang.book.model.entity.RequestContext;
import com.langlang.book.model.entity.User;
import com.langlang.book.service.UserService;
import com.langlang.book.util.RandomUtil;
import com.langlang.book.util.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: xiehongbin
 * @Date: 2018/12/20
 */
@RestController
@RequestMapping("/user")
@Api("用户模块")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Map register(@RequestBody JSONObject params){
        String name = params.getString("name");
        String password = params.getString("password");
        String replyPassword = params.getString("replyPassword");
        String email = params.getString("email");
        if (StringUtils.isEmpty(name)){
            throw new RuntimeException("用户名不能为空");
        }
        if (StringUtils.isEmpty(password)){
            throw new RuntimeException("密码不能为空");
        }
        if (StringUtils.isEmpty(replyPassword)){
            throw new RuntimeException("确认密码不能为空!");
        }
        if (!password.equals(replyPassword)){
            throw new RuntimeException("两次密码输入不一致");
        }
        if (StringUtils.isEmpty(email)){
            throw new RuntimeException("邮箱不能为空");
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setId(RandomUtil.generatorID());
        userService.register(user);
        return ResultData.success(true, "注册成功");
    }


    @RequestMapping("/query_all_user")
    public List<User> queryAllUser(){
        return userService.listAllUser();
    }

    @ApiOperation("用户登录")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name="email", value = "邮箱", required = true, dataType = "String"),
//            @ApiImplicitParam(name="password", value = "密码", required = true, dataType = "String")
//    })
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map login(@RequestBody JSONObject params){
        String password = params.getString("password");
        String email = params.getString("email");
        if (StringUtils.isEmpty(password)){
            throw new RuntimeException("密码不能为空!");
        }
        if (StringUtils.isEmpty(email)){
            throw new RuntimeException("邮箱不能为空!");
        }
        User user = userService.login(email, password);
        if (ObjectUtils.isEmpty(user)){
            throw new RuntimeException("用户不存在");
        }
        RequestContext.getCurrentContext().setUserId(user.getId());
        Map<String, Object> map = new HashMap(16);
        map.put("user", user);
        map.put("token", UUID.randomUUID());
        return ResultData.success(true,map);
    }

}
