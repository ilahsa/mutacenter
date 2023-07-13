package com.ifeng.ad.mutacenter.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 类描述: 广告打开类型
 */
public enum OpenType {
	// 1：landing; 2：download; 3：deeplink
	LANDING, DOWNLOAD, DEEPLINK;
	
	@JsonCreator
	public static OpenType getItem(String value) {
		for (OpenType item : values()) {
			if (item.value().equals(value)) {
				return item;
			}
		}
		return null;
	}

	@JsonValue
	public String value() {
		 return String.valueOf(ordinal() + 1);
	}
}