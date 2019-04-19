package com.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 后置过滤
 *
 * @author JustryDeng
 * @date 2019/3/1 1:36
 */
@Component
@Slf4j
public class PostFilter extends ZuulFilter {


    /**
     * 过滤类型
     * 有:
     *  【pre】路由请求前被调用过滤、
     *  【post】后置过滤、
     *  【error】错误过滤、
     *  【route】路由请求时被调用
     *
     * @date 2019/3/19 14:49
     */
    @Override
    public String filterType() {
        // 设置为 后置过滤
        return "post";
    }

    /**
     * 设置过滤优先级
     *
     * 注:当有多个ZuulFilter的子类时，可使用此返回值指定过滤器优先级;值越小越先过滤
     *
     * @date 2019/3/19 14:55
     */
    @Override
    public int filterOrder() {
        return 2;
    }

    /**
     * 设置 哪些响应需要过滤
     *
     * @date 2019/3/19 14:55
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 实际逻辑
     */
    @Override
    public Object run() {
        // 获取请求上下文
        RequestContext requestContext = RequestContext.getCurrentContext();
        // 修改返回给前端的响应体
        requestContext.setResponseBody("咿呀咿呀哟......");
        requestContext.getResponse().setContentType("application/json;charset=UTF-8");
        return null;
    }
}
