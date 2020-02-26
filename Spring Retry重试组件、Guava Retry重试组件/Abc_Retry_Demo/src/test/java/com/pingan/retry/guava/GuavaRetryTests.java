package com.pingan.retry.guava;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * Guava的retry组件 测试
 *
 * @author JustryDeng
 * @date 2020/2/25 21:43:21
 */
@Slf4j
@SpringBootTest
@RequiredArgsConstructor
class GuavaRetryTests {

    @Test
    void contextLoads() {
        XyzRemoteCall.jd();
    }

}
