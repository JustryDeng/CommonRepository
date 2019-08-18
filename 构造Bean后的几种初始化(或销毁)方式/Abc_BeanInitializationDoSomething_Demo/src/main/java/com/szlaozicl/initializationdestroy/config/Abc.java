package com.szlaozicl.initializationdestroy.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 初始化、销毁 --- 测试
 *
 * @author JustryDeng
 * @date 2019/8/18 12:25
 */
public class Abc implements InitializingBean, DisposableBean {


    public Abc() {
        System.out.println("构造方法");
    }

    @PostConstruct
    public void testPostConstruct() {
        System.out.println("@PostConstruct标注的方法");
    }

    @PreDestroy
    public void testPreDestroy() {
        System.out.println("@PreDestroy标注的方法");
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("InitializingBean接口的afterPropertiesSet方法");
    }

    @Override
    public void destroy() {
        System.out.println("DisposableBean接口的destroy方法");
    }

    public void myInit() {
        System.out.println("@Bean时，指定的initMethod方法");
    }

    public void myDestroy() {
        System.out.println("@Bean时，指定的destroyMethod方法");
    }

}

