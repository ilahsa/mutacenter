package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

public class DspRule {
    public String conId; //三表合一以后的值，以-分隔
    public String posId;
    public String devId;
    public String appId;
    public String areaCode; //地域。格式：标识（!:不包含）+地域列表(’,’分割)
    public String timeWork; //工作日小时。格式：标识（!:不包含）+小时列表(’,’分割)
    public String timeWeekend; //周末小时。格式：标识（!:不包含）+小时列表(’,’分割)
    public String tradeDsp; //行业。格式：标识（!:不包含）+ DSP列表(DSPID+’:’+行业id列表(’,’分割)+’&’分割)
    public String url; //广告URL列表。格式：标识（!:不包含）+ url列表(’,’分割)
    public String creative; //广告创意列表。格式：标识（!:不包含）+ 创意列表(’,’分割)
    public String tag; //标签。格式：标识（!:不包含）+ 标签名称列表(’,’分割)
    public String status;

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getTimeWork() {
        return timeWork;
    }

    public void setTimeWork(String timeWork) {
        this.timeWork = timeWork;
    }

    public String getTimeWeekend() {
        return timeWeekend;
    }

    public void setTimeWeekend(String timeWeekend) {
        this.timeWeekend = timeWeekend;
    }

    public String getTradeDsp() {
        return tradeDsp;
    }

    public void setTradeDsp(String tradeDsp) {
        this.tradeDsp = tradeDsp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreative() {
        return creative;
    }

    public void setCreative(String creative) {
        this.creative = creative;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
