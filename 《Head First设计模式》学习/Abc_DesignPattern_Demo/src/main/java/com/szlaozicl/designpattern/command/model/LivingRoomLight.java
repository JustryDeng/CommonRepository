package com.szlaozicl.designpattern.command.model;

/**
 * 客厅灯
 *
 * @author JustryDeng
 * @date 2020/1/22 10:55
 */
public class LivingRoomLight extends AbstractLight {

    public LivingRoomLight() {
        initLightType();
    }

    @Override
    protected void initLightType() {
        type = "living room light";
    }
}
