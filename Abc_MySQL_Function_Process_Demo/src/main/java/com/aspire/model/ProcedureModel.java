package com.aspire.model;

/**
 * 使用对象来封装参数
 *
 * @author JustryDeng
 * @date 2019/1/27 11:18
 */
public class ProcedureModel {

    private Integer paramA;

    private Integer paramB;

    private String paramC;

    public Integer getParamA() {
        return paramA;
    }

    public void setParamA(Integer paramA) {
        this.paramA = paramA;
    }

    public Integer getParamB() {
        return paramB;
    }

    public void setParamB(Integer paramB) {
        this.paramB = paramB;
    }

    public String getParamC() {
        return paramC;
    }

    public void setParamC(String paramC) {
        this.paramC = paramC;
    }

    @Override
    public String toString() {
        return "ProcedureModel{paramA=" + paramA + ", paramB=" + paramB + ", paramC='" + paramC + "'}";
    }
}
