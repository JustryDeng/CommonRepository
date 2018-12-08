package com.aspire.model;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 学校模型
 *
 * @author JustryDeng
 * @date 2018/12/8 17:12
 */
public class SchoolVO {

    /** 学校名称 */
    @Excel(name = "学校名称", orderNum = "6", width = 20)
    private String schoolName;

    /** 学校地址 */
    @Excel(name = "学校地址", orderNum = "8", width = 20)
    private String schoolAddress;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    @Override
    public String toString() {
        return "SchoolVO{schoolName='" + schoolName + "', schoolAddress='" + schoolAddress + "'}";
    }
}
