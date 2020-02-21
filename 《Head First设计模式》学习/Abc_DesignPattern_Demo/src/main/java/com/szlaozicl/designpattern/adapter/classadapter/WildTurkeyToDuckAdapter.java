package com.szlaozicl.designpattern.adapter.classadapter;

import com.szlaozicl.designpattern.adapter.Duck;
import com.szlaozicl.designpattern.adapter.WildTurkey;

/**
 * WildTurkey转Duck 的 类适配器
 *
 * @author JustryDeng
 * @date 2020/2/21 14:51:39
 */
public class WildTurkeyToDuckAdapter extends WildTurkey implements Duck {

    @Override
    public void quack() {
        super.gobble();
    }

    @Override
    public void fly() {
        super.fastFly();
    }
}
