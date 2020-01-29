package com.szlaozicl.designpattern.singleton;

/**
 * 单例模式
 *
 * @author JustryDeng
 * @date 2020/1/21 13:53
 */
@SuppressWarnings("unused")
public class SingletonObject {

    /**
     * 在多线程环境下，volatile可使各个线程获取到实时的字段信息。
     * <p>
     * 注意: 1.4以及更早版本的java中，volatile会使双重检查加锁失效。
     */
    private volatile static SingletonObject uniqueSingleton;

    /**
     * 私有构造
     */
    private SingletonObject() {
    }

    /**
     * 获取单例
     *
     * @return SingletonObject单例
     * @date 2020/1/21 13:57
     */
    public static SingletonObject getInstance() {
        if (uniqueSingleton == null) {
            synchronized (SingletonObject.class) {
                if (uniqueSingleton == null) {
                    uniqueSingleton = new SingletonObject();
                }
            }
        }
        return uniqueSingleton;
    }

}
