package com.pingan.springsecurity.config.authorizer;

import com.pingan.springsecurity.mapper.DaoMapper;
import com.pingan.springsecurity.model.ApiResource;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 存储ConfigAttribute信息， 并根据ConfigAttribute信息的有无， 决定是否走 决策器
 * 即: 若{@link this#getAttributes}返回的集合满足CollectionUtils.isEmpty(list)为true的话，
 *     那么不会走决策器,
 *     否者会走决策器
 *
 * @author JustryDeng
 * @date 2019/12/14 11:20
 */
@Component
@RequiredArgsConstructor
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private List<String> needAuthPaths = new ArrayList<>(16);

    private final DaoMapper mapper;

    @PostConstruct
    private void init() {
        needAuthPaths.addAll(mapper.selectNeedAuthPaths());
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        String targetPath = request.getRequestURI();
        if (needAuthPaths.contains(targetPath)) {
            // 需要鉴权
            List<ConfigAttribute> list = new ArrayList<>(1);
            list.add(ApiResource.builder().build());
            return list;
        }
        // 不需要鉴权
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
