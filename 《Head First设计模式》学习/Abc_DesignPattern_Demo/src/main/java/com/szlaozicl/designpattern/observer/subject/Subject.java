package com.szlaozicl.designpattern.observer.subject;

import com.szlaozicl.designpattern.observer.observer.Observer;

/**
 * 主题(即:可/被观察者)
 *
 * 注:所有被观察者均应实现此接口。
 *
 * @author JustryDeng
 * @date 2019/10/12 11:53
 */
public interface Subject {

    /**
     * 注册(新增)观察者
     *
     * @param o
     *            要注册的观察者对象
     *
     * @return 是否注册成功
     * @date 2019/10/12 11:53
     */
    boolean registerObserver(Observer o);

    /**
     * 注销(移除)观察者
     *
     * @param o
     *            要注销的观察者对象
     *
     * @return 是否注销成功
     * @date 2019/10/12 11:53
     */
    boolean removeObserver(Observer o);

    /**
     * 通知观察者们
     *
     * @date 2019/10/12 11:53
     */
    void notifyObservers();
}
