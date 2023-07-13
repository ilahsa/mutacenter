package com.ifeng.ad.mutacenter.common.enums;

/**
 * 项目描述: 运营商枚举
 */
public enum CarrierType {
	// 0.未知; 1.中国移动; 2.中国联通; 3.中国电信
	UNKNOW, CHINA_MOBILE, CHINA_UNICOM, CHINA_TELECOM;

	public int value() {
		return ordinal();
	}
}
