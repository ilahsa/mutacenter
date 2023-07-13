package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

public class AllianceAppInfo {
    public String posId; //广告位id
    public String platformId; //售卖平台id
    public String allianceAppId; //联盟appid
    public String alliancePositionId; //联盟广告位id
    public String feedsAttrCode;//联盟广告位属性code
    public String appId; //appid
    public String status;
    public String onOff;
    public String allianceAppKey;//联盟appkey
    public String allianceAccount;//联盟账号

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getAllianceAppId() {
        return allianceAppId;
    }

    public String getFeedsAttrCode() {
        return feedsAttrCode;
    }

    public void setFeedsAttrCode(String feedsAttrCode) {
        this.feedsAttrCode = feedsAttrCode;
    }

    public void setAllianceAppId(String allianceAppId) {
        this.allianceAppId = allianceAppId;
    }

    public String getAlliancePositionId() {
        return alliancePositionId;
    }

    public void setAlliancePositionId(String alliancePositionId) {
        this.alliancePositionId = alliancePositionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOnOff() {
        return onOff;
    }

    public void setOnOff(String onOff) {
        this.onOff = onOff;
    }



    public String getAllianceAccount() {
        return allianceAccount;
    }

    public void setAllianceAccount(String allianceAccount) {
        this.allianceAccount = allianceAccount;
    }

    public String getAllianceAppKey() {
        return allianceAppKey;
    }

    public void setAllianceAppKey(String allianceAppKey) {
        this.allianceAppKey = allianceAppKey;
    }
}
