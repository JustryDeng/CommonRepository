package com.pingan.catchsyncthreadexception.test;

import com.pingan.catchsyncthreadexception.author.JustryDeng;
import com.pingan.catchsyncthreadexception.executor.MyThreadPoolExecutor;
import com.pingan.catchsyncthreadexception.handler.MyUncaughtExceptionHandler;
import com.pingan.catchsyncthreadexception.threads.MyRunnable;

import java.util.concurrent.*;

/**
 * Runnable测试
 *
 * @author {@link JustryDeng}
 * @date 2020/5/1 12:42:10
 */
@SuppressWarnings("unused")
public class TestRunnable {

    /**
     * 测试
     *
     * 注: 为了排除干扰， 测试某个方法时，可以先将其它方法 注释掉
     */
    public static void main(String[] args) {
        testRunnable1();

        // testRunnable2();

        // testRunnable3();

        // testRunnable4();

        // testRunnable5();
    }

    
    /// ------------------------ 不使用线程池
    
    /**
     * [不使用线程池]方式一: Thread.setDefaultUncaughtExceptionHandler 设置默认的线程异常处理器
     */
    private static void testRunnable1() {
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        MyRunnable myRunnable = new MyRunnable();
        new Thread(myRunnable).start();
    }

    /**
     * [不使用线程池]方式二: Thread实例.setUncaughtExceptionHandler 给当指定线程设置线程异常处理器
     */
    private static void testRunnable2() {
        MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(myRunnable);
        thread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        thread.start();
    }

    /// ------------------------ 使用线程池 execute方法
    
    /**
     * [使用线程池execute]方式一: Thread.setDefaultUncaughtExceptionHandler 设置默认的线程异常处理器
     */
    private static void testRunnable3() {
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        MyRunnable myRunnable = new MyRunnable();
        executorService.execute(myRunnable);
    }

    /**
     * [使用线程池execute]方式二: 自定义ThreadPoolExecutor， 重写afterExecute方法，在afterExecute方法中感知异常
     */
    private static void testRunnable4() {
        ExecutorService executorService = new MyThreadPoolExecutor(5,
                50, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<>(20),
                Thread::new, new ThreadPoolExecutor.AbortPolicy());
        MyRunnable myRunnable = new MyRunnable();
        executorService.execute(myRunnable);
    }

    /// ------------------------ 使用线程池 submit方法

    /**
     * [使用线程池submit]方式一: 自定义ThreadPoolExecutor， 重写afterExecute方法，在afterExecute方法中感知异常
     */
    private static void testRunnable5() {
        ExecutorService executorService = new MyThreadPoolExecutor(5,
                50, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<>(20),
                Thread::new, new ThreadPoolExecutor.AbortPolicy());
        MyRunnable myRunnable = new MyRunnable();
        executorService.submit(myRunnable);
    }

}
