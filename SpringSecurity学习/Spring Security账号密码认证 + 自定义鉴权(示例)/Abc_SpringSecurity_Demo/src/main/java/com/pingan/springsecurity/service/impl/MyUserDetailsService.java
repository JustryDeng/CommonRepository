package com.pingan.springsecurity.service.impl;

import com.pingan.springsecurity.mapper.DaoMapper;
import com.pingan.springsecurity.model.ApiResource;
import com.pingan.springsecurity.model.MyUserDetails;
import com.pingan.springsecurity.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 对{@link UserDetailsService#loadUserByUsername(String)}进行重写
 *
 * @author JustryDeng
 * @date 2019/12/14 10:20
 */
@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final DaoMapper mapper;

    /**
     * 根据账号名, 查询用户信息
     *
     * todo 用户名不存在时，不处理的话，最终抛出InternalAuthenticationServiceException
     */
    @Override
    public UserDetails loadUserByUsername(String accountNo) throws UsernameNotFoundException {
        // 查询用户基本信息
        MyUserDetails myUserDetails = mapper.selectUserBasicInfoByAccountNo(accountNo);
        // 查询用户角色信息
        List<Role> roleList = mapper.selectRolesByUserId(myUserDetails.getId());
        // 查询用户权限信息(即:查询用户可访问的资源)
        List<Integer> roleIdList = roleList.parallelStream().map(Role::getId).collect(Collectors.toList());
        List<ApiResource> apiResources = mapper.selectApiResourcesByRoleIds(roleIdList);

        // 组装信息并返回
        myUserDetails.setRoles(roleList);
        myUserDetails.setAccessibleApis(apiResources);
        return myUserDetails;
    }

}
