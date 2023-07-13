package com.ifeng.ad.mutacenter.lianghua;

//基金的数据
public class JijinData {
    private int id; //int ;
    private int index;
    private String  jzrq;// comment '净值日期',
    private String fundcode; //varchar(10) comment '基金代码',

    private String name; //基金名称
    private float dwjz; //float comment '单位净值',
    private float gsz;// float comment '估计值',
    private float gszzl;// float comment '估计涨幅',
    private String gztime ;//datetime comment '估计时间',
    private float d20jz;// float comment '20天均值',
    private float grz; // float comment '过热值',
    private float glz;// float comment '过冷值'

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJzrq() {
        return jzrq;
    }

    public void setJzrq(String jzrq) {
        this.jzrq = jzrq;
    }

    public String getFundcode() {
        return fundcode;
    }

    public void setFundcode(String fundcode) {
        this.fundcode = fundcode;
    }

    public float getDwjz() {
        return dwjz;
    }

    public void setDwjz(float dwjz) {
        this.dwjz = dwjz;
    }

    public float getGsz() {
        return gsz;
    }

    public void setGsz(float gsz) {
        this.gsz = gsz;
    }

    public float getGszzl() {
        return gszzl;
    }

    public void setGszzl(float gszzl) {
        this.gszzl = gszzl;
    }

    public String getGztime() {
        return gztime;
    }

    public void setGztime(String gztime) {
        this.gztime = gztime;
    }

    public float getD20jz() {
        return d20jz;
    }

    public void setD20jz(float d20jz) {
        this.d20jz = d20jz;
    }

    public float getGrz() {
        return grz;
    }

    public void setGrz(float grz) {
        this.grz = grz;
    }

    public float getGlz() {
        return glz;
    }

    public void setGlz(float glz) {
        this.glz = glz;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
