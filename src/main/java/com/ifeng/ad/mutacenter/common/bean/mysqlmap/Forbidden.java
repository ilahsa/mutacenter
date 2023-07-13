package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

/**
 * 禁投行业
 */
public class Forbidden {
    public String developerId; //开发者id
    public String platformName; //禁投客户名称标识
    public String tradeValue; //禁投内容

    public String getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(String developerId) {
        this.developerId = developerId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getTradeValue() {
        return tradeValue;
    }

    public void setTradeValue(String tradeValue) {
        this.tradeValue = tradeValue;
    }
}
