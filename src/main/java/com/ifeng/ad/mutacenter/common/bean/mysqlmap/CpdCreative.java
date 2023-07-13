package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

/**
 * cpd创意信息
 */
public class CpdCreative {
    public String creativeId;
    public String developerId;
    public String positionId;
    public String deliveryId;
    public String producetId;
    public String sizeId;
    public String formType;
    public String fomrId;
    public String customerId; //客户id
    public String orderId;
    public String expiryDate; //k->v格式，多个再以\n分隔，k是日期，v是该创意在该k里所占的轮播数
    public String define;//k->v格式，多个以\n分隔 防止文案中的特殊字符导致分隔错误
    public String status;

//    public static CpdCreative creater(String creativeId) {
//        CpdCreative cpdCreative = new CpdCreative();
//        cpdCreative.setCreativeId(creativeId);
//        return cpdCreative;
//    }

    public String getCreativeId() {
        return creativeId;
    }

    public void setCreativeId(String creativeId) {
        this.creativeId = creativeId;
    }

    public String getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(String developerId) {
        this.developerId = developerId;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getProducetId() {
        return producetId;
    }

    public void setProducetId(String producetId) {
        this.producetId = producetId;
    }

    public String getSizeId() {
        return sizeId;
    }

    public void setSizeId(String sizeId) {
        this.sizeId = sizeId;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getFomrId() {
        return fomrId;
    }

    public void setFomrId(String fomrId) {
        this.fomrId = fomrId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getDefine() {
        return define;
    }

    public void setDefine(String define) {
        this.define = define;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
