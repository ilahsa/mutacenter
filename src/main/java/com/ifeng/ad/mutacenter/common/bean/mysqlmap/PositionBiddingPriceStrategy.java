package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

public class PositionBiddingPriceStrategy {
    public String posId; //广告位id
    public String devId; //开发者id
    public String appId; //应用id
    public String platformId; //平台id
    public String percentage; //概率
    public String standardDeviation; //标准差
    public String offset; //偏移量
    public String scale;//最高价比例
    public String percent; //限制凤飞客户请求凤羽的比例
    public String status;
    public String returnRate; //返价分成比例
    public String noDivideCode; //不参与分成的网盟的code(,分隔)
    public String floorPrice; //发给dsp的底价，如果没有的话，用app发出的底价，如果再没有，用广告位的底价
    public String returnPrice; //返价价格,可设置，可为空(每个广告位有个返价价格，如果有，则它的优先级最高，没有就是算法算的价格）
    public String isRtb; //是否RTB(0否 1是)
    public String upPrice; //加价比例：百分比但只能填正整数
    public String profitPrice; //利润比例：百分比但只能填正整数
    public String isTrans; //besq 是否价格透传 0否 1是

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
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

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(String standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReturnRate() {
        return returnRate;
    }

    public void setReturnRate(String returnRate) {
        this.returnRate = returnRate;
    }

    public String getNoDivideCode() {
        return noDivideCode;
    }

    public void setNoDivideCode(String noDivideCode) {
        this.noDivideCode = noDivideCode;
    }

    public String getFloorPrice() {
        return floorPrice;
    }

    public void setFloorPrice(String floorPrice) {
        this.floorPrice = floorPrice;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getReturnPrice() {
        return returnPrice;
    }

    public void setReturnPrice(String returnPrice) {
        this.returnPrice = returnPrice;
    }

    public String getIsRtb() {
        return isRtb;
    }

    public void setIsRtb(String isRtb) {
        this.isRtb = isRtb;
    }

    public String getUpPrice() {
        return upPrice;
    }

    public void setUpPrice(String upPrice) {
        this.upPrice = upPrice;
    }

    public String getProfitPrice() {
        return profitPrice;
    }

    public void setProfitPrice(String profitPrice) {
        this.profitPrice = profitPrice;
    }

    public String getIsTrans() {
        return isTrans;
    }

    public void setIsTrans(String isTrans) {
        this.isTrans = isTrans;
    }
}
