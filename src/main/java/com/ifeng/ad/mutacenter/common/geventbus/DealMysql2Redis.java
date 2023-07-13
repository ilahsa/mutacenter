package com.ifeng.ad.mutacenter.common.geventbus;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.google.common.eventbus.Subscribe;
import com.ifeng.ad.mutacenter.Constant;
import com.ifeng.ad.mutacenter.SQLConstant;
import com.ifeng.ad.mutacenter.common.bean.mysqlmap.*;
import com.ifeng.ad.mutacenter.common.geventbus.bean.ReqObj;
import com.ifeng.ad.mutacenter.common.utils.*;
import com.ifeng.ad.mutacenter.service.MysqlService;
import com.ifeng.ad.mutacenter.service.RedisClusterService;
import com.ifeng.ad.mutacenter.service.RedisService;
import com.ifeng.ad.mutacenter.verticles.KafkaVerticle;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * Created by  on 2019/1/22.
 */
@Component
public class DealMysql2Redis implements Constant {
    private static Logger logger = LoggerFactory.getLogger(DealMysql2Redis.class);

    @Value("${debugredis}")
    private boolean debugredis;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private MysqlService mysqlService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private RedisClusterService redisClusterService;
    @Autowired
    private KafkaVerticle kafkaVerticle;

    /**
     * 接口处理入口
     *
     * @param reqObj
     */
    @Subscribe
    public void listener(ReqObj reqObj) {
        logger.info("Listen:" + JsonUtils.convert2Json(reqObj));
        String dev = reqObj.getDev();
        dev = "0".equals(dev) ? null : dev;
        String type = reqObj.getType();
        Set<String> ids = reqObj.getIds();

        if ("-1".equals(dev) ||
                "-1".equals(type) ||
                ids == null ||
                ids.isEmpty() ||
                ids.stream().filter("-1"::equals).count() > 0) {
            /**
             * 过滤异常数据，dev和type为-1异常
             * ids为空或者存在-1，异常
             * 异常数据不处理
             */
            return;
        }

        if (ids.stream().filter("0"::equals).count() > 0) {
            ids = null;
        }

        //执行更新操作
        switch (type) {
            case "0": //全部内容
                pushall(dev);
                break;
            case "1": //广告位
                adposition(ids, SQLConstant.WHERE_IDS_ADPOSITION, SQLConstant.WHERE_SHOWTIME_POSITON, dev);
                break;
            case "2": //余量平台配置
                sellplatform(ids);
                break;
            case "3": //广告形式
                adform(ids);
                break;
            case "4": //策略配置
                allowance(ids, dev);
                break;
            case "5": //cpd创意
                cpdcreative(ids, dev, SQLConstant.WHERE_IDS_CPDCREATIVE, SQLConstant.WHERE_SHOWTIME_CPDCREATIVE_IDS);
                break;
            case "6": //广告尺寸
                adsize(ids);
                break;
            case "7": //投放单
                cpdcreative(ids, dev, SQLConstant.WHERE_DELIVER_CPDCREATIVE, SQLConstant.WHERE_SHOWTIME_CPDCREATIVE_DELIVER);
                break;
            case "8": //产品排期
                cpdcreative(ids, dev, SQLConstant.WHERE_DELIVER_SCHEDULE_CPDCREATIVE, SQLConstant.WHERE_SHOWTIME_CPDCREATIVE_DELIVER_SCHEDULE);
                break;
            case "9": //应用信息
                appinfo(ids, dev);
                break;
            case "10": //凤翼配置信息
                winginfo(ids, dev);
                break;
            case "11": //联盟应用ID修改
                allianceAppinfo(ids, SQLConstant.WHERE_ALLIANCEAPPINFO_APPID, dev);
                break;
            case "12": //联盟广告位ID修改
                allianceAppinfo(ids, SQLConstant.WHERE_ALLIANCEAPPINFO_POSID, dev);
                break;
            case "13": //开发者
                if (ids != null && ids.size() > 0) {
                    ids.stream().forEach(this::pushallbydev);
                }
                break;
            case "14": //sdk支持网盟
                sdkSupportAlliance(ids);
                break;
            case "15": //网盟不同位置投放权重
                //临时注掉，不使用该权限策略
                //dspWeight(ids);
                break;
            case "16": //网盟不同位置的底价
                adpositionprice(ids);
                break;
            case "17": //网盟不同位置的底价
                adposition(ids, SQLConstant.WHERE_PRODUCT_ADPOSITION, SQLConstant.WHERE_SHOWTIME_PRODUCT_POSITON, dev);
                break;
            case "18": //禁投行业
                forbidden(dev);
                break;
            case "19": //竞价策略
                biddingPriceStrategy(ids, dev);
                adposition(ids, SQLConstant.WHERE_BPP_ID, dev);
                break;
            case "20": //DSP投放策略
                dspRule(ids, dev);
                break;
            case "21": //DSP素材审核禁投
                dspForbidden(ids, dev);
                break;
            case "22": //app流量分配占比（暂时不需要管理端同步，定时取，以后可能会做）
                appFlowRatio();
                break;
            case "23": //过去几天的流量情况（不需要管理端同步，志鹏老师直接算好入库，定时取）
                statisticsEffectDate();
                break;
            case "24": //APP版本控制
                appVersionForbidden(ids, dev);
                break;
            case "25": //广告位反作弊配置
                adposition(ids, SQLConstant.WHERE_CHEAT_ADPOSITON, dev);
                break;
            case "26": //平台变量配置
                platformDefVariable(ids, dev);
                platformFromTypeVariable(null, ids);
                break;
            case "27": //平台变量数值配置
                platformFromTypeVariable(ids, null);
                break;
            case "28":  //过去几天的广告类别的流量情况（不需要管理端同步，志鹏老师直接算好入库，定时取）
                fromTypeStatisticsEffectDate();
                break;
            case "29": //开发者的配置
                devConfigInfo();
                break;
            case "35": //全局配置
                settingInfo();
                break;
            case "36": //冷启动的配置
                coldBootInfo(ids, dev);
                break;
            case "38": //平台应用流量控制
                allianceFlow(ids, dev);
                break;
            case "40": //平台广告位固定价格配置
                BiConsumer<Set<String>, String> action = (v1, v2) -> {
                    syncPlatformPrice(v1, v2);
                };
                applyTask(ids, dev, action);
                break;
            case "41": //价格控制平台广告位
                positionBiddingPriceStrategy(ids);
                break;
            case "42": //混量包配置
                mixFlowInfo(ids, null);
                break;
            case "43": //混投版本号
                mixAppVersionInfo(null, null);
                break;
            case "44":
                //更新平台策略 app+平台策略一并更新
                platformFlowRatio(ids, null);
                appPlatformFlowRatio(null, null);
                break;
            case "45":
                appPlatformFlowRatio(ids, null);
                break;
            case "46":
                allianceappPositionFlow(ids,null);
                break;
            case "47":
                appinfo(ids, dev);
                break;
            case "48":
                //广告形式转换
                platformPositionAdFormSwitch(ids,null);
                break;
            case "49":
                positionMobileWhite(ids,null);
                break;
            case "50":
                appOther(ids,null);
                break;
            case "51":
                appPlatformtrans(ids,null);
                break;
            case "9999": {
                //同步appver 黑名单信息
                Set<String> blackAppVers = new HashSet<>();
                blackAppVers.add("mutaEx:blackappver_for_clickmacro");
                kafkaVerticle.writeSet2Kafk(blackAppVers);
                break;
            }
        }
    }

    /**
     * 推送全部信息
     */
    private void pushall(String dev) {
        if (dev == null) {
            pushallsystem();
        } else {
            pushallbydev(dev);
        }
    }

