package com.langlang.book.mapper;

import com.langlang.book.model.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: xiehongbin
 * @Date: 2018/12/25
 */
@Repository
public interface UserMapper {

    /**
     * 添加用户
     * @param user 用户数据
     * @return
     */
    int insert(User user);

    /**
     * 查询所有用户
     * @return
     */
    List<User> listAllUser();
}