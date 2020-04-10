package com.szlaozicl.demo.aop;

import com.alibaba.fastjson.JSON;
import com.szlaozicl.demo.annotation.RecordParameters;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 方法 入参、出参 记录
 *
 * @author JustryDeng
 * @date 2019/12/4 13:57
 */
@Slf4j
@Order
@Aspect
@Configuration
public class RecordParametersAdvice {

    /** 栈帧局部变量表参数名侦查器 */
    private static final LocalVariableTableParameterNameDiscoverer PARAMETER_NAME_DISCOVER = new LocalVariableTableParameterNameDiscoverer();

    /** 无返回值 */
    private static final String VOID_STRING = void.class.getName();

    /** controller类后缀 */
    private static final String CONTROLLER_STRING = "Controller";

    private static final String EMPTY_STR = "";

    private final AopSupport aopSupport;

    public RecordParametersAdvice(AopSupport aopSupport) {
        this.aopSupport = aopSupport;
    }

    /**
     * 【@within】: 当将注解加在类上时，等同于 在该类下的所有方法上加上了该注解(即:该类的所有方法都会被aop)。
     *              注意:注解必须写在类上，不能写在接口上。
     * 【@annotation】: 当将注解加在某个方法上时，该方法会被aop。
     * 【execution】: 这里:
     *                    第一个*, 匹配所有返回类型
     *                    第二个..*，匹配com.szlaozicl.demo.controller包下的，所有的类(含其子孙包下的类)
     *                    最后的*(..), 匹配任意方法任意参数。
     */
    @Pointcut(
            "(@within(com.szlaozicl.demo.annotation.RecordParameters)"
            + " || "
            + "@annotation(com.szlaozicl.demo.annotation.RecordParameters)"
            + " || "
            + "execution(* com.szlaozicl.demo.controller..*.*(..)"
            + ")"
            + " && "
            + "!@annotation(com.szlaozicl.demo.annotation.IgnoreRecordParameters))"
    )
    public void executeAdvice() {
    }

    /**
     * 环绕增强
     */
    @Around("executeAdvice()")
    public Object aroundAdvice(ProceedingJoinPoint thisJoinPoint) throws Throwable {

        // 获取目标Class
        Object targetObj = thisJoinPoint.getTarget();
        Class<?> targetClazz = targetObj.getClass();
        String clazzName = targetClazz.getName();
        // 获取目标method
        MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        // 获取目标annotation
        RecordParameters annotation = targetMethod.getAnnotation(RecordParameters.class);
        if (annotation == null) {
            annotation = targetClazz.getAnnotation(RecordParameters.class);
            // 如果是通过execution触发的，那么annotation可能为null, 那么给其赋予默认值即可
            if (annotation == null && clazzName.endsWith(CONTROLLER_STRING) ) {
                annotation = (RecordParameters) AnnotationUtils.getDefaultValue(RecordParameters.class);
            }
        }
        // 是否需要记录入参、出参
        boolean shouldRecordInputParams;
        boolean shouldRecordOutputParams;
        RecordParameters.LogLevel logLevel;
        if (annotation != null) {
            shouldRecordInputParams = annotation.strategy() == RecordParameters.Strategy.INPUT
                                      ||
                                      annotation.strategy() == RecordParameters.Strategy.INPUT_OUTPUT;
            shouldRecordOutputParams = annotation.strategy() == RecordParameters.Strategy.OUTPUT
                                       ||
                                       annotation.strategy() == RecordParameters.Strategy.INPUT_OUTPUT;
            logLevel = annotation.logLevel();
        // 此时，若仍然为null, 那说明是通过execution(* com.szlaozicl.demo.controller.*.*(..)触发切面的
        } else {
            shouldRecordInputParams = true;
            shouldRecordOutputParams = true;
            logLevel = RecordParameters.LogLevel.INFO;
        }
        if (shouldRecordInputParams) {
            preHandel(logLevel, targetMethod, thisJoinPoint, clazzName);
        }
        Object obj = thisJoinPoint.proceed();
        if (shouldRecordOutputParams) {
            boolean returnNow = postHandel(logLevel, targetMethod, obj);
            if(returnNow) {
                return obj;
            }
        }
        return obj;
    }

