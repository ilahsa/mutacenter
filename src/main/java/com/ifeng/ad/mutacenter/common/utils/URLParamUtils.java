package com.ifeng.ad.mutacenter.common.utils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 项目描述: 将对象转换为HTTP请求参数串
 */
public class URLParamUtils {

	private static final String SERIAL_VERSION_UID = "serialVersionUID";

	/**
	 * 功能描述: 将对象转化成URL参数
	 *   
	 * @param obj 数据对象
	 * @return   请求参数URL串
	 */
	public static String String2UrlParams(Object obj) {
		return String2UrlParams(obj, "?");
	}

	/**
	 * 功能描述: 将对象转化成URL参数
	 *   
	 * @param obj 数据对象
	 * @param nullShow 数据为空字段不转换
	 * @return   请求参数URL串
	 */
	public static String String2UrlParams(Object obj, boolean nullShow) {
		return String2UrlParams(obj, "?", nullShow);
	}
	
	

	/**
	 * 功能描述: 将对象转化成url参数并指定第一个连接符
	 *   
	 * @param obj 数据对象
	 * @param firstChar 第一个字符
	 * @return   请求参数URL串
	 */
	public static String String2UrlParams(Object obj, String firstChar) {
		return String2UrlParams(obj, firstChar, false);
	}

	/**
	 * 功能描述: 将对象转化成url参数并指定第一个连接符
	 *   
	 * @param obj 数据对象
	 * @param firstChar 第一个字符
	 * @param nullShow 数据为空字段不转换
	 * @return   请求参数URL串
	 */
	public static String String2UrlParams(Object obj, String firstChar, boolean nullShow) {

		StringBuilder params = new StringBuilder(firstChar);
		Class<? extends Object> clazz = obj.getClass();
		List<Field> fieldList = ReflectionUtils.getClassFields(clazz);

		for (Field field : fieldList) {

			// 排除java对象因实现Serializable接口添加的serialVersionUID属性
			if (field.getName().equals(SERIAL_VERSION_UID)) {
				continue;
			}
			String fieldName = field.getName();
			Object fieldValue = ReflectionUtils.getFieldValue(obj, fieldName);
			
			if(fieldValue == null && nullShow) {
				continue;
			}
			
			params.append(fieldName).append("=").append(fieldValue).append("&");
			
		}

		return params.length() == firstChar.length() ? "" : params.substring(0, params.length() -1);
	}

}
