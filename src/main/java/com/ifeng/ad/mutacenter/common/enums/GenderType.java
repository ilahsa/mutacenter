package com.ifeng.ad.mutacenter.common.enums;

/**
 * 项目描述: 性别
 */
public enum GenderType {
	// male：男性，female：女性，unknown：未知。
	MALE, FEMALE, UNKNOWN;

	public String value() {
		return this.toString().toLowerCase();
	}

}
