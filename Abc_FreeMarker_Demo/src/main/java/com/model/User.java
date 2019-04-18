package com.model;

import lombok.Builder;
import lombok.Data;

/**
 * 用户
 *
 * @author JustryDeng
 * @date 2019/4/17 23:51
 */
@Builder
@Data
public class User {

    /** 姓名 */
    private String name;

    /** 年龄 */
    private Integer age;

    /** 性别 */
    private String gender;

    /** 爱好 */
    private String hobby;

    /** 座右铭 */
    private String motto;
}
