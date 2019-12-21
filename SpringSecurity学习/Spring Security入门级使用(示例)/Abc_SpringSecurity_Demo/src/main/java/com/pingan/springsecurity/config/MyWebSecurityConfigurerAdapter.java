package com.pingan.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SpringSecurity配置
 *
 * @author JustryDeng
 * @date 2019/12/7 14:08
 */
@Configuration
public class MyWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        /*
         * 对于那些没必要进行保护的资源， 可以使用ignoring，使其跳过SpringSecurity
         *
         * 注:configure(HttpSecurity http)方法里的permitAll();也有类似的效果，
         *    不过permitAll会走SpringSecurity，只是说无条件放行而已。
         */
        web.ignoring().antMatchers("/picture/**");
        web.ignoring().antMatchers("/md/**");
        // 开发时，可以将SpringSecurity的debug打开
        web.debug(true);
    }

    /**
     * SpringSecurity提供有一些基本的页面(如:login、logout等)；如果觉得它提供的
     * 基础页面难看，想使用自己的页面的话，可以在此方法里面进行相关配置。
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 访问 匹配以下ant的url不需要(非匿名)认证、不需要鉴权
        http.authorizeRequests().antMatchers("/login", "/logout", "/logout/success", "/403", "/hello").permitAll();

        // 只要认证通过，就可访问匹配以下ant的url。 (不论这人的权限是什么)
        http.authorizeRequests().antMatchers("/index").authenticated();

        /*
         *  访问 匹配以下ant的url时， 需要至少有一个角色 "USER", "ADMIN"  (需要鉴权)
         *
         *  注:[鉴权] 这个动作里面就隐含[认证]了， 因为只有认证后，才能拿到权限信息，才能进行鉴权；
         *     如果连认证都没过的话，鉴权自然会失败。
         */
        http.authorizeRequests().antMatchers("/user").hasAnyRole("USER", "ADMIN", "abc");
        // 访问 匹配以下ant的url时， 需要至少有一个角色 "DBA", "ADMIN" (需要鉴权)
        http.authorizeRequests().antMatchers("/dba").hasAnyRole("DBA", "ADMIN");
        // 访问 匹配以下ant的url时， 需要有角色 "ADMIN" (需要鉴权)
        http.authorizeRequests().antMatchers("/admin").hasRole("ADMIN");

        /*
         * 设置任何请求都需要认证(除了前面.permitAll()的)。
         *
         * 注:如果不设置此项的话，那么对于那些未作任何配置的URL， 那么是默认 不认证、不鉴权的
         */
        http.authorizeRequests().anyRequest().authenticated();

        // 设置登录方式为 表单登录
        http.formLogin();
        /// 设置登录方式为 弹框登录
        /// http.httpBasic();
        /// 自定义登录页
        /// http.formLogin().loginPage("myLoginPae");
        /// 自定义登出页
        /// http.logout().logoutUrl("myLogoutPae");
        // 登出成功时，跳转至此url
        http.logout().logoutSuccessUrl("/logout/success");
        // 登录成功时，跳转至此url
        // 注意:如果未登录，直接访问 登录失败页的话，会被DefaultLoginPageGeneratingFilter识别，并跳转至登录页进行登录
        http.formLogin().successForwardUrl("/index");
        // 登录失败时，跳转至此url
        // 注意:如果未登录，直接访问 登录失败页的话，会被DefaultLoginPageGeneratingFilter识别，并跳转至登录页进行登录
        http.formLogin().failureUrl("/login/failed");
        /// 当鉴权不通过，是 跳转至此url
        http.exceptionHandling().accessDeniedPage("/403");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 配置几个用户
        auth.inMemoryAuthentication().withUser("user").password("user123").roles("USER");
        auth.inMemoryAuthentication().withUser("dba").password("dba123").roles("DBA");
        auth.inMemoryAuthentication().withUser("admin").password("admin123").roles("ADMIN");

        // 配置这个用户的目的，是为了说明: 角色名瞎**起都可以
        auth.inMemoryAuthentication().withUser("other").password("other123").roles("abc", "DBA");
    }

    /**
     * 自定义 加密器
     *
     * 注:只需要将其注册进入容器中即可，InitializeUserDetailsBeanManagerConfigurer类会从容器
     *    拿去PasswordEncoder.class实现，作为其加密器
     *
     * @date 2019/12/21 17:59
     */
    @Bean
    public PasswordEncoder myPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword == null ? "" : rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                if (rawPassword == null || rawPassword.length() == 0) {
                    return false;
                }
                return rawPassword.equals(encodedPassword);
            }
        };
    }
}