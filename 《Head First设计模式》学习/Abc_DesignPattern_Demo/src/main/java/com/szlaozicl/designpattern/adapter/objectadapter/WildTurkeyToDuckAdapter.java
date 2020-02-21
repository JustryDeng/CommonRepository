package com.szlaozicl.designpattern.adapter.objectadapter;

import com.szlaozicl.designpattern.adapter.Duck;
import com.szlaozicl.designpattern.adapter.WildTurkey;

/**
 * WildTurkey转Duck 的 对象适配器
 *
 * @author JustryDeng
 * @date 2020/2/21 14:51:39
 */
public class WildTurkeyToDuckAdapter implements Duck {

    /** 持有adaptee */
    private final WildTurkey wildTurkey;

    public WildTurkeyToDuckAdapter(WildTurkey wildTurkey) {
        this.wildTurkey = wildTurkey;
    }

    @Override
    public void quack() {
        wildTurkey.gobble();
    }

    @Override
    public void fly() {
        wildTurkey.fastFly();
    }
}
