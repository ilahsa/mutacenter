import com.ifeng.ad.mutacenter.Application;
import com.ifeng.ad.mutacenter.common.utils.JsonUtils;
import com.ifeng.ad.mutacenter.common.utils.StringUtils;
import com.ifeng.ad.mutacenter.lianghua.GupiaoData;
import com.ifeng.ad.mutacenter.lianghua.JijinData;
import com.ifeng.ad.mutacenter.service.MysqlService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class) /*添加SpringJUnit支持，引入Spring-Test框架*/
@SpringBootTest(classes = Application.class) /*指定Springboot启动类启动*/
public class GupiaoTest {
    private static Logger logger = LoggerFactory.getLogger(CommonTest.class);
    @Autowired
    private MysqlService mysqlService;
    private OkHttpClient okHttpClient ;
    @Test
    public void updateGupiao(){
        String code = "sz000002";
        okHttpClient = new OkHttpClient();
        //拿10天的数据
        String url = String.format("https://money.finance.sina.com.cn/quotes_service/api/json_v2.php/CN_MarketData.getKLineData?symbol=%s&scale=5&ma=5&datalen=480",code);
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            List<GupiaoData> lst = new ArrayList<>();
            Response response = okHttpClient.newCall(request).execute();
            if(response.code() == 200){
                //jsonpgz({"fundcode":"012349","name":"天弘恒生科技指数(QDII)C","jzrq":"2023-06-30","dwjz":"0.5774","gsz":"0.5975","gszzl":"3.47","gztime":"2023-07-03 16:00"});
                String respBody = response.body().string();
                if(StringUtils.isNotEmpty(respBody)){
                    respBody = respBody.replace("jsonpgz(","").replace(");","");
                    List<GupiaoData> jds = JsonUtils.json2list(respBody, GupiaoData.class);
                    List<GupiaoData> existsJds = getGupiaodata(code);
                    //一天只有一条记录
                    List<String> days = new ArrayList<>();
                    List<GupiaoData> dayJds = new ArrayList<>();
                    //更新数据
                    String sql = "insert into gupiao(`code`,`day`,`daystr`,`open`,`high`,`low`,`close`,`ma_price5`) VALUES(?,?,?,?,?,?,?,?);";
                    for (GupiaoData gp : jds){
                        //gp 的day 格式是 2023-09-27 09:35:00
                        //数据库中查询的格式是 2023-09-27 09:35:00.0
                       Optional<GupiaoData> optGp = existsJds.stream().filter(v->{
                                   try {
                                       String st1 = v.getDay().substring(0,19);
                                       String st2 = gp.getDay();
                                       if(st1.equals(st2)){
                                           return true;
                                       }else{
                                           return false;
                                       }
                                   } catch (Exception e) {
                                       throw new RuntimeException(e);
                                   }
                               }
                       ).findFirst();
                       if(!optGp.isPresent()){
                           String daystr = gp.getDay().replace(":","").replace(" ","").replace("-","");
                           mysqlService.execute(sql,code,gp.getDay(),daystr,gp.getOpen(),gp.getHigh(),gp.getLow(),gp.getClose(),gp.getMa_price5());
                       }

                       String day = gp.getDay().substring(0,10);
                       if(!days.contains(day)){
                           days.add(day);
                       }
                    }
                    int index =0;
                    for(String day : days){
                        List<GupiaoData> tmpLs = jds.stream().filter(v->v.getDay().startsWith(day)).sorted(Comparator.comparing(GupiaoData::getDay)).collect(Collectors.toList());
                        if(CollectionUtils.isNotEmpty(tmpLs)){
                            GupiaoData tmpData = tmpLs.get(tmpLs.size()-1);
                            tmpData.setIndex(index);
                            index = index+1;
                            dayJds.add(tmpData);
                        }
                    }
                    //
                    for (GupiaoData gp:dayJds){
                        List<GupiaoData> jd20Data = getJinjinBefore20Data(dayJds,gp.getIndex());
                        if(CollectionUtils.isNotEmpty(jd20Data)){
                            //20日均值
                            float f = (float) (jd20Data.stream().mapToDouble(GupiaoData::getMa_price5).sum() / jd20Data.size());
                            gp.setD20jz(f);
                            double totalV = 0d;
                            //计算标准差
                            for(GupiaoData jdx:jd20Data){
                                totalV =totalV + Math.pow(jdx.getMa_price5()-f,2);
                            }
                            double bzc = Math.sqrt((double)(totalV/jd20Data.size()));
                            //更新20日均值 过冷值 过热值
                            if(1==1 || gp.getD20jz()<=0 || gp.getGrz() <=0 || gp.getGlz() <=0){
                                updateGupiao20Data(f,f+2*bzc,f-2*bzc ,gp.getDay(),code);
                            }
                        }
                    }
                    System.out.println("1111111111111");
                }
            }
        }catch (Exception ex){
            logger.info(ex.getMessage());
        }
    }

    @Test
    public void toLineTest() {
        //清理目录
        File file = new File("C:\\work\\java\\mutacenter\\gp");
        try {
            FileUtils.cleanDirectory(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String name1 = "万科";
        String code1 = "sz000002";
        List<GupiaoData> ls = getGupiaodataForLine(code1);
        Test2.setLanguageCN();
        Test2.toLine(ls,"gp",name1);
        System.out.println(name1 + "图像绘制完成");
    }

    /**
     * 获取股票数据
     * @param code
     * @return
     */
    private List<GupiaoData> getGupiaodata(String code){
        //取过去90天的记录
        String sql ="select * from (select * from gupiao where code = ? order by day desc limit 5000) as t1 order by day asc ";
        List<GupiaoData> ds = mysqlService.find(sql,GupiaoData.class,code);
        return ds;
    }
    private List<GupiaoData> getGupiaodataForLine(String code){
        List<String> days = new ArrayList<>();
        List<GupiaoData> ls = getGupiaodata(code);
        List<GupiaoData> dayLs = new ArrayList<>();
        for (GupiaoData gp : ls){
            //gp 的day 格式是 2023-09-27 09:35:00
            //数据库中查询的格式是 2023-09-27 09:35:00.0
            String day = gp.getDay().substring(0,10);
            if(!days.contains(day)){
                days.add(day);
            }
        }
        int index =0;
        for(String day : days){
            List<GupiaoData> tmpLs = ls.stream().filter(v->v.getDay().startsWith(day)).sorted(Comparator.comparing(GupiaoData::getDay)).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(tmpLs)){
                GupiaoData tmpData = tmpLs.get(tmpLs.size()-1);
                tmpData.setIndex(index);
                index = index+1;
                dayLs.add(tmpData);
            }
        }
        return dayLs;
    }
    //获取20天均线需要的数据
    private List<GupiaoData> getJinjinBefore20Data(List<GupiaoData> datas ,int index){
        List<GupiaoData> ret = datas.stream().filter(v -> v.getIndex() <= index && (v.getIndex() + 19) >= index).collect(Collectors.toList());
        return ret;
    }

    private void updateGupiao20Data(double jz20,double grz,double glz,String day,String code){
        String daystr = day.replace(":","").replace(" ","").replace("-","");
        String sql = "update gupiao set d20jz = ?,grz=?,glz=? where daystr = ? and code = ? and d20jz is null";
        mysqlService.execute(sql,jz20,grz,glz,daystr,code);
    }
}
