package com.szlaozicl.designpattern.observer.observer;

import com.szlaozicl.designpattern.observer.subject.Subject;

/**
 * 观察者
 *
 * 注:所有观察者均应实现此接口。
 *
 * @author JustryDeng
 * @date 2019/10/12 11:57
 */
public interface Observer {

    /**
     * 更新
     *
     *  注: 当被观察者发生变化时， 有两种方式将数据 从主题传递给观察者
     *      1. 被观察者 主动传参给 观察者， 即: 被观察者主动推数据给观察者
     *      2. 观察者 通过调用 被观察者的一些方法(如:getter)主动获取相关数据, 即:观察者主动从被观察者拉数据
     *
     * @param subject
     *            主题(即:被观察者对象)
     *            注:用于 观察者主动从被观察者拉数据。
     * @param args
     *            被观察者注定push给观察者的参数
     *            注:用于 被观察者主动推数据给观察者。
     *
     * @date 2019/10/12 12:00
     */
    void update(Subject subject, Object... args);
}
