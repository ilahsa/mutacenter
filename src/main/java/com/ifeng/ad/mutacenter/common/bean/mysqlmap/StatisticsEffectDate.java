package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

/**
 * 过去一定时间的流量响应情况
 * 具体过去几天，根据志鹏老师的算出来的数来定
 */
public class StatisticsEffectDate {
    private String platformId;
    private String positionId;
    private long requestCount;
    private long responseCount;
    private long impressionCount;
    private long clickCount;
    private double consume;
    private long winCount;

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public long getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(long requestCount) {
        this.requestCount = requestCount;
    }

    public long getResponseCount() {
        return responseCount;
    }

    public void setResponseCount(long responseCount) {
        this.responseCount = responseCount;
    }

    public long getImpressionCount() {
        return impressionCount;
    }

    public void setImpressionCount(long impressionCount) {
        this.impressionCount = impressionCount;
    }

    public long getClickCount() {
        return clickCount;
    }

    public void setClickCount(long clickCount) {
        this.clickCount = clickCount;
    }

    public double getConsume() {
        return consume;
    }

    public void setConsume(double consume) {
        this.consume = consume;
    }

    public long getWinCount() {
        return winCount;
    }

    public void setWinCount(long winCount) {
        this.winCount = winCount;
    }
}
