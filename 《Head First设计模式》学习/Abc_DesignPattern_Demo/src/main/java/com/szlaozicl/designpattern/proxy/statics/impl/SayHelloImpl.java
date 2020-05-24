package com.szlaozicl.designpattern.proxy.statics.impl;

import com.szlaozicl.designpattern.author.JustryDeng;
import com.szlaozicl.designpattern.proxy.statics.SayHello;

/**
 * 源对象类
 *
 * @author {@link JustryDeng}
 * @date 2020/5/21 17:30:59
 */
public class SayHelloImpl implements SayHello {

    @Override
    public void hello() {
        System.err.println(" 蚂蚁牙~黑！");
    }
}
