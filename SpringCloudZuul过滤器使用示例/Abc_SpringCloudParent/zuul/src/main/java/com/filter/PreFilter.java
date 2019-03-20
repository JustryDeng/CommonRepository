package com.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.util.RequestBodyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Random;

/**
 * 使用ZuulFilter实现 简单认证
 *
 * @author JustryDeng
 * @date 2019/3/1 1:36
 */
@Component
@Slf4j
@PropertySource(value = {"classpath:/authe_info.properties"}, encoding="utf8")
public class PreFilter extends ZuulFilter {

    @Value("${need-filter-uri}")
    private String[] needFilterURIs;

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
        // 设置为 前置过滤
        return "pre";
    }

    /**
     * 设置过滤优先级
     *
     * 注:当有多个同类型(即；同filterType)的ZuulFilter的子类时，可使用此返回值指定过滤器优先级;值越小越先过滤
     *
     * @date 2019/3/19 14:55
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 设置 哪些请求 需要过滤
     *
     * @date 2019/3/19 14:55
     */
    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String uri = request.getRequestURI();
        // 当为目标URI时，需要过滤
        return Arrays.asList(needFilterURIs).contains(uri);
    }

    /**
     * 实际逻辑逻辑
     *
     * 注:当RequestContext.setSendZuulResponse(false);时表示过滤失败，zuul不对其进行路由
     */
    @Override
    public Object run() {
        // 获取请求上下文
        RequestContext requestContext = RequestContext.getCurrentContext();

        // 获取请求
        HttpServletRequest request = requestContext.getRequest();
        if (doFiltrate(request)) {
            // 验证通过
            return null;
        }
        // 如果验证不通过，那么过滤该请求，不往下级服务去转发请求，到此结束
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
        requestContext.setResponseBody(HttpStatus.FORBIDDEN.getReasonPhrase());
        requestContext.getResponse().setContentType("text/html;charset=UTF-8");
        return null;
    }
    
    /**
     * 进行过滤
     *
     * @param request
     *             请求
     * @return 是否通过
     * @author JustryDeng
     * @date 2019/3/1 2:13
     */
    private boolean doFiltrate (HttpServletRequest request) {
        try {
            /// 获取请求头
            String who = request.getHeader("Authorization");
            log.info("requestHeader param 【Authorization】 is -> {} !", who);

            /// 向请求头中添加信息
            // requestContext.addZuulRequestHeader("");

            // 获取请求体
            RequestBodyUtil requestBodyUtil = new RequestBodyUtil(request);
            String requestBody = requestBodyUtil.getBody();
            log.info(" got requestBady -> {}", requestBody);

            // TODO 由于是测试代码，这里随机返回 成功、失败
            return new Random().nextBoolean();
        } catch (Exception e) {
            log.error("zull authe occur error !", e);
            return false;
        }
    }
}
