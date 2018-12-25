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
}
