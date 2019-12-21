package com.pingan.springsecurity.model;

import com.alibaba.fastjson.JSON;
import lombok.*;

/**
 * 角色
 *
 * @author JustryDeng
 * @date 2019/12/14 9:36
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyRole {

    /** 角色id */
    private Integer id;

    /** 角色名 */
    private String name;

    /** 角色描述 */
    private String description;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}