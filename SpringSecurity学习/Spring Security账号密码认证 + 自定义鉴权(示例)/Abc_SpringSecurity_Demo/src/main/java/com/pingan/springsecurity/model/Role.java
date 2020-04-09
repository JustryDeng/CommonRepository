package com.pingan.springsecurity.model;

import lombok.*;
import com.alibaba.fastjson.JSON;

/**
 * 角色
 *
 * @author JustryDeng
 * @date 2020/4/8 15:55:41
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    /** id */
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