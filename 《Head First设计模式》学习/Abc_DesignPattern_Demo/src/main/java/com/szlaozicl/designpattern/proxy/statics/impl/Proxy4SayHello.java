package com.szlaozicl.designpattern.proxy.statics.impl;

import com.szlaozicl.designpattern.author.JustryDeng;
import com.szlaozicl.designpattern.proxy.statics.SayHello;

/**
 * 代理对象类(代理SayHelloImpl)
 *
 * @author {@link JustryDeng}
 * @date 2020/5/21 17:30:59
 */
public class Proxy4SayHello implements SayHello {

    /** 面向接口编程, 这里我们不直接持有SayHelloImpl, 而是持有SayHello */
    private final SayHello target;

    public Proxy4SayHello(SayHello target) {
        this.target = target;
    }

    @Override
    public void hello() {
        // 前切入逻辑
        System.err.println(" hello()之前, 让俺老邓先喝口水~");
        // 调用源对象的方法
        target.hello();
        // 后切入逻辑
        System.err.println(" 果然! 喝口水再hello(), 就是这么流畅~");
    }
}
