package com.szlaozicl.demo.controller;

import com.szlaozicl.demo.annotation.RecordParameters;
import com.szlaozicl.demo.aop.RecordParametersAdvice;
import com.szlaozicl.demo.author.JustryDeng;
import com.szlaozicl.demo.util.GsonUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/**
 * 动态改变日志级别
 *
 * @author {@link JustryDeng}
 * @date 2020/6/6 14:53:40
 */
@RestController
public class DynamicLogController {
    
    /**
     * 根据方法名, 修改日志级别
     *
     * @param classMethod
     *            全类名#方法名
     * @param logLevelStr
     *            要修改至的日志级别(大小写不敏感)
     * @return  成功与否
     */
    @GetMapping("/dynamic-log/change-by-method-name")
    public String changeLogLevelByMethodName(String classMethod, String logLevelStr) {
        if (StringUtils.isEmpty(logLevelStr)) {
            return "log-level cannot be empty";
        }
        RecordParameters.LogLevel logLevel;
        try {
            logLevel = RecordParameters.LogLevel.valueOf(logLevelStr.toUpperCase(Locale.US));
        } catch (IllegalArgumentException e) {
            return "un-support enum[" + logLevelStr + "], [warn, info, debug] be only supported";
        }
        // 不考虑并发put导致的覆盖情况
        RecordParametersAdvice.methodLogLevelConverterMap.put(classMethod, logLevel);
        return "success";
    }
    
    /**
     * 重置指定方法上的日志级别转换
     *
     * @param classMethod
     *            全类名#方法名
     * @return  success-成功; failure-失败
     */
    @GetMapping("/dynamic-log/reset-by-method-name")
    public String resetLogLevelByMethodName(String classMethod) {
        // 若不存在， 也返回success
        RecordParametersAdvice.methodLogLevelConverterMap.remove(classMethod);
        return "success";
    }
    
    /**
     * 根据方法名, 修改日志级别
     *
     * @param requestPath
     *            请求path
     * @param logLevelStr
     *            要修改至的日志级别(大小写不敏感)
     * @return  成功与否
     */
    @GetMapping("/dynamic-log/change-by-path")
    public String changeLogLevelByPath(String requestPath, String logLevelStr) {
        if (StringUtils.isEmpty(logLevelStr)) {
            return "log-level cannot be empty";
        }
        RecordParameters.LogLevel logLevel;
        try {
            logLevel = RecordParameters.LogLevel.valueOf(logLevelStr.toUpperCase(Locale.US));
        } catch (IllegalArgumentException e) {
            return "failure. un-support enum[" + logLevelStr + "], [warn, info, debug] be only supported";
        }
        // 不考虑并发put导致的覆盖情况
        RecordParametersAdvice.pathLogLevelConverterMap.put(requestPath, logLevel);
        return "success";
    }
    
    /**
     * 重置指定path的日志级别转换
     *
     * @param requestPath
     *            请求的path
     * @return  success-成功; failure-失败
     */
    @GetMapping("/dynamic-log/reset-by-path")
    public String resetLogLevelByPath(String requestPath) {
        // 若不存在， 也返回success
        RecordParametersAdvice.pathLogLevelConverterMap.remove(requestPath);
        return "success";
    }
    
    /**
     * 重置所有的日志级别转换
     *
     * @return success-成功; failure-失败
     */
    @GetMapping("/dynamic-log/reset-all")
    public String resetAllLogLevel() {
        RecordParametersAdvice.methodLogLevelConverterMap.clear();
        RecordParametersAdvice.pathLogLevelConverterMap.clear();
        return "success";
    }
    
    /**
     * 获取基于path的的日志级别转换信息
     *
     * @return 基于path的的日志级别转换信息
     */
    @GetMapping("/dynamic-log/get-path-converter-info")
    public String getPathLogLevelConverterInfo() {
        return GsonUtil.toJson(RecordParametersAdvice.pathLogLevelConverterMap);
    }
    
    /**
     * 获取基于方法的的日志级别转换信息
     *
     * @return 基于方法的的日志级别转换信息
     */
    @GetMapping("/dynamic-log/get-method-converter-info")
    public String getMethodLogLevelConverterInfo() {
        return GsonUtil.toJson(RecordParametersAdvice.methodLogLevelConverterMap);
    }
}
