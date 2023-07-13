package com.ifeng.ad.mutacenter.common.enums;

/**
 * 项目描述: 网络连接类型
 */
public enum ConnectionType {
	
	// 0.未知; 1.WIFI; 2.2G; 3.3G; 4.4G; 5.5G
	UNKNOW, WIFI, CELL_2G, CELL_3G, CELL_4G, CELL_5G;

	public int value() {
		return ordinal();
	}
}
