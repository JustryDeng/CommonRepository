package com.szlaozicl.designpattern.status.status.impl;

import com.szlaozicl.designpattern.status.GoodsLogisticsInfo;
import com.szlaozicl.designpattern.status.status.BillState;
import lombok.RequiredArgsConstructor;

/**
 * 已购物状态
 *
 * 注: 在已购物状态下，唯一能做的就是 => 让(商家)发货
 *
 * @author JustryDeng
 * @date 2020/4/6 15:46:03
 */
@RequiredArgsConstructor
public class HasShoppedState implements BillState {

    private final GoodsLogisticsInfo goodsLogisticsInfo;

    @Override
    public String name() {
        return "已购物状态";
    }

    @Override
    public void shipping() {
        System.out.println("(商家)发货");
        // 将状态置为 已揽件状态
        goodsLogisticsInfo.setCurrentBillState(goodsLogisticsInfo.getHasShippedState());
    }

}
