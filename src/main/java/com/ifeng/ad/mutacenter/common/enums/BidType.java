package com.ifeng.ad.mutacenter.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 项目描述: 交易类型
 */
public enum BidType {
	
	CPM, // 按曝光扣费（千次展示）
	CPC, // 按点击扣费（一次点击）
	CPD, // 按下载扣费（一次下载）
	CPA; // 按激活扣费（一次激活）

	@JsonCreator
	public static BidType getItem(int value) {
		for (BidType item : values()) {
			if (item.value() == value) {
				return item;
			}
		}
		return null;
	}

	@JsonValue
	public int value() {
		return ordinal();
	}
}
