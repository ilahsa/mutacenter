package com.ifeng.ad.mutacenter.common.bean.redisobj;

import java.util.List;

/**
 * 定向
 */
public class Dimension {
    public List<String> area;
    public List<String> os;
    public List<String> hour;

    public List<String> getArea() {
        return area;
    }

    public void setArea(List<String> area) {
        this.area = area;
    }

    public List<String> getOs() {
        return os;
    }

    public void setOs(List<String> os) {
        this.os = os;
    }

    public List<String> getHour() {
        return hour;
    }

    public void setHour(List<String> hour) {
        this.hour = hour;
    }
}
