package com.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * 异常过滤器
 *
 * @author JustryDeng
 * @date 2019/3/1 1:36
 */
@Component
@Slf4j
public class ErrorFilter extends ZuulFilter {

    @Override
    public String filterType() {
        // 设置为 错误过滤
        return "error";
    }

    @Override
    public int filterOrder() {
        // 注意: 此处自定义的优先级 要高于SendErrorFilter的优先级，
        // 由于SendErrorFilter的优先级是0， 由于值越小优先级越高，
        // 所以这里的值要小于0
        return -1;
    }

    @Override
    public boolean shouldFilter() {
       return true;
    }

    /**
     * 重写run()方法
     *
     * 提示：此方法的编写 可 参考 SendErrorFilter的run方法;
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        try {
            Throwable throwable = ctx.getThrowable();
            log.error("记录zuul调用服务时发生的异常信息", throwable);
            // 设置请求转发到自定义的controller方法对应的uri地址
            RequestDispatcher dispatcher = request.getRequestDispatcher("/justrydeng/error");
            if (dispatcher != null) {
                if (!ctx.getResponse().isCommitted()) {
                    // 一定要将【sendErrorFilter.ran】的值设置为true;这样就不会走SendErrorFilter过滤器了
                    // 提示:由于过滤器执行顺序的问题，如果走了SendErrorFilter的话，那么就会覆盖我们设置好了的响应信息
                    ctx.set("sendErrorFilter.ran", true);
                    dispatcher.forward(request, ctx.getResponse());
                }
            }
        } catch (Exception ex) {
            // 若到这一步， 可选择邮件告警等
            log.error("系统异常", ex);
            ReflectionUtils.rethrowRuntimeException(ex);
        }
        return null;
    }

}
