package com.pingan.retry.spring;

import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Spring Retry基于动态代理
 *
 * @author JustryDeng
 * @date 2020/2/25 21:40:11
 */
@Component
public class AbcRemoteCall {

    private int times = 0;

    /// --------------------------------------------------------- 编程式使用spring-retry 示例

    @SuppressWarnings("all")
    public Object retryCoding() throws Throwable {
        /*
         * spring-retry1.3.x版本开始提供建造者模式支持了，可
         * 详见https://github.com/spring-projects/spring-retry
         */
        RetryTemplate template = new RetryTemplate();

        // 设置重试策略
        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
        simpleRetryPolicy.setMaxAttempts(5);
        template.setRetryPolicy(simpleRetryPolicy);

        // 执行
        Object result = template.execute(
                new RetryCallback<Object, Throwable>() {
                    @Override
                    public Object doWithRetry(RetryContext context) throws Throwable {
                        // 第一次请求，不算重试， 所以第一次请求时，context.getRetryCount()值为0
                        throw new RuntimeException("第" + (context.getRetryCount() + 1) + "次调用失败!");
                    }
                },
                new RecoveryCallback<Object>() {
                    @Override
                    public Object recover(RetryContext context) throws Exception {
                        Throwable lastThrowable = context.getLastThrowable();
                        return "走recover逻辑了! \t异常类是" + lastThrowable.getClass().getName()
                                + "\t异常信息是" + lastThrowable.getMessage();
                    }
                });
        System.out.println(result);
        return result;
    }

    /// --------------------------------------------------------- 默认项测试

    /**
     * - 默认最多请求3次(注: 重试两次, 加上本身那一次一起3次)
     *
     * - 默认在所有异常的情况下，都进行重试; 若重试的这几次都没有成功,都出现了异常,
     *   那么最终抛出的是最后一次重试时出现的异常
     */
    @Retryable
    public String methodOne() {
        times++;
        int i = ThreadLocalRandom.current().nextInt(10);
        if (i < 9) {
            if (times == 3) {
                throw new IllegalArgumentException("最后一次重试时, 发生了IllegalArgumentException异常");
            }
            throw new RuntimeException("times=" + times + ", 当前i的值为" + i);
        }
        return "在第【" + times + "】次调用时, 调通了!";
    }

    /// --------------------------------------------------------- include、exclude测试

    /**
     * - 在尝试次数内，
     *      1. 如果抛出的是include里面的异常(或其子类异常)，那么仍然会继续重试
     *      2. 如果抛出的是include范围外的异常(或其子类异常)  或者 抛出的是
     *         exclude里面的异常(或其子类异常), 那么不再继续重试，直接抛出异常
     *
     *      注意: 若抛出的异常即是include里指定的异常的子类，又是exclude里指定的异常的子类,那么
     *            判断当前异常是按include走，还是按exclude走，需要根据【更短路径原则】。
     *            如本例所示, RuntimeException 是 IllegalArgumentException的超类，
     *                      IllegalArgumentException 又是 NumberFormatException的超类,
     *                      此时因为IllegalArgumentException离NumberFormatException“路径更短”,
     *                      所以抛出的NumberFormatException按照IllegalArgumentException算,走include。
     */
    @Retryable(include = {IllegalArgumentException.class}, exclude = {RuntimeException.class})
    public String methodTwo() {
        times++;
        /// if (times == 1) {
        ///     throw new IllegalArgumentException("times=" + times + ", 发生的异常是IllegalArgumentException");
        /// }
        /// if (times == 2) {
        ///     throw new RuntimeException("times=" + times + ", 发生的异常是RuntimeException");
        /// }
        if (times == 1) {
            throw new NumberFormatException("times=" + times + ", 发生的异常是IllegalArgumentException的子类");
        }
        if (times == 2) {
            throw new ArithmeticException("times=" + times + ", 发生的异常是RuntimeException的子类");
        }
        return "在第【" + times + "】次调用时, 调通了!";
    }

    /**
     * - 在尝试次数内，
     *      如果抛出的是exclude里面的异常(或其子类异常)，那么不再继续重试，直接抛出异常
     *      如果抛出的是include里面的异常(或其子类异常)，那么仍然会继续重试
     */
    @Retryable(include = {RuntimeException.class}, exclude = {IllegalArgumentException.class})
    public String methodTwoAlpha() {
        times++;
        if (times == 1) {
            throw new ArithmeticException("times=" + times + ", 发生的异常是RuntimeException的子类");
        }
        if (times == 2) {
            throw new NumberFormatException("times=" + times + ", 发生的异常是IllegalArgumentException的子类");
        }
        return "在第【" + times + "】次调用时, 调通了!";
    }

