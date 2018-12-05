package com.aspire.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import java.util.Date;

/**
 * Student数据模型
 *
 * @author JustryDeng
 * @date 2018/12/5 9:35
 */
public class StudentVO {

    /**
     * name指定导出excel时生成的列名
     * orderNum可指定导出的该属性对应的所在列的位置
     * width设置单元格宽度
     * type设置导出类型  1是文本, 2是图片, 3是函数,10 数字 默认是文本
     */
    @Excel(name = "学号", orderNum = "1", type = 10, width = 15)
    private String studentID;

    @Excel(name = "姓名", orderNum = "2", width = 15)
    private String name;

    /**
     * 当gender为1时，导出的结果为 男， 当gender为0时，导出的结果为 女
     */
    @Excel(name = "性别", replace = {"男_1", "女_0"},orderNum = "3", width = 5)
    private Integer gender;

    /**
     * type设置导出类型  1是文本, 2是图片, 3是函数,10 数字 默认是文本
     */
    @Excel(name = "年龄",orderNum = "4", type = 10, width = 5)
    private int age;

    /**
     * 将Data日期导出为yyyy-MM-dd格式
     */
    @Excel(name = "入校时间",format = "yyyy-MM-dd", orderNum = "5", width = 20)
    private Date entranceTime;

    @Excel(name = "班级",orderNum = "6", width = 15)
    private String className;

    /**
     * 无参构造
     */
    public StudentVO() {
    }

    /**
     * 全参构造
     */
    public StudentVO(String studentID, String name, Integer gender,
                     int age, Date entranceTime, String className) {
        this.studentID = studentID;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.entranceTime = entranceTime;
        this.className = className;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Date getEntranceTime() {
        return entranceTime;
    }

    public void setEntranceTime(Date entranceTime) {
        this.entranceTime = entranceTime;
    }

    @Override
    public String toString() {
        return "StudentVO{studentID='" + studentID + "', name='" + name + "', gender='"
                   + gender + "', age=" + age + ", entranceTime=" + entranceTime
                       + ", className='" + className + "'}";
    }
}
