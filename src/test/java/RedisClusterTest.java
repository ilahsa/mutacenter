import com.google.common.collect.Maps;
import com.ifeng.ad.mutacenter.Application;
import com.ifeng.ad.mutacenter.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by wangting3 on 2019/1/27.
 */
@RunWith(SpringJUnit4ClassRunner.class) /*添加SpringJUnit支持，引入Spring-Test框架*/
@SpringBootTest(classes = Application.class) /*指定Springboot启动类启动*/
public class RedisClusterTest {

    @Autowired
    private RedisService redisService;

    @Test
    public void test() throws SQLException {
        Map map = Maps.newHashMap();
        map.put("111","222");
        System.out.println(redisService.hmset("test",map));
    }
}
