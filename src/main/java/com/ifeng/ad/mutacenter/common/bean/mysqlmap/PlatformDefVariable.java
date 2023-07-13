package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

/**
 * 平台默认变量
 */
public class PlatformDefVariable {
    private String platformId;//平台id
    private String os; //操作系统
    private String vName; //变量名
    private String val; //变量值
    private String status;

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
