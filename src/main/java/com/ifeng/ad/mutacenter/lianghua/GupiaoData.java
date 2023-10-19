package com.ifeng.ad.mutacenter.lianghua;

public class GupiaoData {
    /**
     *  {
     *         "day": "2023-09-12 09:40:00",
     *         "open": "13.620",
     *         "high": "13.660",
     *         "low": "13.610",
     *         "close": "13.620",
     *         "volume": "2417400",
     *         "ma_price5": 13.672,
     *         "ma_volume5": 2173421
     *     }
     */
    private String daystr;
    private int id;
    private int index;
    private String code;
    private String day;
    private float open;
    private float high;
    private float low;
    private float close;
    private  float ma_price5;

    private float d20jz;
    private float grz;
    private float glz;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDaystr() {
        return daystr;
    }

    public void setDaystr(String daystr) {
        this.daystr = daystr;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public float getMa_price5() {
        return ma_price5;
    }

    public void setMa_price5(float ma_price5) {
        this.ma_price5 = ma_price5;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
}
