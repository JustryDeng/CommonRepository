package com.szlaozicl.designpattern.status.status.impl;

import com.szlaozicl.designpattern.status.GoodsLogisticsInfo;
import com.szlaozicl.designpattern.status.status.BillState;
import lombok.RequiredArgsConstructor;

/**
 * 起始状态
 *
 * 注: 在起始状态下，唯一能做的就是 => 买
 *
 * @author JustryDeng
 * @date 2020/4/6 15:46:03
 */
@RequiredArgsConstructor
public class StartingState implements BillState {

    private final GoodsLogisticsInfo goodsLogisticsInfo;

    @Override
    public String name() {
        return "起始状态";
    }

    @Override
    public void shopping() {
        System.out.println("(买家)购物");
        // 将状态置为 已购物状态
        goodsLogisticsInfo.setCurrentBillState(goodsLogisticsInfo.getHasShoppedState());
    }

}
