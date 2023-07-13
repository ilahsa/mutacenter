package com.ifeng.ad.mutacenter;

/**
 * 项目描述: 常量类
 */
public interface SQLConstant {

    public final static String WHERE_IDS_ADPOSITION = "and pinfo.id in (%s)\n";
    public final static String WHERE_DEV_ADPOSITION = "and pinfo.dev_id in (%s)\n";
    public final static String WHERE_PRODUCT_ADPOSITION = "and pinfo.id in (select pifo.position_id from product_info pifo where pifo.id in (%s))\n";
    public final static String WHERE_APPID_ADPOSITION = "and pinfo.app_id in (%s)\n";
    public final static String WHERE_CHEAT_ADPOSITON = "and pinfo.id in (select cpa.position_id from cheat_position_allocation cpa where cpa.id in (%s))\n";
    public final static String WHERE_BPP_ID = "and bps.id in (%s)\n";

    public final static String WHERE_IDS_ADFORM = "and af.id in (%s)\n";
    public final static String WHERE_ADFORM_ADSIZE = "and sinfo.id in (%s)\n";


    public final static String WHERE_IDS_CPDCREATIVE = "and cinfo.id in (%s)\n";
    public final static String WHERE_DELIVER_CPDCREATIVE = "and cinfo.delivery_id in (%s)\n";
    public final static String WHERE_DELIVER_SCHEDULE_CPDCREATIVE = "and cinfo.delivery_schedule_id in (%s)\n";
    public final static String WHERE_DEV_CPDCREATIVE = "and cinfo.dev_id in (%s)\n";
    public final static String HAVING_CPDCREATIVE = "having expiryDate is not null\n";

    public final static String WHERE_SHOWTIME_POSITON = "and pinfo.id in (%s)\n";
    public final static String WHERE_SHOWTIME_PRODUCT_POSITON = "and pinfo.id in (select pifo.position_id from product_info pifo where pifo.id in (%s))\n";
    public final static String WHERE_SHOWTIME_ADFORM = "and pinfo.id in (select proinfo.position_id from product_info proinfo where proinfo.form_id in (%s))\n";
    public final static String WHERE_SHOWTIME_ALLOWANCE = "and pinfo.dev_id in (select ast.dev_id from app_platform ast where ast.id in (%s))\n";
    public final static String WHERE_SHOWTIME_CPDCREATIVE_IDS = "and pinfo.id in (select cinfo.position_id from creative_info cinfo where cinfo.id in (%s))\n";
    public final static String WHERE_SHOWTIME_CPDCREATIVE_DELIVER = "and pinfo.id in (select cinfo.position_id from creative_info cinfo where cinfo.delivery_id in (%s))\n";
    public final static String WHERE_SHOWTIME_CPDCREATIVE_DELIVER_SCHEDULE = "and pinfo.id in (select cinfo.position_id from creative_info cinfo where cinfo.delivery_schedule_id in (%s))\n";
    public final static String WHERE_SHOWTIME_WINGSINFO = "and pinfo.id in (select wdc.ad_position_id from wings_ad_config wdc where wdc.id in (%s))\n";
    public final static String WHERE_SHOWTIME_APPINFO = "and pinfo.app_id in (%s)\n";
    public final static String WHERE_SHOWTIME_DEV = "and pinfo.dev_id in (%s)\n";

    public final static String WHERE_ALLIANCEAPPINFO_APPID = "and app.id in (%s)\n";
    public final static String WHERE_ALLIANCEAPPINFO_POSID = "and pos.id in (%s)\n";
    public final static String WHERE_DEV_ALLIANCEAPPINFO = "and app.dev_id in (%s)\n";

    public final static String BEFORE_RUN = "SET GLOBAL group_concat_max_len=102400";
    public final static String BEFORE_RUN2 = "SET SESSION group_concat_max_len=102400";


}
