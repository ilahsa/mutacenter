package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

public class Developer {
    private String devId;//开发者ID
    private String devCode;//唯一标识
    private String accessType;//对接类型 1:SDK；2:API
    private String agreeType;//协议类别 1:凤飞；2:对方协议
    private String apiVersion;//协议版本
    private Integer useFfId;//是否传递凤飞广告位ID(1:是；0:否)
    private Integer dealType;//交易方式(1分成；2RTB；3保价)
    private Integer devType;//开发者类型(1：白盒；2：黑盒)
    private Integer isBasePrice;//是否设置底价(1:是；0:否)
    private String status;

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getDevCode() {
        return devCode;
    }

    public void setDevCode(String devCode) {
        this.devCode = devCode;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getAgreeType() {
        return agreeType;
    }

    public void setAgreeType(String agreeType) {
        this.agreeType = agreeType;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public Integer getUseFfId() {
        return useFfId;
    }

    public void setUseFfId(Integer useFfId) {
        this.useFfId = useFfId;
    }

    public Integer getDealType() {
        return dealType;
    }

    public void setDealType(Integer dealType) {
        this.dealType = dealType;
    }

    public Integer getDevType() {
        return devType;
    }

    public void setDevType(Integer devType) {
        this.devType = devType;
    }

    public Integer getIsBasePrice() {
        return isBasePrice;
    }

    public void setIsBasePrice(Integer isBasePrice) {
        this.isBasePrice = isBasePrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
