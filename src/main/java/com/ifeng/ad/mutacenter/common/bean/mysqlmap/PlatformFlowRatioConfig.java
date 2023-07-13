package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

/**
 * Created by baibq on 2020-10-19.
 */
public class PlatformFlowRatioConfig {

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appIdf) {
        this.appId = appIdf;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String appId; //平台id

    private String platformId;//平台id

    private String config; // 配置 {分组名称};{配置百分比},{分组名称};{配置百分比}

    private String status;//状态

}
