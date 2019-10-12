package com.szlaozicl.designpattern.observer;

import com.szlaozicl.designpattern.observer.observer.impl.ConcreteObserver;
import com.szlaozicl.designpattern.observer.observer.impl.ForecastObserver;
import com.szlaozicl.designpattern.observer.observer.impl.StatisticsObserver;
import com.szlaozicl.designpattern.observer.subject.impl.WeatherData;

import java.util.concurrent.TimeUnit;

/**
 * 测试 --- 观察者模式
 *
 * @author JustryDeng
 * @date 2019/10/12 13:23
 */
public class Test {

    /**
     * 函数入口
     */
    public static void main(String[] args) throws InterruptedException {
        /// 创建 被观察者
        WeatherData weatherData = new WeatherData();

        /// 创建 观察者们
        ConcreteObserver concreteObserver = new ConcreteObserver();
        ForecastObserver forecastObserver = new ForecastObserver();
        StatisticsObserver statisticsObserver = new StatisticsObserver();

        /// 观察者 进行注册，开始观察 被观察者
        boolean resultOne = weatherData.registerObserver(concreteObserver);
        boolean resultTwo = weatherData.registerObserver(forecastObserver);
        boolean resultThree = weatherData.registerObserver(statisticsObserver);
        System.out.println("concreteObserver注册观察weatherData, " + (resultOne ? "成功!" : "失败!"));
        System.out.println("forecastObserver注册观察weatherData, " + (resultTwo ? "成功!" : "失败!"));
        System.out.println("registerObserver注册观察weatherData, " + (resultThree ? "成功!" : "失败!"));

        /// ------------------------ 测试验证
        // 被观察者 第一次数据发生变化
        TimeUnit.MILLISECONDS.sleep(100);
        System.err.println("\n被观察者 第一次 数据发生变化！");
        weatherData.setMeasurements(10.5F, 0.23F, 50.2F);



        // 被观察者 第二次数据发生变化
        TimeUnit.MILLISECONDS.sleep(100);
        System.err.println("被观察者 第二次 数据发生变化！");
        weatherData.setMeasurements(27.5F, 0.68F, 100.2F);

        /// 观察者 取消观察
        boolean resultFour = weatherData.removeObserver(concreteObserver);
        boolean resultFive = weatherData.removeObserver(forecastObserver);
        boolean resultSix = weatherData.removeObserver(statisticsObserver);
        System.out.println("concreteObserver取消观察weatherData, " + (resultFour ? "成功!" : "失败!"));
        System.out.println("forecastObserver取消观察weatherData, " + (resultFive ? "成功!" : "失败!"));
        System.out.println("registerObserver取消观察weatherData, " + (resultSix ? "成功!" : "失败!"));

        // 被观察者 第三次数据发生变化
        TimeUnit.MILLISECONDS.sleep(100);
        System.err.println("\n被观察者 第三次 数据发生变化！");
        weatherData.setMeasurements(30.5F, 0.88F, 680.2F);
    }
}
