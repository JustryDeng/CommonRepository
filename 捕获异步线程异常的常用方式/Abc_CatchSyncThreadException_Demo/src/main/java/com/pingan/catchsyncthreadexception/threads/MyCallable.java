package com.pingan.catchsyncthreadexception.threads;


import java.util.concurrent.Callable;

/**
 * Callable创建线程
 *
 * @author {@link JustryDeng}
 * @date 2020/4/30 22:46:49
 */
@SuppressWarnings("all")
public class MyCallable implements Callable<Object> {

    @Override
    public Object call() throws Exception {
        return 1 / 0;
    }
}