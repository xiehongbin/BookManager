package com.langlang.book;

import java.io.UnsupportedEncodingException;

/**
 * @Author: xiehongbin
 * @Date: 2019/1/15 0015 16:31
 */
public class demo {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = "abc";
        String s1 = s.toUpperCase();
        char[] chars = s1.toCharArray();
        for (char c: chars) {
            System.out.println(c);
        }
    }
}


