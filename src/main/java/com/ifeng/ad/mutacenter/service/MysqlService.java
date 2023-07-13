package com.ifeng.ad.mutacenter.service;

import com.ifeng.ad.mutacenter.common.bean.mysqlmap.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * mysql数据库查询类
 * Created by wangting3 on 2019/1/16.
 */
public interface MysqlService {
    /**
     * 广告形式获取
     * @param formids
     * @return
     */
    public List<Adform> findAdform(Set<String> formids);
    public List<Adform> findAdformByAdSize(Set<String> formids);

    /**
     * 获取凤翼信息
     * @param wingsids
     * @return
     */
    public List<WingsInfo> findWingsInfo(Set<String> wingsids, String devid);

    /**
     * 应用信息
     * @param formids
     * @param devid
     * @return
     */
    public List<AppInfo> findAppInfo(Set<String> formids, String devid);

    /**
     * 广告位
     * @param posIds
     * @return
     */
    public List<Adposition> findAdposition(Set<String> posIds, String WHERESQL, String devid);

    /**
     * 余量策略
     * @param allowanceIds
     * @return
     */
    public List<Allowance> findAllowance(Set<String> allowanceIds, String devid);

    /**
     * cpd创意
     * @param creativeIds
     * @return
     */
    public List<CpdCreative> findCpdCreative(Set<String> creativeIds, String devid, String WHERESQL);

    /**
     * 售卖平台信息
     * @param platformIds
     * @return
     */
    public List<SellPlatform> findSellPlatform(Set<String> platformIds);

    /**
     * dev_configure的信息
     */
    public List<DevConfigure> findDevConfig(String devId, String appId, String positionId);

    /**
     * coldBoot_config的信息
     */
    public List<ColdBootConfig> findColdBootConfig(Set<String> ids, String devid);

    /**
     * 该广告此时需要展示广告的清单
     * @param posIds 支持批量查询更新
     * @return
     */
    public List<ShowTime> findShowTime(Set<String> posIds, String WHERESQL);

    /**
     * 获取联盟应用id和广告位id
     * @param ids 应用映射关系id或者广告位映射关系id
     * @param WHERESQL 应用映射SQL 或者 广告位映射SQL
     * @param devid 开发者id
     * @return
     */
    public List<AllianceAppInfo> findAllianceAppInfo(Set<String> ids, String WHERESQL, String devid);

    /**
     * 获取sdk支持的网盟
     * @param ids sdk的id
     * @return
     */
    public Map<String, String> findSdkSupport(Set<String> ids);

    /**
     * 获取网盟权重列表
     * @param ids 权重id
     * @return
     */
    public List<DspWeight> findDspWeight(Set<String> ids);

    /**
     * 获取广告位底价
     * @param ids
     * @return
     */
    public List<AdpositionPrice> findAdpositionPrice(Set<String> ids);

    /**
     * 获取禁投行业信息
     * @param devid 开发者id
     * @return
     */
    public List<Forbidden> findForbidden(String devid);

    /**
     * 获取竞价策略信息
     * @param ids
     * @return
     */
    public List<BiddingPriceStrategy> findBiddingPriceStrategy(Set<String> ids, String devid);

    /**
     * 获取单个广告位的竞价策略信息（优先级高）
     * @param ids
     * @return
     */
    public List<PositionBiddingPriceStrategy> findPositionBiddingPriceStrategy(Set<String> ids);

    /**
     * 获取dsp的屏蔽列表的，是在投放以后的要屏蔽的功能（左总需求）
     * @param ids
     * @param devid
     * @return
     */
    public List<DspForbidden> findDspForbidden(Set<String> ids, String devid);

    /**
     * 获取dsp的规则内容，（张琦的需求）
     * @param ids
     * @param devid
     * @return
     */
    public List<DspRule> findDspRule(Set<String> ids, String devid);

    public List<AppFlowRatio> findAppFlowRatio();

    public List<StatisticsEffectDate> findStatisticsEffectDate();

    public List<FromTypeStatisticsEffectDate> findFromTypeStatisticsEffectDate();

    public List<AppVersionForbidden> findAppVersionForbidden(Set<String> ids);

    public List<PlatformDefVariable> findPlatformDefVariable(Set<String> ids);

    public List<PlatformFromTypeVariable> findPlatformFromTypeVariable(Set<String> ids, Set<String> pareIds);

    public List<Developer> findDeveloper(Set<String> ids, String devid);

    public List<AllianceFlow> findAllianceFlow(Set<String> ids, String devid);

    public List<MixFlow> findMixFlow(Set<String> ids, String devid);

    public List<MixAppVersion> findMixAppVersion(Set<String> ids, String devid);

    public List<PlatformFlowRatio> findPlatformFlowRatio(Set<String> ids, String devid) ;

    public List<AppPlatformFlowRatio> findAppPlatformFlowRatio(Set<String> ids, String devid) ;

    public List<AllianceappPositionFlow> findAllianceappPositionFlow(Set<String> ids, String devid) ;

    List<AdFormConvert> findPlatformPositionAdFormConvert(Set<String> ids, String devid) ;

    <T> List<T> find(String sql,Class<T> clazz,Object... params);

    <T> List<T> find(Class<T> clazz, Set<String> ids, String devid);

    boolean execute(String sql, Object... params);
}
