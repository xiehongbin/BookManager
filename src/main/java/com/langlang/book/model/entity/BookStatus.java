package com.langlang.book.model.entity;

/**
 * @Author: xiehongbin
 * @Date: 2018/12/24
 */
public class BookStatus {
    /**
     * 上架
     */
    private static final Integer PUTAWAY = 1;

    /**
     * 下架
     */
    private static final Integer SOLD_OUT = 0;

    public static Integer getPUTAWAY() {
        return PUTAWAY;
    }

    public static Integer getSoldOut() {
        return SOLD_OUT;
    }
}
