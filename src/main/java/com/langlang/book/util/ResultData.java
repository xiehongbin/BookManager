package com.langlang.book.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xiehongbin
 * @Date: 2018/12/25 0025 10:21
 * @Desc: 返回结果集
 */
public class ResultData implements Serializable {

    public static Map success(boolean success, Object content){
        Map<String, Object> map = new HashMap(16);
        map.put("success", success);
        map.put("content", content);
        return map;
    }
}
