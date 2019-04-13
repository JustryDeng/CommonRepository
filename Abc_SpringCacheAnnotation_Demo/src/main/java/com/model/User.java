package com.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Employee表模型
 *
 * @author JustryDeng
 * @date 2019/4/11 14:56
 */
@Data
@Builder
@AllArgsConstructor
public class User implements Serializable {

    /** 姓名 */
    private String name;

    /** 年龄 */
    private Integer age;

    /** 性别 */
    private String gender;

    /** 座右铭 */
    private String motto;
}
