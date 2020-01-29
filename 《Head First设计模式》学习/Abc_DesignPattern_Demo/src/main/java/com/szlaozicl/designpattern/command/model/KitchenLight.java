package com.szlaozicl.designpattern.command.model;

/**
 * 厨房灯
 *
 * @author JustryDeng
 * @date 2020/1/22 10:56
 */
public class KitchenLight extends AbstractLight {

    public KitchenLight() {
        initLightType();
    }

    @Override
    protected void initLightType() {
        type = "kitchen light";
    }
}
