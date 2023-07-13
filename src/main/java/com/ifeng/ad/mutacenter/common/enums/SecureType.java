package com.ifeng.ad.mutacenter.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 类描述: url类型
 */
public enum SecureType {
	
	HTTP, // http
	HTTPS; // https

	@JsonCreator
	public static String getItem(int value) {
		for (SecureType item : values()) {
			if (item.value() == value) {
				return item.toString().toLowerCase();
			}
		}
		return null;
	}

	@JsonValue
	public int value() {
		return ordinal();
	}
}
