package com.pingan.springsecurity.controller;

import com.pingan.springsecurity.model.MyUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于测试的controller层
 *
 * @author JustryDeng
 * @date 2019/11/26 10:05
 */
@Slf4j
@RestController
public class DemoController {

    /**
     * 未登录就可访问的页面
     *
     * 注: 用于.permitAll()测试。
     */
    @GetMapping("/hello")
    public String hello() {
        return "hello 靓仔~";
    }

    /**
     * 首页(登录成功后跳转至此页)
     *
     * 注:默认的，表单登录 登录成功时， 是以POST重定向至登陆成功页的，所以这里至少要支持POST请求。
     */
    @RequestMapping(value = "/index", method = {RequestMethod.GET, RequestMethod.POST})
    public String home() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUserDetails myUserDetails = (MyUserDetails)user;
        return "欢迎大哥【" + myUserDetails.getName() + "】来到index！给大哥点点关注！！！";
    }

    /**
     * 登录失败页
     */
    @GetMapping("/login/failed")
    public String error() {
        return "登录失败~";
    }

    /**
     * 登出成功页
     */
    @GetMapping("/logout/success")
    public String logout() {
        return "您已成功退出~";
    }

    /**
     * 鉴权失败页
     */
    @GetMapping("/403")
    public String forbidden() {
        return "小伙~你的权限不够~";
    }

    @GetMapping("/user")
    public String user() {
        return "普通用户~";
    }

    @GetMapping("/dba")
    public String dba() {
        return "数据库DBA~";
    }

    @GetMapping("/admin")
    public String admin() {
        return "超级管理员~";
    }
}
