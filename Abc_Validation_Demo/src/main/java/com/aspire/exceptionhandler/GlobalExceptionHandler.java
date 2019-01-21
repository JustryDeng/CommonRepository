package com.aspire.exceptionhandler;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * SpringMVC统一异常处理
 * 注:@ControllerAdvice为Controller层增强器,其只能处理Controller层抛出的异常;
 * 由于代码间的层级调用机制  、异常的处理机制等,所以这里处理Controller层的异常,就相当于
 * 处理了全局异常
 * <p>
 * 注: @RestControllerAdvice等同于  @ResponseBody 加上 @ControllerAdvice
 *
 * @author JustryDeng
 * @date 2018年8月21日 下午11:33:19
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 具体的处理异常的逻辑
     * 注:@ExceptionHandler的value属性指定要处理哪些异常;
     *
     * @param ex
     *         捕获到的异常
     * @return 返回给前端的data
     * @date 2018年8月21日 下午11:33:42
     */
    @ExceptionHandler(value = {Exception.class})
    public Map<String, Object> globalExceptionHandleMethod(Exception ex) {
        Map<String, Object> resultMap = new HashMap<>(4);
        if (ex instanceof ConstraintViolationException) {
            ConstraintViolationException cvExceptionex = (ConstraintViolationException) ex;
            resultMap.put("msg", "@Validated约束在类上，直接校验接口的参数时异常 -> " + cvExceptionex.getMessage());
            resultMap.put("code", "1");
        } else if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException manvExceptionex = (MethodArgumentNotValidException) ex;
            resultMap.put("msg", "@Validated约束在参数模型前，校验该模型的字段时发生异常 -> " + manvExceptionex.getMessage());
            resultMap.put("code", "2");
        } else {
            resultMap.put("msg", "系统异常");
            resultMap.put("code", "3");
        }
        return resultMap;
    }
}