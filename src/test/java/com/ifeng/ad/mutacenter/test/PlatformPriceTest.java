package com.ifeng.ad.mutacenter.test;

import com.ifeng.ad.mutacenter.common.bean.mysqlmap.PlatformPrice;
import com.ifeng.ad.mutacenter.service.MysqlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by baibq on 2020-07-10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PlatformPriceTest {
    @Autowired
    private MysqlService mysqlService;
    @Test
    //数据库的逻辑测试
    public void dbTest(){
        //1. 找 platform_price的记录
        //2. 通过表 alliance_position_allocation 反查我们的广告位id'
        //3. 他们的一个位置对应我们的多个，更新我们的多条cache信息
        String sql = "SELECT id,platform_id,position_id,form_type,price,`status` FROM ifstest.platform_price";
        List<PlatformPrice> ls = mysqlService.find(sql,PlatformPrice.class,null);

        System.out.println("ls size:"+ls.size());
        PlatformPrice platformPrice = ls.get(0);
        System.out.println("detail:"+platformPrice.getPrice());

    }
}
