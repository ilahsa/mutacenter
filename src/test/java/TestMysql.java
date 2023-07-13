import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ifeng.ad.mutacenter.Application;
import com.ifeng.ad.mutacenter.SQLConstant;
import com.ifeng.ad.mutacenter.common.bean.mysqlmap.ShowTime;
import com.ifeng.ad.mutacenter.common.utils.JsonUtils;
import com.ifeng.ad.mutacenter.service.MysqlService;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by  on 2019/1/15.
 */
@RunWith(SpringJUnit4ClassRunner.class) /*添加SpringJUnit支持，引入Spring-Test框架*/
@SpringBootTest(classes = Application.class) /*指定Springboot启动类启动*/
public class TestMysql implements SQLConstant {

    @Autowired
    private HikariDataSource hikariDataSource;

    @Autowired
    private MysqlService mysqlService;

    @Test
    public void test() throws SQLException {
//        QueryRunner qr = new QueryRunner(hikariDataSource);
//        List<Map<String, Object>> stuent = qr.query(SQL_ADPOSITION,new MapListHandler());
//        stuent.forEach(s->{
//            System.out.println(s);
//        });

//        List<Adposition> stuent1 = mysqlService.findAdposition("1");
//        stuent1.forEach(s -> System.out.println(JsonUtils.convert2Json(s)));

       // List<ShowTime> stuent1 = mysqlService.findShowTime(null);
        //stuent1.forEach(s -> System.out.println(JsonUtils.convert2Json(s)));

        System.out.println("-----------");

       // List<ShowTime> stuent2 = mysqlService.findShowTime(Sets.newHashSet("5,6"));
        //stuent2.forEach(s -> System.out.println(JsonUtils.convert2Json(s)));
    }
}
