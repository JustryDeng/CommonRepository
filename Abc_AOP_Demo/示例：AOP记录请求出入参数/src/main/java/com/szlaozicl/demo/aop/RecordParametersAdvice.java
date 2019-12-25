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
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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

    /** 参数名发现器 */
    private static final LocalVariableTableParameterNameDiscoverer PARAMETER_NAME_DISCOVER = new LocalVariableTableParameterNameDiscoverer();

    /** 无返回值 */
    private static final String VOID_STRING = "void";

    private final AopSupport aopSupport;

    public RecordParametersAdvice(AopSupport aopSupport) {
        this.aopSupport = aopSupport;
    }

    /**
     * 【@within】: 当将注解加在类上时，等同于 在该类下的所有方法上加上了该注解(即:该类的所有方法都会被aop)。
     *              注意:注解必须写在类上，不能写在接口上。
     * 【@annotation】: 当将注解加在某个方法上时，该方法会被aop。
     */
    @Pointcut(
            "("
               + "@within(com.szlaozicl.demo.annotation.RecordParameters)"
               + " || "
               + "@annotation(com.szlaozicl.demo.annotation.RecordParameters)"
            + ")"
            + " && "
            + "!@annotation(com.szlaozicl.demo.annotation.IgnoreRecordParameters)"
    )
    public void executeAdvice() {
    }


    /**
     * 环绕增强
     */
    @Around("executeAdvice()")
    public Object aroundAdvice(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        final String emptyStr = "";
        // 获取目标Class
        Object targetObj = thisJoinPoint.getTarget();
        Class<?> targetClazz = targetObj.getClass();
        // 获取目标method
        MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        // 获取目标annotation
        RecordParameters annotation = targetMethod.getAnnotation(RecordParameters.class);
        if (annotation == null) {
            annotation = targetClazz.getAnnotation(RecordParameters.class);
        }
        if (annotation == null) {
            throw new RuntimeException("not found @RecordParameters!");
        }
        // 是否需要记录入参、出参
        boolean shouldRecordInputParams = annotation.strategy() == RecordParameters.Strategy.INPUT
                || annotation.strategy() == RecordParameters.Strategy.INPUT_OUTPUT;
        boolean shouldRecordOutputParams = annotation.strategy() == RecordParameters.Strategy.OUTPUT
                || annotation.strategy() == RecordParameters.Strategy.INPUT_OUTPUT;

        if (shouldRecordInputParams) {
            aopSupport.log(annotation.logLevel(), "【the way in】 Class#Method → {}#{}", targetClazz.getName(),
                    targetMethod.getName());
            /// log.info("【the way in】 Class#Method → {}#{}", targetClazz.getName(), targetMethod.getName());
            Object[] parameterValues = thisJoinPoint.getArgs();
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
                aopSupport.log(annotation.logLevel(), "【the way in】 Param ↓ \n{}{}", sb.toString(), emptyStr);
                /// log.info("【the way in】 Param ↓ \n{}", sb.toString());
            }
        }

        Object obj = thisJoinPoint.proceed();
        if (shouldRecordOutputParams) {
            Class<?> returnClass = targetMethod.getReturnType();
            aopSupport.log(annotation.logLevel(), "【the way out】 ReturnType → {}{}", targetMethod.getReturnType(),
                    emptyStr);
            /// log.info("【the way out】 ReturnType → {}", targetMethod.getReturnType());
            if (VOID_STRING.equals(returnClass.getName())) {
                return obj;
            }
            aopSupport.log(annotation.logLevel(), "【the way out】 ReturnResult → {}{}", aopSupport.jsonPretty(obj),
                    emptyStr);
            /// log.info("【the way out】 ReturnResult → {}", aopSupport.jsonPretty(obj));
        }
        return obj;
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
    }

}