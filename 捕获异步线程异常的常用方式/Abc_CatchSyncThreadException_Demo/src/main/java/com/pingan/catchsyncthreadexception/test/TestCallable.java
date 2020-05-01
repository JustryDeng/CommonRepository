package com.pingan.catchsyncthreadexception.test;

import com.pingan.catchsyncthreadexception.author.JustryDeng;
import com.pingan.catchsyncthreadexception.executor.MyThreadPoolExecutor;
import com.pingan.catchsyncthreadexception.threads.MyCallable;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * Callable测试
 *
 * @author {@link JustryDeng}
 * @date 2020/5/1 12:42:10
 */
@Slf4j
@SuppressWarnings("unused")
public class TestCallable {

    /**
     * 测试
     *
     * 注: 为了排除干扰， 测试某个方法时，可以先将其它方法 注释掉
     */
    public static void main(String[] args) throws InterruptedException {
        testCallable1();

        // testCallable2();

        // multiTimesObtainResult();
    }

    
    /// ------------------------ 不使用线程池
    
    /**
     * [不使用线程池]: 在使用Future#get()方法获取Callable#call()的返回结果时， try-catch获取异常
     *
     * 注: 此方式， 若不使用Future#get()获取结果，那么 异步线程的异常信息将会被吞掉
     */
    private static void testCallable1() throws InterruptedException {
        MyCallable myCallable = new MyCallable();
        FutureTask<Object> futureTask = new FutureTask<> (myCallable);
        Thread thread = new Thread(futureTask);
        thread.start();
        try {
            // 在尝试获取结果时，捕获异常
            Object o = futureTask.get();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            log.error(" callable-thread occur exception", cause);
        }
    }

    /// ------------------------ 使用线程池 submit方法

    /**
     * [使用线程池submit]: 自定义ThreadPoolExecutor， 重写afterExecute方法，在afterExecute方法中感知异常
     */
    private static void testCallable2() {
        ExecutorService executorService = new MyThreadPoolExecutor(5,
                50, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<>(20),
                Thread::new, new ThreadPoolExecutor.AbortPolicy());
        MyCallable myCallable = new MyCallable();
        executorService.submit(myCallable);
    }

    /// ------------------ 测试多次获取Callable#call的结果

    /**
     * 测试: 多次获得Callable#call的返回结果
     *
     * 注: 在我们自定义的MyThreadPoolExecutor#afterExecute里面已经获得过Callable#call的返回结果了，
     *     这里再次获取， 验证还是能获取到(即:Callable#call的结果可以反复获取)。
     * 注: 虽然在我们自定义的MyThreadPoolExecutor#afterExecute里感知了异常，但是并不影响其往外抛，所以这个
     *     对Future#get进行try-catch时，仍然能 捕获到callable线程出现的异常的。
     */
    private static void multiTimesObtainResult() throws InterruptedException {
        ExecutorService executorService = new MyThreadPoolExecutor(5,
                50, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<>(20),
                Thread::new, new ThreadPoolExecutor.AbortPolicy());
        // 测试 对Future#get进行try-catch异常,仍然会捕获到异常
        /// Callable<String> callable = () -> "我是返回结果" + 1/0;
        // 测试 结果可以多次获取
        Callable<String> callable = () -> "我是返回结果";
        FutureTask<String> futureTask = new FutureTask<> (callable);
        executorService.submit(futureTask);
        try {
            // 在尝试获取结果时，捕获异常
            String str = futureTask.get();
            System.out.println(str);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            log.error(" callable-thread occur exception", cause);
        }
    }

}
