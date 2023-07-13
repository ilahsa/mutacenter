package com.ifeng.ad.mutacenter.common.utils;

/**
 * 项目描述: Integer工具类
 */
public class IntegerUtils {

	public static final Integer DEFAULTVAL = 0;
	 

	/**
	 * 功能描述: 获得非空数字
	 *
	 * @param integer 数字
	 * @return 非空数字
	 */
	public static Integer getNoneNullInteger(Integer integer) {
		return getNoneNullString(integer,DEFAULTVAL);
	}
	
	/**
	 * 功能描述: 获得非空数字
	 *   
	 * @param integer 数字
	 * @param defaultVal 默认值（当为null时返回默认值）
	 * @return 非空数字
	 */
	public static Integer getNoneNullString(Integer integer, Integer defaultVal) {
		if(integer == null) {
			return defaultVal;
		}
		return integer;
	}

	/**
	 * 功能描述: 校验Integer类型是否为空或者为0
	 *
	 * @param integer 数字
	 * @return 为空或0 true 不为空或0返回false
	 */
	public static boolean isEmpty(Integer integer) {
		if(integer == null) {
			return true;
		}
		return false;
	}

}
