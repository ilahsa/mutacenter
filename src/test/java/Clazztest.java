import com.ifeng.ad.mutacenter.common.bean.mysqlmap.Adform;
import com.ifeng.ad.mutacenter.common.bean.mysqlmap.SellPlatform;
import org.junit.Test;

/**
 * Created by wangting3 on 2019/1/22.
 */
public class Clazztest {
    @Test
    public void test() {
        Class clz1 = Adform.class;

        Class clz2 = Adform.class;

        System.out.println(clz1.getName());
    }
}
