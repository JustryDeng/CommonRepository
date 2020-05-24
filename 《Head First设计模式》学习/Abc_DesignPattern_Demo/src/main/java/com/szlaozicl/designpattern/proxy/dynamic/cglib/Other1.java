package com.szlaozicl.designpattern.proxy.dynamic.cglib;

import com.szlaozicl.designpattern.author.JustryDeng;

/**
 * 测试cglib不会代理final方法
 *
 * @author {@link JustryDeng}
 * @date 2020/5/24 11:57:20
 */
public class Other1 {

    public void abc() {
        System.err.println("abc不是final方法");
    }

    public final void xyz() {
        System.err.println("xyz是final方法");
    }
}
