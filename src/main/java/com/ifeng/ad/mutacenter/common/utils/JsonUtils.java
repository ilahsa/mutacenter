package com.ifeng.ad.mutacenter.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifeng.ad.mutacenter.common.exception.BusinessException;
import com.ifeng.ad.mutacenter.common.exception.Errors;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.List;

/**
 * 项目描述: Json工具类
 */
public class JsonUtils {
	
	// 默认Mapper对象
	public static ObjectMapper defaultMapper = new ObjectMapper();

	// 空属性不序列化Mapper
	public static ObjectMapper nonNullMapper = new ObjectMapper();
	
	/**
	 * 初始化时设置默认Mapper的属性
	 */
	static {
		// 未知属性不报错
		defaultMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 未知属性不报错
		nonNullMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 空属性不序列化
		nonNullMapper.setSerializationInclusion(Include.NON_EMPTY);
	}

	/**
	 * 功能描述: 将Json串转换为对象
	 */
	public static <T> T convert2Obj(String json, Class<T> clazz) {
		// 如果Json无内容，直接返回空
		if (StringUtils.isEmpty(json)) {
			return null;
		}
		
		try {
			return defaultMapper.readValue(json, clazz);
		} catch (IOException e) {
			throw new BusinessException(Errors.INTERNAL_ERROR, "将Json串转换为对象错误，转换串：{},转换类名：{}", json, clazz);
		}
	}

	public static List<Bean>  convert2Obj22(String json) {
		// 如果Json无内容，直接返回空
		if (StringUtils.isEmpty(json)) {
			return null;
		}

		try {
			return defaultMapper.readValue(json, new TypeReference<List<Bean>>() {});
		} catch (IOException e) {
			throw new BusinessException(Errors.INTERNAL_ERROR, "将Json串转换为对象错误，转换串：{},转换类名", json);
		}
	}
	/**
	 * 功能描述: 将对象转换为Json字符串
	 *   
	 * @param obj 需要转换的对象
	 * @return Json字符串
	 */
	public static String convert2Json(Object obj) {
		return convert2Json(obj, true);
	}
	
	/**
	 * 功能描述: 将对象转换为Json字符串
	 *   
	 * @param obj 需要转换的对象
	 * @param nonNull 空字段时间转换, true:转换, false:不转换
	 * @return
	 */
	public static String convert2Json(Object obj, boolean nonNull) {
		try {
			if (nonNull) {
				return defaultMapper.writeValueAsString(obj);
			} else {
				return nonNullMapper.writeValueAsString(obj);
			}
		} catch (JsonProcessingException e) {
			throw new BusinessException(Errors.INTERNAL_ERROR, "将对象转换为Json字符串错误");
		}
	}

}