    /**
     * 刷新所有数据
     */
    private void pushallsystem() {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> adformmap = findAdform(null);
            Map<String, Map<String, String>> appinfo = findAppInfo(null, null);
            Map<String, Map<String, String>> wingsinfo = findWingsInfo(null, null);
            Map<String, Map<String, String>> adpositionmap = findAdpositions(null, SQLConstant.WHERE_IDS_ADPOSITION, null);
            Map<String, Map<String, String>> allowancemap = findAllowances(null, null);
            Map<String, Map<String, String>> cpdCreativemap = findCpdCreatives(null, null, SQLConstant.WHERE_IDS_CPDCREATIVE);
            Map<String, Map<String, String>> sellPlatformsmap = findSellPlatform(null);
            Map<String, Map<String, String>> allianceAppInfomap = findAllianceAppInfo(null, SQLConstant.WHERE_ALLIANCEAPPINFO_APPID, null);
            Map<String, Map<String, String>> showmap = findShowTime(null, null);
            Map<String, Map<String, String>> forbiddenmap = findForbidden(null);

            Map<String, Map<String, String>> biddingPriceStrategymap = findBiddingPriceStrategy(null, null);
            Map<String, Map<String, String>> positionbiddingPriceStrategymap = findPositionBiddingPriceStrategy(null);

            Map<String, Map<String, String>> dspForbiddenmap = findDspForbidden(null, null);
            Map<String, Map<String, String>> dspRulemap = findDspRule(null, null);
            Map<String, Map<String, String>> appflowRatiomap = findAppFlowRatio();
            Map<String, Map<String, String>> statisticsEffectDatemap = findStatisticsEffectDate();
            Map<String, Map<String, String>> fromTypeStatisticsEffectDatemap = findFromTypeStatisticsEffectDate();
            Map<String, Map<String, String>> appVersionForbiddenmap = findAppVersionForbidden(null, null);
            Map<String, Map<String, String>> platformDefVariablemap = findPlatformDefVariable(null, null);
            Map<String, Map<String, String>> platformFromTypeVariablemap = findPlatformFromTypeVariable(null, null);
            Map<String, Map<String, String>> developermap = findDeveloper(null, null);
            Map<String, Map<String, String>> devConfiguremap = findAllDevConfigure(null, null, null);
            Map<String, Map<String, String>> coldBootConfigmap = findAllColdBootConfig(null, null);
            Map<String, Map<String, String>> allianceFlowmap = findAllianceFlow(null, null);
            Map<String, Map<String, String>> mixFlowMap = findMixFlow(null, null);
            Map<String, Map<String, String>> mixAppVersionMap = findMixAppVersion(null, null);

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(adformmap));
                logger.info(JsonUtils.convert2Json(appinfo));
                logger.info(JsonUtils.convert2Json(wingsinfo));
                logger.info(JsonUtils.convert2Json(adpositionmap));
                logger.info(JsonUtils.convert2Json(allowancemap));
                logger.info(JsonUtils.convert2Json(cpdCreativemap));
                logger.info(JsonUtils.convert2Json(sellPlatformsmap));
                logger.info(JsonUtils.convert2Json(allianceAppInfomap));
                logger.info(JsonUtils.convert2Json(showmap));
                logger.info(JsonUtils.convert2Json(forbiddenmap));
                logger.info(JsonUtils.convert2Json(biddingPriceStrategymap));
                logger.info(JsonUtils.convert2Json(dspForbiddenmap));
                logger.info(JsonUtils.convert2Json(dspRulemap));
                logger.info(JsonUtils.convert2Json(appflowRatiomap));
                logger.info(JsonUtils.convert2Json(statisticsEffectDatemap));
                logger.info(JsonUtils.convert2Json(fromTypeStatisticsEffectDatemap));
                logger.info(JsonUtils.convert2Json(appVersionForbiddenmap));
                logger.info(JsonUtils.convert2Json(platformDefVariablemap));
                logger.info(JsonUtils.convert2Json(platformFromTypeVariablemap));
                logger.info(JsonUtils.convert2Json(developermap));
                logger.info(JsonUtils.convert2Json(devConfiguremap));
                logger.info(JsonUtils.convert2Json(coldBootConfigmap));
                logger.info(JsonUtils.convert2Json(allianceFlowmap));
            }
            Set<String> adformkeys = redisService.maptoredis(adformmap, true,
                    Sets.newHashSet(WORD_MUTA + WORD_SPLIT_COLON + Adform.class.getSimpleName() + WORD_SPLIT_COLON));
            kafkaVerticle.writeSet2Kafk(adformkeys);

            Set<String> appinfos = redisService.maptoredis(appinfo, true,
                    Sets.newHashSet(WORD_MUTA + WORD_SPLIT_COLON + AppInfo.class.getSimpleName() + WORD_SPLIT_COLON));
            kafkaVerticle.writeSet2Kafk(appinfos);

            Set<String> wingsinfos = redisService.maptoredis(wingsinfo, true,
                    Sets.newHashSet(WORD_MUTA + WORD_SPLIT_COLON + WingsInfo.class.getSimpleName() + WORD_SPLIT_COLON));
            kafkaVerticle.writeSet2Kafk(wingsinfos);

            Set<String> adpositionkeys = redisService.maptoredis(adpositionmap, true,
                    Sets.newHashSet(WORD_MUTA + WORD_SPLIT_COLON + Adposition.class.getSimpleName() + WORD_SPLIT_COLON));
            kafkaVerticle.writeSet2Kafk(adpositionkeys);

            Set<String> allowancekeys = redisService.maptoredis(allowancemap, true,
                    Sets.newHashSet(WORD_MUTA + WORD_SPLIT_COLON + Allowance.class.getSimpleName() + WORD_SPLIT_COLON));
            kafkaVerticle.writeSet2Kafk(allowancekeys);

            Set<String> cpdCreativekeys = redisService.maptoredis(cpdCreativemap, true,
                    Sets.newHashSet(WORD_MUTA + WORD_SPLIT_COLON + CpdCreative.class.getSimpleName() + WORD_SPLIT_COLON));
            kafkaVerticle.writeSet2Kafk(cpdCreativekeys);

            Set<String> sellPlatformskeys = redisService.maptoredis(sellPlatformsmap, true,
                    Sets.newHashSet(WORD_MUTA + WORD_SPLIT_COLON + SellPlatform.class.getSimpleName() + WORD_SPLIT_COLON));
            kafkaVerticle.writeSet2Kafk(sellPlatformskeys);

            Set<String> allianceappkeys = redisService.maptoredis(allianceAppInfomap, true,
                    Sets.newHashSet(WORD_MUTA + WORD_SPLIT_COLON + AllianceAppInfo.class.getSimpleName() + WORD_SPLIT_COLON));
            kafkaVerticle.writeSet2Kafk(allianceappkeys);

            Set<String> showkeys = redisService.maptoredis(showmap, true,
                    Sets.newHashSet(WORD_MUTA + WORD_SPLIT_COLON + ShowTime.class.getSimpleName() + WORD_SPLIT_COLON));
            kafkaVerticle.writeSet2Kafk(showkeys);

            Set<String> fkeys = redisService.maptoredis(forbiddenmap, true,
                    Sets.newHashSet(WORD_MUTA + WORD_SPLIT_COLON + Forbidden.class.getSimpleName() + WORD_SPLIT_COLON));
            kafkaVerticle.writeSet2Kafk(fkeys);

            Set<String> biddingPriceKeys = redisService.maptoredis(biddingPriceStrategymap, true,
                    Sets.newHashSet(WORD_MUTA + WORD_SPLIT_COLON + BiddingPriceStrategy.class.getSimpleName() + WORD_SPLIT_COLON));
            kafkaVerticle.writeSet2Kafk(biddingPriceKeys);

            Set<String> positionBiddingPriceKeys = redisService.maptoredis(positionbiddingPriceStrategymap, true,
                    Sets.newHashSet(WORD_MUTA + WORD_SPLIT_COLON + PositionBiddingPriceStrategy.class.getSimpleName() + WORD_SPLIT_COLON));
            kafkaVerticle.writeSet2Kafk(positionBiddingPriceKeys);

            Set<String> dspForBiddenkeys = redisService.maptoredis(dspForbiddenmap, true,
                    Sets.newHashSet(WORD_MUTA + WORD_SPLIT_COLON + DspForbidden.class.getSimpleName() + WORD_SPLIT_COLON));
            kafkaVerticle.writeSet2Kafk(dspForBiddenkeys);

            Set<String> dspRulekeys = redisService.maptoredis(dspRulemap, true,
                    Sets.newHashSet(WORD_MUTA + WORD_SPLIT_COLON + DspRule.class.getSimpleName() + WORD_SPLIT_COLON));
            kafkaVerticle.writeSet2Kafk(dspRulekeys);

            Set<String> appflowRatiokeys = redisService.maptoredis(appflowRatiomap, true,
                    Sets.newHashSet(WORD_MUTA + WORD_SPLIT_COLON + AppFlowRatio.class.getSimpleName() + WORD_SPLIT_COLON));
            kafkaVerticle.writeSet2Kafk(appflowRatiokeys);

            Set<String> statisticsEffectDatekeys = redisService.maptoredis(statisticsEffectDatemap, true,
                    Sets.newHashSet(WORD_MUTA + WORD_SPLIT_COLON + StatisticsEffectDate.class.getSimpleName() + WORD_SPLIT_COLON));
            kafkaVerticle.writeSet2Kafk(statisticsEffectDatekeys);

            Set<String> fromTypeStatisticsEffectDatekeys = redisService.maptoredis(fromTypeStatisticsEffectDatemap, true,
                    Sets.newHashSet(WORD_MUTA + WORD_SPLIT_COLON + FromTypeStatisticsEffectDate.class.getSimpleName() + WORD_SPLIT_COLON));
            kafkaVerticle.writeSet2Kafk(fromTypeStatisticsEffectDatekeys);

            Set<String> appVersionForbiddenkeys = redisService.maptoredis(appVersionForbiddenmap, true,
                    Sets.newHashSet(WORD_MUTA + WORD_SPLIT_COLON + AppVersionForbidden.class.getSimpleName() + WORD_SPLIT_COLON));
            kafkaVerticle.writeSet2Kafk(appVersionForbiddenkeys);

            Set<String> platformDefVariablekeys = redisService.maptoredis(platformDefVariablemap, true,
                    Sets.newHashSet(WORD_MUTA + WORD_SPLIT_COLON + PlatformDefVariable.class.getSimpleName() + WORD_SPLIT_COLON));
            kafkaVerticle.writeSet2Kafk(platformDefVariablekeys);

            Set<String> platformFromTypeVariablekeys = redisService.maptoredis(platformFromTypeVariablemap, true,
                    Sets.newHashSet(WORD_MUTA + WORD_SPLIT_COLON + PlatformFromTypeVariable.class.getSimpleName() + WORD_SPLIT_COLON));
            kafkaVerticle.writeSet2Kafk(platformFromTypeVariablekeys);

            Set<String> developerkeys = redisService.maptoredis(developermap, true,
                    Sets.newHashSet(WORD_MUTA + WORD_SPLIT_COLON + Developer.class.getSimpleName() + WORD_SPLIT_COLON));
            kafkaVerticle.writeSet2Kafk(developerkeys);
            Set<String> devConfigkeys = redisService.maptoredis(devConfiguremap, true,
                    Sets.newHashSet(WORD_MUTA + WORD_SPLIT_COLON + DevConfigure.class.getSimpleName() + WORD_SPLIT_COLON));
            kafkaVerticle.writeSet2Kafk(devConfigkeys);
            Set<String> coldBootConfigkeys = redisService.maptoredis(coldBootConfigmap, true,
                    Sets.newHashSet(WORD_MUTA + WORD_SPLIT_COLON + ColdBootConfig.class.getSimpleName() + WORD_SPLIT_COLON));
            kafkaVerticle.writeSet2Kafk(coldBootConfigkeys);

            Set<String> allianceFlowkeys = redisService.maptoredis(allianceFlowmap, true,
                    Sets.newHashSet(WORD_MUTA + WORD_SPLIT_COLON + AllianceFlow.class.getSimpleName() + WORD_SPLIT_COLON));
            kafkaVerticle.writeSet2Kafk(allianceFlowkeys);

            Set<String> mixFlowkeys = redisService.maptoredis(mixFlowMap, true,
                    Sets.newHashSet(WORD_MUTA + WORD_SPLIT_COLON + MixFlow.class.getSimpleName() + WORD_SPLIT_COLON));
            kafkaVerticle.writeSet2Kafk(mixFlowkeys);

            Set<String> mixAppVersionkeys = redisService.maptoredis(mixAppVersionMap, true,
                    Sets.newHashSet(WORD_MUTA + WORD_SPLIT_COLON + MixAppVersion.class.getSimpleName() + WORD_SPLIT_COLON));
            kafkaVerticle.writeSet2Kafk(mixAppVersionkeys);

            //setting的数据不需要存在数据库，只需要发一个kafka消息给config项目，去数据库查就可以
            kafkaVerticle.write2SdkKafkaNosleep("all");

            //同步广告位出价信息
            syncPlatformPrice(null, null);

            //同步平台算法分组信息
            platformFlowRatio(null, null);
            //同步app+平台算法分组信息
            appPlatformFlowRatio(null,null);
            //同步 {平台}_{广告位id} 频次控制
            allianceappPositionFlow(null,null);
            //同步 {平台}_{广告位id} 投放白名单数据
            positionMobileWhite(null,null);
            appOther(null,null);
            appPlatformtrans(null,null);
            //同步appver 黑名单信息
            Set<String> blackAppVers = new HashSet<>();
            blackAppVers.add("mutaEx:blackappver_for_clickmacro");
            kafkaVerticle.writeSet2Kafk(blackAppVers);
        });
    }

    private void pushallbydev(String dev) {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> adpositionmap = findAdpositions(null, SQLConstant.WHERE_IDS_ADPOSITION, dev);
            Map<String, Map<String, String>> appinfo = findAppInfo(null, dev);
            Map<String, Map<String, String>> wingsinfo = findWingsInfo(null, dev);
            Map<String, Map<String, String>> allowancemap = findAllowances(null, dev);
            Map<String, Map<String, String>> cpdCreativemap = findCpdCreatives(null, dev, SQLConstant.WHERE_IDS_CPDCREATIVE);
            Map<String, Map<String, String>> allianceAppInfomap = findAllianceAppInfo(null, SQLConstant.WHERE_ALLIANCEAPPINFO_APPID, dev);
            Map<String, Map<String, String>> showmap = findShowTime(Sets.newHashSet(dev), SQLConstant.WHERE_SHOWTIME_DEV);
            Map<String, Map<String, String>> forbiddenmap = findForbidden(dev);
            Map<String, Map<String, String>> biddingPriceStrategymap = findBiddingPriceStrategy(null, dev);
            Map<String, Map<String, String>> dspForbiddenmap = findDspForbidden(null, dev);
            Map<String, Map<String, String>> dspRulemap = findDspRule(null, dev);
            Map<String, Map<String, String>> appVersionForbiddenmap = findAppVersionForbidden(null, null);
            Map<String, Map<String, String>> developermap = findDeveloper(null, dev);
            Map<String, Map<String, String>> coldBootConfigmap = findAllColdBootConfig(null, dev);
            Map<String, Map<String, String>> allianceFlowmap = findAllianceFlow(null, dev);
            Map<String, Map<String, String>> mixFlowMap = findMixFlow(null, dev);
            Map<String, Map<String, String>> mixAppVersionMap = findMixAppVersion(null, dev);
            Map<String, Map<String, String>> positionbiddingPriceStrategymap = findPositionBiddingPriceStrategy(null);

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(adpositionmap));
                logger.info(JsonUtils.convert2Json(appinfo));
                logger.info(JsonUtils.convert2Json(wingsinfo));
                logger.info(JsonUtils.convert2Json(allowancemap));
                logger.info(JsonUtils.convert2Json(cpdCreativemap));
                logger.info(JsonUtils.convert2Json(allianceAppInfomap));
                logger.info(JsonUtils.convert2Json(showmap));
                logger.info(JsonUtils.convert2Json(forbiddenmap));
                logger.info(JsonUtils.convert2Json(biddingPriceStrategymap));
                logger.info(JsonUtils.convert2Json(dspForbiddenmap));
                logger.info(JsonUtils.convert2Json(dspRulemap));
                logger.info(JsonUtils.convert2Json(appVersionForbiddenmap));
                logger.info(JsonUtils.convert2Json(developermap));
                logger.info(JsonUtils.convert2Json(coldBootConfigmap));
                logger.info(JsonUtils.convert2Json(allianceFlowmap));
            }
            Set<String> appinfokeys = redisService.maptoredis(appinfo);
            kafkaVerticle.writeSet2Kafk(appinfokeys);

            Set<String> wingsinfokeys = redisService.maptoredis(wingsinfo);
            kafkaVerticle.writeSet2Kafk(wingsinfokeys);

            Set<String> adpositionkeys = redisService.maptoredis(adpositionmap);
            kafkaVerticle.writeSet2Kafk(adpositionkeys);

            Set<String> allowancekeys = redisService.maptoredis(allowancemap);
            kafkaVerticle.writeSet2Kafk(allowancekeys);

            Set<String> cpdCreativekeys = redisService.maptoredis(cpdCreativemap);
            kafkaVerticle.writeSet2Kafk(cpdCreativekeys);

            Set<String> allianceappkeys = redisService.maptoredis(allianceAppInfomap);
            kafkaVerticle.writeSet2Kafk(allianceappkeys);

            Set<String> showkeys = redisService.maptoredis(showmap);
            kafkaVerticle.writeSet2Kafk(showkeys);

            Set<String> fkeys = redisService.maptoredis(forbiddenmap);
            kafkaVerticle.writeSet2Kafk(fkeys);

            Set<String> biddingPricekeys = redisService.maptoredis(biddingPriceStrategymap);
            kafkaVerticle.writeSet2Kafk(biddingPricekeys);

            Set<String> dspForBiddenkeys = redisService.maptoredis(dspForbiddenmap);
            kafkaVerticle.writeSet2Kafk(dspForBiddenkeys);

            Set<String> dspRulekeys = redisService.maptoredis(dspRulemap);
            kafkaVerticle.writeSet2Kafk(dspRulekeys);

            Set<String> appVersionForbiddenkeys = redisService.maptoredis(appVersionForbiddenmap);
            kafkaVerticle.writeSet2Kafk(appVersionForbiddenkeys);

            Set<String> developerkeys = redisService.maptoredis(developermap);
            kafkaVerticle.writeSet2Kafk(developerkeys);

            Set<String> coldBootConfigkeys = redisService.maptoredis(coldBootConfigmap);
            kafkaVerticle.writeSet2Kafk(coldBootConfigkeys);

            Set<String> allianceFlowkeys = redisService.maptoredis(allianceFlowmap);
            kafkaVerticle.writeSet2Kafk(allianceFlowkeys);

            Set<String> mixFlowkeys = redisService.maptoredis(mixFlowMap);
            kafkaVerticle.writeSet2Kafk(mixFlowkeys);

            Set<String> mixAppVersionkeys = redisService.maptoredis(mixAppVersionMap);
            kafkaVerticle.writeSet2Kafk(mixAppVersionkeys);

            Set<String> positionbiddingPricekeys = redisService.maptoredis(positionbiddingPriceStrategymap);
            kafkaVerticle.writeSet2Kafk(positionbiddingPricekeys);
        });
    }

    private void adform(Set<String> formsids) {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findAdform(formsids);
            Map<String, Map<String, String>> showmap = findShowTime(formsids, SQLConstant.WHERE_SHOWTIME_ADFORM);

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(showmap));
                logger.info(JsonUtils.convert2Json(map));
            }
            Set<String> adformkeys = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(adformkeys);

            Set<String> showkeys = redisService.maptoredis(showmap);
            kafkaVerticle.writeSet2Kafk(showkeys);
        });
    }

    private void appinfo(Set<String> appids, String dev) {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findAppInfo(appids, dev);
            Map<String, Map<String, String>> admap = findAdpositions(appids, SQLConstant.WHERE_APPID_ADPOSITION, dev);
            Map<String, Map<String, String>> showmap = findShowTime(appids, SQLConstant.WHERE_SHOWTIME_APPINFO);

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(map));
                logger.info(JsonUtils.convert2Json(admap));
                logger.info(JsonUtils.convert2Json(showmap));
            }
            Set<String> appinfokeys = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(appinfokeys);

            Set<String> adpositionkeys = redisService.maptoredis(admap);
            kafkaVerticle.writeSet2Kafk(adpositionkeys);

            Set<String> showkeys = redisService.maptoredis(showmap);
            kafkaVerticle.writeSet2Kafk(showkeys);
        });
    }


    private void winginfo(Set<String> wingids, String dev) {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findWingsInfo(wingids, dev);
            Map<String, Map<String, String>> showmap = findShowTime(wingids, SQLConstant.WHERE_SHOWTIME_WINGSINFO);

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(map));
                logger.info(JsonUtils.convert2Json(showmap));
            }
            Set<String> wingsinfokeys = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(wingsinfokeys);

            Set<String> showkeys = redisService.maptoredis(showmap);
            kafkaVerticle.writeSet2Kafk(showkeys);
        });
    }

    private void adsize(Set<String> sizeids) {
        threadPoolExecutor.execute(() -> {
            List<Adform> adforms = mysqlService.findAdformByAdSize(sizeids);
            List<Object> objects = adforms.stream().collect(Collectors.toList());
            Map<String, Map<String, String>> map = listBean2MapMap(Adform.class, objects, "formId", "sizeId");
            Set<String> formsids = new HashSet<>();
            adforms.stream().map(adform -> adform.getFormId()).forEach(formsids::add);
            Map<String, Map<String, String>> showmap = null;

            if (formsids.size() > 0) {
                showmap = findShowTime(formsids, SQLConstant.WHERE_SHOWTIME_ADFORM);
            }

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(map));
                logger.info(JsonUtils.convert2Json(showmap));
            }
            Set<String> adformkeys = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(adformkeys);

            Set<String> showkeys = redisService.maptoredis(showmap);
            kafkaVerticle.writeSet2Kafk(showkeys);
        });
    }

    /**
     * 只更新广告位
     *
     * @param posids
     * @param WHERESQL
     * @param dev
     */
    private void adposition(Set<String> posids, String WHERESQL, String dev) {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findAdpositions(posids, WHERESQL, dev);

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(map));
            }
            Set<String> adpositionkeys = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(adpositionkeys);

        });
    }

    /**
     * 更新广告位同时更新广告位的索引
     *
     * @param posids
     * @param WHERESQL
     * @param dev
     */
    private void adposition(Set<String> posids, String WHERESQL, String SHOWTIMEWHERESQL, String dev) {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findAdpositions(posids, WHERESQL, dev);
            Map<String, Map<String, String>> showmap = findShowTime(posids, SHOWTIMEWHERESQL);

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(showmap));
                logger.info(JsonUtils.convert2Json(map));
            }
            Set<String> adpositionkeys = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(adpositionkeys);

            Set<String> showkeys = redisService.maptoredis(showmap);
            kafkaVerticle.writeSet2Kafk(showkeys);
        });
    }

    private void adpositionprice(Set<String> posids) {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findAdPositionPrice(posids);

            //特殊底价处理 muta:Adposition:posId:-1024
            //碰到这个逻辑，不更新这个广告位，更新环境变量的配置
            String defalutprice = "muta:Adposition:posId:-1024";
            if (map.containsKey(defalutprice)) {
                Map<String, String> valuemap = map.get(defalutprice);
                map.remove(defalutprice);
                map.put(REDIS_PREFIX_ENVIONMENT, valuemap);
            }

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(map));
            }
            Set<String> adpositonkeys = redisService.map4updateredis(map, true, Arrays.asList(REDIS_PREFIX_ENVIONMENT));
            kafkaVerticle.writeSet2Kafk(adpositonkeys);
        });
    }

    private void allowance(Set<String> allowanceids, String dev) {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findAllowances(allowanceids, dev);
            Map<String, Map<String, String>> showmap = findShowTime(allowanceids, SQLConstant.WHERE_SHOWTIME_ALLOWANCE);

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(showmap));
                logger.info(JsonUtils.convert2Json(map));
            }
            Set<String> allowancekeys = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(allowancekeys);

            Set<String> showkeys = redisService.maptoredis(showmap);
            kafkaVerticle.writeSet2Kafk(showkeys);

            List<String> ids = new ArrayList<>();
            map.entrySet().forEach(e -> {
                if (e.getValue().containsKey("appId")) {
                    ids.add(e.getValue().get("appId"));
                }
            });
            if (ids != null && ids.size() > 0) {
                kafkaVerticle.write2SdkKafkaNosleep("appid:" + StringUtils.join(ids, ","));
            }

        });
    }

    private void cpdcreative(Set<String> cids, String dev, String WHERESQL, String SHOWTIME_WHTERESQL) {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findCpdCreatives(cids, dev, WHERESQL);
            Map<String, Map<String, String>> showmap = findShowTime(cids, SHOWTIME_WHTERESQL);

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(showmap));
                logger.info(JsonUtils.convert2Json(map));
            }
            Set<String> cpdCreativekeys = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(cpdCreativekeys);

            Set<String> showkeys = redisService.maptoredis(showmap);
            kafkaVerticle.writeSet2Kafk(showkeys);
        });
    }

    private void sellplatform(Set<String> platformids) {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findSellPlatform(platformids);

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(map));
            }
            Set<String> sellPlatformskeys = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(sellPlatformskeys);
        });
    }

    private void allianceAppinfo(Set<String> ids, String WHERESQL, String dev) {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findAllianceAppInfo(ids, WHERESQL, dev);

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(map));
            }
            Set<String> allianceAppkeys = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(allianceAppkeys);
            if (WHERESQL.equals(SQLConstant.WHERE_ALLIANCEAPPINFO_APPID)) { //传的是id，要转成appid，则要发到config项目处理
                Set<String> appIds = new HashSet<>();
                map.entrySet().stream().forEach(entry -> {
                    if (entry.getValue().containsKey("appId") && StringUtils.isNotEmpty(entry.getValue().get("appId"))) {
                        appIds.add(entry.getValue().get("appId"));
                    }
                });
                if (CollectionUtils.isEmpty(appIds)) {
                    kafkaVerticle.write2SdkKafkaNosleep("appid");
                } else {
                    kafkaVerticle.write2SdkKafkaNosleep("appid:" + StringUtils.join(appIds, ","));
                }
            }
        });
    }

    /**
     * 推送不同sdk版本支持的网盟对象
     */
    private void sdkSupportAlliance(Set<String> ids) {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findSdkSupport(ids);

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(map));
            }
            Set<String> sdkkeys = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(sdkkeys);
        });
    }

    private void biddingPriceStrategy(Set<String> ids, String dev) {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findBiddingPriceStrategy(ids, dev);

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(map));
            }
            Set<String> biddingpricekey = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(biddingpricekey);
        });
    }

    private void positionBiddingPriceStrategy(Set<String> ids) {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> positionMap = findPositionBiddingPriceStrategy(ids);

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(positionMap));
            }
            Set<String> positionBiddingpricekey = redisService.maptoredis(positionMap);//位置相关的竞价策略也要放进redis
            kafkaVerticle.writeSet2Kafk(positionBiddingpricekey);
        });
    }


    /**
     * 更新权重
     *
     * @param ids
     */
    private void dspWeight(Set<String> ids) {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findDspWeight(ids);

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(map));
            }
            Set<String> sdkkeys = redisService.map4updateredis(map, false, null);
            kafkaVerticle.writeSet2Kafk(sdkkeys);
        });
    }

    /**
     * 更新禁投行业的数据
     *
     * @param devid
     */
    private void forbidden(String devid) {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findForbidden(devid);

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(map));
            }
            Set<String> fkeys = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(map.keySet());
        });
    }


    /**
     * 更新DSP禁投行业的数据
     *
     * @param ids
     * @param dev
     */
    private void dspForbidden(Set<String> ids, String dev) {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findDspForbidden(ids, dev);

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(map));
            }
            Set<String> fkeys = redisService.maptoredis(map, true,
                    Sets.newHashSet(WORD_MUTA + WORD_SPLIT_COLON + DspForbidden.class.getSimpleName() + WORD_SPLIT_COLON));
            kafkaVerticle.writeSet2Kafk(map.keySet());
        });
    }

    /**
     * 将任务提交给线程池处理，之前重复方法太多，提取出来
     *
     * @param ids
     * @param dev
     * @param action
     */
    private void applyTask(Set<String> ids, String dev, BiConsumer<Set<String>, String> action) {
        threadPoolExecutor.execute(() -> {
            action.accept(ids, dev);
        });
    }

    private void appFlowRatio() {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findAppFlowRatio();

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(map));
            }
            Set<String> fkeys = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(map.keySet());
        });
    }

    private void statisticsEffectDate() {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findStatisticsEffectDate();

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(map));
            }
            Set<String> fkeys = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(map.keySet());
        });
    }

    private void fromTypeStatisticsEffectDate() {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findFromTypeStatisticsEffectDate();

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(map));
            }
            Set<String> fkeys = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(map.keySet());
        });
    }

    /**
     * 更新devconfig表的数据
     */
    private void devConfigInfo() {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findAllDevConfigure(null, null, null);

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(map));
            }
            Set<String> fkeys = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(map.keySet());
        });
    }

    /**
     * 更新setting表的数据
     */
    private void settingInfo() {
        threadPoolExecutor.execute(() -> {
            kafkaVerticle.write2SdkKafkaNosleep("setting");
        });
    }

    /**
     * 更新devconfig表的数据
     */
    private void coldBootInfo(Set<String> ids, String dev) {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findAllColdBootConfig(ids, dev);

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(map));
            }
            Set<String> fkeys = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(map.keySet());
        });
    }

    /**
     * 更新DSP禁投行业的数据
     *
     * @param ids
     * @param dev
     */
    private void dspRule(Set<String> ids, String dev) {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findDspRule(ids, dev);

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(map));
            }
            Set<String> fkeys = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(map.keySet());
        });
    }

    /**
     * 更新app版本禁投的数据
     *
     * @param ids app_version_rule表的id
     */
    private void appVersionForbidden(Set<String> ids, String dev) {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findAppVersionForbidden(ids, dev);

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(map));
            }
            Set<String> fkeys = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(map.keySet());
        });
    }

    /**
     * 更新平台变量
     */
    private void platformDefVariable(Set<String> ids, String dev) {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findPlatformDefVariable(ids, dev);

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(map));
            }
            Set<String> fkeys = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(map.keySet());
        });
    }

    /**
     * 更新平台广告类别变量
     */
    private void platformFromTypeVariable(Set<String> ftIds, Set<String> defIds) {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findPlatformFromTypeVariable(ftIds, defIds);
            if (debugredis) {
                logger.info(JsonUtils.convert2Json(map));
            }
            Set<String> fkeys = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(map.keySet());
        });
    }

    /**
     * 更新devconfig表的数据
     */
    private void allianceFlow(Set<String> ids, String dev) {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findAllianceFlow(ids, dev);

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(map));
            }
            Set<String> fkeys = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(map.keySet());
        });
    }

    /**
     * 更新混量
     */
    private void mixFlowInfo(Set<String> ids, String dev) {

        Map<String, Map<String, String>> map = findMixFlow(ids, dev);

        if (debugredis) {
            logger.info(JsonUtils.convert2Json(map));
        }
        Set<String> fkeys = redisService.maptoredis(map);
        kafkaVerticle.writeSet2Kafk(map.keySet());

    }

    /**
     * 更新混量APP版本
     */
    private void mixAppVersionInfo(Set<String> ids, String dev) {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findMixAppVersion(ids, dev);

            if (debugredis) {
                logger.info(JsonUtils.convert2Json(map));
            }
            Set<String> fkeys = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(map.keySet());
        });
    }

    /**
     * @param ids
     * @param dev
     */
    private void platformFlowRatio(Set<String> ids, String dev) {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findPlatformFlowRatio(ids, dev);
            Set<String> fkeys = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(map.keySet());
        });
    }

    public Map<String, Map<String, String>> listBean2MapMap(Class<?> clazz, List<Object> objlist, String... filed) {
        return listBean2MapMap(clazz.getSimpleName(), objlist, filed);
    }

    /**
     * 统一转换list为map
     *
     * @param objlist list对象
     * @param filed   以哪个字段为map的key,多个以:分隔
     * @return
     * @throws Exception
     */
    public Map<String, Map<String, String>> listBean2MapMap(String primaryKey, List<Object> objlist, String... filed) {
        Map<String, Map<String, String>> finalmap = new HashMap<>();

        for (Object object : objlist) {
            try {
                Map<String, String> beanmap = BeanUtil.bean2Map(object);
                List<String> mapkeylist = new ArrayList<>();

                for (String fd : filed) {
                    mapkeylist.add(fd + WORD_SPLIT_COLON + beanmap.get(fd));
                }

                if (mapkeylist.size() > 0) {
                    finalmap.put(WORD_MUTA + WORD_SPLIT_COLON + primaryKey + WORD_SPLIT_COLON + Joiner.on(WORD_SPLIT_COLON).join(mapkeylist), beanmap);
                }
            } catch (Exception e) {
                continue;
            }
        }

        return finalmap;
    }

    private Map<String, Map<String, String>> findAdform(Set<String> ids) {
        List<Adform> adforms = mysqlService.findAdform(ids);
        List<Object> adformsObj = adforms.stream().collect(Collectors.toList());
        return listBean2MapMap(Adform.class, adformsObj, "formId", "sizeId");
    }

    private Map<String, Map<String, String>> findAppInfo(Set<String> ids, String dev) {
        List<AppInfo> appInfos = mysqlService.findAppInfo(ids, dev);
        List<Object> appInfoObj = appInfos.stream().collect(Collectors.toList());
        return listBean2MapMap(AppInfo.class, appInfoObj, "appId");
    }

    private Map<String, Map<String, String>> findWingsInfo(Set<String> ids, String dev) {
        List<WingsInfo> wingsInfos = mysqlService.findWingsInfo(ids, dev);
        List<Object> wingsInfoObj = wingsInfos.stream().collect(Collectors.toList());
        return listBean2MapMap(WingsInfo.class, wingsInfoObj, "id");
    }

    private Map<String, Map<String, String>> findAdpositions(Set<String> ids, String WHERESQL, String dev) {
        List<Adposition> adpositions = mysqlService.findAdposition(ids, WHERESQL, dev);
        List<Object> adpositionObj = adpositions.stream().collect(Collectors.toList());
        Map<String, Map<String, String>> map = listBean2MapMap(Adposition.class, adpositionObj, "posId");
//        kafkalogger.info("adposition:"+JsonUtils.convert2Json(map));
        return map;
    }

    private Map<String, Map<String, String>> findAllowances(Set<String> ids, String dev) {
        List<Allowance> allowances = mysqlService.findAllowance(ids, dev);
        List<Object> allowancesObj = allowances.stream().collect(Collectors.toList());
        return listBean2MapMap(Allowance.class, allowancesObj, "allowanceId");
    }

    private Map<String, Map<String, String>> findCpdCreatives(Set<String> ids, String dev, String SQL) {
        List<CpdCreative> cpdCreatives = mysqlService.findCpdCreative(ids, dev, SQL);
        List<Object> cpdCreativeObj = cpdCreatives.stream().collect(Collectors.toList());
        return listBean2MapMap(CpdCreative.class, cpdCreativeObj, "creativeId");
    }

    private Map<String, Map<String, String>> findSellPlatform(Set<String> ids) {
        List<SellPlatform> sellPlatforms = mysqlService.findSellPlatform(ids);
        String nowDate = DateTimeUtils.getDateFlag();
        sellPlatforms.stream().forEach(s -> {
            if (ObjectUtils.isNotEmpty(s.getTrafficLimitDay()) && s.getTrafficLimitDay() > 0) {
                redisClusterService.expAtSet("muta:limitDay:" + nowDate + ":platform:" + s.getPlatformId(), s.getTrafficLimitDay().toString(), DateTimeUtils.getTomorrow1amUnixTime());
                s.setLimitDayStatus(1);
            } else {
                s.setLimitDayStatus(0);
            }
            s.setTrafficLimitDay(null);
        });
        List<Object> sellPlatformsObj = sellPlatforms.stream().collect(Collectors.toList());

        return listBean2MapMap(SellPlatform.class, sellPlatformsObj, "platformId");
    }

    private Map<String, Map<String, String>> findAllDevConfigure(String devId, String appId, String positionId) {
        List<DevConfigure> devConfigures = mysqlService.findDevConfig(devId, appId, positionId);
        List<Object> devConfiguresObj = devConfigures.stream().collect(Collectors.toList());
        return listBean2MapMap(DevConfigure.class, devConfiguresObj, "devConfigId");
    }

    private Map<String, Map<String, String>> findAllColdBootConfig(Set<String> ids, String dev) {
        List<ColdBootConfig> coldbootConfigures = mysqlService.findColdBootConfig(ids, dev);
        List<Object> coldBootConfigObj = coldbootConfigures.stream().collect(Collectors.toList());
        return listBean2MapMap(ColdBootConfig.class, coldBootConfigObj, "posId");
    }

    private Map<String, Map<String, String>> findShowTime(Set<String> ids, String WHERESQL) {
        List<ShowTime> showtime = mysqlService.findShowTime(ids, WHERESQL);
        List<Object> objects = showtime.stream().collect(Collectors.toList());
        return listBean2MapMap(ShowTime.class, objects, "posId");
    }

    private Map<String, Map<String, String>> findAllianceAppInfo(Set<String> ids, String WHERESQL, String dev) {
        List<AllianceAppInfo> allianceAppInfos = mysqlService.findAllianceAppInfo(ids, WHERESQL, dev);
        List<Object> objects = allianceAppInfos.stream().collect(Collectors.toList());
        return listBean2MapMap(AllianceAppInfo.class, objects, "posId", "platformId");
    }

    private Map<String, Map<String, String>> findSdkSupport(Set<String> ids) {
        Map<String, String> sdkSupports = mysqlService.findSdkSupport(ids);
        Map<String, Map<String, String>> toredismap = new HashMap<>();
        toredismap.put(REDIS_PREFIX_SDKSUPPORT, sdkSupports);
        return toredismap;
    }

    private Map<String, Map<String, String>> findBiddingPriceStrategy(Set<String> ids, String dev) {
        List<BiddingPriceStrategy> biddingPriceMap = mysqlService.findBiddingPriceStrategy(ids, dev);
        List<Object> objects = biddingPriceMap.stream().collect(Collectors.toList());
        return listBean2MapMap(BiddingPriceStrategy.class, objects, "appId", "type");
    }

    private Map<String, Map<String, String>> findPositionBiddingPriceStrategy(Set<String> ids) {
        List<PositionBiddingPriceStrategy> positionBiddingPriceMap = mysqlService.findPositionBiddingPriceStrategy(ids);
        List<Object> objects = positionBiddingPriceMap.stream().collect(Collectors.toList());
        return listBean2MapMap(PositionBiddingPriceStrategy.class, objects, "posId", "platformId");
    }

    private Map<String, Map<String, String>> findDspWeight(Set<String> ids) {
        List<DspWeight> dspWeights = mysqlService.findDspWeight(ids);
        Map<String, Map<String, String>> toredismap = new HashMap<>();

        if (dspWeights != null && dspWeights.size() > 0) {
            dspWeights.forEach(dspWeight -> {
                String redisWeightKey = REDIS_PREFIX_WEIGHT + dspWeight.getPositionId();
                if (!toredismap.containsKey(redisWeightKey)) {
                    Map<String, String> redisweightvalue = new HashMap<>();
                    toredismap.put(redisWeightKey, redisweightvalue);
                }
                toredismap.get(redisWeightKey).put(dspWeight.getPlatformId(), dspWeight.getSellper());
            });
        }

        return toredismap;
    }

    private Map<String, Map<String, String>> findAdPositionPrice(Set<String> ids) {
        List<AdpositionPrice> adpositionPrices = mysqlService.findAdpositionPrice(ids);
        Map<String, Map<String, String>> toredismap = new HashMap<>();

        if (adpositionPrices != null && adpositionPrices.size() > 0) {
            adpositionPrices.forEach(adpositionPrice -> {
                String redisAdpositionKey = REDIS_PREFIX_ADPOSITIONPRICE + adpositionPrice.getPosId();

                if (!toredismap.containsKey(redisAdpositionKey)) {
                    Map<String, String> redisweightvalue = new HashMap<>();
                    toredismap.put(redisAdpositionKey, redisweightvalue);
                }
                toredismap.get(redisAdpositionKey).put("bottomPrice", adpositionPrice.getBottomPrice());
            });
        }

        return toredismap;
    }

    private Map<String, Map<String, String>> findForbidden(String devids) {
        Map<String, Map<String, String>> forbiddenMap = new HashMap<>();
        List<Forbidden> forbiddens = mysqlService.findForbidden(devids);
        String primeKey = new StringBuffer(WORD_MUTA)
                .append(WORD_SPLIT_COLON)
                .append(Forbidden.class.getSimpleName())
                .append(WORD_SPLIT_COLON)
                .append("developerId")
                .append(WORD_SPLIT_COLON).toString();

        forbiddens.forEach(forbidden -> {
            String rediskey = primeKey + forbidden.getDeveloperId();
            if (!forbiddenMap.containsKey(rediskey)) {
                forbiddenMap.put(rediskey, new HashMap<>());
            }
            forbiddenMap.get(rediskey).put(forbidden.getPlatformName(), forbidden.getTradeValue());
        });

        return forbiddenMap;
    }

    private Map<String, Map<String, String>> findDspForbidden(Set<String> ids, String dev) {
        List<DspForbidden> dspForbiddens = mysqlService.findDspForbidden(ids, dev);
        List<Object> objectList = dspForbiddens.stream().collect(Collectors.toList());
        return listBean2MapMap(DspForbidden.class, objectList, "platformId", "appId", "id");
    }

    private Map<String, Map<String, String>> findDspRule(Set<String> ids, String dev) {
        List<DspRule> dspRules = mysqlService.findDspRule(ids, dev);
        List<Object> objectList = dspRules.stream().collect(Collectors.toList());
        return listBean2MapMap(DspRule.class, objectList, "posId", "conId");
    }

    private Map<String, Map<String, String>> findAppFlowRatio() {
        List<AppFlowRatio> appFlowRatios = mysqlService.findAppFlowRatio();
        List<Object> objectList = appFlowRatios.stream().collect(Collectors.toList());
        return listBean2MapMap(AppFlowRatio.class, objectList, "appId");
    }

    private Map<String, Map<String, String>> findStatisticsEffectDate() {
        List<StatisticsEffectDate> statisticsEffectDates = mysqlService.findStatisticsEffectDate();
        List<Object> objectList = statisticsEffectDates.stream().collect(Collectors.toList());
        return listBean2MapMap(StatisticsEffectDate.class, objectList, "positionId", "platformId");
    }

    private Map<String, Map<String, String>> findFromTypeStatisticsEffectDate() {
        List<FromTypeStatisticsEffectDate> fromTypeStatisticsEffectDate = mysqlService.findFromTypeStatisticsEffectDate();
        List<Object> objectList = fromTypeStatisticsEffectDate.stream().collect(Collectors.toList());
        return listBean2MapMap(FromTypeStatisticsEffectDate.class, objectList, "platformId", "formType");
    }

    private Map<String, Map<String, String>> findAppVersionForbidden(Set<String> ids, String dev) {
        //按照开发者更新内容的需求，如果以后有需求，再做
        List<AppVersionForbidden> appVersionForbiddens = mysqlService.findAppVersionForbidden(ids);
        List<Object> objectList = appVersionForbiddens.stream().collect(Collectors.toList());
        return listBean2MapMap(AppVersionForbidden.class, objectList, "rtype", "businessId", "rid");
    }

    private Map<String, Map<String, String>> findPlatformDefVariable(Set<String> ids, String dev) {
        List<PlatformDefVariable> platformDefVariables = mysqlService.findPlatformDefVariable(ids);
        List<Object> objectList = platformDefVariables.stream().collect(Collectors.toList());
        return listBean2MapMap(PlatformDefVariable.class, objectList, "platformId", "os", "vName");
    }

    private Map<String, Map<String, String>> findPlatformFromTypeVariable(Set<String> ids, Set<String> pareIds) {
        List<PlatformFromTypeVariable> platformDefVariables = mysqlService.findPlatformFromTypeVariable(ids, pareIds);
        List<Object> objectList = platformDefVariables.stream().collect(Collectors.toList());
        return listBean2MapMap(PlatformFromTypeVariable.class, objectList, "platformId", "os", "formType", "style", "vName");
    }

    private Map<String, Map<String, String>> findDeveloper(Set<String> ids, String dev) {
        List<Developer> apiCustomers = mysqlService.findDeveloper(ids, dev);
        List<Object> objectList = apiCustomers.stream().collect(Collectors.toList());
        return listBean2MapMap(Developer.class, objectList, "devId");
    }

    private Map<String, Map<String, String>> findAllianceFlow(Set<String> ids, String dev) {
        List<AllianceFlow> apiCustomers = mysqlService.findAllianceFlow(ids, dev);
        List<Object> objectList = apiCustomers.stream().collect(Collectors.toList());
        return listBean2MapMap(AllianceFlow.class, objectList, "appId", "positionId", "platformId");
    }

    private Map<String, Map<String, String>> findMixFlow(Set<String> ids, String dev) {
        List<MixFlow> mixFlows = mysqlService.findMixFlow(ids, dev);
        List<Object> objectList =  new ArrayList<>();
        //有相同的 平台id_广告位id 的数据 状态是被删除的，bean转map 保证状态为0的要覆盖状态为非0的
        objectList.addAll(mixFlows.stream().filter(v->!v.getStatus().equalsIgnoreCase("0")).collect(Collectors.toList()));
        objectList.addAll(mixFlows.stream().filter(v->v.getStatus().equalsIgnoreCase("0")).collect(Collectors.toList()));
        return listBean2MapMap(MixFlow.class, objectList, "platformId", "origAdId", "mixAdId");
    }

    private Map<String, Map<String, String>> findMixAppVersion(Set<String> ids, String dev) {
        List<MixAppVersion> mixAppVersions = mysqlService.findMixAppVersion(ids, dev);
        List<Object> objectList = mixAppVersions.stream().collect(Collectors.toList());
        return listBean2MapMap(MixAppVersion.class, objectList, "appId");
    }

    private Map<String, Map<String, String>> findPlatformFlowRatio(Set<String> ids, String dev) {
        List<PlatformFlowRatio> platformFlowRatios = mysqlService.findPlatformFlowRatio(ids, dev);
        if (CollectionUtils.isNotEmpty(platformFlowRatios)) {
            //分组处理 多条记录合并为一条存储
            Map<String, List<PlatformFlowRatio>> groupMap = platformFlowRatios.stream().collect(Collectors.groupingBy(PlatformFlowRatio::getPlatformId));
            List<Object> objectList = new ArrayList<>();
            for (String key : groupMap.keySet()) {
                PlatformFlowRatioConfig ratioConfig = new PlatformFlowRatioConfig();
                ratioConfig.setPlatformId(key);
                List<PlatformFlowRatio> values = groupMap.get(key);
                StringBuilder stringBuilder = new StringBuilder();
                for (PlatformFlowRatio ratio : values) {
                    if ("0".equals(ratio.getStatus())) {
                        stringBuilder.append(String.format("%s;%s,", ratio.getAlgoGroup(), ratio.getFlowRatio()));
                    }
                }
                String config = stringBuilder.toString();
                if (StringUtils.isNotEmpty(stringBuilder.toString())) {
                    ratioConfig.setConfig(config.substring(0, config.length() - 1));
                    ratioConfig.setStatus("0");
                } else {
                    ratioConfig.setStatus("-1");
                }
                objectList.add(ratioConfig);
            }
            return listBean2MapMap(PlatformFlowRatio.class, objectList, "platformId");
        } else {
            return null;
        }
    }
    /**
     *
     * @param ids
     * @param dev
     */
    private void appPlatformFlowRatio(Set<String> ids, String dev) {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findAppPlatformFlowRatio(ids, dev);
            Set<String> fkeys = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(map.keySet());
        });
    }

    /**
     *
     * {平台}_{广告位} 频次控制
     * @param ids
     * @param dev
     */
    private void allianceappPositionFlow(Set<String> ids, String dev) {
        threadPoolExecutor.execute(() -> {
            Map<String, Map<String, String>> map = findAllianceappPositionFlow(ids, dev);
            Set<String> fkeys = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(map.keySet());
        });
    }

    /**
     * app+平台配置流量策略
     * @param ids
     * @param dev
     * @return
     */
    private Map<String, Map<String, String>> findAppPlatformFlowRatio(Set<String> ids, String dev) {
        List<AppPlatformFlowRatio> appPlatformFlowRatios = mysqlService.findAppPlatformFlowRatio(ids, dev);
        if(CollectionUtils.isNotEmpty(appPlatformFlowRatios)){
            //分组处理 多条记录合并为一条存储
            Map<String,List<AppPlatformFlowRatio>> groupMap = appPlatformFlowRatios.stream().collect(Collectors.groupingBy(v->v.getAppId()+"_" + v.getPlatformId()));
            List<Object> objectList = new ArrayList<>();
            for(String key : groupMap.keySet()){
                String[] keys = key.split("_");
                PlatformFlowRatioConfig ratioConfig = new PlatformFlowRatioConfig();
                ratioConfig.setAppId(keys[0]);
                ratioConfig.setPlatformId(keys[1]);
                List<AppPlatformFlowRatio> values = groupMap.get(key);
                StringBuilder stringBuilder = new StringBuilder();
                for(AppPlatformFlowRatio ratio:values){
                    if("0".equals(ratio.getStatus())){
                        stringBuilder.append(String.format("%s;%s,",ratio.getAlgoGroup(),ratio.getFlowRatio()));
                    }
                }
                String config = stringBuilder.toString();
                if(StringUtils.isNotEmpty(config)){
                    ratioConfig.setConfig(config.substring(0,config.length()-1));
                    ratioConfig.setStatus("0");
                }else{
                    ratioConfig.setStatus("-1");
                }
                objectList.add(ratioConfig);
            }
            return listBean2MapMap(AppPlatformFlowRatio.class, objectList, "appId","platformId");
        }else{
            return null;
        }
    }

    /**
     * 平台+广告位的频次控制
     * @param ids
     * @param dev
     * @return
     */
    private Map<String, Map<String, String>> findAllianceappPositionFlow(Set<String> ids, String dev) {
        List<AllianceappPositionFlow> allianceappPositionFlows = mysqlService.findAllianceappPositionFlow(ids,dev);
        List<Object> objectList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(allianceappPositionFlows)){
            //有相同的 平台id_广告位id 的数据 状态是被删除的，bean转map 保证状态为0的要覆盖状态为非0的
            objectList.addAll(allianceappPositionFlows.stream().filter(v->!v.status.equalsIgnoreCase("0")).collect(Collectors.toList()));
            objectList.addAll(allianceappPositionFlows.stream().filter(v->v.status.equalsIgnoreCase("0")).collect(Collectors.toList()));
            return listBean2MapMap(AllianceappPositionFlow.class, objectList, "platformId","positionId");
        }
        return listBean2MapMap(AllianceappPositionFlow.class, objectList, "platformId","positionId");
    }

    /**
     * 网盟广告位价格修改后需要同步价格信息
     *
     * @param ids
     * @param dev
     * @return
     */
    private void syncPlatformPrice(Set<String> ids, String dev) {
        //status 是0正常 -1删除 1停用
        //价格单位元*100 换算为分
        String className = PlatformPrice.class.getSimpleName();
        String sql = "SELECT a.id,a.platform_id,a.position_id as alliance_position_id,a.form_type,a.price*100 as `price`,a.`status`,b.position_id as position_id FROM platform_price a inner join alliance_position_allocation b on a.platform_id = b.platform_id and a.position_id = b.alliance_position_id";
        List<PlatformPrice> ret = mysqlService.find(sql, PlatformPrice.class, null);
        if (ret != null) {
            List<Object> objectList = ret.stream().collect(Collectors.toList());
            Map<String, Map<String, String>> infos = listBean2MapMap(className, objectList, "platform_id", "position_id");
            //状态为0的更新入db
            //将整个结构删掉 再更新
            Map<String, Map<String, String>> infosOk = new HashMap<>();
            for (Map.Entry<String, Map<String, String>> v : infos.entrySet()) {
                if (v.getValue().get("status") != null && "0".equals(v.getValue().get("status"))) {
                    infosOk.put(v.getKey(), v.getValue());
                }
            }
            Set<String> fkeys = redisService.maptoredis(infosOk, true, Sets.newHashSet(WORD_MUTA + WORD_SPLIT_COLON + className + WORD_SPLIT_COLON));
            //指定了id 同步muta 部分更新
            if (ids != null) {
                //部分通知
                Set<String> kySet = new HashSet<>();
                for (Map.Entry<String, Map<String, String>> v : infos.entrySet()) {
                    if (v.getValue().get("id") != null && ids.contains((v.getValue().get("id")))) {
                        kySet.add(v.getKey());
                    }
                }
                kafkaVerticle.writeSet2Kafk(kySet);
                logger.info("platfromprice 增量更新");
                for (String str : kySet) {
                    logger.info("update platformprice,key={}", str);
                }
            } else {
                kafkaVerticle.writeSet2Kafk(infos.keySet());
                logger.info("platformprice 全量更新");
            }
        } else {
            String idsStr = ids != null ? String.join(",", ids) : "";
            logger.error(String.format("platformprice is null,sql=%s&ids=%s", sql, String.join("", idsStr)));
        }
    }

    /**
     * 平台广告位 广告形式转换
     *
     * @param ids
     * @param dev
     */
    private void platformPositionAdFormSwitch(Set<String> ids, String dev) {
        List<AdFormConvert> appPlatformFlowRatios = mysqlService.findPlatformPositionAdFormConvert(ids, dev);
        if (appPlatformFlowRatios != null) {
            List<Object> objectList = appPlatformFlowRatios.stream().collect(Collectors.toList());
            Map<String, Map<String, String>> infos = listBean2MapMap(AdFormConvert.class, objectList, "platformId", "positionId");
            Set<String> fkeys = redisService.maptoredis(infos);
            kafkaVerticle.writeSet2Kafk(infos.keySet());
        }
    }

    /**
     * 同步平台_位置 投放白名单 数据
     * @param ids
     * @param dev
     */
    private void positionMobileWhite(Set<String> ids, String dev) {
        threadPoolExecutor.execute(() -> {
            List<PositionMobileWhite> positionMobileWhites = mysqlService.find(PositionMobileWhite.class,ids,dev);
            if(CollectionUtils.isNotEmpty(positionMobileWhites)){
                List<Object> objectList =  new ArrayList<>();
                //有相同的 平台id_广告位id 的数据 状态是被删除的，bean转map 保证状态为0的要覆盖状态为非0的
                objectList.addAll(positionMobileWhites.stream().filter(v->!v.getStatus().equalsIgnoreCase("0")).collect(Collectors.toList()));
                objectList.addAll(positionMobileWhites.stream().filter(v->v.getStatus().equalsIgnoreCase("0")).collect(Collectors.toList()));
                Map<String, Map<String, String>> map = listBean2MapMap(PositionMobileWhite.class, objectList, "platformId", "positionId");
                Set<String> fkeys = redisService.maptoredis(map);
                kafkaVerticle.writeSet2Kafk(map.keySet());
            }
        });
    }
    private void appOther(Set<String> ids, String dev) {
        List<AppOther> appOthers = mysqlService.find(AppOther.class,ids,dev);
        if(CollectionUtils.isNotEmpty(appOthers)){
            List<Object> objectList =  new ArrayList<>();
            //有相同的 平台id_广告位id 的数据 状态是被删除的，bean转map 保证状态为0的要覆盖状态为非0的
            objectList.addAll(appOthers.stream().filter(v->!v.getStatus().equalsIgnoreCase("0")).collect(Collectors.toList()));
            objectList.addAll(appOthers.stream().filter(v->v.getStatus().equalsIgnoreCase("0")).collect(Collectors.toList()));
            Map<String, Map<String, String>> map = listBean2MapMap(AppOther.class, objectList, "os", "packageName");
            Set<String> fkeys = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(map.keySet());
        }
    }

    private void appPlatformtrans(Set<String> ids, String dev) {
        List<AppPlatformtrans> appPlatformtrans = mysqlService.find(AppPlatformtrans.class,ids,dev);
        if(CollectionUtils.isNotEmpty(appPlatformtrans)){
            List<Object> objectList =  new ArrayList<>();
            //有相同的 平台id_广告位id 的数据 状态是被删除的，bean转map 保证状态为0的要覆盖状态为非0的
            objectList.addAll(appPlatformtrans.stream().filter(v->!v.getStatus().equalsIgnoreCase("0")).collect(Collectors.toList()));
            objectList.addAll(appPlatformtrans.stream().filter(v->v.getStatus().equalsIgnoreCase("0")).collect(Collectors.toList()));
            Map<String, Map<String, String>> map = listBean2MapMap(AppPlatformtrans.class, objectList, "appId", "platformId");
            Set<String> fkeys = redisService.maptoredis(map);
            kafkaVerticle.writeSet2Kafk(map.keySet());
        }
    }

}
