package com.aspire.model;

/**
 * 具体信息 模型
 *
 * @author JustryDeng
 * @date 2018/12/5 16:57
 */
public class HandsomeBoyPOJO {

    /** 姓名 */
    private String name;

    /** 性别 */
    private String gender;

    /** 年龄 */
    private int age;

    /** 爱好 */
    private String hobby;

    /** 帅气值 */
    private String handsomeValue;

    /** 座右铭 */
    private String motto;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getHandsomeValue() {
        return handsomeValue;
    }

    public void setHandsomeValue(String handsomeValue) {
        this.handsomeValue = handsomeValue;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    @Override
    public String toString() {
        return "HandsomeBoyPOJO{name='" + name + "', gender='" + gender + "', age=" + age
                + ", hobby='" + hobby + "', handsomeValue='" + handsomeValue + "', motto='" + motto + "'}";
    }
}