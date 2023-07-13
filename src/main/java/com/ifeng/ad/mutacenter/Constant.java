package com.ifeng.ad.mutacenter;

import java.util.concurrent.TimeUnit;

/**
 * 项目描述: 常量类
 */
public interface Constant {
    /**
     * 基础包包名
     */
    String BASE_PACKAGE = Constant.class.getPackage().getName();
    
	/** 默认字符 **/
	String DEFAULT_ENCODING = "UTF-8";

	String WORD_EMPTY = "";
	String WORD_SPLIT_COLON=":";

	String WORD_DEVID= "developerid";
	String WORD_UPDATETPYE= "updatetype";
	String WORD_UPDATEID="updateid";
	String WORD_MUTA="muta";
	String WORD_CONFIGKEY="configkey";
	String EVENT_BUS_LISTEN_WORKER_POOL_NAME = "eventbus-pool";
	int EVENT_BUS_LISTEN_WORKER_POOL_SIZE = 50;
	int EVENT_BUS_LISTEN_WORKER_POOL_MAX_EXEC_TIME = 100;
	TimeUnit EVENT_BUS_LISTEN_WORKER_POOL_TIME_UNIT = TimeUnit.MILLISECONDS;

	/**
	 * redis中权重的前缀
	 */
	String REDIS_PREFIX_WEIGHT = "muta:Weight:posId:";

	/**
	 * redis中权重的前缀
	 */
	String REDIS_PREFIX_ECPM = "dmp:ecpm:posId:";

	/**
	 * redis中广告位信息的值
	 */
	String REDIS_PREFIX_ADPOSITIONPRICE = "muta:Adposition:posId:";

	/**
	 * sdk支持
	 */
	String REDIS_PREFIX_SDKSUPPORT = "muta:sdkSupport";

	String REDIS_PREFIX_ENVIONMENT  = "muta:envionment";
}
