package com.pingan.springsecurity.model;

import com.alibaba.fastjson.JSON;
import lombok.*;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.GrantedAuthority;

/**
 * api资源信息
 *
 * 注: 这里通过资源的path来判断用户是否有权访问
 *
 * @author JustryDeng
 * @date 2020/4/8 15:55:41
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResource implements GrantedAuthority, ConfigAttribute {

    /** id */
    private Integer id;

    /** 父资源id */
    private Integer pid;

    /** 资源名 */
    private String name;

    /** 资源路径 */
    private String path;

    /** 请求该资源所需要的方法(多个之间使用逗号分割) */
    private String requestMethod;

    /** 资源描述 */
    private String description;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public String getAuthority() {
        return this.path;
    }

    @Override
    public String getAttribute() {
        return this.path;
    }
}
