package com.langlang.book.util;

import java.util.Random;

/**
 * @Author: xiehongbin
 * @Date: 2018/12/25 0025 10:38
 * @Desc: 随机数
 */
public class RandomUtil {

    /**
     * 循环的次数
     */
    private static final int NUM = 3;

    private static Long generatorID(){
        // 当前时间搓
        Long currentTimeMillis = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        sb.append(currentTimeMillis.toString());
        Random random = new Random();
        for (int i = 0; i<NUM; i++){
            Integer number = random.nextInt(9);
            sb.append(number);
        }
        return Long.parseLong(sb.toString());
    }

    public static void main(String[] args) {
        System.out.println(RandomUtil.generatorID());
    }
}
