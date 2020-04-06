package com.szlaozicl.designpattern.status.status;

/**
 * 订单状态
 *
 * @author JustryDeng
 * @date 2020/4/6 14:47:36
 */
public interface BillState {

    /**
     * 状态名
     *
     * return 状态名
     */
    String name();

    /**
     * (买家)购物
     */
    default void shopping() {
        throw new UnsupportedOperationException("状态【" + this.getClass().getSimpleName() + "】下, 不支持sopping操作");
    }

    /**
     * (商家)发货
     */
    default void shipping() {
        throw new UnsupportedOperationException("状态【" + this.getClass().getSimpleName() + "】下, 不支持shipping操作");
    }

    /**
     * (收寄中心)揽件
     */
    default void packaging() {
        throw new UnsupportedOperationException("状态【" + this.getClass().getSimpleName() + "】下, 不支持packaging操作");
    }

    /**
     * (物流)开始运输
     */
    default void transport() {
        throw new UnsupportedOperationException("状态【" + this.getClass().getSimpleName() + "】下, 不支持transport操作");
    }

    /**
     * (快递小哥)派送
     */
    default void dispatch() {
        throw new UnsupportedOperationException("状态【" + this.getClass().getSimpleName() + "】下, 不支持dispatch操作");
    }

    /**
     * (买家)签收 or 拒签
     *
     * @param accept
     *            true - 买家签收;
     *            false - 买家拒签
     */
    default void signAccept(boolean accept) {
        throw new UnsupportedOperationException("状态【" + this.getClass().getSimpleName() + "】下, 不支持signAccept操作");
    }
}
