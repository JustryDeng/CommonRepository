package com.szlaozicl.designpattern.status;

import com.szlaozicl.designpattern.status.status.BillState;
import com.szlaozicl.designpattern.status.status.impl.*;
import lombok.Data;

/**
 * 商品物流信息
 *
 * @author JustryDeng
 * @date 2020/4/6 14:47:04
 */
@Data
public class GoodsLogisticsInfo {

    /** 所有可能的状态 */
    private BillState startingState;
    private BillState hasShoppedState;
    private BillState hasShippedState;
    private BillState hasPackagedState;
    private BillState transportState;
    private BillState dispatchState;
    private BillState hasReceiptedState;
    private BillState hasReturnedState;

    /** 当前状态 */
    private BillState currentBillState;

    public GoodsLogisticsInfo() {
        this.startingState = new StartingState(this);
        this.hasShoppedState = new HasShoppedState(this);
        this.hasShippedState = new HasShippedState(this);
        this.hasPackagedState = new HasPackagedState(this);
        this.transportState = new TransportState(this);
        this.dispatchState = new DispatchState(this);
        this.hasReceiptedState = new HasReceiptedState();
        this.hasReturnedState = new HasReturnedState();
        this.currentBillState = this.startingState;
    }

    public void currStateInfo() {
        System.err.println("订单当前处于【" + currentBillState.name() + "】");
    }

    public void shopping() {
        currentBillState.shopping();
    }

    public void shipping() {
        currentBillState.shipping();
    }

    public void packaging() {
        currentBillState.packaging();
    }

    public void transport() {
        currentBillState.transport();
    }

    public void dispatch() {
        currentBillState.dispatch();
    }

    public void signAccept(boolean accept) {
        currentBillState.signAccept(accept);
    }
}
