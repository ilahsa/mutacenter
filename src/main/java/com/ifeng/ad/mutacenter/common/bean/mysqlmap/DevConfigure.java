package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

/**
 * 开发者-应用-广告位：对应的价格
 * platform_info 表中，如果is_use_price是1，则使用该对象中的price当作比例，是0就是用price当价格
 */
public class DevConfigure {

    public String devConfigId; //devId_appId_positionId 的合体
    public String devId; //开发者id
    public String appId; //appid
    public String positionId; //广告位id
    public double price;  //价格 或者 比例
    public int status;

    public String getDevConfigId() {
        return devConfigId;
    }

    public void setDevConfigId(String devConfigId) {
        this.devConfigId = devConfigId;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
