package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

/**
 * Created by baibq on 2020-10-19.
 */
public class PlatformFlowRatio {
    private String id; //主键

    private String platformId;//平台id

    private String algoGroup;//算法分组

    private String flowRatio;//百分比

    private String status;//状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getAlgoGroup() {
        return algoGroup;
    }

    public void setAlgoGroup(String algoGroup) {
        this.algoGroup = algoGroup;
    }

    public String getFlowRatio() {
        return flowRatio;
    }

    public void setFlowRatio(String flowRatio) {
        this.flowRatio = flowRatio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
