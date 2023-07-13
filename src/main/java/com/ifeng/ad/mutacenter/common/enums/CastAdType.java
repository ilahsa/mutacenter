package com.ifeng.ad.mutacenter.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 项目描述: 映射广告类型
 */
public enum CastAdType {
	
	BANNER, // 图片
	NATIVE, // 图文
	NATIVE_GROUP, // 组图
	NATIVE_VIDEO, // 短视频
	VIDEO; // 长视频

	@JsonCreator
	public static CastAdType getItem(int value) {
		for (CastAdType item : values()) {
			if (item.value() == value) {
				return item;
			}
		}
		return null;
	}

	@JsonValue
	public int value() {
		return ordinal() + 1;
	}
}
