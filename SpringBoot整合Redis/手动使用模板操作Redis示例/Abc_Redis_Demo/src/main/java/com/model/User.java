package com.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户模型
 *
 *  P.S. 引入不引入lombok依赖，对本文介绍如何使用Redis无影响，
 *       不过为了省去一些冗余的操作，本人这里使用了lombok
 *
 * @author JustryDeng
 * @date 2019/4/11 14:56
 */
@Data
@Builder
@AllArgsConstructor
public class User implements Serializable {

    public User() {
    }

    /** 姓名 */
    private String name;

    /** 年龄 */
    private Integer age;

    /** 性别 */
    private String gender;

    /** 座右铭 */
    private String motto;
}
