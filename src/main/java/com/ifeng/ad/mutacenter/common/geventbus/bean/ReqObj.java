package com.ifeng.ad.mutacenter.common.geventbus.bean;

import java.util.Set;

/**
 * Created by  on 2019/1/24.
 */
public class ReqObj {
    String dev;
    String type;
    Set<String> ids;

    public static ReqObj create() {
        return new ReqObj();
    }

    public String getDev() {
        return dev;
    }

    public ReqObj setDev(String dev) {
        this.dev = dev;
        return this;
    }

    public String getType() {
        return type;
    }

    public ReqObj setType(String type) {
        this.type = type;
        return this;
    }

    public Set<String> getIds() {
        return ids;
    }

    public ReqObj setIds(Set<String> ids) {
        this.ids = ids;
        return this;
    }
}
