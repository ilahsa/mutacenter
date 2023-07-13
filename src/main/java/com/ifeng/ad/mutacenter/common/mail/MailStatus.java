package com.ifeng.ad.mutacenter.common.mail;

import java.time.LocalDateTime;

/**
 * 邮件发送状态对象
 */
public class MailStatus {
    private LocalDateTime lastCatchTime; //获取到的最近一次报警时间
    private LocalDateTime firstWarnTime; //第一次的报警时间
    private int warnCount; //出现连续错误次数
    private String ffServerIp; //凤飞投放机ip
    private String dspUrl; //凤飞中dsp客户的url
    private boolean warnMail; //是否发送了报警邮件

    public LocalDateTime getLastCatchTime() {
        return lastCatchTime;
    }

    public void setLastCatchTime(LocalDateTime lastCatchTime) {
        this.lastCatchTime = lastCatchTime;
    }

    public LocalDateTime getFirstWarnTime() {
        return firstWarnTime;
    }

    public void setFirstWarnTime(LocalDateTime firstWarnTime) {
        this.firstWarnTime = firstWarnTime;
    }

    public int getWarnCount() {
        return warnCount;
    }

    public void setWarnCount(int warnCount) {
        this.warnCount = warnCount;
    }

    public void incrWarnCount(int num) {
        this.warnCount += num;
    }

    public String getFfServerIp() {
        return ffServerIp;
    }

    public void setFfServerIp(String ffServerIp) {
        this.ffServerIp = ffServerIp;
    }

    public String getDspUrl() {
        return dspUrl;
    }

    public void setDspUrl(String dspUrl) {
        this.dspUrl = dspUrl;
    }

    public boolean isWarnMail() {
        return warnMail;
    }

    public void setWarnMail(boolean warnMail) {
        this.warnMail = warnMail;
    }

    @Override
    public String toString() {
        return "";
    }
}
