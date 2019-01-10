package com.langlang.book.util;

import java.beans.BeanInfo;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/***
 * 转换工具类
 * @author xiehb
 *
 */
public class TransitionUtil {
    
    /**
     * 实体类转Map
     * @param obj
     * @return
     */
    public static Map<String, Object> EntityToMap(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
 
                    map.put(key, value);
                }
 
            }
        } catch (Exception e) {
           throw new RuntimeException("转换异常");
        }
        return map;
    }     
}
