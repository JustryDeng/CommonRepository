package com.pingan.catchsyncthreadexception.executor;

import com.pingan.catchsyncthreadexception.author.JustryDeng;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 自定义ThreadPoolExecutor, 重写
 * java.util.concurrent.ThreadPoolExecutor#afterExecute(java.lang.Runnable, java.lang.Throwable)
 * 以实现异步线程异常捕捉
 *
 * @author {@link JustryDeng}
 * @date 2020/4/30 23:56:44
 */
@Slf4j
@SuppressWarnings("unused")
public class MyThreadPoolExecutor extends ThreadPoolExecutor {

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
                                RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    /**
     * 提示: 这里的逻辑只是感知并记录了异常， 并没有抑制异常原来的轨迹，
     *       所以，如果外层还有异常处理的话， 是能正常捕获并处理到异常的。
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);
            if (t == null && r instanceof Future<?>) {
                try {
                    Object result = ((Future<?>) r).get();
                } catch (CancellationException ce) {
                    t = ce;
                } catch (ExecutionException ee) {
                    t = ee.getCause();
                } catch (InterruptedException ie) {
                    // ignore/reset
                    Thread.currentThread().interrupt();
                }
            }
            if (t != null) {
                log.error("async-thread occur exception", t);
            }

    }
}
