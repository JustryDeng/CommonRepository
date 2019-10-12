package com.szlaozicl.designpattern.observer.subject.impl;

import com.szlaozicl.designpattern.observer.observer.Observer;
import com.szlaozicl.designpattern.observer.subject.Subject;
import lombok.Data;

import java.util.ArrayList;

/**
 * 气象数据
 *
 * @author JustryDeng
 * @date 2019/10/12 12:09
 */
@Data
public class WeatherData implements Subject {

    /** 温度 */
    private float temperature;

    /** 湿度 */
    private float humidity;

    /** 气压(千帕) */
    private float pressure;

    /**
     * 观察者容器
     *
     * 注: 观察者的 注册 与 注销， 操作此集合即可
     */
    private ArrayList<Observer> observers = new ArrayList<>(8);

    @Override
    public boolean registerObserver(Observer o) {
        return observers.add(o);
    }

    @Override
    public boolean removeObserver(Observer o) {
        int index = observers.indexOf(o);
        if (index >= 0) {
            observers.remove(index);
        }
        return true;
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            // 这里采用: 观察者主动从 被观察者拉数据的方式
            // void update(Subject subject, Object... args);
            observer.update(this);
        }
    }

    /**
     * 当参数值改变时，会调用此方法
     *
     * @date 2019/10/12 12:33
     */
    private void measurementsChanged() {
        notifyObservers();
    }

    /**
     * 设置参数值
     *
     * @param temperature
     *            温度
     * @param humidity
     *            湿度
     * @param pressure
     *            气压
     *
     * @date 2019/10/12 12:33
     */
    public void setMeasurements(float temperature, float humidity, float pressure) {
        boolean haveChange = this.temperature != temperature
                || this.humidity != humidity || this.pressure != pressure;
        if (haveChange) {
            this.temperature = temperature;
            this.humidity = humidity;
            this.pressure = pressure;
            measurementsChanged();
        }

    }

}
