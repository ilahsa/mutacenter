package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

public class MixFlow {
    public String id;//主键
    public String platformId;//平台ID
    public String origAdId;//原始广告位ID
    public String mixAdId;//混量广告位ID
    public String mixAdRate;//混量广告位概率
    public String status;//

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getOrigAdId() {
        return origAdId;
    }

    public void setOrigAdId(String origAdId) {
        this.origAdId = origAdId;
    }

    public String getMixAdId() {
        return mixAdId;
    }

    public void setMixAdId(String mixAdId) {
        this.mixAdId = mixAdId;
    }

    public String getMixAdRate() {
        return mixAdRate;
    }

    public void setMixAdRate(String mixAdRate) {
        this.mixAdRate = mixAdRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
