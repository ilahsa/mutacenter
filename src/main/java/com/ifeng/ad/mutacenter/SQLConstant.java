package com.ifeng.ad.mutacenter;

/**
 * 项目描述: 常量类
 */
public interface SQLConstant {

    public final static String SQL_ADPOSITION = "select DISTINCT  \n" +
            "pinfo.id posId,\n" +
            "pinfo.dev_id developerId,\n" +
            "pinfo.app_id appId,\n" +
            "pinfo.page_id pageId,\n" +
            "pinfo.third_party_sell isThirdSell,\n" +
            "pinfo.dev_position_id AS devAdId,\n" +
            "(case when ps.play_num is null then pinfo.play_num else ps.play_num end) carousel,\n" +
            "form_type formType,\n" +
            "(select group_concat(concat(proinfo.form_id, '->',proinfo.size_id, '->', proinfo.third_party_sell)) from product_info proinfo where proinfo.position_id = pinfo.id and proinfo.status=0) adform,\n" +
            "tprice.price bottomPrice,\n" +
            "ifnull((select group_concat(cpa.platform_id) from cheat_position_allocation cpa where cpa.status = 0 and cpa.position_id = pinfo.id), -1) cheatallocation,\n" +
            "(case when pinfo.status = 0 and devinfo.status = 0 and (appinfo.status = 0 or appinfo.status = 4 or appinfo.status = 5) then 0 else -1 end) status,\n" +
            "bpp.price returnPrice\n" +
            "from position_info pinfo\n" +
            "left join (select position_id, play_num from position_stock where status=0 and play_time = date(now()) group by position_id order by play_num desc) ps\n" +
            "on ps.position_id = pinfo.id\n" +
            "left join dev_info devinfo\n" +
            "on pinfo.dev_id = devinfo.id\n" +
            "left join app_info appinfo\n" +
            "on pinfo.app_id = appinfo.id\n" +
            "left join bidding_position_price bpp\n" +
            "on bpp.position_id = pinfo.id and bpp.status = 0\n" +
            "left join (select * from base_price\n" +
            "where id in (select max(id) from base_price group by position_id)) as tprice \n" +
            "on tprice.position_id = pinfo.id\n" +
            "LEFT JOIN bidding_price_strategy bps \n" +
            "ON bps.dev_id = pinfo.dev_id and bps.app_id=pinfo.app_id \n" +
            "where pinfo.id is not null\n";

    public final static String WHERE_IDS_ADPOSITION = "and pinfo.id in (%s)\n";
    public final static String WHERE_DEV_ADPOSITION = "and pinfo.dev_id in (%s)\n";
    public final static String WHERE_PRODUCT_ADPOSITION = "and pinfo.id in (select pifo.position_id from product_info pifo where pifo.id in (%s))\n";
    public final static String WHERE_APPID_ADPOSITION = "and pinfo.app_id in (%s)\n";
    public final static String WHERE_CHEAT_ADPOSITON = "and pinfo.id in (select cpa.position_id from cheat_position_allocation cpa where cpa.id in (%s))\n";
    public final static String WHERE_BPP_ID = "and bps.id in (%s)\n";

    public final static String SQL_APPINFO = "select \n" +
            "appif.id appId,\n" +
            "appif.name appName,\n" +
            "appif.type_code typeCode,\n" +
            "(case appif.`system` when 0 then 'ios' when 1 then 'android' else 'other' end) appOs,\n" +
            "appvif.package_name packageName,\n" +
            "(case appif.status when 4 then 1 else 0 end) testStatus,\n" +
            "(case when (appif.status = 0 or appif.status = 4 or appif.status = 5) and appvif.status = 0 and devinfo.status = 0 then 0 else -1 end) status,\n" +
            "appif.open_restrict as openRestrict, \n" +
            "appif.is_trans as isTrans \n" +
            "from app_info appif\n" +
            "left join app_verify_info appvif\n" +
            "on appif.id = appvif.app_id and appif.dev_id = appvif.dev_id\n" +
            "left join dev_info devinfo\n" +
            "on appif.dev_id = devinfo.id\n" +
            "where appif.id is not null\n";

    public final static String WHERE_IDS_APPINFO = "and appif.id in (%s)\n";
    public final static String WHERE_DEV_APPINFO = "and appif.dev_id in (%s)\n";

    public final static String SQL_ADFORM = "select \n" +
            "af.id formId,\n" +
            "af.name formCode,\n" +
            "enum_itme_code formType,\n" +
            "sinfo.id sizeId,\n" +
            "af.width sizeWidth,\n" +
            "af.height sizeHeight,\n" +
            "(select group_concat(concat(sdb.sys_enum_item_code, '\\t',ifnull(sdb.width,'-1'), '\\t',ifnull(sdb.height,'-1')) SEPARATOR '\\n')\n" +
            "from size_define_border sdb where sdb.form_id = af.id and sdb.size_id=sinfo.id and sdb.status = 0) macroField,\n" +
            "af.code macroCode,\n" +
            "af.click_type clickType,\n" +
            "(case when af.status = 0 and sinfo.status = 0 then 0 else -1 end) status\n" +
            "from ad_form af,size_info sinfo\n" +
            "where af.id = sinfo.form_id\n";

    public final static String WHERE_IDS_ADFORM = "and af.id in (%s)\n";
    public final static String WHERE_ADFORM_ADSIZE = "and sinfo.id in (%s)\n";

