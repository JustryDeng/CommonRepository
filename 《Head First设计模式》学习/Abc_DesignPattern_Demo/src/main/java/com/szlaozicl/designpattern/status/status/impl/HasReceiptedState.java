package com.szlaozicl.designpattern.status.status.impl;

import com.szlaozicl.designpattern.status.status.BillState;

/**
 * 已收货状态
 *
 * 注: 已收货状态是结束状态, 不能做任何操作。
 *
 * @author JustryDeng
 * @date 2020/4/6 15:46:03
 */
public class HasReceiptedState implements BillState {

    @Override
    public String name() {
        return "已收货状态";
    }
}