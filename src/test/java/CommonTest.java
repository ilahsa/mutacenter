import com.ifeng.ad.mutacenter.Application;
import com.ifeng.ad.mutacenter.common.bean.mysqlmap.TestUser;
import com.ifeng.ad.mutacenter.common.utils.JsonUtils;
import com.ifeng.ad.mutacenter.common.utils.StringUtils;
import com.ifeng.ad.mutacenter.lianghua.JijinData;
import com.ifeng.ad.mutacenter.lianghua.JijinInfo;
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
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class) /*添加SpringJUnit支持，引入Spring-Test框架*/
@SpringBootTest(classes = Application.class) /*指定Springboot启动类启动*/
public class CommonTest {
    private static Logger logger = LoggerFactory.getLogger(CommonTest.class);
    @Autowired
    private MysqlService mysqlService;
    private OkHttpClient okHttpClient ;
    @Test
    public void dbTest(){
        try{
            String sql = "select id,name from testuser";
            List<String> parameter =null ;
            List<TestUser> ret=  mysqlService.find(sql, TestUser.class, null);
            System.out.println(11111);
        }catch (Exception ex){
            System.out.println("error--------");
        }
    }
    public void jsoupTest(){
        String url = "https://fundf10.eastmoney.com/F10DataApi.aspx?type=lsjz&code=163402&page=1&sdate=19950101&edate={}&per=100";

       // Document doc =   Jsoup.parse("http://www.dangdang.com");
       // Jsoup.
    }

