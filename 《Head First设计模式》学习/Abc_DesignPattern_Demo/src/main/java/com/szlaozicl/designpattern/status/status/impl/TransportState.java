package com.szlaozicl.designpattern.status.status.impl;

import com.szlaozicl.designpattern.status.GoodsLogisticsInfo;
import com.szlaozicl.designpattern.status.status.BillState;
import lombok.RequiredArgsConstructor;

/**
 * 运输状态
 *
 * 注: 在运输状态下，唯一能做的就是 => 等运输完毕，然后让(快递小哥)派送
 *
 * @author JustryDeng
 * @date 2020/4/6 16:11:13
 */
@RequiredArgsConstructor
public class TransportState implements BillState {

    private final GoodsLogisticsInfo goodsLogisticsInfo;

    @Override
    public String name() {
        return "运输状态";
    }

    @Override
    public void dispatch() {
        System.out.println("运输完毕，(快递小哥)派送");
        // 将状态置为 派送状态
        goodsLogisticsInfo.setCurrentBillState(goodsLogisticsInfo.getDispatchState());
    }

}
