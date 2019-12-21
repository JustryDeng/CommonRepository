package com.pingan.springsecurity.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 对{@link UserDetailsService#loadUserByUsername(String)}进行重写
 *
 * @author JustryDeng
 * @date 2019/12/14 10:20
 */
@Service
public class MyUserDetailsService implements UserDetailsService {


    /**
     * 根据用户名(即:账号名)查询用户信息
     *
     * todo 用户名不存在时，不处理的话，最终抛出InternalAuthenticationServiceException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

}
