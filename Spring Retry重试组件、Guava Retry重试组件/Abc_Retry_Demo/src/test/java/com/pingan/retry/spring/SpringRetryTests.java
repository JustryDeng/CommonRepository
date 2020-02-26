package com.pingan.retry.spring;

import com.pingan.retry.spring.service.OopService;
import com.pingan.retry.spring.service.impl.OopServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Spring的retry组件 测试
 *
 * @author JustryDeng
 * @date 2020/2/25 21:43:21
 */
@SpringBootTest
class SpringRetryTests {

    @Autowired
    private AbcRemoteCall abcRemoteCall;

    @Autowired
    private QwerRemoteCall qwerRemoteCall;

    @Autowired
    @Qualifier("oopServiceImpl")
    private OopService oopService;

    @Autowired
    @Qualifier("oopServiceImplPlus")
    private OopService oopServicePlus;

    /// --------------------------------------------------------- 默认项测试
    @Test
    void testOne() {
        System.out.println(abcRemoteCall.methodOne());
    }

    /// --------------------------------------------------------- include、exclude测试
    @Test
    void testTwo() {
        System.out.println(abcRemoteCall.methodTwo());
    }

    @Test
    void testTwoAlpha() {
        System.out.println(abcRemoteCall.methodTwoAlpha());
    }

    @Test
    void testTwoBeta() {
        System.out.println(abcRemoteCall.methodTwoBeta());
    }

    /// --------------------------------------------------------- maxAttempts测试
    @Test
    void testThree() {
        System.out.println(abcRemoteCall.methodThere());
    }


    /// --------------------------------------------------------- @Recover测试
    @Test
    void testFour() {
        System.out.println(qwerRemoteCall.methodFour(1, "hello"));
    }

    /// --------------------------------------------------------- @Backoff测试
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Test
    void testFive() {
        System.out.println("发送请求的时间是" + dateTimeFormatter.format(LocalTime.now()));
        System.out.println(abcRemoteCall.methodFive());
    }

    @Test
    void testFiveAlpha() {
        System.out.println("发送请求的时间是" + dateTimeFormatter.format(LocalTime.now()));
        System.out.println(abcRemoteCall.methodFiveAlpha());
    }

    @Test
    void testFiveBeta() {
        System.out.println("发送请求的时间是" + dateTimeFormatter.format(LocalTime.now()));
        System.out.println(abcRemoteCall.methodFiveBeta());
    }

    @Test
    void testFiveGamma() {
        System.out.println("发送请求的时间是" + dateTimeFormatter.format(LocalTime.now()));
        System.out.println(abcRemoteCall.methodFiveGamma());
    }

    /// --------------------------------------------------------- 编程式使用spring-retry
    @Test
    void testCoding() {
        System.out.println("发送请求的时间是" + dateTimeFormatter.format(LocalTime.now()));
        System.out.println(abcRemoteCall.methodFiveGamma());
    }

    /// --------------------------------------------------------- 多AOP重叠可能导致的问题
    @Test
    void multipleAopTest() {
        oopService.multipleAop();
    }

    /// --------------------------------------------------------- 多AOP重叠的避免方式
    @Test
    void multipleAopPlusTest() {
        oopServicePlus.multipleAop();
    }
}
