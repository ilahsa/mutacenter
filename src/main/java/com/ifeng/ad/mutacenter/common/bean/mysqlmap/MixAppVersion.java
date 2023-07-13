package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

public class MixAppVersion {
    public String devId;//平台ID
    public String appId;//客户端ID
    public String version;//常用版本

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
