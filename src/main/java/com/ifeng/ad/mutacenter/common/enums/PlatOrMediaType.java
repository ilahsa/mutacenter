package com.ifeng.ad.mutacenter.common.enums;
/**
 * 类描述: 流量来源是平台或者媒体
 */
public enum PlatOrMediaType {
    // plat(平台):pid,media(媒体):mid
    PLAT("PID"), MEDIA("MID");

    private final String value;

    PlatOrMediaType(String value) {
        this.value = value;
    }
    public String value() {
        return this.value.toLowerCase();
    }

}
