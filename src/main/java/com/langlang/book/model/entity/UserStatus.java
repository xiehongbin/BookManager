package com.langlang.book.model.entity;

/**
 * @Author: xiehongbin
 * @Date: 2018/12/25
 */
public class UserStatus {
    /**
     * 启用
     */
    private static final Integer START = 1;

    /**
     * 禁用
     */
    private static final Integer FORBIDDEN = 0;

    public static Integer getSTART() {
        return START;
    }

    public static Integer getFORBIDDEN() {
        return FORBIDDEN;
    }
}
