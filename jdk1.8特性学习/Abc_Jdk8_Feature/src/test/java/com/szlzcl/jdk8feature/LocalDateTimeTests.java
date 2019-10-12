package com.szlzcl.jdk8feature;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * LocalDateTime使用学习
 *
 * 注:LocalDate、LocalTime、LocalDateTime这三者的用法几乎一样
 *
 * @author JustryDeng
 * @date 2019/8/10 18:29
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class LocalDateTimeTests {

    /**
     * 获取 当前(本地)时间
     */
    @Test
    public void test1() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
    }


    /**
     * 获取 年、月、日、时、分、秒
     */
    @Test
    public void test2() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("当前时间 -> 【" + localDateTime + "】");
        // -> 获取年(等价于: .get(ChronoField.YEAR))
        System.out.println("----------------年相关");
        int year = localDateTime.getYear();
        System.out.println("\tgetYear()结果是int -> " + year);


        // -> 获取月
        System.out.println("----------------月相关");
        // 等价于: .get(ChronoField.MONTH_OF_YEAR)
        int monthValue = localDateTime.getMonthValue();
        System.out.println("\tgetMonthValue()结果是int -> " + monthValue);
        Month month = localDateTime.getMonth();
        System.out.println("\tgetMonth()结果是Month -> " + month);

        // -> 获取日
        System.out.println("----------------日相关");
        // 等价于: .get(ChronoField.DAY_OF_YEAR)
        int dayOfYear = localDateTime.getDayOfYear();
        System.out.println("\tgetDayOfYear()结果是int -> " + dayOfYear);
        // 等价于: .get(ChronoField.DAY_OF_MONTH)
        int dayOfMonth = localDateTime.getDayOfMonth();
        System.out.println("\tgetDayOfMonth()结果是int -> " + dayOfMonth);
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        System.out.println("\tgetDayOfWeek()结果是DayOfWeek -> " + dayOfWeek);

        System.out.println("----------------时、分、秒相关");
        // -> 获取时(等价于: .get(ChronoField.HOUR_OF_DAY))
        int hour = localDateTime.getHour();
        System.out.println("\tgetHour()结果是int -> " + hour);
        // -> 获取分(等价于: .get(ChronoField.MINUTE_OF_HOUR))
        int minute = localDateTime.getMinute();
        System.out.println("\tgetMinute()结果是int -> " + minute);
        // -> 获取秒(等价于: .get(ChronoField.SECOND_OF_MINUTE))
        int second = localDateTime.getSecond();
        System.out.println("\tgetSecond()结果是int -> " + second);
    }

    /**
     * 字符串 与 日期  互相转换
     */
    @Test
    public void test3() {
        // 设置格式
        // 注:DateTimeFormatter是线程安全的
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime now = LocalDateTime.now();

        // LocalDateTime 转换为 String
        // 等价于: String result = dateTimeFormatter.format(now);
        String result = now.format(dateTimeFormatter);
        System.out.println(result);

        // String 转换为 LocalDateTime
        String oneDayAfter = "1994-02-05 11:33:46";
        // 等价于: LocalDateTime localDateTime =  LocalDateTime.from(dateTimeFormatter.parse(oneDayAfter));
        LocalDateTime localDateTime = LocalDateTime.parse(oneDayAfter, dateTimeFormatter);
        System.out.print(localDateTime.getYear() + "年\t");
        System.out.print(localDateTime.getMonthValue() + "月\t");
        System.out.print(localDateTime.getDayOfMonth() + "日\t");
        System.out.print(localDateTime.getHour() + "时\t");
        System.out.print(localDateTime.getMinute() + "分\t");
        System.out.print(localDateTime.getSecond() + "秒");
    }

    /**
     * 比较两个LocalDateTime之间的早晚
     */
    @Test
    public void test4() {
        // 获取格式化器
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime a = LocalDateTime.parse("2019-08-13 00:00:00", dateTimeFormatter);
        System.out.println("a -> 【" + a + "】");
        LocalDateTime b = LocalDateTime.parse("2019-08-12 00:00:00", dateTimeFormatter);
        System.out.println("b -> 【" + b + "】");

        /// 方式一-------------------------------------- compareTo
        // 比较两个LocalDateTime之间的早晚， a.compareTo(b),
        //     若: a - b > 0 则返回 1， 表示: a晚于b
        //     若: a - b = 0 则返回 0   表示: a等于b
        //     若: a - b < 0 则返回 -1  表示: a早于b
        System.out.println("\n方式一----------------- compareTo");
        int result = a.compareTo(b);
        System.out.println(result > 0 ? "a晚于b" : result == 0 ? "a等于b" : "a早于b");

        /// 方式二-------------------------------------- isAfter、isBefore、isEqual
        System.out.println("\n方式二----------------- isAfter、isBefore、isEqual");
        boolean isAfterResult = a.isAfter(b);
        System.out.println("a晚于b? --- " + isAfterResult);
        boolean isBeforeResult = a.isBefore(b);
        System.out.println("a早于b? --- " + isBeforeResult);
        boolean isEqualResult = a.isEqual(b);
        System.out.println("a等于? --- " + isEqualResult);
    }

    /**
     * 日期的 加 减
     */
    @Test
    public void test5() {
        // 获取当前时间
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("当前时间是 -> " + localDateTime);

        // + 1 天
        LocalDateTime plusRes = localDateTime.plus(1, ChronoUnit.DAYS);
        System.out.println("一天后是  -> " + plusRes);

        // - 2 天
        LocalDateTime minusRes = localDateTime.minus(2, ChronoUnit.DAYS);
        System.out.println("两天前是  -> " + minusRes);
    }

    /**
     * LocalDateTime 与 时间偏移量 的相互转换
     *
     * 注:偏移量从 1970-01-01 00:00:00 开始算
     */
    @Test
    public void test6() {
        System.out.println(" System.currentTimeMillis : " + System.currentTimeMillis());
        // 获取当前时间
        LocalDateTime localDateTime = LocalDateTime.now();

        // LocalDateTime  =>  时间偏移量(毫秒)
        long offSetMilliSecond = localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println(" LocalDateTime  =>  时间偏移量(毫秒) : " + offSetMilliSecond);

        // LocalDateTime  =>  时间偏移量(秒)
        long offSetSecond = localDateTime.toEpochSecond(ZoneOffset.of("+8"));
        System.out.println(" LocalDateTime  =>  时间偏移量(秒) : " + offSetSecond);

        // -> 时间偏移量(毫秒)  =>  LocalDateTime
        // 第一步，先获得当前指定UTC的瞬时时间
        //       注意:此时并不需要将Instant再转换为执行北京时间的 瞬时时间
        Instant tmpStant1 = Instant.ofEpochMilli(System.currentTimeMillis());
        // 第二步:通过(指向UTC的)瞬时时间 + 时区,得到(指向本地的)LocalDateTime
        LocalDateTime localDateTime1 = LocalDateTime.ofInstant(tmpStant1, ZoneId.systemDefault());
        System.out.println(" 时间偏移量(毫秒)  =>  LocalDateTime : " + localDateTime1);

        // -> 时间偏移量(秒)  =>  LocalDateTime
        // 第一步，先获得当前指定UTC的瞬时时间
        //       注意:此时并不需要将Instant再转换为执行北京时间的 瞬时时间
        Instant tmpStant2 = Instant.ofEpochSecond(System.currentTimeMillis() / 1000);
        // 第二步:通过(指向UTC的)瞬时时间 + 时区,得到(指向本地的)LocalDateTime
        LocalDateTime localDateTime2 = LocalDateTime.ofInstant(tmpStant2, ZoneId.systemDefault());
        System.out.println(" 时间偏移量(秒)  =>  LocalDateTime : " + localDateTime2);
    }

    /**
     * 计算两个LocalDateTime之间的时间差
     */
    @Test
    public void test7() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime a = LocalDateTime.parse("1994-02-05 11:23:30", dateTimeFormatter);
        System.out.println("a -> 【" + a + "】");
        LocalDateTime b = LocalDateTime.parse("2019-08-11 13:45:24", dateTimeFormatter);
        System.out.println("b -> 【" + b + "】");

        /*
         * 注意: a.until(b, xxx), 是计算a到b的时间差的， 是用 “b - a”的，
         *      如果a.until(b, xxx)为正数，那么b.until(a, xxx)就一定为
         *      负数
         */
        // 相差 年 数
        long diffYear = a.until(b, ChronoUnit.YEARS);
        System.out.println(" a 到 b， 相差年数为 : " + diffYear);
        // 相差 月 数
        long diffMonth = a.until(b, ChronoUnit.MONTHS);
        System.out.println(" a 到 b， 相差月份数为 : " + diffMonth);
        // 相差 日 数
        long diffDay = a.until(b, ChronoUnit.DAYS);
        System.out.println(" a 到 b， 相差天数为 : " + diffDay);
        // 相差 时 数
        long diffHour = a.until(b, ChronoUnit.HOURS);
        System.out.println(" a 到 b， 相差月小时数为 : " + diffHour);
        // 相差 分 数
        long diffMinutes = a.until(b, ChronoUnit.MINUTES);
        System.out.println(" a 到 b， 相差分钟数为 : " + diffMinutes);
        // 相差 秒 数
        long diffSeconds = a.until(b, ChronoUnit.SECONDS);
        System.out.println(" a 到 b， 相差秒数为 : " + diffSeconds);
        // 相差 毫秒 数
        long diffMilliSeconds = a.until(b, ChronoUnit.MILLIS);
        System.out.println(" a 到 b， 相差毫秒数为 : " + diffMilliSeconds);

    }
}