package com.aspire.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


/**
 * AOP切面
 *
 * 以下几个增强的执行顺序是:
 *     1.aroundAdvice(.proceed();语句之前的代码)
 *     2.beforeAdvice
 *     3.被增强方法
 *     4.aroundAdvice(.proceed();语句之后的代码)
 *     5.afterAdvice
 *     6.afterReturningAdvice
 *     7.afterThrowingAdvice（有异常时才会走，无异常不会走此方法）
 *
 *  注: 当被增强方法 或 afterAdvice 或 afterReturningAdvice抛出异常时，会被afterThrowingAdvice
 *      捕获到异常，进而短路走 afterThrowingAdvice方法
 *
 * @author JustryDeng
 * @date 2018/12/17 20:25
 */
@Aspect
@Configuration
public class AopConfig {

    /**
     * 将表达式【* com.aspire.controller.AopController.*(..))】所匹配的所有方法标记为切点，
     * 切点名为 excudeAdvice()
     *
     * 注:execution里的表达式所涉及到的类名(除了基本类以外)，其它的要用全类名;干脆不管是不
     *    是基础类，都推荐使用全类名
     *
     * @author JustryDeng
     * @date 2018/12/18 13:43
     */
    // @Pointcut("execution(* com.aspire.controller.AopController.*(..))")
    /**
     * 使用注解来定位AOP作为节点的方法们
     */
    @Pointcut("@annotation(com.aspire.annotation.AdviceOne)")
    public void excudeAdvice() {
    }

    /**
     * 切点excudeAdvice()的前置增强方法
     *
     * @author JustryDeng
     * @date 2018/12/18 13:47
     */
    @Before(value = "excudeAdvice()")
    public void beforeAdvice(JoinPoint joinPoint) {
        Object[] paramArray = joinPoint.getArgs();
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " -> beforeAdvice获取到了被增强方法的参数了，为:" + paramArray);
    }

    /**
     * 切点excudeAdvice()的后增强方法
     *
     * @author JustryDeng
     * @date 2018/12/18 13:47
     */
    @After("excudeAdvice()")
    public void afterAdvice() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " -> 后置增强afterAdvice执行了");
    }

    /**
     * 切点excudeAdvice()的后增强方法
     *
     * 注:当被增强方法 或  afterAdvice正常执行时，才会走此方法
     * 注: returning指明获取到的(环绕增强返回的)返回值
     *
     * @author JustryDeng
     * @date 2018/12/18 13:47
     */
    @AfterReturning(value = "excudeAdvice()", returning = "map")
    public void afterReturningAdvice(Map<String, Object> map) {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " -> afterReturningAdvice获得了返回结果 map -> " + map);
    }


    /**
     *  当被增强方法 或 afterAdvice 或 afterReturningAdvice抛出异常时，会被afterThrowingAdvice
     *  捕获到异常，进而短路走 afterThrowingAdvice方法
     *
     * @author JustryDeng
     * @date 2018/12/18 13:57
     */
    @AfterThrowing(value = "excudeAdvice()", throwing ="ex")
    public void afterThrowingAdvice(Exception ex) {
        System.out.println("AfterThrowing捕获到了 --->" + ex);
    }


    /**
     *  环绕增强 会在 被增强方法执行完毕后  第一个执行，
     *  所以在绝大多数时候，我们都直接返回thisJoinPoint.proceed();的返回值；
     *  如果此方法返回null,那么@AfterReturning方法获取到的返回值 就会是null
     */
    @Around("excudeAdvice()")
    public Object aroundAdvice(ProceedingJoinPoint thisJoinPoint) {
        String threadName = Thread.currentThread().getName();
        Object obj = null;
        System.err.println(threadName + " -> 环绕增强aroundAdvice --> before proceed()执行了");
        try {
            // 执行被增强方法，并获取到返回值
            // 类似于 过滤器的chain.doFilter(req,resp)方法
            obj = thisJoinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.err.println(threadName + " -> 环绕增强aroundAdvice --> after proceed()执行了");
        return obj;
    }

}
