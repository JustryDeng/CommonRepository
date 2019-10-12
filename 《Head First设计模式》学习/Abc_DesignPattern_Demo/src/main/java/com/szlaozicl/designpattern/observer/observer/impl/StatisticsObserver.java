package com.szlaozicl.designpattern.observer.observer.impl;

import com.szlaozicl.designpattern.observer.observer.Observer;
import com.szlaozicl.designpattern.observer.other.Display;
import com.szlaozicl.designpattern.observer.subject.Subject;
import com.szlaozicl.designpattern.observer.subject.impl.WeatherData;
import lombok.Data;

/**
 * 统计信息 观察者
 *
 * @author JustryDeng
 * @date 2019/10/12 12:48
 */
@Data
public class StatisticsObserver implements Observer, Display {

    /** 最小温度 */
    private float minTemperature;

    /** 最大温度 */
    private float maxTemperature;

    /** 最小湿度 */
    private float minHumidity;

    /** 最大湿度 */
    private float maxHumidity;

    /** 最小气压(千帕) */
    private float minPressure;

    /** 最大气压(千帕) */
    private float maxPressure;

    /**
     * 一个简单的计数器，用来计数 第几次统计
     * 注: 这里简单示例，不考虑多线程的情况。
     */
    private int count;

    @Override
    public void update(Subject subject, Object... args) {
        if (!(subject instanceof WeatherData)) {
            count++;
            display();
            return;
        }
        WeatherData weatherData = (WeatherData) subject;
        float curTemperature = weatherData.getTemperature();
        float curHumidity = weatherData.getHumidity();
        float curPressure = weatherData.getPressure();
        if (count == 0) {
            minTemperature = maxTemperature = curTemperature;
            minHumidity = maxHumidity = curHumidity;
            minPressure = maxPressure = curPressure;
            count++;
            display();
            return;
        }
        minTemperature = curTemperature >= minTemperature ? minTemperature : curTemperature;
        minHumidity = curHumidity >= minHumidity ? minHumidity : curHumidity;
        minPressure = curPressure >= minPressure ? minPressure : curPressure;
        maxTemperature = curTemperature >= maxTemperature ? curTemperature : maxTemperature;
        maxHumidity = curHumidity >= maxHumidity ? curHumidity : maxHumidity;
        maxPressure = curPressure >= maxPressure ? curPressure : maxPressure;
        count++;
        display();
    }

    @Override
    public void display() {
        // 简单计算平均值
        float averageTemperature = (minTemperature + maxTemperature) / 2;
        float averageHumidity = (minHumidity + maxHumidity) / 2;
        float averagePressure = (minPressure + maxPressure) / 2;
        String info = "\n【统计信息观察者StatisticsObserver】观察到的数据是:\n"
                .concat("\t最小温度: ").concat(String.valueOf(minTemperature)).concat("\n")
                .concat("\t最大温度: ").concat(String.valueOf(maxTemperature)).concat("\n")
                .concat("\t平均温度: ").concat(String.valueOf(averageTemperature)).concat("\n")
                .concat("\t最小湿度: ").concat(String.valueOf(minHumidity)).concat("\n")
                .concat("\t最大湿度: ").concat(String.valueOf(maxHumidity)).concat("\n")
                .concat("\t平均湿度: ").concat(String.valueOf(averageHumidity)).concat("\n")
                .concat("\t最小气压: ").concat(String.valueOf(minPressure)).concat("\n")
                .concat("\t最大气压: ").concat(String.valueOf(maxPressure)).concat("\n")
                .concat("\t平均气压: ").concat(String.valueOf(averagePressure)).concat("\n");
        System.out.println(info);
    }

}
