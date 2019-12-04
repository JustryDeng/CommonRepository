package com.aspire.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户模型
 *
 * @author JustryDeng
 * @date 2018/12/17 19:43
 */
@Data
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

}
