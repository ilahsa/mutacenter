package com.ifeng.ad.mutacenter.common.utils;

import java.util.List;
import java.util.Random;

/**
 * 项目描述: 随机工具类
 */
public class RandomUtils {

	/** Random实例 **/
	private static Random random = new Random();

	/**
	 * 功能描述: 获得List中随机的一个对象
	 *   
	 * @param list List数据
	 * @return   List中随机的一个对象
	 */
	public static <T> T getRandom(List<T> list) {
		// 如果List为空，返回空
		if (ObjectUtils.isEmpty(list)) {
			return null;
		}

		// 获得List的Size
		int size = list.size();

		// 如果只有一个对象，返回第一个
		if (size == 1) {
			return list.get(0);
		}
		
		// 通过List的Size获得一个随机数
		int idx = random.nextInt(size);

		// 返回随机下标的对象
		return list.get(idx);
	}
	public static int RandomInt(int max){
		return org.apache.commons.lang3.RandomUtils.nextInt(0, max);
	}


	public static int RandomInt(int min, int max){
		return org.apache.commons.lang3.RandomUtils.nextInt(min, max);
	}
}
