package com.pingan.catchsyncthreadexception.threads;


/**
 * Thread创建线程
 *
 * @author {@link JustryDeng}
 * @date 2020/4/30 22:44:43
 */
@SuppressWarnings("all")
public class MyThread extends Thread {

    @Override
    public void run() {
        int a = 1 / 0;
    }
}
