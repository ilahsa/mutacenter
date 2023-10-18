import com.ifeng.ad.mutacenter.Application;
import com.ifeng.ad.mutacenter.common.utils.JsonUtils;
import com.ifeng.ad.mutacenter.common.utils.StringUtils;
import com.ifeng.ad.mutacenter.lianghua.GupiaoData;
import com.ifeng.ad.mutacenter.lianghua.JijinData;
import com.ifeng.ad.mutacenter.service.MysqlService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
                    String sql = "insert into gupiao(`code`,`day`,`open`,`high`,`low`,`close`,`ma_price5`) VALUES(?,?,?,?,?,?,?);";
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
                           mysqlService.execute(sql,code,gp.getDay(),gp.getOpen(),gp.getHigh(),gp.getLow(),gp.getClose(),gp.getMa_price5());
                       }
                    }
                }
            }
        }catch (Exception ex){
            logger.info(ex.getMessage());
        }
    }

    /**
     * 获取股票数据
     * @param code
     * @return
     */
    private List<GupiaoData> getGupiaodata(String code){
        String sql ="select * from gupiao where code = ? order by day desc limit 10000 ";
        List<GupiaoData> ds = mysqlService.find(sql,GupiaoData.class,code);
        return ds;
    }
}
