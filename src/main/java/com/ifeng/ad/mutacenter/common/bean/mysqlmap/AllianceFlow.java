package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

public class AllianceFlow {
    public String id;//主键
    public String platformId;//平台ID
    public String devId;//开发者ID
    public String appId;//应用ID
    public String positionId;//广告位ID
    public String areaCode;//定向地域,逗号分隔
    public String requestType;//比例类型(0：按应用；1：按广告位)
    public String appRatio;//应用请求比例(%)
    public String positionRatio;//广告位请求比例(%)
    public String uaVerify;//UA开关（0：否；1：是）
    public String oversizeRatio;//超限比例（临时DMP控制）
    public String status;//
    public String no_auth_flow; //是否分发未授权流量（0：否；1：是）
    public String impl_upper; //曝光上线

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

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getAppRatio() {
        return appRatio;
    }

    public void setAppRatio(String appRatio) {
        this.appRatio = appRatio;
    }

    public String getPositionRatio() {
        return positionRatio;
    }

    public void setPositionRatio(String positionRatio) {
        this.positionRatio = positionRatio;
    }

    public String getUaVerify() {
        return uaVerify;
    }

    public void setUaVerify(String uaVerify) {
        this.uaVerify = uaVerify;
    }

    public String getOversizeRatio() {
        return oversizeRatio;
    }

    public void setOversizeRatio(String oversizeRatio) {
        this.oversizeRatio = oversizeRatio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNo_auth_flow() {
        return no_auth_flow;
    }

    public void setNo_auth_flow(String no_auth_flow) {
        this.no_auth_flow = no_auth_flow;
    }

    public String getImpl_upper() {
        return impl_upper;
    }

    public void setImpl_upper(String impl_upper) {
        this.impl_upper = impl_upper;
    }
}
