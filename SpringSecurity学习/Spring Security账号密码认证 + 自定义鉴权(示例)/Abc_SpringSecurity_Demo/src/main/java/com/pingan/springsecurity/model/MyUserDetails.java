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
 * @date 2020/4/8 15:56:23
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyUserDetails implements UserDetails {

    /** id */
    private Integer id;

    /** 用户登录账号 */
    private String accountNo;

    /** 用户登录密码 */
    private String password;

    /** 用户名 */
    private String name;

    /** 用户拥有的权限(可访问的资源集合) */
    private List<ApiResource> accessibleApis;

    /** 用户拥有的角色 */
    private List<Role> roles;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.accessibleApis;
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