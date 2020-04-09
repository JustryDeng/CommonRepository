package com.pingan.springsecurity.config.authorizer;

import com.pingan.springsecurity.model.MyUserDetails;
import com.pingan.springsecurity.service.impl.MyUserDetailsService;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 决策器 (判断鉴权是否通过)
 *
 * @author JustryDeng
 * @date 2019/12/14 11:34
 */
@Component
public class MyAccessDecisionManager implements AccessDecisionManager {

    /**
     * 决策
     *
     * @param authentication
     *            当前用户的信息模型
     *            注: 由于重写了{@link MyUserDetailsService#loadUserByUsername}，
     *                重写后该方法实际返回的类型是{@link MyUserDetails}， 所以这里直接
     *                将Authentication强转为MyUserDetails。
     * @param object
     *            当前request的封装
     * @param configAttributes
     *            与访问的目标uri相关联的配置属性
     *            注:在本示例中，不需要用到此属性； 如果在这里需要用到此属性的话，可以在通过
     *               实现{@link FilterInvocationSecurityMetadataSource},重写相关返
     *               回Collection<ConfigAttribute>的方法，该返回值会作为形参传递到本方法
     *               然后在这里就能拿到对应的值了。
     *               可参考网友的示例<linked>https://www.jianshu.com/p/e715cc993bf0</linked>
     *
     * @throws AccessDeniedException
     *             当前用户无权访问
     * @throws InsufficientAuthenticationException
     *             当前用户信任级别不够，无法访问
     * @date 2019/12/14 12:06
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        String targetPath = request.getRequestURI();
        Object principal = authentication.getPrincipal();
        /*
         * 若认证过，那么principal instanceof MyUserDetails为true, 否则用户没有认证过
         *
         * 注: 鉴权一般都在认证之后, 没有认证谈何鉴权。
         *
         * 注: 一旦抛出异常后，
         *     在{@link ExceptionTranslationFilter#doFilter}中会对抛出的AuthenticationException异常(包括其子异常)
         *     进行相关处理。如: 抛出AuthenticationCredentialsNotFoundException异常，就会被处理然后页面跳转至登录页
         *
         */
        if (!(principal instanceof MyUserDetails)) {
            throw new AuthenticationCredentialsNotFoundException(" there is no any Authentication object MyUserDetails in the SecurityContext");
        }
        MyUserDetails myUserDetails = (MyUserDetails)principal;
        // 这个用户可访问的所有资源信息
        Collection<? extends GrantedAuthority> grantedAuthority = myUserDetails.getAuthorities();
        List<String> list = grantedAuthority.parallelStream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        if(list.contains(targetPath)) {
            // 鉴权通过， 有访问权限
            return;
        }
        // 鉴权不通过， 没有访问权限
        throw new AccessDeniedException(
                String.format("You(%s) don't have any authorizion access %s", myUserDetails.getName(), targetPath)
        );
    }

    /**
     * 当前AccessDecisionManager实例能否处理 传递的ConfigAttribute呈现的授权请求
     */
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    /**
     * 当前AccessDecisionManager实例是否支持提供访问控制决策
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
