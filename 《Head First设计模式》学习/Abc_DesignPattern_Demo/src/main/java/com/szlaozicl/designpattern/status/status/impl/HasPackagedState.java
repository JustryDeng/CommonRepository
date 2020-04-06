package com.szlaozicl.designpattern.status.status.impl;

import com.szlaozicl.designpattern.status.GoodsLogisticsInfo;
import com.szlaozicl.designpattern.status.status.BillState;
import lombok.RequiredArgsConstructor;

/**
 * 已揽件状态
 *
 * 注: 在已揽件转态下，唯一能做的就是 => 让(物流)开始运输
 *
 * @author JustryDeng
 * @date 2020/4/6 15:46:03
 */
@RequiredArgsConstructor
public class HasPackagedState implements BillState {

    private final GoodsLogisticsInfo goodsLogisticsInfo;

    @Override
    public String name() {
        return "已揽件状态";
    }

    @Override
    public void transport() {
        System.out.println("(物流)开始运输");
        // 将状态置为 运输状态
        goodsLogisticsInfo.setCurrentBillState(goodsLogisticsInfo.getTransportState());
    }

}
