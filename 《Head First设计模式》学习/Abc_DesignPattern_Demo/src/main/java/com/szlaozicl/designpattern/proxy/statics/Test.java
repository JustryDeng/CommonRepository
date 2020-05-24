package com.szlaozicl.designpattern.proxy.statics;

import com.szlaozicl.designpattern.author.JustryDeng;
import com.szlaozicl.designpattern.proxy.statics.impl.Proxy4SayHello;
import com.szlaozicl.designpattern.proxy.statics.impl.SayHelloImpl;

/**
 * 静态代理测试
 *
 * @author {@link JustryDeng}
 * @date 2020/5/21 17:38:09
 */
public class Test {

    public static void main(String[] args) {
        // 创建一个被代理对象
        SayHello target = new SayHelloImpl();
        // (通过被代理对象)获得代理对象
        SayHello proxy = new Proxy4SayHello(target);
        // 调用代理对象的hello方法
        proxy.hello();
    }
}
