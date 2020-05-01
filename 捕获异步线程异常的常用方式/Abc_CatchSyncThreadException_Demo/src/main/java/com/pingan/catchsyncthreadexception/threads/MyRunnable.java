package com.pingan.catchsyncthreadexception.threads;


/**
 * Runnable创建线程
 *
 * @author {@link JustryDeng}
 * @date 2020/4/30 22:45:46
 */
@SuppressWarnings("all")
public class MyRunnable implements Runnable {

    @Override
    public void run() {
        int a = 1 / 0;
    }
}