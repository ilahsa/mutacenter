package com.ifeng.ad.mutacenter.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 项目描述: 是否是测试流量
 */
public enum IsTest {
	NO, // 否（正式流量）
	YES; // 是（测试流量）
	
	@JsonCreator
	public static IsTest getItem(int value) {
		for (IsTest item : values()) {
			if (item.value() == value) {
				return item;
			}
		}
		return null;
	}

	@JsonValue
	private int value() {
		return ordinal();
	}
}
