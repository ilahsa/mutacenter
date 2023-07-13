package com.ifeng.ad.mutacenter.common.utils;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 项目描述: 对象工具类
 */
public final class ObjectUtils {
    private static final Logger log = LoggerFactory.getLogger(ObjectUtils.class);

    /**
     * 功能描述: 判断对象是否为空
     *
     * @param obj 需要判断的对象
     * @return true:非空，false:空
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * 功能描述: 判断对象是否为空
     *
     * @param obj 需要判断的对象
     * @return true:空，false:非空
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).isEmpty();
        }

        // else
        return false;
    }

    /**
     * 功能描述: Map对象深clone
     *
     * @param map 需要判断的对象（假定map的value都是不可变对象）
     * @return Map
     */
    public static <T> Map<String, T> mapClone(Map<String, T> map) {
        if (map == null) {
            return null;
        }
        Map<String, T> mapReturn = Maps.newHashMapWithExpectedSize(map.size());
        if (map.isEmpty()) {
            return mapReturn;
        }
        Iterator<Map.Entry<String, T>> iterator = map.entrySet().iterator();
        Map.Entry<String, T> entry;
        while (iterator.hasNext()) {
            entry = iterator.next();
            mapReturn.put(entry.getKey(), entry.getValue());
        }
        return mapReturn;
    }

    /**
     * 功能描述: Set对象深clone
     *
     * @param set 需要判断的对象
     * @return Set
     */
    @SuppressWarnings("unchecked")
    public static <T> Set<T> setClone(Set<T> set) {
        if (set == null) {
            return null;
        }
        Set<T> setReturn = Sets.newHashSetWithExpectedSize(set.size());
        if (set.isEmpty()) {
            return setReturn;
        }
        Iterator<T> it = set.iterator();
        T ele;
        while (it.hasNext()) {
            ele = it.next();
            if (ele != null 
                    && ele instanceof Cloneable
                    && MethodUtils.getAccessibleMethod(ele.getClass(), "clone", new Class<?>[]{}) != null) {// 对象实现了Cloneable接口并且覆盖了clone方法
                try {
                    setReturn.add((T) MethodUtils.invokeMethod(ele, "clone", null));
                } catch (Exception e) {
                    log.error(ele.getClass().getName() + " implements Cloneable but cannot invoke clone() method");
                }
            } else {// 认为对象是不可变对象
                setReturn.add(ele);
            }
        }
        return setReturn;
    }
}
