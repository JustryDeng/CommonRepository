package com.szlaozicl.designpattern.status.status.impl;

import com.szlaozicl.designpattern.status.GoodsLogisticsInfo;
import com.szlaozicl.designpattern.status.status.BillState;
import lombok.RequiredArgsConstructor;

/**
 * 派送状态
 * <p>
 * 注: 在派送状态下，唯一能做的就是 => 等待买家签收或拒签
 *
 * @author JustryDeng
 * @date 2020/4/6 15:46:03
 */
@RequiredArgsConstructor
public class DispatchState implements BillState {

    private final GoodsLogisticsInfo goodsLogisticsInfo;

    @Override
    public String name() {
        return "派送状态";
    }

    @Override
    public void signAccept(boolean accept) {
        if (accept) {
            System.out.println("(买家签收, 交易完成。进入)已收货状态");
            // 将状态置为 已收货状态
            goodsLogisticsInfo.setCurrentBillState(goodsLogisticsInfo.getHasReceiptedState());
        } else {
            System.out.println("(买家拒签, 交易完成。进入)已退货状态");
            // 将状态置为 已退货状态
            goodsLogisticsInfo.setCurrentBillState(goodsLogisticsInfo.getHasReturnedState());
        }
    }

}