    public final static String SQL_ALLOWANCE = "SELECT allowanceId,developerId,appId,dspList,priority,allianceList,positionId,status FROM (SELECT \n" +
            "ast.allowanceId,\n" +
            "ast.developerId,\n" +
            "ast.appId,\n" +
            "ast.dspList,\n" +
            "ast.priority,\n" +
            "ast.allianceList,\n" +
            "ast.positionId,\n" +
            "(CASE WHEN ast.status = 0 AND devinfo.status = 0 THEN 0 ELSE -1 END) STATUS\n" +
            "FROM(select  \n" +
            "id AS allowanceId,\n" +
            "dev_id AS developerId,\n" +
            "app_id AS appId,\n" +
            "max(dsp_id) as dspList,\n" +
            "(CASE WHEN position_id IS NULL THEN 1 ELSE 2 END) priority,\n" +
            "max(alliance_id) as allianceList,\n" +
            "(CASE WHEN position_id IS NULL THEN '*' ELSE position_id END) positionId,\n" +
            "`status`\n" +
            "from (\n" +
            "    select  id,`status`,dev_id,app_id,position_id,case type when 0 then platform_id end as alliance_id ,case type when 1 then platform_id end as dsp_id \n" +
            "    from (\n" +
            "        select DISTINCT id,`status`,dev_id,app_id,position_id,GROUP_CONCAT(a.platform_id) as platform_id ,0 as type \n" +
            "        from (\n" +
            "            select DISTINCT d.id,d.`status`,d.dev_id,d.app_id,position_id ,d.platform_id ,ua.platform_id as ua_platform_id\n" +
            "            from (\n" +
            "            SELECT id,`status`,dev_id,app_id,position_id,substring_index(substring_index(t.alliance_id,',', b.help_topic_id + 1), ',', -1) platform_id\n" +
            "            FROM app_platform t join mysql.help_topic b ON b.help_topic_id <  (LENGTH(t.alliance_id) - LENGTH(REPLACE(t.alliance_id, ',', '')) + 1)  \n" +
            "            )d left join  app_platform_ua ua on ua.status !=-1 and d.platform_id = ua.platform_id and ua.app_id = d.app_id \n" +
            "        ) a where a.ua_platform_id is null \n" +
            "        GROUP BY id\n" +
            "        union all  \n" +
            "        select DISTINCT id,`status`,dev_id,app_id,position_id,GROUP_CONCAT(a.platform_id) ,1 as type \n" +
            "        from (\n" +
            "            select DISTINCT d.id,d.`status`,d.dev_id,d.app_id,position_id ,d.platform_id ,ua.platform_id as ua_platform_id\n" +
            "            from (\n" +
            "            SELECT id,`status`,dev_id,app_id,position_id,substring_index(substring_index(t.dsp_id,',', b.help_topic_id + 1), ',', -1) platform_id\n" +
            "            FROM app_platform t join mysql.help_topic b ON b.help_topic_id <  (LENGTH(t.dsp_id) - LENGTH(REPLACE(t.dsp_id, ',', '')) + 1)\n" +
            "            )d left join  app_platform_ua ua on ua.status !=-1 and d.platform_id = ua.platform_id and ua.app_id = d.app_id \n" +
            "        ) a where a.ua_platform_id is null \n" +
            "        GROUP BY id\n" +
            "    ) p   \n" +
            "    union all  \n" +
            "    SELECT id,`status`,dev_id,app_id,position_id,NULL,NULL\n" +
            "    FROM app_platform t WHERE dsp_id is NULL AND alliance_id is NULL\n" +
            ") p GROUP BY id,dev_id,app_id,position_id  \n" +
            "order by id,dev_id,app_id,position_id) ast\n" +
            "LEFT JOIN dev_info devinfo\n" +
            "ON ast.developerId = devinfo.id\n" +
            "WHERE \n" +
            "ast.allowanceId IS NOT NULL\n" +
            "ORDER BY appId) tab\n" +
            "WHERE 1 = 1\n";

    public final static String WHERE_IDS_ALLOWANCE = "and allowanceId in (%s)\n";
    public final static String WHERE_DEV_ALLOWANCE = "and developerId in (%s)\n";

    public final static String SQL_CPDCREATIVE = "select \n" +
            "cinfo.id creativeId,\n" +
            "cinfo.dev_id developerId,\n" +
            "cinfo.position_id positionId,\n" +
            "cinfo.delivery_id deliveryId,\n" +
            "cinfo.product_id producetId,\n" +
            "cinfo.size_id sizeId,\n" +
            "cinfo.form_type formType,\n" +
            "cinfo.form_id fomrId,\n" +
            "(select oinfo.customer_id from order_info oinfo where oinfo.id = dinfo.order_id) customerId,\n" +
            "dinfo.order_id orderId,\n" +
            "(select group_concat(concat(csch.play_time,'->',csch.play_num) SEPARATOR '\\n') from creative_schedule csch where csch.status = 0 and csch.creative_id = cinfo.id and csch.play_time >= date(now())) expiryDate,\n" +
            "(select group_concat(concat(cdef.define_code,'->',cdef.define_value) SEPARATOR '\\n') from creative_define cdef where cdef.status = 0 and cdef.creative_id = cinfo.id) define,\n" +
            "(case when cinfo.status = 0 and dinfo.status = 0 and devinfo.status = 0 and (select dsinfo.status = 0 from delivery_schedule_info dsinfo where dsinfo.position_id = cinfo.position_id and dsinfo.delivery_id = dinfo.id and dsinfo.product_id = cinfo.product_id order by dsinfo.update_time desc limit 1) then 0 else -1 end) status\n" +
            "from creative_info cinfo\n" +
            "left join delivery_info dinfo\n" +
            "on cinfo.delivery_id = dinfo.id\n" +
            "left join dev_info devinfo\n" +
            "on cinfo.dev_id = devinfo.id\n" +
            "where cinfo.id is not null\n";

