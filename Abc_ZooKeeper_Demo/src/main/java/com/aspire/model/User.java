package com.aspire.model;

import java.io.Serializable;

/**
 * User模型
 * 注:如果ZkClient客户端，使用的是SerializableSerializer序列化器;
 *    那么当以一个对象作为节点数据模型时，该对象必须保证能序列化，即:必须实现Serializable功能性接口
 *
 * @author JustryDeng
 * @date 2018/12/7 12:43
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1106309769739955005L;

    /** 姓名 */
    private String name;

    /** 性别 */
    private String gender;

    /** 年龄 */
    private String hobby;

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

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    @Override
    public String toString() {
        return "User{name='" + name + "', gender='" + gender + "', hobby='" + hobby + "'}";
    }
}
