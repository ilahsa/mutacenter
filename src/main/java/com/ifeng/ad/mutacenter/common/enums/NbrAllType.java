package com.ifeng.ad.mutacenter.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 类描述: 未竞价的所有原因
 */
public enum NbrAllType {
	
	SUCCESS, // 0：成功
	RESPONSE_TIME_OUT, // 1：响应超时
	RESPONSE_PROTOCOL_ERROR, // 2：响应协议异常
	RESPONSE_NO_AD, // 3：响应无广告
	PRICE_LESS_BIDFLOOR, // 4：出价低于底价
	RESPONSE_SIZE_ERROR, // 5：响应尺寸不匹配
	PRICE_TOO_LESS, // 6：出价低于成交价格
	CREATIVE_UNREVIEWED, // 7：创意审核未通过
	ADVER_UNREVIEWED, // 8：广告主审核未通过
	INDUSTRY_LIMITED, // 9：行业受限
	RESPONSE_NO_MATERIAL,	// 10：KDG返回协议没有素材资源
	URL_SECURE_ERROR; // 11：响应url与请求要求不匹配

	@JsonCreator
	public static NbrAllType getItem(int value) {
		for (NbrAllType item : values()) {
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
	
	public static NbrAllType getItem(DSPRespFlag dspResFlag) {
		if(DSPRespFlag.TIMEOUT.equals(dspResFlag)) {
			return RESPONSE_TIME_OUT;
		}
		if(DSPRespFlag.PROTOCOL_ERROR.equals(dspResFlag)) {
			return RESPONSE_PROTOCOL_ERROR;
		}
		if(DSPRespFlag.NONE_AD.equals(dspResFlag)) {
			return RESPONSE_NO_AD;
		}
		return SUCCESS;
	}

	public static NbrAllType getItem(NbrType nbrType) {
		if (NbrType.SUCCESS.equals(nbrType)) {
			return SUCCESS;
		}
		if (NbrType.PRICE_LESS_BIDFLOOR.equals(nbrType)) {
			return PRICE_LESS_BIDFLOOR;
		}
		if (NbrType.RESPONSE_SIZE_ERROR.equals(nbrType)) {
			return RESPONSE_SIZE_ERROR;
		}
		if (NbrType.CREATIVE_UNREVIEWED.equals(nbrType)) {
			return CREATIVE_UNREVIEWED;
		}
		if (NbrType.ADVER_UNREVIEWED.equals(nbrType)) {
			return ADVER_UNREVIEWED;
		}
		if (NbrType.INDUSTRY_LIMITED.equals(nbrType)) {
			return INDUSTRY_LIMITED;
		}
		if (NbrType.RESPONSE_NO_MATERIAL.equals(nbrType)) {
			return RESPONSE_NO_MATERIAL;
		}
		if (NbrType.URL_SECURE_ERROR.equals(nbrType)) {
			return URL_SECURE_ERROR;
		}
		return PRICE_TOO_LESS;
	}
}
