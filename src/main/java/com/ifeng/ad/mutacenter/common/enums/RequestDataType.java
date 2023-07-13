package com.ifeng.ad.mutacenter.common.enums;


/**
 * 类描述: 请求数据类型
 */

public enum RequestDataType {
    /**
     *  流量请求时的数据格式:
     *      1.JSON数据格式
     *      2.PB数据格式
     */
    JSON, PB;

    public String value() {
        return this.toString();
    }
}
