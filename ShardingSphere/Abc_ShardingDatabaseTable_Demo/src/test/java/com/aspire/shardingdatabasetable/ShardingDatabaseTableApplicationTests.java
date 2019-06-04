package com.aspire.shardingdatabasetable;

import com.aspire.shardingdatabasetable.model.StaffPO;
import com.aspire.shardingdatabasetable.service.ShardingService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShardingDatabaseTableApplicationTests {

    @Autowired
    ShardingService shardingService;

    @Test
    public void allTest() {
        oneTest();
        twoTest();
        threeTest();
        fourTest();
        fiveTest();
        sixTest();
    }

    @Test
    public void oneTest() {
        String uuid = UUID.randomUUID().toString();
        StaffPO staffPO = StaffPO.builder()
                                 .id(uuid)
                                 .name("邓沙利文")
                                 .age(25)
                                 .gender("男")
                                 .build();
        int result = shardingService.insertDemo(staffPO);
        Assert.assertEquals(1, result);
    }

    @Test
    public void twoTest() {
        String uuid = UUID.randomUUID().toString();
        StaffPO staffPO = StaffPO.builder()
                                 .id(uuid)
                                 .name("疯一般的女子")
                                 .age(123)
                                 .gender("女")
                                 .build();
        int result = shardingService.insertDemo(staffPO);
        Assert.assertEquals(1, result);
    }

    @Test
    public void threeTest() {
        String uuid = UUID.randomUUID().toString();
        StaffPO staffPO = StaffPO.builder()
                                 .id(uuid)
                                 .name("无敌小婷妹")
                                 .age(21)
                                 .gender("女")
                                 .build();
        int result = shardingService.insertDemo(staffPO);
        Assert.assertEquals(1, result);
    }

    @Test
    public void fourTest() {
        String uuid = UUID.randomUUID().toString();
        StaffPO staffPO = StaffPO.builder()
                                 .id(uuid)
                                 .name("邓~")
                                 .age(125)
                                 .gender("男")
                                 .build();
        int result = shardingService.insertDemo(staffPO);
        Assert.assertEquals(1, result);
    }

    @Test
    public void fiveTest() {
        String uuid = UUID.randomUUID().toString();
        StaffPO staffPO = StaffPO.builder()
                                 .id(uuid)
                                 .name("亨得帅")
                                 .age(66)
                                 .gender("男")
                                 .build();
        int result = shardingService.insertDemo(staffPO);
        Assert.assertEquals(1, result);
    }

    @Test
    public void sixTest() {
        String uuid = UUID.randomUUID().toString();
        StaffPO staffPO = StaffPO.builder()
                                 .id(uuid)
                                 .name("小聋女")
                                 .age(80)
                                 .gender("女")
                                 .build();
        int result = shardingService.insertDemo(staffPO);
        Assert.assertEquals(1, result);
    }

    @Test
    public void qeuryTest() {
        List<StaffPO> result = shardingService.queryDemo(10);
        for (StaffPO staffPO : result) {
            System.out.println(staffPO);
        }
    }
}
