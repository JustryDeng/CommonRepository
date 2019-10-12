package com.szlaozicl.designpattern.observer.observer.impl;

import com.szlaozicl.designpattern.observer.observer.Observer;
import com.szlaozicl.designpattern.observer.other.Display;
import com.szlaozicl.designpattern.observer.subject.Subject;
import com.szlaozicl.designpattern.observer.subject.impl.WeatherData;
import lombok.Data;

/**
 * 天气预报 观察者
 *
 * @author JustryDeng
 * @date 2019/10/12 12:48
 */
@Data
public class ForecastObserver implements Observer, Display {

    /** 温度 */
    private float temperature;

    /** 湿度 */
    private float humidity;

    /** 气压(千帕) */
    private float pressure;

    @Override
    public void update(Subject subject, Object... args) {
        // 简单预估一下
        if (subject instanceof WeatherData) {
            WeatherData weatherData = (WeatherData)subject;
            this.temperature = 2 * weatherData.getTemperature() - this.temperature;
            this.humidity = 2 * weatherData.getHumidity() - this.humidity;
            this.pressure = 2 * weatherData.getPressure() - this.pressure;
        }
        display();
    }

    @Override
    public void display() {
        String info = "\n【天气预报观察者ForecastObserver】观察到的数据是:\n"
                .concat("\t未来一段时间的温度: ").concat(String.valueOf(temperature)).concat("\n")
                .concat("\t未来一段时间的湿度: ").concat(String.valueOf(humidity)).concat("\n")
                .concat("\t未来一段时间的气压: ").concat(String.valueOf(pressure)).concat("\n");
        System.out.println(info);
    }
}
