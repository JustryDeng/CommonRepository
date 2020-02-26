package com.pingan.retry.spring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户模型
 *
 * @author JustryDeng
 * @date 2020/2/26 17:40:30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    /** 用户id */
    private Integer id;

    /** 名字 */
    private String name;

    /** 年龄 */
    private Integer age;

    /** 性别 */
    private String gender;

    /** 座右铭 */
    private String motto;

    /** 生日 */
    private String birthday;

    /** 爱好 */
    private String hobby;

}
