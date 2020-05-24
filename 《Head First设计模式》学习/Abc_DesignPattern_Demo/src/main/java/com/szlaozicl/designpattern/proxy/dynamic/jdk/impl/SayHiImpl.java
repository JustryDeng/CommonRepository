package com.szlaozicl.designpattern.proxy.dynamic.jdk.impl;

import com.szlaozicl.designpattern.author.JustryDeng;
import com.szlaozicl.designpattern.proxy.dynamic.jdk.SayHi;

import java.io.Serializable;

/**
 * impl for SayHi
 *
 * 注: 为了方便后面说明, 这里不妨随便再实现一个Serializable接口好了
 *
 * @author {@link JustryDeng}
 * @date 2020/5/24 9:34:23
 */
public class SayHiImpl implements SayHi, Serializable {

    @Override
    public void hi() {
        System.err.println(" 这里的山路十八弯~这里的水路九连环~");
    }
}
