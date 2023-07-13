package com.ifeng.ad.mutacenter.common.factory;

import io.vertx.core.Verticle;
import io.vertx.core.spi.VerticleFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 类描述: 允许将Verticle实现为Spring bean，从而实现于依赖注入
 */
@Component
public class SpringVerticleFactory implements VerticleFactory, ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public boolean blockingCreate() {
		// 通常verticle实例化很快，但由于我们的Verticle是Spring  Beans,
		// 它们可能依赖于构建/查找速度慢的其他beans/resources。
		return true;
	}

	@Override
	public String prefix() {
		// 只是一个必须唯一标识Verticle工厂的任意字符串
		return "mutacenter";
	}

	@Override
	public Verticle createVerticle(String verticleName, ClassLoader classLoader) throws Exception {
		// 我们在这个例子中的约定是将类名称作为verticle名称
		String clazz = VerticleFactory.removePrefix(verticleName);
		return (Verticle) applicationContext.getBean(Class.forName(clazz));
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
