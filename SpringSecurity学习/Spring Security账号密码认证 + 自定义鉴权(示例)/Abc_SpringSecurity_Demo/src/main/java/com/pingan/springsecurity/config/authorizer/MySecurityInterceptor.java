package com.pingan.springsecurity.config.authorizer;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * 通过MySecurityInterceptor 注册 MyAccessDecisionManager
 *
 * @author JustryDeng
 * @date 2019/12/14 12:50
 */
@Component
public class MySecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    private final MyFilterInvocationSecurityMetadataSource myFilterInvocationSecurityMetadataSource;

    public MySecurityInterceptor(MyAccessDecisionManager myAccessDecisionManager,
                                 MyFilterInvocationSecurityMetadataSource myFilterInvocationSecurityMetadataSource) {
        this.myFilterInvocationSecurityMetadataSource = myFilterInvocationSecurityMetadataSource;
        // 设置 以 自定义的决策权 进行 鉴权管理
        super.setAccessDecisionManager(myAccessDecisionManager);
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(servletRequest, servletResponse, filterChain);
        invoke(fi);
    }

    public void invoke(FilterInvocation fi) throws IOException, ServletException {
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    /**
     * 返回自定义的SecurityMetadataSource
     */
    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        // 如果有实现SecurityMetadataSource的话，可以设置采用自定义的 资源器， 如:
        // 如果实现有FilterInvocationSecurityMetadataSource的话，可以将其进行注册(即:返回其实例)
        return myFilterInvocationSecurityMetadataSource;
    }
}
