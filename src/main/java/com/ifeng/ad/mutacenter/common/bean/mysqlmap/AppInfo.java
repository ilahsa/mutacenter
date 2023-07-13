package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

/**
 * Created by  on 2019/3/1.
 */
public class AppInfo {
    public String appId; //应用id
    public String appOs; //应用所属平台
    public String appName; //应用名称
    public String packageName; //应用包名
    public String typeCode;//应用分类代码
    public String testStatus;//应用测试状态(0:正式，1：测试，默认正式)
    public String status;//状态（0:运行中,1:停止,2:待审核,3:审核拒绝,4:测试,-1:删除）
    public String openRestrict;
    public String isTrans; // 是否透传包名称 0 否 1 是

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppOs() {
        return appOs;
    }

    public void setAppOs(String appOs) {
        this.appOs = appOs;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(String testStatus) {
        this.testStatus = testStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOpenRestrict() {
        return openRestrict;
    }

    public void setOpenRestrict(String openRestrict) {
        this.openRestrict = openRestrict;
    }

    public String getIsTrans() {
        return isTrans;
    }

    public void setIsTrans(String isTrans) {
        this.isTrans = isTrans;
    }
}
