package com.langlang.book.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DoubleUtils {

	/**
	 * 返回指定位数的double数据，如果位数不小于0
	 * 
	 * @param decimalNum
	 *            小数点位数
	 * @param value
	 *            需转化的值
	 * @return
	 */
	public static double getDecimalDouble(int decimalNum, double value) {
		if (decimalNum < 0) {
			decimalNum = 0;
		}
		return new BigDecimal(value).setScale(decimalNum, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	public static DecimalFormat df = new DecimalFormat("#.00");
	/** 保留4位小数 */
	public static DecimalFormat df4 = new DecimalFormat("0.####");
	
	/**
	 * 获取两位小数,四舍五入
	 * @return
	 */
	public static double get2DecimalDouble(double value) {
		return Double.parseDouble(df.format(value));
	}
	
	/**
	 * 获取4位小数，四舍五入
	 * @param value
	 * @return
	 */
	public static String get4DecimalString(Double value) {
		if(value == null) {
			return null;
		}
		return df4.format(value);
	}
	
	/**
	 * 获取两位小数，都入
	 * @param value
	 * @return 1.011 => 1.02
	 */
	public static Double getUpMoney(Double value) {
		if(value == null) {
			return null;
		}
		return getUpMoney(value, 2);
	}
	
	public static Double getUpMoney(String value) {
		if(value == null || "".equals(value.trim())){
			return null;
		}
		return getUpMoney(Double.valueOf(value));		
	}
	
	public static Double getUpMoney(Double value,int scale) {
		if(value == null){
			return null;
		}
		return new BigDecimal(String.valueOf(value)).setScale(scale, RoundingMode.UP).doubleValue();		
	}
	
	
	/**
	 * 获取两位小数，都舍
	 * @param value
	 * @return 1.016 => 1.01
	 */
	public static Double getDownMoney(Double value) {
		if(value == null) {
			return null;
		}
		return getDownMoney(value, 2);
	}
	
	public static Double getDownMoney(String value) {
		if(value == null || "".equals(value.trim())){
			return null;
		}
		return getDownMoney(Double.valueOf(value));		
	}
	public static Double getDownMoney(Double value,int scale) {
		if(value == null){
			return null;
		}
		return new BigDecimal(String.valueOf(value)).setScale(scale, RoundingMode.DOWN).doubleValue();		
	}
	
	
	/**
	 * 获取两位小数，四舍五入
	 * @param value
	 * @return 1.016 => 1.01
	 */
	public static Double getHalfUpMoney(Double value) {
		return getHalfUpMoney(value, 2);		
	}
	
	public static Double getHalfUpMoney(Double value,int scale) {
		if(value == null){
			return null;
		}
		return new BigDecimal(String.valueOf(value)).setScale(scale, RoundingMode.HALF_UP).doubleValue();		
	}
	
	/**
	 * 获取两位小数，最接近数字方向舍入
	 * 
	 * 向最接近数字方向舍入的舍入模式，如果与两个相邻数字的距离相等，则向相邻的偶数舍入。
	 * 如果舍弃部分左边的数字为奇数，则舍入行为同RoundingMode.HALF_UP；
	 * 如果为偶数，则舍入行为同RoundingMode.HALF_DOWN。
	 * 注意，在重复进行一系列计算时，此舍入模式可以在统计上将累加错误减到最小。
	 * 此舍入模式也称为“银行家舍入法”，主要在美国使用。
	 * 此舍入模式类似于 Java 中对float 和double 算法使用的舍入策略。
	 * @param value
	 * @return 1.016 => 1.01
	 */
	public static Double getHalfEvenMoney(Double value) {
		return getHalfEvenMoney(value, 2);		
	}
	
	public static Double getHalfEvenMoney(Double value,int scale) {
		if(value == null){
			return null;
		}
		return new BigDecimal(String.valueOf(value)).setScale(scale, RoundingMode.HALF_EVEN).doubleValue();		
	}
	
	
	/**
	 * 求 x + y的和 
	 * @param x
	 * @param y
	 * @return
	 */
	public static double add(double x, double y) {
		BigDecimal result = new BigDecimal(String.valueOf(x)).add(new BigDecimal(String.valueOf(y)));
        return result.doubleValue();
	}
	
	/**
	 * 连加
	 * @param x
	 * @return
	 */
	public static double add(double... x) {
		if (x == null || x.length < 1) {
			return 0d;
		}
		BigDecimal result = new BigDecimal("0");
		for (double d : x) {
			result = result.add(new BigDecimal(String.valueOf(d)));
		}
		return result.doubleValue();
	}
	
	/**
	 * 求 x - y的差 
	 * @param x
	 * @param y
	 * @return
	 */
	public static double subtract(double x, double y) {
		BigDecimal result = new BigDecimal(String.valueOf(x)).subtract(new BigDecimal(String.valueOf(y)));
        return result.doubleValue();
	}
	
	/**
	 * 连减
	 * @param x
	 * @return
	 */
	public static double subtract(double... x) {
		if (x == null || x.length < 1) {
			return 0d;
		}
		BigDecimal result = new BigDecimal(String.valueOf(x[0]));
		for (double d : x) {
			result = result.add(new BigDecimal(String.valueOf(-d)));
		}
		return result.add(new BigDecimal(String.valueOf(x[0]))).doubleValue();
	}

	/**
	 * 求 x * y的值 
	 * @param x
	 * @param y
	 * @return
	 */
	public static double multiply(double x, double y) {
		if(x == 0 || y == 0) {
			return 0;
		}
		
		BigDecimal result = new BigDecimal(String.valueOf(x)).multiply(new BigDecimal(String.valueOf(y)));
        return result.doubleValue();
	}
	
	/**
	 * 求 x / y的值
	 * 	默认保留4位小数
	 * @param x
	 * @param y
	 * @return
	 */
	public static double divide(double x, double y) {
		return divide(x, y, 4);
	}
	public static double divide(double x, double y,int scale) {
		if(x == 0) {
			return 0;
		}
		if(scale < 0) {
			scale = 0;
		}
		
		BigDecimal result = new BigDecimal(String.valueOf(x)).divide(new BigDecimal(String.valueOf(y)), scale, BigDecimal.ROUND_HALF_EVEN);
        return result.doubleValue();
	}

}
