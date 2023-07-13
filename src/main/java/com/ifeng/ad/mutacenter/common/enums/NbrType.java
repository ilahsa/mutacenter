package com.ifeng.ad.mutacenter.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 类描述: 未竞价的Bid原因
 */
public enum NbrType {
	
	SUCCESS, // 0：成功
	PRICE_LESS_BIDFLOOR, // 1：出价低于底价
	RESPONSE_SIZE_ERROR, // 2：响应尺寸不匹配
	PRICE_TOO_LESS, // 3：出价低于成交价格
	CREATIVE_UNREVIEWED, // 4：创意审核未通过
	ADVER_UNREVIEWED, // 5：广告主审核未通过
	INDUSTRY_LIMITED, // 6：行业受限
	RESPONSE_NO_MATERIAL,	// 7：KDG返回协议没有素材资源
	URL_SECURE_ERROR; //8：响应url与请求要求不匹配

	@JsonCreator
	public static NbrType getItem(int value) {
		for (NbrType item : values()) {
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
