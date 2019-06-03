package com.aspire.configurationproperties.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户
 *
 * @author JustryDeng
 * @date 2019/6/3 13:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    /** 姓名 */
    private String name;

    /** 年龄 */
    private Integer age;

    /** 性别 */
    private String gender;

    /** 座右铭 */
    private String motto;

}