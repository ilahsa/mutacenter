package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

/**
 * @author baibq
 * @version 1.0
 * @date 2023-03-10 10:54
 */
public class AppOther {
    //select id, app_id ,pack_name,app_name,app_type,os,status from app_other
    private String id;

    private String appId;

    private String packageName;

    private String appName;

    private String appType;

    private String os;

    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
