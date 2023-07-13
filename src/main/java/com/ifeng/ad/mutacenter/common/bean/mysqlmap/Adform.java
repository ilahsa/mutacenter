package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

/**
 * 广告形式对象
 */
public class Adform {
    public String formId;
    public String formCode;
    public String formType;
    public String sizeId;
    public String sizeWidth;
    public String sizeHeight;
    public String macroField;//逗号分隔
    public String macroCode;
    public String clickType;//下载，唤起[1,2]
    public String status;

//    public static Adform creater(String formId) {
//        Adform adform = new Adform();
//        adform.setFormId(formId);
//        return adform;
//    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
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

    public String getSizeId() {
        return sizeId;
    }

    public void setSizeId(String sizeId) {
        this.sizeId = sizeId;
    }

    public String getSizeWidth() {
        return sizeWidth;
    }

    public void setSizeWidth(String sizeWidth) {
        this.sizeWidth = sizeWidth;
    }

    public String getSizeHeight() {
        return sizeHeight;
    }

    public void setSizeHeight(String sizeHeight) {
        this.sizeHeight = sizeHeight;
    }

    public String getMacroField() {
        return macroField;
    }

    public void setMacroField(String macroField) {
        this.macroField = macroField;
    }

    public String getMacroCode() {
        return macroCode;
    }

    public void setMacroCode(String macroCode) {
        this.macroCode = macroCode;
    }

    public String getClickType() {
        return clickType;
    }

    public void setClickType(String clickType) {
        this.clickType = clickType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
