package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

/**
 * 广告位信息对象
 */
public class Adposition {
    public String posId;
    public String developerId;
    public String appId;
    public String pageId;
    public String isThirdSell;
    public String devAdId;
    public String carousel;
    public String formType;
    public String adform;//逗号分隔
    public String bottomPrice;
    public String cheatallocation;//作弊流量还投放的网盟，网盟id，逗号分隔
    public String status;
    public String returnPrice; //返价价格,可设置，可为空(每个广告位有个返价价格，如果有，则它的优先级最高，没有就是算法算的价格）

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }

    public String getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(String developerId) {
        this.developerId = developerId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getIsThirdSell() {
        return isThirdSell;
    }

    public void setIsThirdSell(String isThirdSell) {
        this.isThirdSell = isThirdSell;
    }

    public String getDevAdId() {
        return devAdId;
    }

    public void setDevAdId(String devAdId) {
        this.devAdId = devAdId;
    }

    public String getCarousel() {
        return carousel;
    }

    public void setCarousel(String carousel) {
        this.carousel = carousel;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getAdform() {
        return adform;
    }

    public void setAdform(String adform) {
        this.adform = adform;
    }

    public String getBottomPrice() {
        return bottomPrice;
    }

    public void setBottomPrice(String bottomPrice) {
        this.bottomPrice = bottomPrice;
    }

    public String getCheatallocation() {
        return cheatallocation;
    }

    public void setCheatallocation(String cheatallocation) {
        this.cheatallocation = cheatallocation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReturnPrice() {
        return returnPrice;
    }

    public void setReturnPrice(String returnPrice) {
        this.returnPrice = returnPrice;
    }
}
