package com.ifeng.ad.mutacenter.common.scheduled;

import com.google.common.collect.Sets;
import com.google.common.eventbus.AsyncEventBus;
import com.ifeng.ad.mutacenter.common.bean.mysqlmap.AppInfo;
import com.ifeng.ad.mutacenter.common.bean.mysqlmap.SellPlatform;
import com.ifeng.ad.mutacenter.common.geventbus.bean.ReqObj;
import com.ifeng.ad.mutacenter.common.mail.MailMessageUtil;
import com.ifeng.ad.mutacenter.common.utils.DateTimeUtils;
import com.ifeng.ad.mutacenter.common.utils.RandomUtils;
import com.ifeng.ad.mutacenter.common.utils.StringUtils;
import com.ifeng.ad.mutacenter.service.MysqlService;
import com.ifeng.ad.mutacenter.service.RedisService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by  on 2019/2/12.
 * 定时任务
 */
@Component
public class AutoRunTask {
    private static final Logger log = LoggerFactory.getLogger(AutoRunTask.class);

    @Autowired
    private AsyncEventBus guavaAsyncEventBus;

    @Autowired
    private MailMessageUtil mailMessageUtil;

    @Autowired
    private RedisService redisService;

    @Resource(name = "redisServiceEx")
    private RedisService redisServiceEx;

    @Autowired
    private MysqlService mysqlService;

    @Scheduled(cron="0 0 0 * * ?")
    private void process() throws Exception{
        log.info("自动任务刷新全部数据 :" + LocalTime.now());
        ReqObj reqObj = ReqObj.create().
                setDev("0").setType("0").setIds(Sets.newHashSet("0"));
        guavaAsyncEventBus.post(reqObj);
    }

    @Scheduled(cron="0 0 5 * * ?")
    private void process2() throws Exception{
        log.info("自动任务刷新【22,23,28】的数据 :" + LocalTime.now());
        ReqObj reqObj22 = ReqObj.create().
                setDev("0").setType("22").setIds(Sets.newHashSet("0"));
        guavaAsyncEventBus.post(reqObj22);

        ReqObj reqObj23 = ReqObj.create().
                setDev("0").setType("23").setIds(Sets.newHashSet("0"));
        guavaAsyncEventBus.post(reqObj23);

        ReqObj reqObj28 = ReqObj.create().
                setDev("0").setType("28").setIds(Sets.newHashSet("0"));
        guavaAsyncEventBus.post(reqObj28);
    }

    @Scheduled(cron="0 30 7 * * ?")
    private void process3() throws Exception{
        log.info("自动任务刷新【43】的数据 :" + LocalTime.now());
        ReqObj reqObj43 = ReqObj.create().
                setDev("0").setType("43").setIds(Sets.newHashSet("0"));
        guavaAsyncEventBus.post(reqObj43);
    }

