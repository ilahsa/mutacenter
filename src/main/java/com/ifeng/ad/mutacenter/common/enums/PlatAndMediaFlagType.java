package com.ifeng.ad.mutacenter.common.enums;

/**
 * 类描述: 平台和媒体中flag值
 */
public enum PlatAndMediaFlagType {
    /**
     * 1开启，2关闭
     */
    OPEN,
    OFF;

    public int value() {
        return ordinal()+1;
    }

}
