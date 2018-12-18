package com.aspire.model;

import java.io.Serializable;

/**
 * 用户模型
 *
 * @author JustryDeng
 * @date 2018/12/17 19:43
 */
public class User implements Serializable {


    private static final long serialVersionUID = 7782375470857580571L;
    
    /** 身份证ID */
    private String cardId;

    /** 姓名 */
    private String name;

    /** 年龄 */
    private Integer age;

    /** 性别 */
    private String gender;

    /** 座右铭 */
    private String motto;

    /** 帅气值 */
    private Integer handsomeValue;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public Integer getHandsomeValue() {
        return handsomeValue;
    }

    public void setHandsomeValue(Integer handsomeValue) {
        this.handsomeValue = handsomeValue;
    }

    @Override
    public String toString() {
        return "User{cardId='" + cardId + "', name='" + name + "', age="
                    + age + ", gender='" + gender + "', motto='" + motto
                        + "', handsomeValue='" + handsomeValue + "'}";
    }
}
