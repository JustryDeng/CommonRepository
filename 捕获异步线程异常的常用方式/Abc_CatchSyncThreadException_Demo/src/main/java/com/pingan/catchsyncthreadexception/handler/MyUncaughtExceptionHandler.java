package com.pingan.catchsyncthreadexception.handler;

import com.pingan.catchsyncthreadexception.author.JustryDeng;
import lombok.extern.slf4j.Slf4j;

/**
 * 异步线程异常处理器
 *
 * @author {@link JustryDeng}
 * @date 2020/4/30 22:38:17
 */
@Slf4j
public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.info("thread [{}] occur exception", t.getName(), e);
    }
}