    public final static String WHERE_IDS_CPDCREATIVE = "and cinfo.id in (%s)\n";
    public final static String WHERE_DELIVER_CPDCREATIVE = "and cinfo.delivery_id in (%s)\n";
    public final static String WHERE_DELIVER_SCHEDULE_CPDCREATIVE = "and cinfo.delivery_schedule_id in (%s)\n";
    public final static String WHERE_DEV_CPDCREATIVE = "and cinfo.dev_id in (%s)\n";
    public final static String HAVING_CPDCREATIVE = "having expiryDate is not null\n";

    public final static String SQL_SELLPLATFORM = "SELECT\n" +
            "  ptinfo.id platformId,\n" +
            "  ptinfo.short_name shotName,\n" +
            "  ptinfo.code nameCode,\n" +
            "  ptinfo.`type` `type`,\n" +
            "  ptinfo.grade grade,\n" +
            "  ptinfo.account_name loginname,\n" +
            "  ptinfo.account_password `password`,\n" +
            "  ptinfo.account_token token,\n" +
            "  ptinfo.protocol_type protocolType,\n" +
            "  ptinfo.protocol_content protocolVer,\n" +
            "  ptinfo.docking_type dockingType,\n" +
            "  ptinfo.docking_url dockingUrl,\n" +
            "  (select group_concat(concat(callo.form_id,'->',callo.size_id) SEPARATOR '\\n') from creative_allocation callo where callo.status=0 and callo.platform_id = ptinfo.id) supportAdforms,\n" +
            "  FLOOR(realflow.qps_up_limit / 15) trafficLimitSeconds,\n" +
            "  FLOOR(realflow.req_up_limit) trafficLimitDay,\n" +
            "  realflow.frequency frequency,\n" +
            "  realflow.mobile_code modelsList,\n" +
            "  realflow.start_date startDate,\n" +
            "  realflow.end_date endDate,\n" +
            "  ptinfo.status status,\n" +
            "  ptinfo.is_use_price isUsePrice,\n" +
            "  ptinfo.tag_id as tagId,\n" +
            "  ptinfo.is_settle as isSettle,\n" +
            "  ptinfo.settle as settle\n" +
            "FROM\n" +
            "  platform_info ptinfo\n" +
            "  LEFT JOIN\n" +
            "    (SELECT\n" +
            "      platform_id,\n" +
            "      qps_up_limit,\n" +
            "      req_up_limit,\n" +
            "      mobile_code,\n" +
            "      frequency,\n" +
            "      start_date,\n" +
            "      end_date\n" +
            "    FROM\n" +
            "      flow_allocation AS b\n" +
            "    WHERE b.status = 0) realflow\n" +
            "    ON ptinfo.id = realflow.platform_id\n" +
            "WHERE ptinfo.id IS NOT NULL\n";

    public final static String WHERE_IDS_SELLPLATFORM = "and ptinfo.id in (%s)\n";

    public final static String SQL_WINGSINFO = "select \n" +
            "wdc.id, \n" +
            "wdc.contract_no contractNum, \n" +
            "wdc.dev_id devId, \n" +
            "wdc.cpm, \n" +
            "wdc.start_time startTime, \n" +
            "wdc.end_time endTime, \n" +
            "(case when wdc.status = 0 and devinfo.status = 0 then 0 else -1 end)status \n" +
            "from wings_ad_config wdc \n" +
            "left join dev_info devinfo\n" +
            "on wdc.dev_id = devinfo.id\n" +
            "where wdc.id is not null \n";

    public final static String WHERE_IDS_WINGSINFO = "and wdc.id in (%s)\n";
    public final static String WHERE_DEV_WINGSINFO = "and wdc.dev_id in (%s)\n";

    public final static String SQL_SHOWTIME = "select \n" +
            "pinfo.id posId,\n" +
            "pinfo.dev_id developerId,\n" +
            "(\n" +
            "select group_concat(concat(cinfo.id,'_',play_num)) \n" +
            "from creative_schedule csinfo,creative_info cinfo \n" +
            "where csinfo.play_time = DATE_FORMAT(NOW(),'%Y-%m-%d') \n" +
            "and csinfo.creative_id = cinfo.id \n" +
            "and cinfo.status=0 and csinfo.status=0 \n" +
            "and (\n" +
            "select dsinfo.status=0 and dinfo.status=0 from delivery_info dinfo,delivery_schedule_info dsinfo \n" +
            "where dinfo.id = dsinfo.delivery_id and dsinfo.position_id = cinfo.position_id and dsinfo.product_id = cinfo.product_id and dsinfo.start_time <= DATE_FORMAT(NOW(),'%Y-%m-%d') and dsinfo.end_time >= DATE_FORMAT(NOW(),'%Y-%m-%d') \n" +
            "order by dsinfo.update_time desc limit 1)\n" +
            "and cinfo.position_id = pinfo.id\n" +
            ") cpd,\n" +
            "(select wdc.id from wings_ad_config wdc where wdc.status=0 and now() >= wdc.start_time and now() <= wdc.end_time and wdc.ad_position_id = pinfo.id order by create_time desc limit 1) wings,\n" +
            "(select group_concat(allowance.id) from app_platform allowance where allowance.status=0 and allowance.dev_id = pinfo.dev_id" +
            " and (allowance.position_id LIKE CONCAT ('%',pinfo.id,'%') OR allowance.position_id IS NULL)) allowance,\n" +
            "(case when pinfo.status = 0 and devinfo.status = 0 and (appinfo.status = 0 or appinfo.status = 4 or appinfo.status = 5) then 0 else -1 end) status,\n" +
            "bpp.price returnPrice\n" +
            "from position_info pinfo\n" +
            "left join dev_info devinfo\n" +
            "on pinfo.dev_id = devinfo.id\n" +
            "left join app_info appinfo\n" +
            "on pinfo.app_id = appinfo.id\n" +
            "left join bidding_position_price bpp\n" +
            "on pinfo.id = bpp.position_id and bpp.status = 0\n" +
            "where pinfo.id is not null\n";

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

