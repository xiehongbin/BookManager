package com.langlang.book.model.entity;

import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author: xiehongbin
 * @Date: 2018/12/24 0024 14:53
 */
public class RequestContext implements Serializable {

    private static ThreadLocal<RequestContext> cache = new ThreadLocal<>();

    private Long userId;

    /**
     *  绑定获取线程
     * @return
     */
    public static RequestContext getCurrentContext(){
        RequestContext requestContext = cache.get();
        if (ObjectUtils.isEmpty(requestContext)){
            cache.set(new RequestContext());
        }
        return cache.get();
    }

    /**
     * 结束线程
     */
    public static void remove(){
        cache.remove();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "RequestContext{" +
                "userId=" + userId +
                '}';
    }
}
