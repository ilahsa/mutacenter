package com.ifeng.ad.mutacenter.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 项目描述: Spring工具类
 */
@Component
public class SpringUtils implements ApplicationContextAware {
	/** 上下文 */
	private static ApplicationContext applicationContext;

	/**
	 * 功能描述: 根据Bean ID获取Bean
	 *   
	 * @param beanId beanId
	 * @return Spring托管的Bean对象
	 */
	public static Object getBean(String beanId) {
		return getApplicationContext().getBean(beanId);
	}

	/**
	 * 功能描述: 根据Bean类型获取Bean
	 *   
	 * @param clazz Bean的Class类型
	 * @return Spring托管的Bean对象
	 */
	public static <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}

	/**
	 * 功能描述: 根据Bean ID获取Bean
	 *   
	 * @param beanId beanId
	 * @param clazz Bean的Class类型
	 * @return Spring托管的Bean对象
	 */
	public static <T> T getBean(String beanId, Class<T> clazz) {
		return getApplicationContext().getBean(beanId, clazz);
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtils.applicationContext = applicationContext;
	}
}
