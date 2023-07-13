package com.ifeng.ad.mutacenter.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Optional;

/**
 * 项目描述: 作弊状态
 */
public enum AntiCheatStatus {
	
	NORMAL, // 正常
	TRACKER_TIMEOUT, // 监测超时
	IMP_TRACKER_REPEAT, // 曝光监测重复
	CLICK_TRACKER_REPEAT, // 点击监测重复
	CLICK_TRACKER_2_FAST, // 点击与曝光时间差<200ms（可配置）点击过快
	IP_ADD_ILLEGAL, // IP地址不合法
	IP_IN_BLACKLIST_FILE, // IP在黑名单文件中
	IMP_TRACKER_IP_FREQUENCY_HIDDEN, // 曝光监测IP频次屏蔽
	CLICK_TRACKER_IP_FREQUENCY_HIDDEN; // 点击监测IP频次屏蔽
	
	@JsonCreator
	public static AntiCheatStatus getItem(int value) {
		Optional<AntiCheatStatus> findFirst = Arrays.stream(AntiCheatStatus.values())
				.filter(type -> type.value() == value).findFirst();

		if (findFirst.isPresent()) {
			return findFirst.get();
		}
		return NORMAL;

	}

	@JsonValue
	public int value() {
		return ordinal();
	}
}
