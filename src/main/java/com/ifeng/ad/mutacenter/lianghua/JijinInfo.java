package com.ifeng.ad.mutacenter.lianghua;

public class JijinInfo {
    private int id; //int ;
    private String fundcode; //varchar(10) comment '基金代码',

    private String name; //基金名称

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFundcode() {
        return fundcode;
    }

    public void setFundcode(String fundcode) {
        this.fundcode = fundcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
