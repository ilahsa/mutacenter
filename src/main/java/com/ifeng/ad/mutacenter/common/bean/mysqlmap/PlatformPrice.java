package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

/**
 * Created by baibq on 2020-07-10.
 */
public class PlatformPrice {
    private int id;
    //平台id
    private String platform_id;
    //广告位id,需要映射为我们的id
    private String alliance_position_id;

    //凤飞的广告位id
    private String position_id;

    private int status;
    private String form_type;
    //数据库的数据为元，需要转为分
    //可能小数后面有值 使用float表示
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlatform_id() {
        return platform_id;
    }

    public void setPlatform_id(String platform_id) {
        this.platform_id = platform_id;
    }

    public String getAlliance_position_id() {
        return alliance_position_id;
    }

    public void setAlliance_position_id(String alliance_position_id) {
        this.alliance_position_id = alliance_position_id;
    }

    public String getPosition_id() {
        return position_id;
    }

    public void setPosition_id(String position_id) {
        this.position_id = position_id;
    }

    public String getForm_type() {
        return form_type;
    }

    public void setForm_type(String form_type) {
        this.form_type = form_type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
