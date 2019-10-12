package com.szlaozicl.designpattern.observer.observer.impl;

import com.szlaozicl.designpattern.observer.observer.Observer;
import com.szlaozicl.designpattern.observer.other.Display;
import com.szlaozicl.designpattern.observer.subject.Subject;
import com.szlaozicl.designpattern.observer.subject.impl.WeatherData;
import lombok.Data;

/**
 * 实时信息 观察者
 *
 * @author JustryDeng
 * @date 2019/10/12 12:48
 */
@Data
public class ConcreteObserver implements Observer, Display {

    /** 温度 */
    private float temperature;

    /** 湿度 */
    private float humidity;

    /** 气压(千帕) */
    private float pressure;

    @Override
    public void update(Subject subject, Object... args) {
        if (subject instanceof WeatherData) {
            WeatherData weatherData = (WeatherData)subject;
            this.temperature = weatherData.getTemperature();
            this.humidity = weatherData.getHumidity();
            this.pressure = weatherData.getPressure();
        }
        display();
    }

    @Override
    public void display() {
        String info = "\n【实时信息观察者ConcreteObserver】观察到的数据是:\n"
                .concat("\t当前温度: ").concat(String.valueOf(temperature)).concat("\n")
                .concat("\t当前湿度: ").concat(String.valueOf(humidity)).concat("\n")
                .concat("\t当前气压: ").concat(String.valueOf(pressure)).concat("\n");
        System.out.println(info);
    }
}
