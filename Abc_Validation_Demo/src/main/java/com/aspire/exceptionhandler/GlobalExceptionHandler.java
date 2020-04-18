package com.aspire.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;

/**
 * SpringMVC统一异常处理
 *
 * @author JustryDeng
 * @date 2019/10/12 16:28
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理Validated校验异常
     * <p>
     * 注: 常见的ConstraintViolationException异常， 也属于ValidationException异常
     *
     * @param e
     *         捕获到的异常
     * @return 返回给前端的data
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {BindException.class, ValidationException.class, MethodArgumentNotValidException.class})
    public Map<String, Object> handleParameterVerificationException(Exception e) {
        log.error(" handleParameterVerificationException has been invoked", e);
        Map<String, Object> resultMap = new HashMap<>(4);
        resultMap.put("code", "100001");
        String msg = null;
        /// BindException
        if (e instanceof BindException) {
            // getFieldError获取的是第一个不合法的参数(P.S.如果有多个参数不合法的话)
            FieldError fieldError = ((BindException) e).getFieldError();
            if (fieldError != null) {
                msg = fieldError.getDefaultMessage();
            }
        /// MethodArgumentNotValidException
        } else if (e instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
            // getFieldError获取的是第一个不合法的参数(P.S.如果有多个参数不合法的话)
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                msg = fieldError.getDefaultMessage();
            }
        /// ValidationException
        } else {
            /*
             * ConstraintViolationException的e.getMessage()形如
             *     {方法名}.{参数名}: {message}
             *  这里只需要取后面的message即可
             */
            msg = e.getMessage();
            if (msg != null) {
                int lastIndex = msg.lastIndexOf(':');
                if (lastIndex >= 0) {
                    msg = msg.substring(lastIndex + 1).trim();
                }
            }
        }
        if (msg == null) {
            resultMap.put("msg", "参数校验不通过，请按要求传参");
        }
        resultMap.put("msg", msg);
        return resultMap;
    }

    /**
     * 处理Exception异常
     *
     * @param e
     *         捕获到的异常
     * @return 返回给前端的data
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleException(Exception e) {
        log.error(" handleException has been invoked", e);
        Map<String, Object> resultMap = new HashMap<>(4);
        resultMap.put("code", "100000");
        resultMap.put("msg", "系统繁忙，请稍后再试");
        return resultMap;
    }
}