package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

public class ColdBootConfig {
    public String devId; //开发者id
    public String appId; //应用id
    public String posId; //广告位id
    public String platStr; //手动网盟序列id,id,id
    public String status;

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

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }

    public String getPlatStr() {
        return platStr;
    }

    public void setPlatStr(String platStr) {
        this.platStr = platStr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
