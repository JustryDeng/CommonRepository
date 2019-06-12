package com.aspire.okhttp.okhttpdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户模型
 *
 * @author JustryDeng
 * @date 2019/6/10 9:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
