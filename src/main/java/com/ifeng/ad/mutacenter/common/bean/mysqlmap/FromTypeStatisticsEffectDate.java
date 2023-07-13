package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

/**

 */
public class FromTypeStatisticsEffectDate {
    private String platformId;
    private String formType;
    private long requestCount;
    private long responseCount;
    private long winCount;
    private long impressionCount;
    private long clickCount;
    private double consume;

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
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

    public long getWinCount() {
        return winCount;
    }

    public void setWinCount(long winCount) {
        this.winCount = winCount;
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
}
