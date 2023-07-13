package com.ifeng.ad.mutacenter.common.enums;

/**
 * 类描述: 平台和媒体中status值
 */
public enum PlatAndMediaStatusType {
    /**
     *状态：0正常，1删除
     */
    NORMAL,
    DELETE;

    public int value() {
        return ordinal();
    }

}
