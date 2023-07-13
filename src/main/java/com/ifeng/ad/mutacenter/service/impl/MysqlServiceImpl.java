package com.ifeng.ad.mutacenter.service.impl;

import com.google.common.base.Joiner;
import com.ifeng.ad.mutacenter.SQLConstant;
import com.ifeng.ad.mutacenter.common.bean.mysqlmap.*;
import com.ifeng.ad.mutacenter.common.utils.StringUtils;
import com.ifeng.ad.mutacenter.service.MysqlService;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by  on 2019/1/17.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MysqlServiceImpl implements MysqlService {
    private static Logger log = LoggerFactory.getLogger(MysqlServiceImpl.class);

    @Autowired
    private QueryRunner queryRunner;

    @Value("${debugsql}")
    private boolean debugsql;

    @Override
    public <T> List<T> find(Class<T> clazz, Set<String> ids, String devid) {
        Map<String, String> wheremap = new HashMap<>();

        try {//类型以及id
            String idstr = ids == null ? null : Joiner.on(",").join(ids);
            if (StringUtils.isNotEmpty(idstr)) {
                wheremap.put(SQLConstant.class.getField("WHERE_IDS_" + clazz.getSimpleName().toUpperCase()).get(null).toString(), idstr);
            }
        } catch (Exception e) {
        }

        try {//开发者
            if (StringUtils.isNotEmpty(devid)) {
                wheremap.put(SQLConstant.class.getField("WHERE_DEV_" + clazz.getSimpleName().toUpperCase()).get(null).toString(), devid);
            }
        } catch (Exception e) {
        }

        return findByWhere(clazz, wheremap);
    }

    public <T> List<T> findByPare(Class<T> clazz, Set<String> ids, Set<String> pareIds) {
        Map<String, String> wheremap = new HashMap<>();

        try {//类型以及id
            String idstr = ids == null ? null : Joiner.on(",").join(ids);
            if (StringUtils.isNotEmpty(idstr)) {
                wheremap.put(SQLConstant.class.getField("WHERE_IDS_" + clazz.getSimpleName().toUpperCase()).get(null).toString(), idstr);
            }
        } catch (Exception e) {
        }

        try {//父ID
            String pareIdStr = pareIds == null ? null : Joiner.on(",").join(ids);
            if (StringUtils.isNotEmpty(pareIdStr)) {
                wheremap.put(SQLConstant.class.getField("WHERE_PIDS_" + clazz.getSimpleName().toUpperCase()).get(null).toString(), pareIdStr);
            }
        } catch (Exception e) {
        }

        return findByWhere(clazz, wheremap);
    }

    public <T> List<T> findByWhere(Class<T> clazz, Map<String, String> whereMap) {
        try {
            String runsql = SQLConstant.class.getField("SQL_" + clazz.getSimpleName().toUpperCase()).get(null).toString();
            if (whereMap != null && !whereMap.isEmpty()) {
                for (Map.Entry<String, String> whereentry : whereMap.entrySet()) {
                    if (StringUtils.isNotEmpty(whereentry.getKey()) &&
                            StringUtils.isNotEmpty(whereentry.getValue())) {
                        runsql += String.format(whereentry.getKey(), whereentry.getValue());
                    }
                }
            }

            //获取group语句
            //获取order语句
            //获取having语句
            try {
                String groupsql = SQLConstant.class.getField("GROUPBY_" + clazz.getSimpleName().toUpperCase()).get(null).toString();
                if (StringUtils.isNotEmpty(groupsql)) {
                    runsql += groupsql;
                }
                String ordersql = SQLConstant.class.getField("ORDERBY_" + clazz.getSimpleName().toUpperCase()).get(null).toString();
                if (StringUtils.isNotEmpty(ordersql)) {
                    runsql += ordersql;
                }
                String havingsql = SQLConstant.class.getField("HAVING_" + clazz.getSimpleName().toUpperCase()).get(null).toString();
                if (StringUtils.isNotEmpty(havingsql)) {
                    runsql += havingsql;
                }
            } catch (Exception e) {
            }

            if (debugsql) {
                log.info("\n" + runsql);
            }
            return queryRunner.query(runsql, new BeanListHandler<>(clazz));
        } catch (Exception e) {
            log.error(e.toString());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Adform> findAdform(Set<String> formids) {
        return find(Adform.class, formids, null);
    }

    @Override
    public List<Adform> findAdformByAdSize(Set<String> formids) {
        Map<String, String> wheremap = new HashMap<>();
        String idstr = formids == null ? null : Joiner.on(",").join(formids);
        wheremap.put(SQLConstant.WHERE_ADFORM_ADSIZE, idstr);
        return findByWhere(Adform.class, wheremap);
    }

    @Override
    public List<AppInfo> findAppInfo(Set<String> appids, String devid) {
        return find(AppInfo.class, appids, devid);
    }

    @Override
    public List<Adposition> findAdposition(Set<String> posIds, String WHERESQL, String devid) {
        Map<String, String> wheremap = new HashMap<>();
        String idstr = posIds == null ? null : Joiner.on(",").join(posIds);
        wheremap.put(WHERESQL, idstr);
        wheremap.put(SQLConstant.WHERE_DEV_ADPOSITION, devid);
        return findByWhere(Adposition.class, wheremap);
    }

    @Override
    public List<WingsInfo> findWingsInfo(Set<String> wingsids, String devid) {
        return find(WingsInfo.class, wingsids, devid);
    }

    @Override
    public List<Allowance> findAllowance(Set<String> allowanceIds, String devid) {
        return find(Allowance.class, allowanceIds, devid);
    }

    @Override
    public List<CpdCreative> findCpdCreative(Set<String> creativeIds, String devid, String WHERESQL) {
        Map<String, String> wheremap = new HashMap<>();
        String idstr = creativeIds == null ? null : Joiner.on(",").join(creativeIds);
        wheremap.put(WHERESQL, idstr);
        wheremap.put(SQLConstant.WHERE_DEV_CPDCREATIVE, devid);
        return findByWhere(CpdCreative.class, wheremap);
    }

    @Override
    public List<SellPlatform> findSellPlatform(Set<String> platformIds) {
        return find(SellPlatform.class, platformIds, null);
    }

    @Override
    public List<DevConfigure> findDevConfig(String devId, String appId, String positionId) {
        Map<String, String> wheremap = new HashMap<>();
        if (devId != null) {
            wheremap.put(" and dev_id = ", devId);
        }
        if (appId != null) {
            wheremap.put(" and app_id = ", appId);
        }
        if (positionId != null) {
            wheremap.put(" and position_id = ", positionId);
        }
        return findByWhere(DevConfigure.class, wheremap);
    }

    @Override
    public List<ColdBootConfig> findColdBootConfig(Set<String> ids, String devid) {
        return find(ColdBootConfig.class, ids, devid);
    }

    @Override
    public List<ShowTime> findShowTime(Set<String> changeids, String WHERESQL) {
        Map<String, String> wheremap = new HashMap<>();
        String idstr = changeids == null ? null : Joiner.on(",").join(changeids);
        wheremap.put(WHERESQL, idstr);
        return findByWhere(ShowTime.class, wheremap);
    }

    @Override
    public List<AllianceAppInfo> findAllianceAppInfo(Set<String> ids, String WHERESQL, String devid) {
        Map<String, String> wheremap = new HashMap<>();
        String idstr = ids == null ? null : Joiner.on(",").join(ids);
        wheremap.put(WHERESQL, idstr);
        wheremap.put(SQLConstant.WHERE_DEV_ALLIANCEAPPINFO, devid);
        return findByWhere(AllianceAppInfo.class, wheremap);
    }

    @Override
    public Map<String, String> findSdkSupport(Set<String> ids) {
        //不处理传进来的ids
        //因为更改频率极低, 所以数据存的map形式, 所以没有必要通过id增减
        Map<String, String> sdksupportmap = new HashMap<>();
        try {
            //因为后面的查询中使用了group_concat，拼装了以后的字符串长度很可能会超过默认1024，所以提前设定一下在执行sql
            queryRunner.execute(SQLConstant.BEFORE_RUN);
            queryRunner.execute(SQLConstant.BEFORE_RUN2);
        } catch (SQLException e) {
            log.error(e.toString());
        }
        List<SdkSupport> sdkSupports = findByWhere(SdkSupport.class, null);
        sdkSupports.forEach(sdkSupport -> sdksupportmap.put(sdkSupport.getVer(), sdkSupport.getAlliance()));
        return sdksupportmap;
    }

    @Override
    public List<DspWeight> findDspWeight(Set<String> ids) {
        return find(DspWeight.class, ids, null);
    }

    @Override
    public List<AdpositionPrice> findAdpositionPrice(Set<String> ids) {
        return find(AdpositionPrice.class, ids, null);
    }

    @Override
    public List<Forbidden> findForbidden(String devid) {
        return find(Forbidden.class, null, devid);
    }

    @Override
    public List<BiddingPriceStrategy> findBiddingPriceStrategy(Set<String> ids, String devid) {
        return find(BiddingPriceStrategy.class, ids, devid);
    }

    @Override
    public List<PositionBiddingPriceStrategy> findPositionBiddingPriceStrategy(Set<String> ids) {
        return find(PositionBiddingPriceStrategy.class, ids, null);
    }

    @Override
    public List<DspForbidden> findDspForbidden(Set<String> ids, String devid) {
        return find(DspForbidden.class, ids, devid);
    }

    @Override
    public List<DspRule> findDspRule(Set<String> ids, String devid) {
        return find(DspRule.class, ids, devid);
    }

    @Override
    public List<AppFlowRatio> findAppFlowRatio() {
        return find(AppFlowRatio.class, null, null);
    }

    @Override
    public List<StatisticsEffectDate> findStatisticsEffectDate() {
        return find(StatisticsEffectDate.class, null, null);
    }

    @Override
    public List<FromTypeStatisticsEffectDate> findFromTypeStatisticsEffectDate() {
        return find(FromTypeStatisticsEffectDate.class, null, null);
    }

    @Override
    public List<AppVersionForbidden> findAppVersionForbidden(Set<String> ids) {
        return find(AppVersionForbidden.class, ids, null);
    }

    @Override
    public List<PlatformDefVariable> findPlatformDefVariable(Set<String> ids) {
        return find(PlatformDefVariable.class, ids, null);
    }

    @Override
    public List<PlatformFromTypeVariable> findPlatformFromTypeVariable(Set<String> ids, Set<String> pareIds) {
        return findByPare(PlatformFromTypeVariable.class, ids, pareIds);
    }

    @Override
    public List<Developer> findDeveloper(Set<String> ids, String devid) {
        return find(Developer.class, ids, devid);
    }

    @Override
    public List<AllianceFlow> findAllianceFlow(Set<String> ids, String devid) {
        return find(AllianceFlow.class, ids, devid);
    }

    @Override
    public List<MixFlow> findMixFlow(Set<String> ids, String devid) {
        return find(MixFlow.class, ids, devid);
    }

    @Override
    public List<MixAppVersion> findMixAppVersion(Set<String> ids, String devid) {
        return find(MixAppVersion.class, ids, devid);
    }
    @Override
    public List<PlatformFlowRatio> findPlatformFlowRatio(Set<String> ids, String devid) {
        return find(PlatformFlowRatio.class, ids, devid);
    }

    @Override
    public List<AppPlatformFlowRatio> findAppPlatformFlowRatio(Set<String> ids, String devid) {
        return find(AppPlatformFlowRatio.class, ids, devid);
    }

    @Override
    public List<AllianceappPositionFlow> findAllianceappPositionFlow(Set<String> ids, String devid) {
        return find(AllianceappPositionFlow.class, ids, devid);
    }
    @Override
    public List<AdFormConvert> findPlatformPositionAdFormConvert(Set<String> ids, String devid){
        return find(AdFormConvert.class, ids, devid);
    }

    @Override
    public <T> List<T> find(String sql, Class<T> clazz, Object... params) {
        //String sql = "SELECT id,platform_id,position_id,form_type,price,`status` FROM ifstest.platform_price";
        List<T> ret;
        try {
            ret = queryRunner.query(sql, new BeanListHandler<>(clazz), params);
        } catch (SQLException e) {
            List<String> ls = new ArrayList<>();
            Arrays.stream(params).forEach(v -> {
                ls.add((String) v);
            });
            log.error(String.format("run sql error,sql=%s&clazz=%s&params=%s", sql, clazz.getName(), String.join(",", ls)));
            ret = null;
        }
        return ret;
    }

    @Override
    public boolean execute(String sql, Object... params) {
        try {
            int ret = queryRunner.execute(sql,params);
            if(ret >0){
                return true;
            }else{
                return false;
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