    public final static String SQL_ALLIANCEAPPINFO = "select pos.position_id posId,\n" +
            "app.platform_id platformId,\n" +
            "app.alliance_app_id allianceAppId, \n" +
            "app.app_key as allianceAppKey , \n" +
            "app.alliance_account as allianceAccount, \n" +
            "pos.alliance_position_id alliancePositionId, \n" +
            "pos.feeds_attr_code feedsAttrCode, \n" +
            "app.app_id appId, \n" +
            "(case when app.alliance_app_id is not null and pos.alliance_position_id is not null and \n" +
            "\t(app.status = 0 or app.status = 2 or app.status = 3) and devinfo.status = 0 then 0 else -1 end) status, \n" +
            "(case pos.status when 1 then 1 else 0 end) onOff \n" +
            "from alliance_app_allocation app\n" +
            "left join (SELECT pos1.id,pos1.platform_id,pos1.dev_id,pos1.app_id,pos1.position_id,pos1.alliance_position_id,pos1.feeds_attr_code,pos1.status\n" +
            "FROM alliance_position_allocation pos1 where pos1.status != -1) pos\n" + //新版没有其他几个状态，只有2 或者 -1
            "on app.platform_id = pos.platform_id \n" +
            "and app.dev_id = pos.dev_id \n" +
            "and app.app_id = pos.app_id \n" +
            "left join dev_info devinfo\n" +
            "on app.dev_id = devinfo.id\n" +
            "where pos.position_id is not null\n";

    public final static String WHERE_ALLIANCEAPPINFO_APPID = "and app.id in (%s)\n";
    public final static String WHERE_ALLIANCEAPPINFO_POSID = "and pos.id in (%s)\n";
    public final static String WHERE_DEV_ALLIANCEAPPINFO = "and app.dev_id in (%s)\n";

    //	public final static String SQL_SDKSUPPORT = "select \n" +
//			"sdki.`version` ver, \n" +
//			"group_concat(sdka.enum_code) alliance \n" +
//			"from sdk_alliance sdka, sdk_info sdki \n" +
//			"where sdki.id = sdka.sdk_id and sdka.status = 0 and sdki.status = 0\n";
    public final static String SQL_SDKSUPPORT = "select  \n" +
            "sdki.`version` ver, \n" +
            "group_concat(sdka.enum_code separator ';') alliance\n" +
            "from\n" +
            "(select sdk_id, concat(enum_code,':',ifnull(ftab.fcode,'')) enum_code, sdk_alliance.status\n" +
            "from sdk_alliance\n" +
            "left join (select sdk_alliance_id, group_concat(form_code) fcode\n" +
            "from sdk_aliance_form\n" +
            "where status = 0\n" +
            "group by sdk_alliance_id) ftab\n" +
            "on sdk_alliance.id = ftab.sdk_alliance_id) sdka, sdk_info sdki \n" +
            "where sdki.id = sdka.sdk_id and sdka.status = 0 and sdki.status in (0,2)\n";
    public final static String GROUPBY_SDKSUPPORT = "group by sdk_id \n";

    public final static String SQL_DSPWEIGHT = "select position_id positionId, platform_id platformId, concat(weight,',',rewards,',',fluctuation_risk) sellper\n" +
            "from base_union_weight\n" +
            "where status = 0\n";
    public final static String WHERE_IDS_DSPWEIGHT = "and id in (%s) \n";

    public final static String SQL_ADPOSITIONPRICE = "select position_id posId, price bottomPrice \n" +
            "from base_price\n" +
            "where status = 0\n";

    public final static String WHERE_IDS_ADPOSITIONPRICE = "and id in (%s)\n";


    public final static String SQL_FORBIDDEN = "select dev_id developerId, platform_code platformName, group_concat(case platform_code when 'DSP_IFENG' then trade_value else trade_code end) tradeValue \n" +
            "from trade_shield_alliance_dev \n" +
            "where status=0 \n";

    public final static String WHERE_DEV_FORBIDDEN = "and dev_id in (%s)\n";
    public final static String GROUPBY_FORBIDDEN = "group by dev_id, platform_code \n";
    public final static String HAVING_FORBIDDEN = "having tradeValue is not null\n";

