package com.aspire.zkclient.lock;

import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

/**
 * 测试类
 *
 * @author JustryDeng
 * @date 2018/12/7 11:19
 */
public class DistributedLockTest {

    /**
     * 程序入口
     */
    public static void main(String[] args) {
        // zookeeper单线程测试
        simpleDistributedLockImplTest();
        // zookeeper多线程测试
        // distributedLockImplTest();
    }

    /**
     * 测试SimpleDistributedLockImpl实现分布式锁
     * <p>
     * 测试原理: 让一个zookeeper客户端率先获取到锁，然后给足够的时间让其他zookeeper客户端
     *          试着去获取锁(会发现:不论给其他时间多长时间，它们都不能获取到锁)，只有等到获
     *          取到锁的zookeeper客户端释放锁之后，其他zookeeper客户端才能获取到锁
     *
     * @date 2018/12/7 11:20
     */
    private static void simpleDistributedLockImplTest() {
        // 分别创建两个客户端，并获取两个SimpleDistributedLockImpl实例
        final MyZkClient zkClientOne = new MyZkClient("10.8.109.32:2181", 5000, 5000, new BytesPushThroughSerializer());
        final SimpleDistributedLockImpl sdliOne = new SimpleDistributedLockImpl(zkClientOne, "/locker");

        final MyZkClient zkClientTwo = new MyZkClient("10.8.109.32:2181", 5000, 5000, new BytesPushThroughSerializer());
        final SimpleDistributedLockImpl sdliTwo = new SimpleDistributedLockImpl(zkClientTwo, "/locker");

        try {
            sdliOne.acquire();
            System.out.println("zkClientOne 获取到锁了!");
            Thread threadOne = new Thread(() -> {
                try {
                    sdliTwo.acquire();
                    System.out.println("zkClientTwo 获取到锁了!");
                    sdliTwo.release();
                    System.out.println("zkClientTwo 释放锁了!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            threadOne.start();
            Thread.sleep(10000);
            sdliOne.release();
            System.out.println("zkClientOne 释放锁了!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试DistributedLockImpl实现分布式锁
     *
     * 注:有几个zookeeper客户端，/locker节点下就应该有几个子节点;子节点个数应只与zookeeper客户端个
     *     数有关，而与客户端的线程数无关
     * <p>
     * 说明:分布式锁，保证的是:同一时刻，最多只能有一个zookeeper客户端获取到锁;但是对于同一个客户端
     *                     内部的众多线程而言，同一时刻同一个zookeeper客户端里，是可以有多个线程
     *                     同时获取到分布式锁(资源)的.
     * <p>
     * 测试原理: 让一个zookeeper客户端的多个线程率先获取到锁，然后让其中的一个线程释放锁，其他线程仍然
     *          持有锁，此时该zookeeper客户端仍然持有着锁;然后给足够的时间让其他zookeeper客户端试着
     *          去获取锁(会发现:不论给其他时间多长时间，它们都不能获取到锁)，只有等到获取到锁的zookeeper
     *          客户端的所有持有锁的线程全部释放完锁之后，这个zookeeper客户端才会真正的释放锁，此时其他
     *          zookeeper客户端才能获取到锁
     *
     * @date 2018/12/7 11:20
     */
    private static void distributedLockImplTest() {
        // 分别创建两个客户端，并获取两个SimpleDistributedLockImpl实例
        final MyZkClient zkClientOne = new MyZkClient("10.8.109.32:2181", 5000, 5000, new BytesPushThroughSerializer());
        final DistributedLockImpl dliOne = new DistributedLockImpl(zkClientOne, "/locker");

        final MyZkClient zkClientTwo = new MyZkClient("10.8.109.32:2181", 5000, 5000, new BytesPushThroughSerializer());
        final DistributedLockImpl dliTwo = new DistributedLockImpl(zkClientTwo, "/locker");
        try {

            Thread threadOne = new Thread(() -> {
                try {
                    System.out.println("zkClientTwo-threadOne 尝试获取锁!");
                    dliTwo.acquire();
                    System.out.println("zkClientTwo-threadOne 获取到锁了!");
                    Thread.sleep(2000);
                    dliTwo.release();
                    System.out.println("zkClientTwo-threadOne 释放锁了!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            Thread threadTwo = new Thread(() -> {
                try {
                    System.out.println("zkClientTwo-threadTwo 尝试获取锁!");
                    dliTwo.acquire();
                    System.out.println("zkClientTwo-threadTwo 获取到锁了!");
                    Thread.sleep(5000);
                    dliTwo.release();
                    System.out.println("zkClientTwo-threadTwo 释放锁了!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            threadOne.start();
            threadTwo.start();
            // 阻塞一秒，保证另一个zookeeper客户端已经获取到锁了，这个zookeeper才去尝试获取锁
            Thread.sleep(2000);
            System.out.println("zkClientOne 尝试获取锁!");
            dliOne.acquire();
            System.out.println("zkClientOne 获取到锁了!");
            dliOne.release();
            System.out.println("zkClientOne 释放锁了!");
            // 为了观察控制台输出，这里sleep 20秒
            Thread.sleep(7000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}