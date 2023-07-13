import com.ifeng.ad.mutacenter.Application;
import com.ifeng.ad.mutacenter.verticles.KafkaVerticle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by  on 2019/1/23.
 */
@RunWith(SpringJUnit4ClassRunner.class) /*添加SpringJUnit支持，引入Spring-Test框架*/
@SpringBootTest(classes = Application.class) /*指定Springboot启动类启动*/
public class KafkaTest {

    @Autowired
    KafkaVerticle kafkaVerticle;

    @Test
    public void test() throws Exception {
        kafkaVerticle.write2Kafka("test");
        kafkaVerticle.write2Kafka("test2");
        kafkaVerticle.write2Kafka("test3");
        Thread.sleep(10000L);
    }
}
