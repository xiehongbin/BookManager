package com.langlang.book.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xiehongbin
 * @Date: 2018/12/26
 */
@ControllerAdvice
public class MyExceptionHandler {

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public Map<String, Object> handlerException(Exception e){
        Map<String, Object> map = new HashMap(16);
        map.put("code", 500);
        map.put("message", e.getMessage());
        return map;
    }


}