    public final static String SQL_BIDDINGPRICESTRATEGY = "select id, \n" +
            "dev_id devId, \n" +
            "app_id appId, \n" +
            "percentage, \n" +
            "standard_deviation standardDeviation, \n" +
            "offset, \n" +
            "price_rate scale, \n" +
            "ifeng_percentage percent, \n" +
            "type, \n" +
            "status, \n" +
            "return_rate returnRate, \n" +
            "no_divide_code noDivideCode \n" +
            "from bidding_price_strategy \n" +
            "where 1=1 \n";
    public final static String WHERE_IDS_BIDDINGPRICESTRATEGY = "and id in (%s) \n";
    public final static String WHERE_DEV_BIDDINGPRICESTRATEGY = "and dev_id in (%s) \n";

    public final static String SQL_DSPRULE = "select concat(t3.strategy_id, '-', t3.rule_id, '-', t3.id) as conId, t1.position_id as posId, t1.dev_id as devId, t1.app_id as appId, \n" +
            "t2.area_code as areaCode, t3.time_work as timeWork, t3.time_weekend as timeWeekend, t3.trade_dsp as tradeDsp, t3.url, t3.creative, t3.tag, \n" +
            "(case when (ifnull(t1.status, 0) = 0 and ifnull(t2.status, 0) = 0 and ifnull(t3.status, 0) = 0) then 0 else -1 end) status\n" +
            "from (\n" +
            "\tselect id, dev_id, app_id, status, position_id position_ids,\n" +
            "\tsubstring_index(substring_index(position_id, ',', b.help_topic_id + 1), ',', -1) position_id\n" +
            "\tfrom dsp_strategy\n" +
            "\tjoin mysql.help_topic b ON b.help_topic_id < (LENGTH(position_id) - LENGTH(REPLACE(position_id, ',', '')) + 1)\n" +
            ") t1\n" +
            "left join dsp_strategy_rule t2\n" +
            "on t1.id = t2.strategy_id \n" +
            "left join (\n" +
            "\tselect id,strategy_id, rule_id, time_work,time_weekend, trade,\n" +
            "\tgroup_concat(ifnull(concat(substring_index(trade_info, ':', 1), ':', (select group_concat(code) from dsp_trade where find_in_set(id, substring_index(trade_info, ':', -1)))), '*') separator '&') trade_dsp,\n" +
            "\turl, creative, tag, status\n" +
            "\tfrom(\n" +
            "\t\tSELECT id,strategy_id, rule_id, time_work,time_weekend, trade,\n" +
            "\t\tsubstring_index(substring_index(t.trade,'&', b.help_topic_id + 1), '&', -1) trade_info, t.url, creative, tag, status\n" +
            "\t\tFROM (select id,strategy_id, rule_id, time_work,time_weekend, trade, url, creative, tag, status from dsp_strategy_condition)t \n" +
            "\t\tjoin mysql.help_topic b ON b.help_topic_id < (LENGTH(t.trade) - LENGTH(REPLACE(t.trade, '&', '')) + 1)) t\n" +
            "\tgroup by t.id\n" +
            ") t3\n" +
            "on t1.id = t3.strategy_id and t2.id = t3.rule_id\n" +
            "where t3.id is not null \n";
    public final static String WHERE_IDS_DSPRULE = "and t3.strategy_id in (%s) \n";
    public final static String WHERE_DEV_DSPRULE = "and t1.dev_id in (%s) \n";

    public final static String SQL_DSPFORBIDDEN = "SELECT id, platform_id platformId, dev_id devId, app_id appId, image_url imageUrl, `text`, click_url clickUrl, " +
            "status\n" +
            "FROM ifs.dsp_creative\n" +
            "WHERE status=1 and forbid_date >= now() \n";
    public final static String WHERE_IDS_DSPFORBIDDEN = "and id in (%s) \n";
    public final static String WHERE_DEV_DSPFORBIDDEN = "and dev_id in (%s) \n";

    public final static String SQL_APPFLOWRATIO = "select \n" +
            "dev_id devId, \n" +
            "app_id appId, \n" +
            "group_concat(case status when 0 then concat(algo_group, ':',flow_ratio) end) percent, \n" +
            "group_concat(status) status \n" +
            "from ifs.app_flow_ratio \n" +
            "group by dev_id,app_id";

    public final static String SQL_STATISTICSEFFECTDATE = "select \n" +
            "platform_id platformId, \n" +
            "position_id positionId, \n" +
            "request_count requestCount, \n" +
            "response_count responseCount, \n" +
            "impression_count impressionCount, \n" +
            "click_count clickCount, \n" +
            "consume*100 consume,  \n" +
            "win_count winCount  \n" +
            "from ifs.statistics_effect_date";

    public final static String SQL_FROMTYPESTATISTICSEFFECTDATE = "select \n" +
            "platform_id platformId,\n" +
            "form_type formType,\n" +
            "request_type requestCount,\n" +
            "response_type responseCount, \n" +
            "win_count winCount,\n" +
            "impression_count impressionCount,\n" +
            "click_count clickCount,\n" +
            "consume*100 consume\n" +
            "from ifs.from_type_statistics_effect_date";

    public final static String SQL_APPVERSIONFORBIDDEN = "select avr.id rid,\n" +
            "(case avr.`type` when 0 then 'app' when 1 then 'position' end) rtype, \n" +
            "avr.business_id businessId, \n" +
            "avr.version_rule verRule, \n" +
            "avr.`version` appVer,\n" +
            "tab1.codelist codelist, \n" +
            "(case avr.status when 0 then 0 else -1 end) status\n" +
            //"(case when (avr.`system`=0 and avr.status=0) or (avr.`system`=1 and avr.status=0 and tab1.status=0) then 0 else -1 end) status\n" +
            "from  app_version_rule avr\n" +
            "left join \n" +
            "(select acr.version_id, group_concat(case when acr.status=0 and ac.status=0 then acr.code end) codelist, \n" +
            "(case when FIND_IN_SET(0,group_concat(case when acr.status=0 and ac.status=0 then 0 else -1 end)) > 0 then 0 else -1 end) status \n" +
            "from app_channel_relation acr, android_channel ac\n" +
            "where acr.channel_id = ac.id\n" +
            "group by acr.version_id) tab1\n" +
            "on avr.id = tab1.version_id\n" +
            "where 1=1 \n";

