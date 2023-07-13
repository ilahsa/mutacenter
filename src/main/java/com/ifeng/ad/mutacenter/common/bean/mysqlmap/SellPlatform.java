package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

/**
 * 对接平台信息对象
 */
public class SellPlatform {
    public String platformId;
    public String shotName; //平台简称
    public String nameCode; //平台标识代码
    public int type; //类型0dsp 1网盟
    public String grade; //分级
    public String loginname; //平台用户名
    public String password; //平台密码
    public String token; //平台token
    public int protocolType; //使用协议 0凤飞 1其他
    public String protocolVer; //协议版本
    public int dockingType; //对接类型 -1全部，0api，1sdk
    public String dockingUrl;//api地址
    public String supportAdforms; //平台支持的类型和尺寸 多个\n分隔 k->v k是类型id v是尺寸id
    public Long trafficLimitSeconds;//qps
    public int limitDayStatus; //是否拥有每日限流(0、没有限流；1、有限流；3、达到上限)
    public Long trafficLimitDay;//日上限
    public Long frequency;//频控
    public String modelsList;//机型列表
    public String status;
    public int isUsePrice; //是否返回价格（1：是，0：否）
    public int isVirtual;//是否虚拟平台(1:是；0:否)
    public String startDate;// 平台投放开始时间，为空则投放
    public String endDate;// 平台投放结束时间，为空则投放
    public String tagId;  //标签tagid 值
//    public static SellPlatform creater(String platformId) {
//        SellPlatform sellPlatform = new SellPlatform();
//        sellPlatform.setPlatformId(platformId);
//        return sellPlatform;
//    }
    public String isSettle; //结算系数 1 无 2启用'
    public String settle;  // 结算系数值

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getShotName() {
        return shotName;
    }

    public void setShotName(String shotName) {
        this.shotName = shotName;
    }

    public String getNameCode() {
        return nameCode;
    }

    public void setNameCode(String nameCode) {
        this.nameCode = nameCode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(int protocolType) {
        this.protocolType = protocolType;
    }

    public String getProtocolVer() {
        return protocolVer;
    }

    public void setProtocolVer(String protocolVer) {
        this.protocolVer = protocolVer;
    }

    public int getDockingType() {
        return dockingType;
    }

    public void setDockingType(int dockingType) {
        this.dockingType = dockingType;
    }

    public String getDockingUrl() {
        return dockingUrl;
    }

    public void setDockingUrl(String dockingUrl) {
        this.dockingUrl = dockingUrl;
    }

    public String getSupportAdforms() {
        return supportAdforms;
    }

    public void setSupportAdforms(String supportAdforms) {
        this.supportAdforms = supportAdforms;
    }

    public Long getTrafficLimitSeconds() {
        return trafficLimitSeconds;
    }

    public void setTrafficLimitSeconds(Long trafficLimitSeconds) {
        this.trafficLimitSeconds = trafficLimitSeconds;
    }

    public int getLimitDayStatus() {
        return limitDayStatus;
    }

    public void setLimitDayStatus(int limitDayStatus) {
        this.limitDayStatus = limitDayStatus;
    }

    public Long getTrafficLimitDay() {
        return trafficLimitDay;
    }

    public void setTrafficLimitDay(Long trafficLimitDay) {
        this.trafficLimitDay = trafficLimitDay;
    }

    public Long getFrequency() {
        return frequency;
    }

    public void setFrequency(Long frequency) {
        this.frequency = frequency;
    }

    public String getModelsList() {
        return modelsList;
    }

    public void setModelsList(String modelsList) {
        this.modelsList = modelsList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIsUsePrice() {
        return isUsePrice;
    }

    public void setIsUsePrice(int isUsePrice) {
        this.isUsePrice = isUsePrice;
    }

    public int getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(int isVirtual) {
        this.isVirtual = isVirtual;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getIsSettle() {
        return isSettle;
    }

    public void setIsSettle(String isSettle) {
        this.isSettle = isSettle;
    }

    public String getSettle() {
        return settle;
    }

    public void setSettle(String settle) {
        this.settle = settle;
    }
}
