package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * zuul异常处理 现场
 *
 *
 * @author JustryDeng
 * @date 2019/4/19 15:52
 */
@RestController
public class ErrorHandlerController {

    /**
     * zuul调用服务异常时，走此方法
     *
     * 注意:method的方法类别 最好全面一点，如本人这里将可能的请求方式全列出来了
     *
     * @return  zuul其他服务异常时的响应
     * @date 2019/4/19 16:51
     */
    @RequestMapping(value = "/justrydeng/error",
                    method = {RequestMethod.GET, RequestMethod.HEAD,
                              RequestMethod.POST, RequestMethod.PUT,
                              RequestMethod.PATCH, RequestMethod.DELETE,
                              RequestMethod.OPTIONS, RequestMethod.TRACE})
    public String errorHandler() {
        // 设置当发生错误时，返回给前端的数据
        return "zuul 调用服务时出错了！";
    }
}
