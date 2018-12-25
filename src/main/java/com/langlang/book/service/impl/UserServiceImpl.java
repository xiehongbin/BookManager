package com.langlang.book.service.impl;

import com.langlang.book.mapper.UserMapper;
import com.langlang.book.model.entity.User;
import com.langlang.book.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: xiehongbin
 * @Date: 2018/12/20
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(User user) {
        // 用户存在进行校验
        List<User> listUser = userMapper.listAllUser();
        for (User oldUser:listUser) {
            if (ObjectUtils.isEmpty(oldUser)){
                  continue;
            }
            if (user.getName().equals(oldUser.getName())){
                throw new RuntimeException("用户名已经存在!");
            }
        }
        // 对密码进行MD5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        user.setGmtCreate(new Timestamp(System.currentTimeMillis()));
        userMapper.insert(user);
    }

    @Override
    public List<User> listAllUser() {
        return userMapper.listAllUser();
    }
}