    /**
     * 用户黑名单 邮件功能
     * @throws Exception
     */
    @Scheduled(cron="0 0 8 * * ?")
    private void process4() throws Exception{
        log.info("扫描用户黑名单 :" + LocalTime.now());
        long t1 = System.currentTimeMillis();
        long t2 = System.currentTimeMillis() - 1000*60*60*24;
        List<String> keys = redisServiceEx.allkeys("mutaEx:UserBlackList:*");
        //黑名单信息 key {appid}_{adid}
        Map<String,String> info = new HashMap<>();
        Map<String,Integer> infoCount = new HashMap<>();
        if(CollectionUtils.isNotEmpty(keys)){
            keys.forEach(v->{
                Map<String,String> value = redisServiceEx.hgetAll(v);
                if(value!=null){
                    String timestamp = value.get("timestamp");
                    //只取最近24小时的数据
                    if(!(StringUtils.isNotEmpty(timestamp) && t2 < Long.parseLong(timestamp) && t1 > Long.parseLong(timestamp))){
                        return;
                    }
                    String appid = value.get("aid");
                    String adid = value.get("adid");
                    String key = String.format("%s_%s",appid,adid);
                    String hv = String.format("header_key %s header_value %s",value.get("header_key"),value.get("header_value"));
                    if(!info.containsKey(key)){
                        info.put(key,hv+"\r\n");
                        infoCount.put(key,1);
                    }else{
                        String str = info.get(key);
                        if(!(str.indexOf(hv)>-1)){
                            str = str + hv + "\r\n";
                            info.put(key,str);
                        }
                        int tmp = infoCount.get(key);
                        infoCount.put(key,tmp+1);
                    }
                }
            });
            TreeMap<Integer,String> tMap = new TreeMap<>();

            StringBuilder stringBuilder = new StringBuilder();
            info.keySet().forEach(key->{
                String tmpStr = info.get(key).length() > 100 ? info.get(key).substring(0,100): info.get(key);
                System.out.println("appid:" + key.split("_")[0] + " positionid:"+ key.split("_")[1] + "\r\n " +tmpStr+ "\r\n" );
                stringBuilder.append("appid:" + key.split("_")[0] + " positionid:"+ key.split("_")[1] +" 异常曝光/点击总数 "+ infoCount.get(key) + "\r\n </br>" + info.get(key) +  "\r\n </br>");
                int count = infoCount.get(key);
                int tKey = getOnlyKey(tMap,count);
                tMap.put(count,"appid:" + key.split("_")[0] + " positionid:"+ key.split("_")[1] +" 异常曝光/点击总数 "+ infoCount.get(key) + "\r\n </br>"+tmpStr+  "\r\n </br>");
            });
            //mailMessageUtil.send("风险新增黑名单信息",stringBuilder.toString());
            System.out.println(stringBuilder.toString());
            log.info("最近24小时投放新增黑名单信息:"+stringBuilder.toString());
            StringBuilder stB = new StringBuilder();
            tMap.descendingKeySet().forEach(k->{
                stB.append(tMap.get(k));
            });
            System.out.println(stB.toString());
            //mailMessageUtil.send("最近24小时投放新增黑名单信息",stB.toString());
            //只发一份邮件
            String tmpKey = "mutaEx:UserBlackList:sendmail:"+DateTimeUtils.getDateFlag();
            if(redisService.setnx(tmpKey,"1") == 1){
                redisService.expire(tmpKey,60*60*24*5);
                mailMessageUtil.send("最近24小时投放新增黑名单信息",stB.toString());
            }
        }
    }

    public Integer getOnlyKey(TreeMap<Integer,String> tMap,int inputValue){
        if(!tMap.containsKey(inputValue)){
            return inputValue;
        }else{
            return getOnlyKey(tMap,inputValue+1);
        }
    }
    /**
     * 开发者 点击事件处理情况 监控
     * @throws Exception
     */
    @Scheduled(cron="0 0 8 * * ?")
    private void clickWatch() throws Exception{
        Date date = new Date();
        //取前一天的数据
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);
        String bdateStr = DateTimeUtils.getDateFlag(c.getTime());
        String key = String.format("mutaEx:clickinfo:date:%s",bdateStr);
        Map<String, String> data = redisService.hgetAll(key);
        StringBuilder stringBuilder = new StringBuilder();

