import okhttp3.*;
import org.junit.*;

import java.io.IOException;

/**
 * Created by  on 2019/1/23.
 */
public class HttpTest {
    private OkHttpClient okHttpClient;

    @Before
    public void before() {
        okHttpClient = new OkHttpClient();
    }

    @After
    public void after() throws IOException {
        okHttpClient.dispatcher().executorService().shutdownNow();
        okHttpClient.connectionPool().evictAll();
    }

    @Test
    @Ignore
    public void test() throws Exception {
        for(int i=0;i<150;i++) {
            String name = "adform";
            if(i < 30) {
                name="adposition";
            }
            else if(i<60) {
                name = "cpdcreative";
            }
            else if(i < 100) {
                name = "allowance";
            }

            Request request = new Request.Builder()
                    .url("http://localhost:8888/"+name+"/"+i)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            Assert.assertEquals(200, response.code());
            System.out.println(response.body().string());
            response.close();
        }
    }
}