    @Test
    public void test6() throws Exception {
        //获取DocumentBuilder
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        //需要解析html
        String html = "<table class='w782 comm lsjz'>\n" +
                "\t<thead>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<th class='first'>净值日期</th>\n" +
                "\t\t\t<th>单位净值</th>\n" +
                "\t\t\t<th>累计净值</th>\n" +
                "\t\t\t<th>日增长率</th>\n" +
                "\t\t\t<th>申购状态</th>\n" +
                "\t\t\t<th>赎回状态</th>\n" +
                "\t\t\t<th class='tor last'>分红送配</th>\n" +
                "\t\t</tr>\n" +
                "\t</thead>\n" +
                "\t<tbody>\n" +
                "<tr>" +
                "<td>2023-06-30</td>" +
                "<td class='tor bold'>0.6526</td>" +
                "<td class='tor bold'>10.4586</td>" +
                "<td class='tor bold red'>0.71%</td>" +
                "\t\t\t<td>限制大额申购</td>\n" +
                "\t\t\t<td>开放赎回</td>\n" +
                "\t\t\t<td class='red unbold'></td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td>2023-06-29</td>" +
                "<td class='tor bold'>0.6480</td>" +
                "<td class='tor bold'>10.4402</td>" +
                "\t\t\t<td class='tor bold bck'>0.00%</td>\n" +
                "\t\t\t<td>限制大额申购</td>\n" +
                "\t\t\t<td>开放赎回</td>\n" +
                "\t\t\t<td class='red unbold'></td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td>2023-06-28</td>" +
                "\t\t\t<td class='tor bold'>0.6480</td>\n" +
                "\t\t\t<td class='tor bold'>10.4402</td>\n" +
                "\t\t\t<td class='tor bold grn'>-0.51%</td>\n" +
                "\t\t\t<td>限制大额申购</td>\n" +
                "\t\t\t<td>开放赎回</td>\n" +
                "\t\t\t<td class='red unbold'></td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td>2023-06-27</td>" +
                "\t\t\t<td class='tor bold'>0.6513</td>\n" +
                "\t\t\t<td class='tor bold'>10.4534</td>\n" +
                "\t\t\t<td class='tor bold red'>1.23%</td>\n" +
                "\t\t\t<td>限制大额申购</td>\n" +
                "\t\t\t<td>开放赎回</td>\n" +
                "\t\t\t<td class='red unbold'></td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td>2023-06-26</td>\n" +
                "\t\t\t<td class='tor bold'>0.6434</td>\n" +
                "\t\t\t<td class='tor bold'>10.4218</td>\n" +
                "\t\t\t<td class='tor bold grn'>-1.15%</td>\n" +
                "\t\t\t<td>限制大额申购</td>\n" +
                "\t\t\t<td>开放赎回</td>\n" +
                "\t\t\t<td class='red unbold'></td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td>2023-06-21</td>\n" +
                "\t\t\t<td class='tor bold'>0.6509</td>\n" +
                "\t\t\t<td class='tor bold'>10.4518</td>\n" +
                "\t\t\t<td class='tor bold grn'>-2.11%</td>\n" +
                "\t\t\t<td>限制大额申购</td>\n" +
                "\t\t\t<td>开放赎回</td>\n" +
                "\t\t\t<td class='red unbold'></td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td>2023-06-20</td>\n" +
                "\t\t\t<td class='tor bold'>0.6649</td>\n" +
                "\t\t\t<td class='tor bold'>10.5077</td>\n" +
                "\t\t\t<td class='tor bold red'>0.08%</td>\n" +
                "\t\t\t<td>限制大额申购</td>\n" +
                "\t\t\t<td>开放赎回</td>\n" +
                "\t\t\t<td class='red unbold'></td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td>2023-06-19</td>\n" +
                "\t\t\t<td class='tor bold'>0.6644</td>\n" +
                "\t\t\t<td class='tor bold'>10.5057</td>\n" +
                "\t\t\t<td class='tor bold red'>0.06%</td>\n" +
                "\t\t\t<td>限制大额申购</td>\n" +
                "\t\t\t<td>开放赎回</td>\n" +
                "\t\t\t<td class='red unbold'></td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td>2023-06-16</td>\n" +
                "\t\t\t<td class='tor bold'>0.6640</td>\n" +
                "\t\t\t<td class='tor bold'>10.5041</td>\n" +
                "\t\t\t<td class='tor bold red'>0.70%</td>\n" +
                "\t\t\t<td>限制大额申购</td>\n" +
                "\t\t\t<td>开放赎回</td>\n" +
                "\t\t\t<td class='red unbold'></td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td>2023-06-15</td>\n" +
                "\t\t\t<td class='tor bold'>0.6594</td>\n" +
                "\t\t\t<td class='tor bold'>10.4857</td>\n" +
                "\t\t\t<td class='tor bold red'>0.18%</td>\n" +
                "\t\t\t<td>限制大额申购</td>\n" +
                "\t\t\t<td>开放赎回</td>\n" +
                "\t\t\t<td class='red unbold'></td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td>2023-06-14</td>\n" +
                "\t\t\t<td class='tor bold'>0.6582</td>\n" +
                "\t\t\t<td class='tor bold'>10.4809</td>\n" +
                "\t\t\t<td class='tor bold red'>0.24%</td>\n" +
                "\t\t\t<td>限制大额申购</td>\n" +
                "\t\t\t<td>开放赎回</td>\n" +
                "\t\t\t<td class='red unbold'></td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td>2023-06-13</td>\n" +
                "\t\t\t<td class='tor bold'>0.6566</td>\n" +
                "\t\t\t<td class='tor bold'>10.4746</td>\n" +
                "\t\t\t<td class='tor bold red'>1.19%</td>\n" +
                "\t\t\t<td>限制大额申购</td>\n" +
                "\t\t\t<td>开放赎回</td>\n" +
                "\t\t\t<td class='red unbold'></td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td>2023-06-12</td>\n" +
                "\t\t\t<td class='tor bold'>0.6489</td>\n" +
                "\t\t\t<td class='tor bold'>10.4438</td>\n" +
                "\t\t\t<td class='tor bold red'>0.05%</td>\n" +
                "\t\t\t<td>限制大额申购</td>\n" +
                "\t\t\t<td>开放赎回</td>\n" +
                "\t\t\t<td class='red unbold'></td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td>2023-06-09</td>\n" +
                "\t\t\t<td class='tor bold'>0.6486</td>\n" +
                "\t\t\t<td class='tor bold'>10.4426</td>\n" +
                "\t\t\t<td class='tor bold red'>0.98%</td>\n" +
                "\t\t\t<td>限制大额申购</td>\n" +
                "\t\t\t<td>开放赎回</td>\n" +
                "\t\t\t<td class='red unbold'></td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td>2023-06-08</td>\n" +
                "\t\t\t<td class='tor bold'>0.6423</td>\n" +
                "\t\t\t<td class='tor bold'>10.4174</td>\n" +
                "\t\t\t<td class='tor bold grn'>-0.05%</td>\n" +
                "\t\t\t<td>限制大额申购</td>\n" +
                "\t\t\t<td>开放赎回</td>\n" +
                "\t\t\t<td class='red unbold'></td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td>2023-06-07</td>\n" +
                "\t\t\t<td class='tor bold'>0.6426</td>\n" +
                "\t\t\t<td class='tor bold'>10.4186</td>\n" +
                "\t\t\t<td class='tor bold bck'>0.00%</td>\n" +
                "\t\t\t<td>限制大额申购</td>\n" +
                "\t\t\t<td>开放赎回</td>\n" +
                "\t\t\t<td class='red unbold'></td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td>2023-06-06</td>\n" +
                "\t\t\t<td class='tor bold'>0.6426</td>\n" +
                "\t\t\t<td class='tor bold'>10.4186</td>\n" +
                "\t\t\t<td class='tor bold grn'>-1.80%</td>\n" +
                "\t\t\t<td>限制大额申购</td>\n" +
                "\t\t\t<td>开放赎回</td>\n" +
                "\t\t\t<td class='red unbold'></td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td>2023-06-05</td>\n" +
                "\t\t\t<td class='tor bold'>0.6544</td>\n" +
                "\t\t\t<td class='tor bold'>10.4658</td>\n" +
                "\t\t\t<td class='tor bold red'>0.29%</td>\n" +
                "\t\t\t<td>限制大额申购</td>\n" +
                "\t\t\t<td>开放赎回</td>\n" +
                "\t\t\t<td class='red unbold'></td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td>2023-06-02</td>\n" +
                "\t\t\t<td class='tor bold'>0.6525</td>\n" +
                "\t\t\t<td class='tor bold'>10.4582</td>\n" +
                "\t\t\t<td class='tor bold red'>1.54%</td>\n" +
                "\t\t\t<td>限制大额申购</td>\n" +
                "\t\t\t<td>开放赎回</td>\n" +
                "\t\t\t<td class='red unbold'></td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td>2023-06-01</td>\n" +
                "\t\t\t<td class='tor bold'>0.6426</td>\n" +
                "\t\t\t<td class='tor bold'>10.4186</td>\n" +
                "\t\t\t<td class='tor bold red'>0.03%</td>\n" +
                "\t\t\t<td>限制大额申购</td>\n" +
                "\t\t\t<td>开放赎回</td>\n" +
                "\t\t\t<td class='red unbold'></td>\n" +
                "\t\t</tr>\n" +
                "\t</tbody>\n" +
                "</table>";
        html = html.replaceAll("\\n","").replaceAll("\\t","");

        Document document = documentBuilder.parse(new ByteArrayInputStream( html.getBytes()));
        System.out.println(document.getElementsByTagName("tbody"));
        //document.getElementsByTagName("tr").item(5).getChildNodes().item(1).getTextContent();
        NodeList tr = document.getElementsByTagName("tr");
       for(int i=1;i<tr.getLength();i++){
           System.out.println(tr.item(i).getChildNodes().item(0).getTextContent() + " ___ "+tr.item(i).getChildNodes().item(1).getTextContent());
       }


    }

