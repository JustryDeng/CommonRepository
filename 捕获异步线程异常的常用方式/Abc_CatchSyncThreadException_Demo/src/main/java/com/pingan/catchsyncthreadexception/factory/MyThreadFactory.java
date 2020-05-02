package com.pingan.catchsyncthreadexception.factory;

import com.pingan.catchsyncthreadexception.author.JustryDeng;
import com.pingan.catchsyncthreadexception.handler.MyUncaughtExceptionHandler;
import org.springframework.lang.NonNull;

import java.util.concurrent.ThreadFactory;

/**
 * 自定义线程工厂
 *
 * @author {@link JustryDeng}
 * @date 2020/5/3 0:31:46
 */
public class MyThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(@NonNull Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        return thread;
    }
}
