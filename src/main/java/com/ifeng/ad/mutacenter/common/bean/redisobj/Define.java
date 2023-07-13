package com.ifeng.ad.mutacenter.common.bean.redisobj;

import java.util.List;

/**
 * cpd具体创意内容素材等
 */
public class Define {
    public int type;
    public List<String> img_url;
    public String title;
    public String text;
    public String desc;
    public String loadpage_url;
    public String dpl_url;
    public List<String> imp_url;
    public List<String> clc_url;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getImg_url() {
        return img_url;
    }

    public void setImg_url(List<String> img_url) {
        this.img_url = img_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLoadpage_url() {
        return loadpage_url;
    }

    public void setLoadpage_url(String loadpage_url) {
        this.loadpage_url = loadpage_url;
    }

    public String getDpl_url() {
        return dpl_url;
    }

    public void setDpl_url(String dpl_url) {
        this.dpl_url = dpl_url;
    }

    public List<String> getImp_url() {
        return imp_url;
    }

    public void setImp_url(List<String> imp_url) {
        this.imp_url = imp_url;
    }

    public List<String> getClc_url() {
        return clc_url;
    }

    public void setClc_url(List<String> clc_url) {
        this.clc_url = clc_url;
    }
}
