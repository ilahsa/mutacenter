package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

/**
 * 广告形式转换
 * Created by guobj@ifeng.com on 2021/11/4.
 */
public class AdFormConvert {
    private String id;


    private String positionId;

    private String platformId;


    private String formId;
    private String sizeId;
    private String formCode;
    private String formType;

    private String switchSizeId;

    private String switchFormId;

    private String switchFormCode;

    //（0：正常；-1 删除；1：停用）
    private String status;

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getSizeId() {
        return sizeId;
    }

    public void setSizeId(String sizeId) {
        this.sizeId = sizeId;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormCode() {
        return formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getSwitchSizeId() {
        return switchSizeId;
    }

    public void setSwitchSizeId(String switchSizeId) {
        this.switchSizeId = switchSizeId;
    }

    public String getSwitchFormId() {
        return switchFormId;
    }

    public void setSwitchFormId(String switchFormId) {
        this.switchFormId = switchFormId;
    }

    public String getSwitchFormCode() {
        return switchFormCode;
    }

    public void setSwitchFormCode(String switchFormCode) {
        this.switchFormCode = switchFormCode;
    }
}
