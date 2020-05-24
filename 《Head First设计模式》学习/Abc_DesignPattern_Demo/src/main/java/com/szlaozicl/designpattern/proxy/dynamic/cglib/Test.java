package com.szlaozicl.designpattern.proxy.dynamic.cglib;

import com.szlaozicl.designpattern.author.JustryDeng;

/**
 * CGLIB动态代理测试
 *
 * @author {@link JustryDeng}
 * @date 2020/5/24 12:04:30
 */
public class Test {

    public static void main(String[] args) {
        // cglib 基本测试
        System.out.println("cglib 基本测试:");
        SayHey sayHeyProxy = MethodInterceptor4Proxy.getProxyInstance(SayHey.class);
        sayHeyProxy.hey();

        System.out.println();
        // 测试cglib不会代理final方法
        System.out.println("测试cglib不会代理final方法:");
        Other1 other1Proxy = MethodInterceptor4Proxy.getProxyInstance(Other1.class);
        other1Proxy.abc();
        other1Proxy.xyz();
    }
}