    @Test
    public void updateJijinDataExTest(){
        String fundcode = "012349";
        updateJijinDataEx(fundcode);
    }

    /**
     * 全量更新数据
     */
    @Test
    public void getJijinData(){
        List<JijinInfo> infos = getJijinInfos();
        for(JijinInfo info:infos){
            logger.info("开始更新 "+info.getName());
            String fundcode = info.getFundcode();
            //抓取数据并更新 从html 中读取的 最近一个月的数据
            updateJijinDataEx(fundcode);
            //更新均值
            updateJZ(fundcode);
            //更新今天的估算值
            updateJijinDataGjz(fundcode);
            //更新均值
            updateJZ(fundcode);
            logger.info("更新完成 "+info.getName());
        }
    }
    /**
     * 折线图
     *
     * @param
     *
     */
    @Test
    public void toLineTest() {
        //清理目录
        File file = new File("C:\\work\\java\\mutacenter\\gl");
        File file1 = new File("C:\\work\\java\\mutacenter\\gr");
        try {
            FileUtils.cleanDirectory(file);
            FileUtils.cleanDirectory(file1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<JijinData> lsGrz = getJijinDataGrz();
        System.out.println("即将或者已经过热的有"+ lsGrz.size());
        if(CollectionUtils.isNotEmpty(lsGrz)){
            lsGrz.stream().forEach(v->{
                List<JijinData> ls = getJijinData(v.getFundcode());
                Test2.setLanguageCN();
                Test2.toLine(ls,"gr");
            });
            System.out.println("过热的绘图完成");
        }
        System.out.println("====================================");
        List<JijinData> lsGlz = getJijinDataGlz();
        System.out.println("即将或者已经过冷的有"+ lsGlz.size());
        if(CollectionUtils.isNotEmpty(lsGlz)){
            lsGlz.stream().forEach(v->{
                List<JijinData> ls = getJijinData(v.getFundcode());
                Test2.setLanguageCN();
                Test2.toLine(ls,"gl");
            });
            System.out.println("过冷的绘图完成");
        }
    }
    @Test
    public void getJijinDataTest(){
        String fundcode ="012349";
        List<JijinData> ls = getJijinData(fundcode);
        System.out.println(ls.size());
    }

    /**
     * 计算20日均值 过冷值 过热值
     * @param fundcode
     */
    public void updateJZ(String fundcode){
        List<JijinData> ls = getJijinData(fundcode);
        for(JijinData jd:ls){
            List<JijinData> jd20Data = getJinjinBefore20Data(ls,jd.getIndex());
            if(CollectionUtils.isNotEmpty(jd20Data)){
                //20日均值
               float f = (float) (jd20Data.stream().mapToDouble(JijinData::getDwjz).sum() / jd20Data.size());
               jd.setD20jz(f);
                double totalV = 0d;
                //计算标准差
                for(JijinData jdx:jd20Data){
                    totalV =totalV + Math.pow(jdx.getDwjz()-f,2);
                }
                double bzc = Math.sqrt((double)(totalV/jd20Data.size()));
                //更新20日均值 过冷值 过热值
                if(1==1 || jd.getD20jz()<=0 || jd.getGrz() <=0 || jd.getGlz() <=0){
                    updateJijin20Data(f,f+2*bzc,f-2*bzc ,jd.getId());
                }
            }
        }
    }

    @Test
    public void updateJijinInfoTest(){
        List<String> fundcodes = Arrays.asList("012349","011513","007531","012420","002978","003376","011309","013025","011113","161005","017489","160717",
                "014413","007950","013396","007475","013108","006381","014597","013431","450009","001316","007346","001217","502010","000893","011473","001743",
                "001301","202213","007497","016858","002315","016600","002671","005535","013430","519697","519002","519133","012635","012725","011228","163402","163417",
                "014010","012767","005680","011437","010728");
        fundcodes.stream().forEach(v->updateJijinInfo(v));
    }

    @Test
    public void getJijinInfosTest(){
        List<JijinInfo> ret = getJijinInfos();
        System.out.println(ret.size());
    }

    public List<JijinInfo> getJijinInfos(){
        String sql = "select fundcode,name from jijin_info where status =0";
        List<JijinInfo> ret = mysqlService.find(sql, JijinInfo.class,null);
        return ret;
    }
    public void updateJijinInfo(String fundcode){
        okHttpClient = new OkHttpClient();
        String url = String.format("http://fundgz.1234567.com.cn/js/%s.js?rt=1463558676006",fundcode);
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            List<JijinData> existLst = getJijinData(fundcode);
            List<JijinData> lst = new ArrayList<>();
            Response response = okHttpClient.newCall(request).execute();
            if(response.code() == 200){
                //jsonpgz({"fundcode":"012349","name":"天弘恒生科技指数(QDII)C","jzrq":"2023-06-30","dwjz":"0.5774","gsz":"0.5975","gszzl":"3.47","gztime":"2023-07-03 16:00"});
                String respBody = response.body().string();
                if(StringUtils.isNotEmpty(respBody)){
                    respBody = respBody.replace("jsonpgz(","").replace(");","");
                    JijinData jd = JsonUtils.convert2Obj(respBody,JijinData.class);
                    String sql = "REPLACE INTO jijin_info (fundcode,name,status) VALUES(?, ?,0);";
                    mysqlService.execute(sql,fundcode,jd.getName());
                }
            }
        }catch (Exception ex){
            logger.info(ex.getMessage());
        }
    }
    /**
     * 更新基金的估计值
     * @param fundcode
     */
    public void updateJijinDataGjz(String fundcode){
        okHttpClient = new OkHttpClient();
        String url = String.format("http://fundgz.1234567.com.cn/js/%s.js?rt=1463558676006",fundcode);
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            List<JijinData> existLst = getJijinData(fundcode);
            List<JijinData> lst = new ArrayList<>();
            Response response = okHttpClient.newCall(request).execute();
            if(response.code() == 200){
                //jsonpgz({"fundcode":"012349","name":"天弘恒生科技指数(QDII)C","jzrq":"2023-06-30","dwjz":"0.5774","gsz":"0.5975","gszzl":"3.47","gztime":"2023-07-03 16:00"});
                String respBody = response.body().string();
                if(StringUtils.isNotEmpty(respBody)){
                    respBody = respBody.replace("jsonpgz(","").replace(");","");
                    JijinData jd = JsonUtils.convert2Obj(respBody,JijinData.class);
                    String gzDate = jd.getGztime().split(" ")[0];
                    //更新估算值
                    if(!existLst.stream().anyMatch(v->v.getJzrq().equals(gzDate))){
                        String sql = "insert into jijin(fundcode,jzrq,dwjz) value(?,?,?) ";
                        mysqlService.execute(sql,fundcode,gzDate,jd.getGsz());
                    }else {
                        String sql = "update jijin set dwjz = ? where fundcode =? and jzrq = ? ";
                        mysqlService.execute(sql,jd.getGsz(),fundcode,gzDate);
                    }
                }
            }
        }catch (Exception ex){

        }
    }