        if(data!=null){
            TreeMap<Double,String> tMap = new TreeMap<>();
            double ftmp = 0.0000001d;
            List<AppInfo> apps = mysqlService.findAppInfo(null,"");
            List<String> noreplaceLs =  data.keySet().stream().filter(v->v.contains("_noreplace")).collect(Collectors.toList());
            for(String k:data.keySet()){
                if(k.indexOf("_noreplace") > -1){
                    String appid = k.replace("_noreplace","");
                    String name = "";
                    Optional<AppInfo> appOpt = apps.stream().filter(v->v.appId.equals(appid)).findFirst();
                    if(appOpt.isPresent()){
                        name = appOpt.get().getAppName();
                    }

                    String v1 = data.get(k);
                    String v2 = data.get(appid + "_total");
                    if(StringUtils.isNotEmpty(v1) && StringUtils.isNotEmpty(v2)){
                        Double f = Double.parseDouble(v1)/Double.parseDouble(v2);
                        ftmp =ftmp + 0.00000000011d; //避免key 重复
                        //stringBuilder.append(String.format("%s_%s %s / %s = %s \r\n </br>",appid,name,v1,v2 ,f));
                        if(tMap.containsKey(f+ftmp)){
                            ftmp = ftmp +  0.00000000011d;
                        }
                        tMap.put(f+ftmp,String.format("%s_%s %s / %s = %s \r\n </br>",appid,name,v1,v2 ,f));
                    }
                }
            }
            if(tMap.size() > 0 && noreplaceLs.size() == tMap.size()){
                tMap.descendingKeySet().forEach(k->{
                    stringBuilder.append(tMap.get(k));
                });
                //多个服务器 想办法只有一个发邮件
                String tmpKey = "mutaEx:clickinfo:sendmail:"+bdateStr;
                if(redisService.setnx(tmpKey,"1") == 1){
                    redisService.expire(tmpKey,60*60*24*5);
                    String exMsg = clickWatch2();
                    String mailMsg = stringBuilder.toString();
                    if(exMsg != null){
                        mailMsg = exMsg +"=====================================================</br>\r\n" +mailMsg;
                    }
                    mailMessageUtil.send(String.format("%s未替换点击坐标的事件统计",bdateStr),mailMsg);
                }
            }
        }
    }
    @Scheduled(cron="0 0/30 8-22 * * ?")
    private void checkNuwaCacheinfo(){
        String bdateStr = DateTimeUtils.getDateFlag();
        String key = String.format("mutaEx:cacheinfo:%s",bdateStr);
        Map<String, String> data = redisService.hgetAll(key);
        StringBuilder stringBuilder = new StringBuilder();
        Map<String,List<String>> mtmp = new HashMap<>();
        if(data!=null){
            for(String k:data.keySet()){
                System.out.println(k);
                if(k.indexOf("_md5") > -1){
                    String value = data.get(k);
                    //154的机器经常排查问题 重启 排查掉
                    if(StringUtils.isNotEmpty(value) && value.contains("154v24")){
                        continue;
                    }
                    if(mtmp.containsKey(value)){
                        mtmp.get(value).add(k.replace("_md5",""));
                    } else {
                        List<String> lst = new ArrayList<>();
                        lst.add(k.replace("_md5",""));
                        mtmp.put(value,lst);
                    }
                }
            }
        }
        if(mtmp.size() == 1){
            System.out.println("nuwa cache 信息一致");
        }
        if(mtmp.size() > 1){
            StringBuilder strB = new StringBuilder();
            mtmp.keySet().forEach(k->{
                List<String> v = mtmp.get(k);
                strB.append("listsize:"+v.size() + " 机器信息:\r\n");
                v.forEach(vt->strB.append(vt+";\r\n"));
            });
            String outInfo = strB.toString();
            System.out.println("nuwa 信息不一致:"+outInfo);
            //收件人
            String toUsers = "test@test.com";
            //只发一份邮件
            String tmpKey = "mutaEx:cacheinfo:sendmail:"+DateTimeUtils.getDateFlag();
            if(redisService.setnx(tmpKey,"1") == 1){
                redisService.expire(tmpKey,60*60*24*5);
                mailMessageUtil.send("nuwa cache 信息不一致",outInfo,toUsers);
            }
        }
    }

    /**
     * app 版本黑名单
     * 1. 不支持点击坐标替换的进入黑名单
     * 2. .....
     * @throws Exception
     */
    @Scheduled(cron="0 0 8 * * ?")
    private void blackAppVersion() throws Exception{
        Date date = new Date();
        //取前一天的数据
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        //昨日数据
        c.set(Calendar.DATE, day - 1);
        String dayStr = DateTimeUtils.getDateFlag(c.getTime());
        String key = String.format("mutaEx:clickinfoex:date:%s",dayStr);
        Map<String, String> dataBefore1 = redisService.hgetAll(key);
        //前天数据
        c.set(Calendar.DATE, day - 2);
        String dayStr2 = DateTimeUtils.getDateFlag(c.getTime());
        String key2 = String.format("mutaEx:clickinfoex:date:%s",dayStr2);
        Map<String, String> dataBefore2 = redisService.hgetAll(key2);

        //-3 天的数据
        c.set(Calendar.DATE, day -3);
        String dayStr3 = DateTimeUtils.getDateFlag(c.getTime());
        String key3 = String.format("mutaEx:clickinfoex:date:%s",dayStr3);
        Map<String, String> dataBefore3 = redisService.hgetAll(key3);

        if(dataBefore1!=null && dataBefore2!=null && dataBefore3!=null){
            for(String tk : dataBefore1.keySet()){
                if(tk.indexOf("_noreplace") > -1){
                    String totalKey = tk.replace("_noreplace","_total");
                    String noreplaceValue = dataBefore1.get(tk);
                    String totalValue = dataBefore1.get(totalKey);
                    //未替换的 和 总数一致 且 -2 天 天都存在
                    //且两天的 未替换总数 >20 个 将该app 和 版本加入投放黑名单
                    if(noreplaceValue!=null && totalValue!=null && noreplaceValue.equals(totalValue)){
                        String noreplaceValue2 = dataBefore2.get(tk);
                        String totalValue2 = dataBefore2.get(totalKey);
                        if(noreplaceValue2!=null && totalValue2!=null && noreplaceValue2.equals(totalValue2)){
                            if(Integer.parseInt(noreplaceValue) + Integer.parseInt(noreplaceValue2) > 20){
                                String blackAppVerKey = "mutaEx:blackappver_for_clickmacro";
                                String bInfo = tk.replace("_noreplace","");
                                Map<String,String> mm = new HashMap<>();
                                //0; 代表状态
                                mm.put(bInfo,String.format("0;%s",DateTimeUtils.formatDateTime(date)));
                                redisService.hmset(blackAppVerKey,mm);
                            }
                        }
                    }
                }
            }
        }
    }
    @Scheduled(cron="0 * * * * ?")
    private void mailJob() throws Exception{
        //log.info("获取邮件报警信息 :" + LocalTime.now());
        mailMessageUtil.collectionDsppunish();
    }

    //针对平台的点击数据
    private String clickWatch2(){
        Date date = new Date();
        //取前一天的数据
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);
        String bdateStr = DateTimeUtils.getDateFlag(c.getTime());
        String key = String.format("mutaEx:clickinfoex2:date:%s",bdateStr);
        Map<String, String> data = redisService.hgetAll(key);
        StringBuilder stringBuilder = new StringBuilder();

        if(data!=null){
            List<AppInfo> apps = mysqlService.findAppInfo(null,"");
            List<SellPlatform> platforms = mysqlService.findSellPlatform(null);
            List<String> noreplaceLs =  data.keySet().stream().filter(v->v.contains("_noreplace")).collect(Collectors.toList());
            Map<String,List<String>> retMap = new HashMap<>();
            for(String k:data.keySet()){
                if(k.indexOf("_noreplace") > -1){
                    String[] strs = k.replace("_noreplace","").split("_");
                    String appid = strs[0];
                    String cid = strs[1];
                    String appName = "";
                    String platformName = "";
                    Optional<AppInfo> appOpt = apps.stream().filter(v->v.appId.equals(appid)).findFirst();
                    if(appOpt.isPresent()){
                        appName = appOpt.get().getAppName();
                    }
                    Optional<SellPlatform> platformOpt = platforms.stream().filter(v -> v.getPlatformId().equals(cid)).findFirst();
                    if(platformOpt.isPresent()){
                        platformName = platformOpt.get().getShotName();
                    }

                    String v1 = data.get(k);
                    String v2 = data.get(appid +"_"+cid+ "_total");
                    if(!v1.equals(v2)){
                        String tKey = cid+"_"+platformName;
                        String tValue = appid+"_"+appName +":" + "noreplace " + v1 + " total " + v2;
                        if(retMap.containsKey(tKey)){
                            retMap.get(tKey).add(tValue);
                        }else{
                            retMap.put(tKey,new ArrayList<>());
                            retMap.get(tKey).add(tValue);
                        }
                    }
                }
            }
            StringBuilder retStrBuilder = new StringBuilder();
            if(retMap.size() > 0) {
                retMap.keySet().forEach(v->{
                    // System.out.println(v);
                    retStrBuilder.append(v +":-----------</br>\r\n");
                    List<String> ls = retMap.get(v);
                    ls.forEach(v1->  retStrBuilder.append("  "+v1 +"</br>\r\n"));
                });
            }
            System.out.println(retStrBuilder.toString());
            return retStrBuilder.toString();
        }
        return null;
    }
}