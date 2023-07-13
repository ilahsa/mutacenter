package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

/**
 * 竞价策略
 */
public class BiddingPriceStrategy {
    public String id;
    public String devId; //开发者id
    public String appId; //应用id
    public String percentage; //概率
    public String standardDeviation; //标准差
    public String offset; //偏移量
    public String scale;//最高价比例
    public String percent; //限制凤飞客户请求凤羽的比例
    public String type;//类型（1:竞价托，2:返价托） 20200729 返价托不再有用，直接靠返价价格来，返价价格是手填，没填的话就是算法算的
    public String status;
    public String returnRate; //返价分成比例
    public String noDivideCode; //不参与分成的网盟的code(,分隔)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
