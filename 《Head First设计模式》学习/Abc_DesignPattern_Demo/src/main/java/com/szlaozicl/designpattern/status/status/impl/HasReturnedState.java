package com.szlaozicl.designpattern.status.status.impl;

import com.szlaozicl.designpattern.status.status.BillState;

/**
 * 已退货状态
 *
 * 注: 已退货状态是结束状态, 不能做任何操作。
 *
 * @author JustryDeng
 * @date 2020/4/6 15:46:03
 */
public class HasReturnedState implements BillState {

    @Override
    public String name() {
        return "已退货状态";
    }
}