package com.langlang.book.service;

import com.langlang.book.model.entity.User;

import java.util.List;

/**
 * @Author: xiehongbin
 * @Date: 2018/12/20
 */
public interface UserService {

    /**
     * 注册
     * @param user
     */
    void register(User user);

    /**
     * 查询所有用户
     * @return
     */
    List<User> listAllUser();

    /**
     * 用户登录
     * @param email 邮箱
     * @param password 密码
     * @return
     */
    User login(String email, String password);
}