    public void updateJijinDataEx(String fundcode) {
        okHttpClient = new OkHttpClient();
        String url = String.format("https://fundf10.eastmoney.com/F10DataApi.aspx?type=lsjz&code=%s&page=1&sdate=19950101&edate={}&per=100", fundcode);
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            List<JijinData> existLst = getJijinData(fundcode);
            List<JijinData> lst = new ArrayList<>();
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() == 200) {
                String respBody = response.body().string();
                if (StringUtils.isEmpty(respBody)) {
                    logger.error("抓取数据异常 respbody is null");
                    return;
                }
                respBody = respBody.replaceAll("\\t", "").replaceAll("\\n", "").replaceAll("var apidata=\\{ content:\"","");
                int index1 = 0;
                int index2 = respBody.indexOf("\",records");
                respBody = respBody.substring(index1, index2 );
                DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = documentBuilder.parse(new ByteArrayInputStream(respBody.getBytes()));
                System.out.println(document.getElementsByTagName("tbody"));
                //document.getElementsByTagName("tr").item(5).getChildNodes().item(1).getTextContent();
                String sql = "insert into jijin(fundcode,jzrq,dwjz) value(?,?,?) ";
                int count = 0;
                NodeList tr = document.getElementsByTagName("tr");
                for (int i = 1; i < tr.getLength(); i++) {
                    System.out.println(tr.item(i).getChildNodes().item(0).getTextContent() + " ___ " + tr.item(i).getChildNodes().item(1).getTextContent());
                    String date = tr.item(i).getChildNodes().item(0).getTextContent();
                    String jz = tr.item(i).getChildNodes().item(1).getTextContent();
                    JijinData jd = new JijinData();
                    jd.setFundcode(fundcode);
                    jd.setJzrq(date);
                    jd.setDwjz(Float.parseFloat(jz));
                    lst.add(jd);
                    //增量更新
                    if (!existLst.stream().anyMatch(v -> jd.getFundcode().equals(v.getFundcode()) && jd.getJzrq().equals(v.getJzrq()))) {
                        mysqlService.execute(sql, jd.getFundcode(), jd.getJzrq(), jd.getDwjz());
                        count = count + 1;
                    }
                    logger.info(String.format("本次更新 fundcode %s 记录数 %s", fundcode, count));
                }
            } else {
                logger.error("抓取数据异常 " + response.code());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    /**
     * 更新基金数据
     * @param fundcode
     */
    public void updateJijinData(String fundcode){
        okHttpClient = new OkHttpClient();
        String url = String.format("http://fund.eastmoney.com/pingzhongdata/%s.js?v=20220518155842",fundcode);
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            List<JijinData> existLst = getJijinData(fundcode);
            List<JijinData> lst = new ArrayList<>();
            Response response = okHttpClient.newCall(request).execute();
            if(response.code() == 200){
                String respBody =response.body().string();
                if(StringUtils.isEmpty(respBody)){
                    logger.error("抓取数据异常 respbody is null");
                    return;
                }
                String[] strs1 = respBody.split("Data_ACWorthTrend =");
                String[] strs2 = strs1[1].split(";");
                System.out.println(strs2[0]);
                //
                String datasStr = strs2[0].trim().replace("[[","").replace("]]","");
                String[] strs3 = datasStr.split("],\\[");

                String sql = "insert into jijin(fundcode,jzrq,dwjz) value(?,?,?) ";
                int count =0;
                for(String str:strs3){
                    Object[] strs4 = str.split(",");
                    JijinData jd = new JijinData();
                    jd.setFundcode(fundcode);
                    jd.setJzrq(timstampToStr((String)strs4[0]));
                    //有类似这样的记录 [1604505600000,null]
                    if(strs4[1] == null || strs4[1].equals("null")) {
                        continue;
                    }
                    jd.setDwjz(Float.parseFloat((String)strs4[1]));
                    lst.add(jd);
                    //增量更新
                    if(!existLst.stream().anyMatch(v->jd.getFundcode().equals(v.getFundcode()) && jd.getJzrq().equals(v.getJzrq()))){
                        mysqlService.execute(sql,jd.getFundcode(),jd.getJzrq(),jd.getDwjz());
                        count = count +1;
                    }
                }
                logger.info(String.format("本次更新 fundcode %s 记录数 %s",fundcode,count));
            }else{
                logger.error("抓取数据异常 "+response.code());
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    /**
     * 更新20日均值
     * @param jz20
     * @param id
     */
    private void updateJijin20Data(double jz20,double grz,double glz,int id){
        String sql = "update jijin set d20jz = ?,grz=?,glz=? where id = ?";
        mysqlService.execute(sql,jz20,grz,glz,id);
    }
    private List<JijinData> getJijinData(String fundcode){
        String sql ="select * from (select a.id,jzrq,a.fundcode,b.name,dwjz,gsz,gszzl,gztime,d20jz,grz,glz from jijin a inner join jijin_info b\n" +
                " on a.fundcode =b.fundcode where a.fundcode =? order by jzrq desc limit 50) as t1 order by jzrq asc  ";
        List<JijinData> ds = mysqlService.find(sql,JijinData.class,fundcode);
        List<JijinData> ret = ds.stream().sorted(Comparator.comparing(JijinData::getJzrq)).collect(Collectors.toList());
        int index = 0;
        //引入index 是为了更好的向前找20日的值
        for(JijinData jd :ret){
            jd.setIndex(index);
            index++;
        }
        return ret;
    }

    /**
     * 获取已经或者即将过热的基金数据
     * @return
     */
    private List<JijinData> getJijinDataGrz(){
        String sql ="select jzrq,a.fundcode,`name`,dwjz,d20jz,grz,glz from jijin a inner join jijin_info b\n" +
                "on a.fundcode = b.fundcode where jzrq  =date_format(now(), '%Y-%m-%d') and (dwjz*1.01>grz or dwjz > grz)  ";
        List<JijinData> ds = mysqlService.find(sql,JijinData.class,null);
        List<JijinData> ret = ds.stream().sorted(Comparator.comparing(JijinData::getJzrq)).collect(Collectors.toList());
        int index = 0;
        //引入index 是为了更好的向前找20日的值
        for(JijinData jd :ret){
            jd.setIndex(index);
            index++;
        }
        return ret;
    }

    /**
     * 获取即将过冷或者已经过冷的基金数据
     * @return
     */
    private List<JijinData> getJijinDataGlz(){
        String sql ="select jzrq,a.fundcode,`name`,dwjz,d20jz,grz,glz from jijin a inner join jijin_info b\n" +
                "on a.fundcode = b.fundcode where jzrq  =date_format(now(), '%Y-%m-%d') and (dwjz*0.99<glz or dwjz <glz)";
        List<JijinData> ds = mysqlService.find(sql,JijinData.class,null);
        List<JijinData> ret = ds.stream().sorted(Comparator.comparing(JijinData::getJzrq)).collect(Collectors.toList());
        int index = 0;
        //引入index 是为了更好的向前找20日的值
        for(JijinData jd :ret){
            jd.setIndex(index);
            index++;
        }
        return ret;
    }
    //获取20日均值计算需要的数据
    private List<JijinData> getJinjinBefore20Data(List<JijinData> datas ,int index){
        List<JijinData> ret = datas.stream().filter(v -> v.getIndex() <= index && (v.getIndex() + 19) >= index).collect(Collectors.toList());
        return ret;
    }
    private String timstampToStr(String timestamp){
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String time = simpleDateFormat.format(new Date(Long.parseLong(timestamp)));
            return time;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return "";
        }

    }
}
