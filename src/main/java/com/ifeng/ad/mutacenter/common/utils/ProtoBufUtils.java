package com.ifeng.ad.mutacenter.common.utils;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.Message;
import io.vertx.core.buffer.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 项目描述: ProtoBuf转换工具类
 */
@SuppressWarnings("unchecked")
public class ProtoBufUtils {
	
	/** 日志对象 **/
	private static Logger logger = LoggerFactory.getLogger(ProtoBufUtils.class);

	/** 方法缓存类 **/
	private static final ConcurrentHashMap<Class<?>, Method> methodCache = new ConcurrentHashMap<Class<?>, Method>();

	
	/**
	 * 功能描述: 将Buffer转换为ProtoBuf对象
	 *   
	 * @param clazz ProtoBuf对象的Calss
	 * @param buffer 数据来源
	 * @param extensionClazzs ProtoBuf扩展类Class数组
	 * @return   转换完成的ProtoBuf对象
	 */
	public static <T extends Message> T convert2ProtoBuf(io.vertx.reactivex.core.buffer.Buffer buffer, Class<T> clazz, 
			Class<?>... extensionClazzs) {
		return convert2ProtoBuf(buffer.getDelegate(), clazz, extensionClazzs);
	}

	/**
	 * 功能描述: 将Buffer转换为ProtoBuf对象
	 *   
	 * @param clazz ProtoBuf对象的Calss
	 * @param buffer 数据来源
	 * @param extensionClazzs ProtoBuf扩展类Class数组
	 * @return   转换完成的ProtoBuf对象
	 */
	public static <T extends Message> T convert2ProtoBuf(Buffer buffer, Class<T> clazz, Class<?>... extensionClazzs) {
		return convert2ProtoBuf(buffer.getBytes(), clazz, extensionClazzs);
	}

	/**
	 * 功能描述: 将byte数组转换为ProtoBuf对象
	 *   
	 * @param clazz ProtoBuf对象的Calss
	 * @param context 数据来源
	 * @param extensionClazzs ProtoBuf扩展类Class数组
	 * @return   转换完成的ProtoBuf对象
	 */
	public static <T extends Message> T convert2ProtoBuf(byte[] context, Class<T> clazz, Class<?>... extensionClazzs) {

		try {
			// 通过Class获得Builder对象
			Message.Builder builder = getMessageBuilder(clazz);

			// 当存在ProtoBuf的扩展类时，转换ProtoBuf对象时加入扩展字段的支持
			if (extensionClazzs != null && extensionClazzs.length > 0) {

				// 通过ProtoBuf的扩展类数组获得ExtensionRegistry
				ExtensionRegistry extensionRegistry = getExtensionRegistry(extensionClazzs);

				// 转换ProtoBuf对象时加入扩展字段的支持
				return (T) builder.mergeFrom(context, extensionRegistry).build();
			}
			
			// 转换ProtoBuf对象
			return (T) builder.mergeFrom(context).build();
		} catch (Exception e) {
			logger.error("Convert Fail, Class:{}, Message:{}", clazz.getName(), e.getMessage());
		}
		return null;
	}

	/**
	 * 功能描述: 将ProtoBuf转换为byte数组
	 *   
	 * @param protoBuf ProtoBuf对象
	 * @return   byte数组
	 */
	public static <T extends Message> byte[] convert2ByteArr(T protoBuf) {
		return protoBuf.toByteArray();
	}

	/**
	 * 功能描述: 通过Class获得Builder对象
	 *   
	 * @param clazz ProtoBuf对象的Calss
	 * @return ProtoBuf对应的Builder对象
	 * @throws Exception   反射导致的异常
	 */
	private static Message.Builder getMessageBuilder(Class<? extends Message> clazz) throws Exception {
		// 先从缓存中读取
		Method method = methodCache.get(clazz);
		// 如果缓存中无此类，获得该类的newBuilder方法，将方法放入到缓存中
		if (method == null) {
			method = clazz.getMethod("newBuilder");
			methodCache.put(clazz, method);
		}
		// 反射调用newBuilder生成Builder对象
		return (Message.Builder) method.invoke(clazz);
	}

	/**
	 * 功能描述: 通过ProtoBuf的扩展类数组获得ExtensionRegistry
	 *   
	 * @param extensionClazzs ProtoBuf的扩展类数组
	 * @return ExtensionRegistry
	 * @throws Exception   反射导致的异常
	 */
	private static ExtensionRegistry getExtensionRegistry(Class<?>[] extensionClazzs) throws Exception {
		// 创建ExtensionRegistry
		ExtensionRegistry registry = ExtensionRegistry.newInstance();
		
		// 遍历ProtoBuf的扩展类数组
		for(Class<?> extensionClazz : extensionClazzs) {
			// 从缓存中读取
			Method method = methodCache.get(extensionClazz);

			// 缓存中无此类时，获得该类的registerAllExtensions方法，将方法放入到缓存中
			if (method == null) {
				method = extensionClazz.getMethod("registerAllExtensions", ExtensionRegistry.class);
				methodCache.put(extensionClazz, method);
			}
			// 调用ExtensionRegistry.registerAllExtensions方法，注册所有扩展字段
			method.invoke(null, registry);
		}
		
		return registry;
	}
	
}
