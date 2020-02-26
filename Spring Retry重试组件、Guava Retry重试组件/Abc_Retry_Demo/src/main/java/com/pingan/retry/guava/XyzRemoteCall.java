package com.pingan.retry.guava;

import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.zip.DataFormatException;

/**
 * 调用远程api的组件
 *
 * @author JustryDeng
 * @date 2020/2/25 21:40:11
 */
public class XyzRemoteCall {

    /**
     * guava retry组件 使用测试
     *
     * 提示:泛型 对应 要返回的数据的类型。
     */
    public static void jd() {
        // 创建callable, 在call()方法里面编写相关业务逻辑
        Callable<Object[]> callable = new Callable<Object[]>() {
            int times = 0;
            @Override
            public Object[] call() throws Exception {
                // business logic
                times++;
                if (times == 1) {
                    throw new RuntimeException();
                }
                if (times ==  2) {
                    throw new Exception();
                }
                // 随机一个数[origin, bound)
                int randomNum = ThreadLocalRandom.current().nextInt(1, 5);
                if (randomNum == 1) {
                    throw new DataFormatException("call()抛出了检查异常DataFormatException");
                } else if (randomNum == 2) {
                    throw new IOException("call()抛出了检查异常IOException");
                } else if (randomNum == 3) {
                    throw new RuntimeException("call()抛出了运行时异常RuntimeException");
                }
                return new Object[]{"邓沙利文", "亨得帅", "邓二洋", "JustryDeng"};
            }
        };

        // 创建重试器
        Retryer<Object[]> retryer = RetryerBuilder.<Object[]>newBuilder()
                /*
                 * 指定什么条件下触发重试
                 *
                 * 注:这里,只要callable中的call方法抛出的异常是Throwable或者
                 *    是Throwable的子类,那么这里都成立，都会进行重试。
                 */
                .retryIfExceptionOfType(Throwable.class)
                /// .retryIfException()
                /// .retryIfRuntimeException()
                /// .retryIfExceptionOfType(@Nonnull Class<? extends Throwable> exceptionClass)
                /// .retryIfException(@Nonnull Predicate<Throwable> exceptionPredicate)
                /// .retryIfResult(@Nonnull Predicate<V> resultPredicate)

                // 设置两次重试之间的阻塞策略(如: 设置线程sleep、设置自旋锁等等)
                ///.withBlockStrategy()
                // 设置监听器 (这个监听器可用于监听每次请求的结果信息， 并作相应的逻辑处理。 如: 统计、预警等等)
                ///.withRetryListener()
                // 设置延时策略, 每次重试前，都要延时一段时间，然后再发起请求。(第一次请求，是不会被延时的)
                ///.withWaitStrategy()
                // 设置停止重试的策略(如:这里设置的是三次请求后, 不再重试)
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                .build();
        try {
            Object[] result = retryer.call(callable);
            System.err.println(Arrays.toString(result));
        /*
         * call()方法抛出的异常会被封装到RetryException或ExecutionException中, 进行抛出
         * 所以在这里，可以通过 e.getCause()获取到call()方法实际抛出的异常
         */
        } catch (RetryException|ExecutionException e) {
            System.err.println("call()方法抛出的异常, 实际是" + e.getCause());
            e.printStackTrace();
        }
    }
}
