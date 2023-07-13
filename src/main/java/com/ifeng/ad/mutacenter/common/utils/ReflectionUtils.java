package com.ifeng.ad.mutacenter.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 项目描述: Java反射工具类
 */
public class ReflectionUtils {

	
	/**
	 * 功能描述: 获取类的所有属性(包括继承过来的属性)
	 *   
	 * @param clazz 需要获取的类class
	 * @return   属性List
	 */
	public static List<Field> getClassFields(Class<?> clazz) {
		List<Field> fieldList = new ArrayList<>();
		getClassFields(clazz, fieldList);
		return fieldList;
	}
	
	/**
	 * 功能描述: 获取类的所有属性(包括继承过来的属性)
	 *   
	 * @param clazz 需要获取的类class
	 * @param fieldList 属性List
	 * @return   属性List
	 */
	public static List<Field> getClassFields(Class<?> clazz, List<Field> fieldList) {
		Field[] fields = clazz.getDeclaredFields();
		if (null == fields) {
			return fieldList;
		}
		if (null == fieldList) {
			fieldList = new ArrayList<Field>();
		}
		fieldList.addAll(Arrays.asList(fields));
		if (null == clazz.getSuperclass()) {
			return fieldList;
		}
		return getClassFields(clazz.getSuperclass(), fieldList);
	}

	/**
	 * 功能描述: 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
	 *   
	 * @param object 对象
	 * @param fieldName 属性名称
	 * @return   属性值
	 */
	public static Object getFieldValue(final Object object, final String fieldName) {
		Field field = getDeclaredField(object, fieldName);
		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		}
		makeAccessible(field);
		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("直接读取对象属性值出现异常", e);
		}
		return result;
	}

	/**
	 * 功能描述: 循环向上转型, 获取对象的DeclaredField. 若向上转型到Object仍无法找到, 返回null.
	 *   
	 * @param object 对象
	 * @param fieldName 属性名称
	 * @return   Field
	 */
	protected static Field getDeclaredField(final Object object, final String fieldName) {
		if (null == object || null == fieldName || fieldName.equals("")) {
			return null;
		}
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {// NOSONAR
				// Field不在当前类定义,继续向上转型
				continue;
			}
		}
		return null;
	}

	/**
	 * 功能描述: 强行设置Field可访问
	 *   
	 * @param field  Field属性
	 */
	protected static void makeAccessible(final Field field) {
		if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
			field.setAccessible(true);
		}
	}
}
