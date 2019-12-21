package com.pingan.springsecurity.model;

import com.alibaba.fastjson.JSON;
import lombok.*;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.GrantedAuthority;

/**
 * 权限
 *
 * @author JustryDeng
 * @date 2019/12/14 9:39
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyPermission implements GrantedAuthority, ConfigAttribute {

    /** 权限id */
    private Integer id;

    /** 父级权限id */
    private Integer parentId;

    /** 权限名(即uri路径) */
    private String authorityName;

    /** 权限描述 */
    private String authorityDescription;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public String getAuthority() {
        return this.authorityName;
    }

    @Override
    public String getAttribute() {
        return this.authorityName;
    }
}
