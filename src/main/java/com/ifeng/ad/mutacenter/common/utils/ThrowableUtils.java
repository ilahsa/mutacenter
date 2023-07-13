package com.ifeng.ad.mutacenter.common.utils;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

/**
 * 有关Throwable的一些工具类。
 * 
 * @author zhangfei
 * @date 2018年8月9日
 */
public final class ThrowableUtils {
	/**
	 * 过滤Throwable的StackTraceElement，在打印error日志时不打印过多的无关信息。
	 * 
	 * @param t
	 * @param classContainString 要包含的字符串。只保留StackTraceElement.getClassName()包含classContainString的StackTraceElement。
	 */
	public static Throwable filterStackTraceElement(Throwable t, String classContainString) {
		Preconditions.checkNotNull(t);
		Preconditions.checkArgument(StringUtils.isNotBlank(classContainString), "classContainString must not be blank");
		
		StackTraceElement[] eles = t.getStackTrace();
		if (ArrayUtils.isEmpty(eles)) {
			return t;
		}
		
		List<StackTraceElement> eleList = Lists.newLinkedList();
		for (StackTraceElement ele : eles) {
			if (ele.getClassName().contains(classContainString)) {
				eleList.add(ele);
			}
		}
		
		t.setStackTrace(eleList.toArray(new StackTraceElement[eleList.size()]));
		return t;
	}
}
