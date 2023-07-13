package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

/**
 * @author baibq
 * @version 1.0
 * @date 2021-03-02 10:04
 * {平台id}_{广告位id}_{用户id} 的频次控制
 */
public class AllianceappPositionFlow {
    private String id;
    private String allianceappId;

    private String positionId;

    private String platformId;

    public String status;

    private int frequency;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAllianceappId() {
        return allianceappId;
    }

    public void setAllianceappId(String allianceappId) {
        this.allianceappId = allianceappId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
