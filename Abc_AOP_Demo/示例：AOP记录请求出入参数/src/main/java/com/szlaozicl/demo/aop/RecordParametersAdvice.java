package com.szlaozicl.demo.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.reflect.Method;

/**
 * 方法 入参、出参 记录
 *
 * @author JustryDeng
 * @date 2019/12/4 13:57
 */
@Slf4j
@Aspect
@Configuration
public class RecordParametersAdvice {

    /** 参数名发现器 */
    private static final LocalVariableTableParameterNameDiscoverer PARAMETER_NAME_DISCOVER = new LocalVariableTableParameterNameDiscoverer();

    /** 无返回值 */
    private static final String VOID_STRING = "void";


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

        Object targetObj = thisJoinPoint.getTarget();
        String className = targetObj.getClass().getName();

        MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        String methodName = targetMethod.getName();
        log.info("【the way in】 Class#Method → {}#{}", className, methodName);


        Object[] parameterValues = thisJoinPoint.getArgs();
        if (parameterValues != null && parameterValues.length > 0) {
            String[] parameterNames = PARAMETER_NAME_DISCOVER.getParameterNames(targetMethod);
            if (parameterNames == null) {
                throw new RuntimeException("parameterNames must not be null!");
            }
            StringBuilder sb = new StringBuilder(8);
            int iterationTimes = parameterValues.length;
            for (int i = 0; i < iterationTimes; i++) {
                sb.append("\t").append(parameterNames[i]).append(" => ")
                        .append(jsonPretty(parameterValues[i]));
                if (i < iterationTimes - 1) {
                    sb.append("\n");
                }
            }
            log.info("【the way in】 Param ↓ \n{}", sb.toString());
        }

        Object obj = thisJoinPoint.proceed();
        Class<?> returnClass = targetMethod.getReturnType();
        log.info("【the way out】 ReturnType → {}", targetMethod.getReturnType());
        if (VOID_STRING.equals(returnClass.getName())) {
            return obj;
        }
        log.info("【the way out】 ReturnResult → {}", jsonPretty(obj));
        return obj;
    }

    /**
     * json格式化输出
     *
     * @param obj
     *         需要格式化的对象
     * @return json字符串
     * @date 2019/12/5 11:03
     */
    private String jsonPretty(Object obj) {
        return JSON.toJSONString(obj);
    }

}
