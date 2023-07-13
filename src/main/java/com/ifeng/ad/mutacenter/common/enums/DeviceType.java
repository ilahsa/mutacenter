package com.ifeng.ad.mutacenter.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Optional;

/**
 * 项目描述: 设备类型
 */
public enum DeviceType {
	
	// 0.未知; 1.pc; 2.phone; 3.pad; 4.tv
	UNKNOW, PC, PHONE, PAD, TV;
	
	@JsonValue
	public int value() {
		return this.ordinal();
	}
	
	@JsonCreator
	public static DeviceType getItem(int code) {
		Optional<DeviceType> findFirst = Arrays.stream(DeviceType.values()).filter(type -> type.value() == code).findFirst();
		if(findFirst.isPresent()) {
			return findFirst.get();
		}
		return DeviceType.UNKNOW;
	}
}