    public final static String WHERE_IDS_APPVERSIONFORBIDDEN = "and avr.id in (%s) \n";

    public final static String SQL_PLATFORMDEFVARIABLE = "SELECT\n" +
            "  pe.platform_id AS platformId,\n" +
            "  pe.os AS os,\n" +
            "  pe.enum_item_code AS vName,\n" +
            "  pe.define_value AS val,\n" +
            "  (CASE WHEN pe.status = 0 THEN 0 ELSE - 1 END) AS status\n" +
            "FROM\n" +
            "  platform_enum pe\n" +
            "WHERE 1=1 ";

    public final static String WHERE_IDS_PLATFORMDEFVARIABLE = "and pe.id in (%s) \n";

    public final static String SQL_PLATFORMFROMTYPEVARIABLE = "SELECT\n" +
            "  pe.platform_id AS platformId,\n" +
            "  pa.ad_form_type AS formType,\n" +
            "  pa.ad_form AS style,\n" +
            "  pe.os AS os,\n" +
            "  pe.enum_item_code AS vName,\n" +
            "  pa.define_value AS val,\n" +
            "  (CASE WHEN pe.status = 0 && pa.status = 0 THEN 0 ELSE - 1 END) AS status\n" +
            "FROM\n" +
            "  platform_enum pe,\n" +
            "  platform_allocation pa\n" +
            "WHERE pe.id = pa.platform_enum_id ";

    public final static String WHERE_IDS_PLATFORMFROMTYPEVARIABLE = "and pa.id in (%s) \n";
    public final static String WHERE_DEV_PLATFORMFROMTYPEVARIABLE = "and pe.platform_id in (%s) \n";

    public final static String BEFORE_RUN = "SET GLOBAL group_concat_max_len=102400";
    public final static String BEFORE_RUN2 = "SET SESSION group_concat_max_len=102400";

    public final static String SQL_DEVELOPER = "SELECT\n" +
            "  id AS devId,\n" +
            "  code AS devCode,\n" +
            "  access_type AS accessType,\n" +
            "  agree_type AS agreeType,\n" +
            "  agree_version AS apiVersion,\n" +
            "  use_ff_position AS useFfId,\n" +
            "  trade_type AS dealType,\n" +
            "  dev_type AS devType,\n" +
            "  is_base_price AS isBasePrice,\n" +
            "  (case status when 0 then 0 else -1 end) AS status\n" +
            "FROM\n" +
            "  dev_info\n" +
            "WHERE 1=1 ";

    public final static String WHERE_IDS_DEVELOPER = "and id in (%s) \n";
    public final static String WHERE_DEV_DEVELOPER = "and id in (%s) \n";

    public final static String SQL_DEVCONFIGURE = "SELECT \n" +
            "CONCAT(dev_id,\"_\",app_id,\"_\",position_id) AS devConfigId, \n" +
            "dev_id AS devId, \n" +
            "app_id AS appId, \n" +
            "position_id AS positionId, \n" +
            "price * 100 as price,\n" + //杨总按照元为单位存储，我们按照分为单位
            "status \n" +
            "FROM dev_configure \n" +
            "WHERE status = 0\n";


    public final static String SQL_COLDBOOTCONFIG = "SELECT\n" +
            "  devId,\n" +
            "  appId,\n" +
            "  posId,\n" +
            "  platStr,\n" +
            "  status\n" +
            "FROM\n" +
            "  (SELECT\n" +
            "    cb.dev_id AS devId,\n" +
            "    cb.app_id AS appId,\n" +
            "    cb.position_id AS posId,\n" +
            "    GROUP_CONCAT(\n" +
            "      platform_id\n" +
            "      ORDER BY priority SEPARATOR ','\n" +
            "    ) AS platStr,\n" +
            "  (case cb.status when 0 then 0 else -1 end) AS status\n" +
            "  FROM\n" +
            "    `coldboot` cb,\n" +
            "    `coldboot_detail` cbd\n" +
            "  WHERE cb.`id` = cbd.`coldboot_id`\n" +
            "  and cbd.status = 0 " +
            "  GROUP BY cb.position_id) cbc\n" +
            "WHERE 1 = 1 ";

    public final static String WHERE_IDS_COLDBOOTCONFIG = "";
    public final static String WHERE_DEV_COLDBOOTCONFIG = "and devId in (%s) \n";

