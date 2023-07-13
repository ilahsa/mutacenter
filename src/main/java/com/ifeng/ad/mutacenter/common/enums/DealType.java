package com.ifeng.ad.mutacenter.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 项目描述: Deal交易类型
 */
public enum DealType {
	PD, PDB;

	
	/**
	 * 功能描述: 返回Deal交易类型对应的Code
	 */
	@JsonValue
	public int value() {
		return ordinal();
	}
	
	@JsonCreator
	public static DealType getItem(int value) {
		for (DealType item : values()) {
			if (item.value() == value) {
				return item;
			}
		}
		return null;
	}
}
