package com;

import com.util.SftpUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SftpApplicationTests {

    @Test
    public void contextLoads() {
    }

    /**
     * 程序入口
     *
     * @date 2019/3/17 12:54
     */
    public static void main(String[] args) {
        deleteTestThree();
    }

    /**
     * 递归删除文件夹(示例)
     */
    private static void deleteTestThree() {
        try {
            SftpUtil.init("10.8.109.35", 22, "tom", "tom123");
            SftpUtil.delete("/files/abc/");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}