    public final static String SQL_ALLIANCEFLOW = "SELECT\n" +
            "  CONCAT(aaf.id,(CASE WHEN aaf.request_type = 0 THEN '' WHEN aaf.request_type = 1 THEN CONCAT('-',apf.id) END)) AS id,\n" +
            "  aaf.platform_id AS platformId,\n" +
            "  aaf.dev_id AS devId,\n" +
            "  aaf.app_id AS appId,\n" +
            "  apf.position_id AS positionId,\n" +
            "  aaf.area_code AS areaCode,\n" +
            "  aaf.request_type AS requestType,\n" +
            "  aaf.request_ratio AS appRatio,\n" +
            "  apf.request_ratio as positionRatio,\n" +
            "  aaf.ua_verify as uaVerify,\n" +
            "  aaf.oversize_ratio as oversizeRatio,\n" +
            "  (CASE WHEN (aaf.status = 0 AND aaf.request_type = 0) OR (aaf.request_type = 1 AND aaf.status = 0 AND apf.status = 0) THEN 0 ELSE - 1 END) AS status,\n" +
            "  ifnull(aaf.no_auth_flow,0) as no_auth_flow,\n" +  //是否分发未授权流量（0：否；1：是
            " apf.impl_upper as impl_upper \n"+
            "FROM\n" +
            "  alliance_app_flow aaf\n" +
            "LEFT JOIN alliance_position_flow apf\n" +
            "   ON aaf.id = apf.alliance_flow_id\n" +
            "   AND aaf.request_type = 1\n" +
            "WHERE 1 = 1 ";

    public final static String WHERE_IDS_ALLIANCEFLOW = "and aaf.id in (%s) \n";
    public final static String WHERE_DEV_ALLIANCEFLOW = "and aaf.dev_id in (%s) \n";


    public final static String SQL_POSITIONBIDDINGPRICESTRATEGY = "select bpps.position_id posId, \n" +
            "bps.dev_id devId, \n" +
            "bps.app_id appId, \n" +
            "bpps.platform_id platformId,\n" +
            "bpps.percentage, \n" +
            "bpps.standard_deviation standardDeviation, \n" +
            "bpps.offset, \n" +
            "bpps.price_rate scale, \n" +
            "bps.ifeng_percentage percent, \n" +
            "(case when bpps.status = 0 && bps.status = 0 then 0 else -1 end) status,\n" +
            "bpps.return_rate returnRate,\n" +
            "bps.no_divide_code noDivideCode,\n" +
            "bpps.floor_price floorPrice, \n" +
            "bpps.is_rtb isRtb,bpps.up_price upPrice,profit_price profitPrice, \n"+
            "bpps.is_trans as isTrans \n"+
            "from bidding_price_strategy bps, bidding_platform_position_price bpps \n" +
            "where bps.id = bpps.bidding_strategy_id\n";
    public final static String WHERE_IDS_POSITIONBIDDINGPRICESTRATEGY = "and bps.id in (%s) \n";

    public final static String SQL_MIXFLOW = "SELECT\n" +
            "  id,\n" +
            "  platformId,\n" +
            "  origAdId,\n" +
            "  mixAdId,\n" +
            "  mixAdRate,\n" +
            "  status\n" +
            "FROM\n" +
            "  (SELECT\n" +
            "    flow.id,\n" +
            "    flow.platform_id AS platformId,\n" +
            "    flow.position_id AS origAdId,\n" +
            "    mix.position_id AS mixAdId,\n" +
            "    mix.rate AS mixAdRate,\n" +
            "    CASE\n" +
            "      WHEN flow.status = 0\n" +
            "      AND mix.status = 0\n" +
            "      THEN 0\n" +
            "      ELSE - 1\n" +
            "    END AS status\n" +
            "  FROM\n" +
            "    (SELECT\n" +
            "      *\n" +
            "    FROM\n" +
            "      (SELECT\n" +
            "        aaf.id,\n" +
            "        aaf.platform_id,\n" +
            "        aaf.dev_id,\n" +
            "        aaf.app_id,\n" +
            "        aafm.position_id,\n" +
            "        aafm.mix_package_id,\n" +
            "        CASE\n" +
            "          WHEN aaf.status = 0\n" +
            "          AND aaf.is_mix = 1\n" +
            "          AND aafm.status = 0\n" +
            "          THEN 0\n" +
            "          ELSE - 1\n" +
            "        END AS status,\n" +
            "        aafm.create_time\n" +
            "      FROM\n" +
            "        alliance_app_flow_mix aafm\n" +
            "        LEFT JOIN alliance_app_flow aaf\n" +
            "          ON aaf.id = aafm.alliance_app_flow_id\n" +
            "      ORDER BY aaf.platform_id,\n" +
            "        aaf.dev_id,\n" +
            "        aaf.app_id,\n" +
            "        aafm.position_id,\n" +
            "        aafm.id DESC) flow\n" +
            "    GROUP BY flow.platform_id,\n" +
            "      flow.dev_id,\n" +
            "      flow.app_id,\n" +
            "      flow.position_id,\n" +
            "      flow.mix_package_id) flow\n" +
            "    LEFT JOIN\n" +
            "      (SELECT\n" +
            "        *\n" +
            "      FROM\n" +
            "        (SELECT\n" +
            "          mix.id,\n" +
            "          mix.dev_id,\n" +
            "          mix.app_id,\n" +
            "          NAME,\n" +
            "          mpp.position_id,\n" +
            "          mpp.rate,\n" +
            "          CASE\n" +
            "            WHEN mix.status = 0\n" +
            "            AND mpp.status = 0\n" +
            "            THEN 0\n" +
            "            ELSE - 1\n" +
            "          END AS status,\n" +
            "          mpp.create_time\n" +
            "        FROM\n" +
            "          mix_package_position mpp,\n" +
            "          mix_package mix\n" +
            "        WHERE mpp.mix_package_id = mix.id  \n" +
            "        ORDER BY mix.id,\n" +
            "          mix.dev_id,\n" +
            "          mix.app_id,\n" +
            "          mpp.position_id,\n" +
            "          mpp.id DESC) mix\n" +
            "      GROUP BY mix.id,\n" +
            "        mix.dev_id,\n" +
            "        mix.app_id,\n" +
            "        mix.position_id) mix\n" +
            "      ON flow.mix_package_id = mix.id) tab\n" +
            "WHERE 1 = 1 ";

