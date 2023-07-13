package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

public class AppVersionForbidden {
    private String rid;
    private String rtype;
    private String businessId;
    private String verRule;
    private String appVer;
    private String codelist;
    private String status;

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getRtype() {
        return rtype;
    }

    public void setRtype(String rtype) {
        this.rtype = rtype;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getVerRule() {
        return verRule;
    }

    public void setVerRule(String verRule) {
        this.verRule = verRule;
    }

    public String getAppVer() {
        return appVer;
    }

    public void setAppVer(String appVer) {
        this.appVer = appVer;
    }

    public String getCodelist() {
        return codelist;
    }

    public void setCodelist(String codelist) {
        this.codelist = codelist;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
