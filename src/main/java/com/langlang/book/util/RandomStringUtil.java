package com.langlang.book.util;

import java.util.Random;
/**
 * 
 * @author xiehb
 *
 */
public class RandomStringUtil {
	
	private static final char[] CHARS = 
			{'0','1','2','3','4','5','6','7','8','9'
			,'a','b','c','d','e','f','g','h','i','j'
			,'k','l','m','n','o','p','q','r','s','t'
			,'u','v','w','x','y','z','A','B','C','D'
			,'E','F','G','H','I','J','K','L','M','N'
			,'O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	
	private static final int LETTER_COUNT = 26;
	
	private static final int NUMBER_COUNT = 10;
	/**
	 * 获取指定位数的随机数字0-9 字符串
	 * @param length 长度
	 * @return
	 */
	public static String getRandomNumberString(Integer length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for(int i=0;i<length;i++) {
			sb.append(random.nextInt(NUMBER_COUNT));
		}
		return sb.toString();
	}
	
	/**
	 * 获取指定位数的随机字符a-z 字符串
	 * @param length 长度
	 * @param upper 是否大写
	 * @return
	 */
	public static String getRandomCharString(Integer length,boolean upper) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for(int i=0;i<length;i++) {
			char temp = (char) ('a' + random.nextInt(LETTER_COUNT));
			sb.append(temp);
		}
		if (upper) {
			return sb.toString().toUpperCase();
		}else {
			return sb.toString().toLowerCase();
		}
	}
	
	/**
	 * 获取指定位数的随机字符a-z,A-Z,0-9 字符串
	 * @param length 长度
	 * @return
	 */
	public static String getRandomCharAndNumberString(Integer length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for(int i=0;i<length;i++) {
			sb.append(CHARS[random.nextInt(CHARS.length)]);
		}
		return sb.toString();
	}
}
