package com.ifeng.ad.mutacenter.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 项目描述: DSP响应标识
 */
public enum DSPRespFlag {

	SUCCESS, // 成功
	TIMEOUT, // 超时
	DSP_NOT_EXISTS, // DSP不存在
	PROTOCOL_ERROR, // 协议异常
	NONE_AD; // 无广告

	@JsonValue
	public int value() {
		return ordinal();
	}
}
