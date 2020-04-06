package com.szlaozicl.designpattern.status.status.impl;

import com.szlaozicl.designpattern.status.GoodsLogisticsInfo;
import com.szlaozicl.designpattern.status.status.BillState;
import lombok.RequiredArgsConstructor;

/**
 * 已发货状态
 *
 * 注: 在已发货状态下，唯一能做的就是 => 让(收寄中心)揽件
 *
 * @author JustryDeng
 * @date 2020/4/6 15:46:03
 */
@RequiredArgsConstructor
public class HasShippedState implements BillState {

    private final GoodsLogisticsInfo goodsLogisticsInfo;

    @Override
    public String name() {
        return "已发货状态";
    }

    @Override
    public void packaging() {
        System.out.println("(收寄中心)揽件");
        // 将状态置为 已发货状态
        goodsLogisticsInfo.setCurrentBillState(goodsLogisticsInfo.getHasPackagedState());
    }

}
