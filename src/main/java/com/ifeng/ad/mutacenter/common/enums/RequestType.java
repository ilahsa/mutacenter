package com.ifeng.ad.mutacenter.common.enums;


/**
 * 类描述: 流量来源类型
 */
public enum RequestType {
    /**
     *  流量请求时的数据格式:
     *      0.JSON数据格式
     *      1.PB数据格式
     */
    JSON, PB;

    public int value() {
        return ordinal();
    }
}