    /**
     * 切面日志后处理
     *
     * @param logLevel
     *            日志级别
     * @param targetMethod
     *            目标方法
     * @param pjp
     *            目标方法的返回结果
     * @param clazzName
     *            目标方法所在的类名
     * @date 2020/4/10 18:21:17
     */
    private void preHandel(RecordParameters.LogLevel logLevel, Method targetMethod,
                           ProceedingJoinPoint pjp, String clazzName) {
        if (clazzName.endsWith(CONTROLLER_STRING)) {
            aopSupport.log(logLevel,
                    " \n【the way in】 request-path【" + aopSupport.getRequestPath() + "】Class#Method → {}#{}",
                    clazzName, targetMethod.getName());
        } else {
            aopSupport.log(logLevel, " \n【the way in】 Class#Method → {}#{}", clazzName,
                    targetMethod.getName());
        }
        /// log.info("【the way in】 Class#Method → {}#{}", targetClazz.getName(), targetMethod.getName());
        Object[] parameterValues = pjp.getArgs();
        if (parameterValues != null && parameterValues.length > 0) {
            String[] parameterNames = PARAMETER_NAME_DISCOVER.getParameterNames(targetMethod);
            if (parameterNames == null) {
                throw new RuntimeException("parameterNames must not be null!");
            }
            StringBuilder sb = new StringBuilder(8);
            int iterationTimes = parameterValues.length;
            for (int i = 0; i < iterationTimes; i++) {
                sb.append("\t").append(parameterNames[i]).append(" => ").append(aopSupport.jsonPretty(parameterValues[i]));
                if (i < iterationTimes - 1) {
                    sb.append("\n");
                }
            }
            aopSupport.log(logLevel, " \n【the way in】 Param ↓ \n{}{}", sb.toString(), EMPTY_STR);
            /// log.info("【the way in】 Param ↓ \n{}", sb.toString());
        }
    }

    /**
     * 切面日志后处理
     *
     * @param logLevel
     *            日志级别
     * @param targetMethod
     *            目标方法
     * @param obj
     *            目标方法的返回结果
     * @return  执行完此方法后, aroundAdvice是否需要立即return
     * @date 2020/4/10 18:21:17
     */
    private boolean postHandel(RecordParameters.LogLevel logLevel, Method targetMethod, Object obj) {
        Class<?> returnClass = targetMethod.getReturnType();
        aopSupport.log(logLevel, "【the way out】 ReturnType → {}{}", targetMethod.getReturnType(),
                EMPTY_STR);
        /// log.info("【the way out】 ReturnType → {}", targetMethod.getReturnType());
        if (VOID_STRING.equals(returnClass.getName())) {
            return true;
        }
        aopSupport.log(logLevel, "【the way out】 ReturnResult → {}{}", aopSupport.jsonPretty(obj),
                EMPTY_STR);
        /// log.info("【the way out】 ReturnResult → {}", aopSupport.jsonPretty(obj));
        return false;
    }

    @Slf4j
    @Component
    static class AopSupport {

        private static Class<?> logClass = log.getClass();

        private static Map<String, Method> methodMap = new ConcurrentHashMap<>(8);

        @PostConstruct
        private void init() throws NoSuchMethodException {
            String debugStr = RecordParameters.LogLevel.DEBUG.name();
            String infoStr = RecordParameters.LogLevel.INFO.name();
            String warnStr = RecordParameters.LogLevel.WARN.name();
            Method debugMethod = logClass.getMethod(debugStr.toLowerCase(), String.class, Object.class, Object.class);
            Method infoMethod = logClass.getMethod(infoStr.toLowerCase(), String.class, Object.class, Object.class);
            Method warnMethod = logClass.getMethod(warnStr.toLowerCase(), String.class, Object.class, Object.class);
            methodMap.put(debugStr, debugMethod);
            methodMap.put(infoStr, infoMethod);
            methodMap.put(warnStr, warnMethod);
        }

        private void log(RecordParameters.LogLevel logLevel, String formatter, Object firstMarkerValue,
                         Object secondMarkerValue){
            doLog(methodMap.get(logLevel.name()), formatter, firstMarkerValue, secondMarkerValue);
        }

        private void doLog(Method method, String formatter, Object firstMarkerValue, Object secondMarkerValue){
            try {
                method.invoke(log, formatter, firstMarkerValue, secondMarkerValue);
            } catch (IllegalAccessException|InvocationTargetException e) {
                throw new RuntimeException("RecordParametersAdvice$AopSupport#log occur error!", e);
            }
        }

        /**
         * json格式化输出
         *
         * @param obj
         *         需要格式化的对象
         * @return json字符串
         * @date 2019/12/5 11:03
         */
        String jsonPretty(Object obj) {
            return JSON.toJSONString(obj);
        }

        /**
         * 获取请求path
         *
         * @return  请求的path
         * @date 2020/4/10 17:13:06
         */
        String getRequestPath() {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes == null) {
                return "non-path";
            }
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            return request.getRequestURI();
        }
    }

}