    public final static String WHERE_IDS_MIXFLOW = " and id in (%s) \n";
    public final static String WHERE_DEV_MIXFLOW = "";
    public final static String ORDERBY_MIXFLOW=" order by status ";
    public final static String GROUPBY_MIXFLOW="";
    public final static String HAVING_MIXFLOW="";

    public final static String SQL_MIXAPPVERSION = "SELECT\n" +
            "  dev_id AS devId,\n" +
            "  app_id AS appId,\n" +
            "  version\n" +
            "FROM\n" +
            "  report_app_version\n" +
            "WHERE id IN\n" +
            "  (SELECT\n" +
            "    MAX(id)\n" +
            "  FROM\n" +
            "    (SELECT\n" +
            "      a.*\n" +
            "    FROM\n" +
            "      report_app_version a\n" +
            "      INNER JOIN\n" +
            "        (SELECT\n" +
            "          MAX(COUNT) AS COUNT,\n" +
            "          app_id,\n" +
            "          version\n" +
            "        FROM\n" +
            "          report_app_version\n" +
            "        WHERE DATE >= DATE_SUB(CURDATE(), INTERVAL 90 DAY)\n" +
            "        GROUP BY app_id) AS b\n" +
            "        ON a.count = b.count\n" +
            "        AND a.app_id = b.app_id) AS t1\n" +
            "  GROUP BY t1.app_id)\n" +
            "GROUP BY dev_id,\n" +
            "  app_id,\n" +
            "  COUNT DESC";

    public final static String WHERE_IDS_MIXAPPVERSION = "";
    public final static String WHERE_DEV_MIXAPPVERSION = "";


    public final static String SQL_PLATFORMFLOWRATIO= "select id, platform_id as platformId,algo_group as algoGroup,flow_ratio as flowRatio,status from platform_flow_ratio where 1=1";

    //更新一条记录 platform_id 相同的一组记录都需要更新
    public final static String WHERE_IDS_PLATFORMFLOWRATIO = " and platform_id in (select platform_id from platform_flow_ratio where id in (%s)) \n";
    public final static String WHERE_DEV_PLATFORMFLOWRATIO= "";


    public final static String SQL_APPPLATFORMFLOWRATIO= "select t1.id,app_id as appId, platform_id as platformId,t2.algo_group as algoGroup,t2.flow_ratio as flowRatio,t1.status from " +
            "app_algo_ratio t1 inner join app_platform_algo_ratio t2 on t1.id = t2.app_algo_ratio_id where 1=1   and t2.algo_group in (select algo_group from platform_flow_ratio where platform_id = t1.platform_id and status =0) ";

    public final static String WHERE_IDS_APPPLATFORMFLOWRATIO = " and t1.id  = %s \n";
    public final static String WHERE_DEV_APPPLATFORMFLOWRATIO = "";


    public final static String SQL_ALLIANCEAPPPOSITIONFLOW = "select a.id,a.alliance_app_id as allianceappId,a.position_id as positionId,frequency,b.platform_id as platformId,a" +
            ".status from alliance_app_position_flow a inner join alliance_app_flow b on a.alliance_app_id = b.id where 1 = 1";

    public final static String WHERE_IDS_ALLIANCEAPPPOSITIONFLOW = " and b.id = %s \n";
    public final static String WHERE_DEV_ALLIANCEAPPPOSITIONFLOW = "";

    public final static String SQL_ADFORMCONVERT="select a.id,a.platform_id as platformId,a.position_id as positionId,a.form_id as formId,b.name as formCode,b.enum_itme_code as formType,a.size_id as sizeId,a.match_form_id as switchFormId,a.match_size_id as switchSizeId ,c.name as switchFormCode,a.`status` from ad_form_convert a left join ad_form b on a.form_id=b.id " +
            " left join ad_form c on  a.match_form_id=c.id where 1=1 ";

    public final static String WHERE_IDS_ADFORMCONVERT = " and a.id = %s \n";

    public final static String WHERE_DEV_ADFORMCONVERT = "";

    public final static String SQL_POSITIONMOBILEWHITE= "select id,app_id as appId,position_id as positionId,platform_id as platformId,mobile_value as mobileValue,status," +
            "start_time as startTime,end_time as endTime\n" +
            " from position_mobile_white where 1 = 1 and end_time >= now() ";
    public final static String WHERE_IDS_POSITIONMOBILEWHITE = " and id  = %s \n";
    public final static String WHERE_DEV_POSITIONMOBILEWHITE = "";

    public final static String SQL_APPOTHER= "select id, app_id as appId ,pack_name as packageName,app_name as appName,app_type as appType,os,status from app_other where 1 =1" ;
    public final static String WHERE_IDS__APPOTHER = " and id  = %s \n";
    public final static String WHERE_DEV__APPOTHER = "";

    public final static String SQL_APPPLATFORMTRANS= "select id,dev_id as devId,app_id as appId,platform_id as platformId,status from app_platform_trans where 1=1 ";
    public final static String WHERE_IDS__APPPLATFORMTRANS = " and id  = %s \n";
    public final static String WHERE_DEV__APPPLATFORMTRANS = "";

}
