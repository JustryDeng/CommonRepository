package com.pingan.springsecurity.model;

import com.alibaba.fastjson.JSON;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 用户信息
 *
 * @author JustryDeng
 * @date 2019/12/14 9:50
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyUserDetails implements UserDetails {

    /** 用户ID */
    private String id;

    /** 用户登录账户名 */
    private String accountNo;

    /** 用户名 */
    private String name;

    /** 用户登录密码 */
    private String password;

    /** 用户拥有的权限 */
    private List<MyPermission> permissions;

    /** 用户拥有的角色 */
    private List<MyRole> roles;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissions;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
