package com.szlaozicl.designpattern.status;

/**
 * 状态模式 --- 测试
 *
 * @author JustryDeng
 * @date 2020/4/6 9:33:50
 */
public class Test {

    public static void main(String[] args) {
        GoodsLogisticsInfo gli = new GoodsLogisticsInfo();
        // 测试 - 做当前状态下能做的事
        gli.currStateInfo();
        gli.shopping();
        gli.currStateInfo();
        gli.shipping();
        gli.currStateInfo();
        gli.packaging();
        gli.currStateInfo();
        gli.transport();
        gli.currStateInfo();
        gli.dispatch();
        gli.currStateInfo();
        gli.signAccept(true);
        gli.currStateInfo();

        // 测试 - 做当前状态下不能做的事
        gli.signAccept(false);
    }
}
