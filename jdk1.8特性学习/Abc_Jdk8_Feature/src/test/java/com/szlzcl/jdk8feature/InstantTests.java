package com.szlzcl.jdk8feature;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * 瞬时时间类Instant学习
 *
 * 注:虽然本人认为 Instant相对于LocalDateTime或者ZonedDateTime来说，用的比较少，
 *    但这里也给出简单的演示。
 *
 * 注:本人只给出 本人认为 相对重要的方法示例
 *
 * @author JustryDeng
 * @date 2019/8/10 18:29
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class InstantTests {

    /**
     * 获取当前(北京)的瞬时时间点
     */
    @Test
    public void test1() {
        // 获取当前Instant
        Instant instant = Instant.now(Clock.systemDefaultZone());

        // 由于北京时间 比 UTC时间晚8小时，所以我们需要得出北京的瞬时时间，还需要加上8小时
        // 提示:虽然可以通过LocalDateTime、ZonedDateTime更轻易的获得当前时间，但是本类
        //      既然是为了演示Instant，那么就只演示通过Instant如何获得当前北京瞬时时间
        instant = instant.plus(8, ChronoUnit.HOURS);
        // 输出形如: 2019-08-11T00:00:33.225Z
        // 注:不建议使用Instant查看当前时间点
        System.out.println(instant);
    }



    /**
     * Instant 与 时间偏移量 的相互转换
     *
     * 注:从1970-01-01 00:00:00开始计算偏移
     */
    @Test
    public void  test2() {
        System.out.println(" System.currentTimeMillis : " + System.currentTimeMillis());

        Instant instant = Instant.now(Clock.systemDefaultZone());

        // -> Instant  =>  时间偏移量(毫秒)
        long offSetMilliSecond = instant.toEpochMilli();
        System.out.println(" Instant  =>  时间偏移量(毫秒) : " + offSetMilliSecond);

        // -> Instant  =>  时间偏移量(秒)
        long offSetSecond = instant.getEpochSecond();
        System.out.println(" Instant  =>  时间偏移量(秒) : " + offSetSecond);

        // 获取当前时间点(抛开整数秒不算)的纳秒数
        // 如: 12345.12秒，抛开整数秒不算,则为0.12秒，那么instant.getNano()的结
        //     果为 0.12 * 1000_000_000 = 120_000_000
        int nano = instant.getNano();
        System.out.println(" 获取当前时间点(抛开整数秒不算)的纳秒数 : " + nano);

        // -> 时间偏移量(毫秒)  =>  Instant
        Instant instant1 = Instant.ofEpochMilli(System.currentTimeMillis());
        // 将instant从 指向UTC时间 改为 指向北京时间
        instant1 = instant1.plus(8, ChronoUnit.HOURS);
        System.out.println(" 时间偏移量(毫秒)  =>  Instant : " + instant1);

        // -> 时间偏移量(秒)  =>  Instant
        Instant instant2 = Instant.ofEpochSecond(System.currentTimeMillis() / 1000);
        // 将instant从 指向UTC时间 改为 指向北京时间
        instant2 = instant2.plus(8, ChronoUnit.HOURS);
        System.out.println(" 时间偏移量(秒)  =>  Instant : " + instant2);
    }

    /**
     * Instant的时间【加】、【减】
     *
     * 注:下面的输出结果形如:
     *    原(北京瞬时)instant -> 2019-08-11T01:18:46.261Z
     *    原(北京瞬时)instant + 1小时,结果是 -> 2019-08-11T02:18:46.261Z
     *    原(北京瞬时)instant - 2小时,结果是 -> 2019-08-10T23:18:46.261Z
     */
    @Test
    public void  test3() {
        // 获取当前瞬时时间,并转换为北京的瞬时时间
        Instant instant = Instant.now(Clock.systemDefaultZone());
        instant = instant.plus(8, ChronoUnit.HOURS);

        System.out.println("原(北京瞬时)instant -> " + instant);

        // + 1 小时
        Instant plusRes = instant.plus(1, ChronoUnit.HOURS);
        System.out.println("原(北京瞬时)instant + 1小时,结果是 -> " + plusRes);

        // - 2 小时
        Instant minusRes = instant.minus(2, ChronoUnit.HOURS);
        System.out.println("原(北京瞬时)instant - 2小时,结果是 -> " + minusRes);
    }


    /**
     * 判断两个Instant之间谁早谁晚
     *
     * 注:下面的输出结果形如:
     *    瞬时时间点instantOne晚于instantTwo ? --- false
     *    瞬时时间点instantOne早于instantTwo ? --- true
     */
    @Test
    public void  test4() {
        // Instant 一
        Instant instantOne = Instant.now(Clock.systemDefaultZone());
        // Instant 二
        Instant instantTwo = instantOne.plus(8, ChronoUnit.HOURS);

        boolean isAfterResult = instantOne.isAfter(instantTwo);
        System.out.println("瞬时时间点instantOne晚于instantTwo ? --- " + isAfterResult);

        boolean isBeforeResult = instantOne.isBefore(instantTwo);
        System.out.println("瞬时时间点instantOne早于instantTwo ? --- " + isBeforeResult);
    }
}