    /**
     * - 在尝试次数内，
     *      如果抛出的是include范围外的异常(或其子类异常)，那么不再继续重试，直接抛出异常
     *      如果抛出的是include里面的异常(或其子类异常)，那么仍然会继续重试
     */
    @Retryable(include = {IllegalArgumentException.class})
    public String methodTwoBeta() {
        times++;
        if (times == 1) {
            throw new NumberFormatException("times=" + times + ", 发生的异常是IllegalArgumentException的子类");
        }
        if (times == 2) {
            throw new ArithmeticException("times=" + times + ", 发生的异常是RuntimeException的子类");
        }
        return "在第【" + times + "】次调用时, 调通了!";
    }

    /// --------------------------------------------------------- maxAttempts测试

    /**
     * maxAttempts指定最大尝试次数, 默认值为3.
     * 注:连本身那一次也会被算在内(若值为5, 那么最多重试4次， 算上本身那一次5次)
     */
    @Retryable(maxAttempts = 5)
    public String methodThere() {
        times++;
        if (times < 5) {
            throw new RuntimeException("times=" + times + ", 发生的异常是RuntimeException");
        }
        return "在第【" + times + "】次调用时, 调通了!";
    }

    /// --------------------------------------------------------- backoff测试

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * Backoff用于指定 重试时的退避策略
     * - @Retryable 或 @Retryable(backoff = @Backoff()), 那么默认延迟 1000ms后重试
     *   注:第一次请求时,是马上进行的,是不会延迟的
     *
     *    效果如:
     *       times=1, 时间是12:02:04
     *       times=2, 时间是12:02:05
     *       times=3, 时间是12:02:06
     */
    @Retryable(backoff = @Backoff())
    public String methodFive() {
        times++;
        System.err.println("times=" + times + ", 时间是" + dateTimeFormatter.format(LocalTime.now()));
        throw new RuntimeException("times=" + times + ", 发生的异常是RuntimeException");
    }

    /**
     * - delay: 延迟多久后,再进行重试。
     *   注:第一次请求时,是马上进行的,是不会延迟的
     *
     *    效果如:
     *       times=1, 时间是11:46:36
     *       times=2, 时间是11:46:41
     *       times=3, 时间是11:46:46
     */
    @Retryable(backoff = @Backoff(delay = 5000))
    public String methodFiveAlpha() {
        times++;
        System.err.println("times=" + times + ", 时间是" + dateTimeFormatter.format(LocalTime.now()));
        throw new RuntimeException("times=" + times + ", 发生的异常是RuntimeException");
    }

    /**
     * 如果不想延迟, 那么需要指定value和delay同时为0
     * 注:原因可详见javadoc 或 源码
     *
     *    效果如:
     *       times=1, 时间是12:05:44
     *       times=2, 时间是12:05:44
     *       times=3, 时间是12:05:44
     */
    @Retryable(backoff = @Backoff(value = 0, delay = 0))
    public String methodFiveBeta() {
        times++;
        System.err.println("times=" + times + ", 时间是" + dateTimeFormatter.format(LocalTime.now()));
        throw new RuntimeException("times=" + times + ", 发生的异常是RuntimeException");
    }

    /**
     * - delay: 延迟多久后,再进行重试。
     * - multiplier: 乘数因子
     *
     *   延迟时间 = delay * (multiplier ^ (n - 1)) , 其中n为第几次重试， n >= 1, 这里 ^ 为次方
     *
     * 注:第一次请求时,是马上进行的,是不会延迟的
     * 注:第二次请求时对应第一次重试
     *
     *    效果如:
     *       times=1, 时间是12:09:14
     *       times=2, 时间是12:09:17
     *       times=3, 时间是12:09:23
     *       times=4, 时间是12:09:35
     *       times=5, 时间是12:09:59
     *     可知，延迟时间越来越大，分别是: 3 6 12 24
     */
    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 3000, multiplier = 2))
    public String methodFiveGamma() {
        times++;
        System.err.println("times=" + times + ", 时间是" + dateTimeFormatter.format(LocalTime.now()));
        throw new RuntimeException("times=" + times + ", 发生的异常是RuntimeException");
    }

}
