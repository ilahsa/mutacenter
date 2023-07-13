import com.ifeng.ad.mutacenter.Application;
import com.ifeng.ad.mutacenter.common.bean.mysqlmap.AppInfo;
import com.ifeng.ad.mutacenter.common.mail.MailMessageUtil;
import com.ifeng.ad.mutacenter.common.utils.DateTimeUtils;
import com.ifeng.ad.mutacenter.common.utils.StringUtils;
import com.ifeng.ad.mutacenter.service.MysqlService;
import com.ifeng.ad.mutacenter.service.RedisService;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.xml.bind.SchemaOutputResolver;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author baibq
 * @version 1.0
 * @date 2021-12-20 10:53
 */
@RunWith(SpringJUnit4ClassRunner.class) /*添加SpringJUnit支持，引入Spring-Test框架*/
@SpringBootTest(classes = Application.class) /*指定Springboot启动类启动*/
public class AutoRunTaskTest {

    @Autowired
    RedisService redisService;
    @Resource(name = "redisServiceEx")
    private RedisService redisServiceEx;

    @Autowired
    private MailMessageUtil mailMessageUtil;

    @Autowired
    private MysqlService mysqlService;

    @Test
    public void redisTest1(){

        List<String> kk = redisServiceEx.hmget("mutaEx:UserBlackList:00000f9fec9acccbaa62803045dc9a04","header_value");
        System.out.println(kk.get(0));


        List<String> kk1 = redisService.hmget("muta:Adposition:posId:1","formType");
        System.out.println(kk.get(0));
    }

    @Test
    public void userBlackListTest(){
        long t1 = System.currentTimeMillis();
        long t2 = System.currentTimeMillis() - 1000*60*60*24;
        List<String> keys = redisServiceEx.allkeys("mutaEx:UserBlackList:*");
        //黑名单信息 key {appid}_{adid}
        Map<String,Integer> infoCount = new HashMap<>();
        Map<String,String> info = new HashMap<>();
        if(CollectionUtils.isNotEmpty(keys)){
            keys.forEach(v->{
                //value 信息 如下
                //   Map<String,String> info = new HashMap<>();
                //            info.put("aid",appid);
                //            info.put("adid",adid);//广告位信息
                //            info.put("header_key",ret.getT2()); //非法的 header key
                //            info.put("header_value",ret.getT3()); //非法的 header 值
                //            info.put("reqid",reqId);  //记录request id 方便回查
                //            info.put("timestamp",String.valueOf(System.currentTimeMillis())); //加入时候的时间戳
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

            StringBuilder stB = new StringBuilder();
            tMap.descendingKeySet().forEach(k->{
                stB.append(tMap.get(k));
            });
           // System.out.println("11111111111111");
            System.out.println(stB.toString());
            mailMessageUtil.send("风险新增黑名单信息",stB.toString());
         }
    }

    public Integer getOnlyKey(TreeMap<Integer,String> tMap,int inputValue){
        if(!tMap.containsKey(inputValue)){
            return inputValue;
        }else{
            return getOnlyKey(tMap,inputValue+1);
        }
    }
    @Test
    public void clickWatchTest(){
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
            double ftmp = 0.0000001f;
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
                        if(tMap.containsKey(f+ftmp)){
                            ftmp = ftmp +  0.00000000011d;
                        }
                        //stringBuilder.append(String.format("%s_%s %s / %s = %s \r\n </br>",appid,name,v1,v2 ,f));
                        tMap.put(f+ftmp,String.format("%s_%s %s / %s = %s \r\n </br>",appid,name,v1,v2 ,f));

                    }
                }
            }
            if(tMap.size() > 0 && noreplaceLs.size() == tMap.size()){
                tMap.descendingKeySet().forEach(k->{
                    stringBuilder.append(tMap.get(k));
                });
                mailMessageUtil.send(String.format("%s未替换点击坐标的事件统计",bdateStr),stringBuilder.toString());
            }
        }
    }
    @Test
    public void cacheinfoTest(){
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
        }
    }

    @Test
    public void blackAppVerTest(){
        String clickinfoEx = "mutaEx:clickinfoex:date:%s";
        Date date = new Date();
        //取前一天的数据
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        //昨日数据
        c.set(Calendar.DATE, day - 1);
        String dayStr = DateTimeUtils.getDateFlag(c.getTime());
        String key = String.format(clickinfoEx,dayStr);
        Map<String, String> dataBefore1 = redisService.hgetAll(key);
        //前天数据
        c.set(Calendar.DATE, day - 2);
        String dayStr2 = DateTimeUtils.getDateFlag(c.getTime());
        String key2 = String.format(clickinfoEx,dayStr2);
        Map<String, String> dataBefore2 = redisService.hgetAll(key2);

        //-3 天的数据
        c.set(Calendar.DATE, day -3);
        String dayStr3 = DateTimeUtils.getDateFlag(c.getTime());
        String key3 = String.format(clickinfoEx,dayStr3);
        Map<String, String> dataBefore3 = redisService.hgetAll(key3);

        if(dataBefore1!=null && dataBefore2!=null ){
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
}
