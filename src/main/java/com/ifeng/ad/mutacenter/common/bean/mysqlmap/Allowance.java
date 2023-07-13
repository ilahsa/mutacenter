package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

/**
 * 余量投放策略
 */
public class Allowance {
    public String allowanceId;
    public String developerId;
    public String appId; //appId
    public String positionId; //广告位id，不存在的是*
    public Integer priority; //优先级，有广告位的优先级比没广告位的（即通投的）高
    public String dspList; //逗号分隔
    public String allianceList; //逗号分隔
    public String status;

    public String getAllowanceId() {
        return allowanceId;
    }

    public void setAllowanceId(String allowanceId) {
        this.allowanceId = allowanceId;
    }

    public String getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(String developerId) {
        this.developerId = developerId;
    }

    public String getDspList() {
        return dspList;
    }

    public void setDspList(String dspList) {
        this.dspList = dspList;
    }

    public String getAllianceList() {
        return allianceList;
    }

    public void setAllianceList(String allianceList) {
        this.allianceList = allianceList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
