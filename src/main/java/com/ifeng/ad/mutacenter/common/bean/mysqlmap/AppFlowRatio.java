package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

import java.util.Arrays;

/**
 * 流量分配信息
 */
public class AppFlowRatio {
    private String devId;
    private String appId;
    private String percent;
    private String status;

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

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if(status != null) {
            String[] ss = status.split(",");
            long count = Arrays.stream(ss).filter(s -> !"0".equals(s)).count();
            //所有的数据都不是0，则表示该条数据应该删除
            if(count == ss.length) {
                this.status = "-1";
            }
            else {
                this.status = "0";
            }
        }
        else {
            this.status = "-1";
        }
    }
}
