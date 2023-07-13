package com.ifeng.ad.mutacenter.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 项目描述: 广告类型
 */
public enum AdType {
	
	BANNER_IMAGE, // 图片
	BANNER_INSTL, // 插屏
	BANNER_SPLASHSCREEN, // 开屏
	NATIVE_TEXT, // 原生图片+文案
	NATIVE_TEXT_ICON, // 原生图片+文案+图标
	NATIVE_GROUP_TEXT, // 原生组图+文案
	NATIVE_GROUP_TEXT_ICON, // 原生组图+文案+图标
	NATIVE_VIDEO_TEXT, // 原生视频+主图片+文案
	NATIVE_VIDEO_TEXT_ICON, // 原生视频+主图片+文案+图标
	VIDEO; // 视频

	@JsonCreator
	public static AdType getItem(int value) {
		for (AdType item : values()) {
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
