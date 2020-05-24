package com.szlaozicl.designpattern.proxy.dynamic.cglib;

import com.szlaozicl.designpattern.author.JustryDeng;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * MethodInterceptor for CGLIB
 *
 * @author {@link JustryDeng}
 * @date 2020/5/24 11:59:31
 */
public class MethodInterceptor4Proxy implements MethodInterceptor {

    private MethodInterceptor4Proxy() { }

    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.err.println("前戏");
        Object object = methodProxy.invokeSuper(proxy, args);
        System.err.println("后戏");
        return object;
    }

    /**
     * 获取到代理对象
     * 注: 在程序执行时, 代理对象会将具体的逻辑委托给MethodInterceptor#intercept进行
     *
     * @param target
     *            被代理对象的类
     *
     * @return 代理对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T getProxyInstance(Class<?> target) {
        // 利用Enhancer来创建代理类对象
        Enhancer en = new Enhancer();
        // 设置要被代理的对象的所属类
        en.setSuperclass(target);
        // 设置回调函数(cglib会将具体逻辑委托给MethodInterceptor#intercept进行执行)
        en.setCallback(new MethodInterceptor4Proxy());
        // 创建代理类对象
        // 注:默认使用的是无参构造、也可以使用有参构造create(Class[] argumentTypes, Object[] arguments)
        return (T)en.create();
    }
}
