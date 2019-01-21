package com.langlang.book.controller;

import com.langlang.book.util.QrCode64Utils;
import com.langlang.book.util.QrCodeImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: xiehongbin
 * @Date: 2018/12/25
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("test_set_redis")
    public String testSetRedis(){
        redisTemplate.opsForHash().put("demo","testRedis", "测试redis添加数据");
        return "redis数据保存成功!";
    }

    @RequestMapping("test_get_redis")
    public String testGetRedis(){
        Object data = redisTemplate.opsForHash().get("demo", "testRedis");
        return data.toString();
    }

    @RequestMapping("testQrCode")
    public void testQrCode() throws Exception {
        String text = "https://blog.csdn.net/ianly123";
        QrCodeImageUtils.encode(text, "G:\\60195609.jpeg","G:\\",true);
    }
}
