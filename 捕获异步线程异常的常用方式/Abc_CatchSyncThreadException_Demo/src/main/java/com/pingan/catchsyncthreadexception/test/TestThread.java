package com.pingan.catchsyncthreadexception.test;

import com.pingan.catchsyncthreadexception.author.JustryDeng;
import com.pingan.catchsyncthreadexception.executor.MyThreadPoolExecutor;
import com.pingan.catchsyncthreadexception.factory.MyThreadFactory;
import com.pingan.catchsyncthreadexception.handler.MyUncaughtExceptionHandler;
import com.pingan.catchsyncthreadexception.threads.MyThread;

import java.util.concurrent.*;

/**
 * Thread测试
 *
 * @author {@link JustryDeng}
 * @date 2020/5/1 12:42:10
 */
@SuppressWarnings("unused")
public class TestThread {

    /**
     * 测试
     *
     * 注: 为了排除干扰， 测试某个方法时，可以先将其它方法 注释掉
     */
    public static void main(String[] args) {
                testThread1();

        //        testThread2();
        //
        //        testThread3();

        //        testThread4();

        //        testThread5();

        //        testThread6();
    }

    
    /// ------------------------ 不使用线程池
    
    /**
     * [不使用线程池]方式一: Thread.setDefaultUncaughtExceptionHandler 设置默认的线程异常处理器
     */
    private static void testThread1() {
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        MyThread myThread = new MyThread();
        myThread.start();
    }

    /**
     * [不使用线程池]方式二: Thread实例.setUncaughtExceptionHandler 给当指定线程设置线程异常处理器
     */
    private static void testThread2() {
        MyThread myThread = new MyThread();
        myThread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        myThread.start();
    }

    /// ------------------------ 使用线程池 execute方法
    
    /**
     * [使用线程池execute]方式一: Thread.setDefaultUncaughtExceptionHandler 设置默认的线程异常处理器
     */
    private static void testThread3() {
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        MyThread myThread = new MyThread();
        executorService.execute(myThread);
    }

    /**
     * [使用线程池execute]方式二: 自定义ThreadPoolExecutor， 重写afterExecute方法，在afterExecute方法中感知异常
     */
    private static void testThread4() {
        ExecutorService executorService = new MyThreadPoolExecutor(5,
                50, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<>(20),
                Thread::new, new ThreadPoolExecutor.AbortPolicy());
        MyThread myThread = new MyThread();
        executorService.execute(myThread);
    }

    /**
     * [使用线程池execute]方式三: 自定义ThreadFactory， 重写newThread方法，给线程工厂生
     *                          产出来的Thread实例设置UncaughtExceptionHandler
     */
    private static void testThread5() {
        ExecutorService executorService = new ThreadPoolExecutor(5,
                50, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<>(20),
                new MyThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        MyThread myThread = new MyThread();
        executorService.execute(myThread);
    }

    /// ------------------------ 使用线程池 submit方法

    /**
     * [使用线程池submit]方式一: 自定义ThreadPoolExecutor， 重写afterExecute方法，在afterExecute方法中感知异常
     */
    private static void testThread6() {
        ExecutorService executorService = new MyThreadPoolExecutor(5,
                50, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<>(20),
                Thread::new, new ThreadPoolExecutor.AbortPolicy());
        MyThread myThread = new MyThread();
        executorService.submit(myThread);
    }

}
