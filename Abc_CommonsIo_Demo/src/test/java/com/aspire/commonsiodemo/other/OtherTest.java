package com.aspire.commonsiodemo.other;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

/**
 * 其它相关测试
 *
 * @author JustryDeng
 * @date 2019/6/25 9:42
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OtherTest {

    /**
     * 磁盘空间测试
     *
     * @date 2019/6/25 9:43
     */
    @Test
    public void testOne() throws IOException {
        FileSystem fileSystem = FileSystems.getDefault();
        for (FileStore fileStore : fileSystem.getFileStores()) {
            // fileStore.getTotalSpace() 获取磁盘总大小
            System.err.println("磁盘【" + fileStore.name() + "】总大小 -> " + FileUtils.byteCountToDisplaySize(fileStore.getTotalSpace()));
            // fileStore.getUsableSpace() 获取磁盘可用大小
            System.err.println("磁盘【" + fileStore.name() + "】可用大小 -> " + FileUtils.byteCountToDisplaySize(fileStore.getUsableSpace()));
        }
    }

}
