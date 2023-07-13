package com.ifeng.ad.mutacenter.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class BeanUtil {

    private static Logger log = LoggerFactory.getLogger(BeanUtil.class);

    // map转bean
    public static <Clazz> Clazz map2Bean(Map map, Class<Clazz> clazz) {
        if (map == null || map.isEmpty() || clazz == null) {
            return null;
        }

        Clazz newInstance = null;
        try {
            newInstance = clazz.newInstance();
            for (Field field : Arrays.asList(clazz.getDeclaredFields())) {
                setField(newInstance, field, map);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return newInstance;
    }

    //bean转map
    public static Map<String,String> bean2Map(Object obj) throws  Exception{
        //obj为空，结束方法
        if(obj==null)
            return null;
        Map<String, String> map=new HashMap<>();
        //Introspector 类为通过工具学习有关受目标 Java Bean 支持的属性、事件和方法的知识提供了一个标准方法。java的自省机制
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] ps = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : ps) {
            String key = propertyDescriptor.getName();

            if(!"class".equals(key)){
                Method getter = propertyDescriptor.getReadMethod();
                Object value = getter.invoke(obj);

                if(value == null || "".equals(value)) {
                    continue;
                }

                map.put(key, String.valueOf(value));
            }
        }
        return map;
    }

    private static <Clazz> void setField(Clazz newInstance, Field field, Map map) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        field.setAccessible(true);
        String fieldName = field.getName();
        Object value;
        Class[] clazzTypes = new Class[1];
        Method method;
        StringBuffer methodName;
        if (!map.containsKey(fieldName)) {
            fieldName = StringUtils.toMapFieldName(fieldName);
        }
        if (map.containsKey(fieldName)) {
            value = map.get(fieldName);
            clazzTypes[0] = field.getType();
            methodName = new StringBuffer("set");
            methodName.append(field.getName().substring(0, 1).toUpperCase());
            methodName.append(field.getName().substring(1));
            method = newInstance.getClass().getMethod(methodName.toString(), clazzTypes);
            method.invoke(newInstance, ConvertUtil.getValue(value.toString(), field.getName(), clazzTypes[0]));
        }
    }
}
