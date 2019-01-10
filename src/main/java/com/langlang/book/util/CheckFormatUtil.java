package com.langlang.book.util;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author xiehb
 *
 */
public class CheckFormatUtil {
	/**
	 * 6位数
	 */
	public static final int SIX  = 6 ;
	/**
	 * 20位数
	 */
	public static final int TWENTY  = 20 ;
	/**
	 * 校验邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 校验手机号
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean isMobile(String phone) {
		boolean flag = false;
		try {
			String check = "^[1][3,4,5,7,8][0-9]{9}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(phone);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 校验QQ号
	 * 
	 * @param qq
	 * @return
	 */
	public static boolean isQQ(String qq) {
		boolean flag = false;
		try {
			//第一位1-9之间的数字，第二位0-9之间的数字，数字范围4-14个之间
			String regex = "[1-9][0-9]{4,14}";
			flag = qq.matches(regex);
		} catch (Exception e) {
			flag = false;
		}
		return flag ;
	}
	
	/**
	 * 校验密码长度
	 */
	public static void checkPassword(String password) {
		if (password.length()<SIX || password.length()>TWENTY) {
			throw new RuntimeException("密码长度需要在6-20位");
		}
	}
	
	/**
	 * 判断该对象是否: 返回ture表示所有属性为null  返回false表示不是所有属性都是null
	 * @param <T>
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static <T> boolean checkAllFieldNull(T obj) throws Exception{
        Class<?> stuCla = obj.getClass();// 得到类对象
        Field[] fs = stuCla.getDeclaredFields();//得到属性集合
        boolean flag = true;
        for (Field f : fs) {//遍历属性
            f.setAccessible(true); // 设置属性是可以访问的(私有的也可以)
            Object val = f.get(obj);// 得到此属性的值
            if(val!=null) {//只要有1个属性不为空,那么就不是所有的属性值都为空
                flag = false;
                break;
            }
        }
        return flag;
    }
}
