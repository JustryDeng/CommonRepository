package com.pingan.retry.spring;

import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

/**
 * 目标方法:被@Retryable标记的方法
 * 处理方法:被@Recover标记的方法
 *
 * 处理方法 和 目标方法 必须满足:
 *    1. 处于同一个类下
 *    2. 两者的参数需要保持一致  或  处理方法的参数可以多一个异常接收类(这一异常接收类必须放在第一个参数的位置)
 *       注:保持一致指的是参数类型保持一致,形参名可以一样可以不一样
 *    3. 返回值类型需要保持一致 (或处理方法的返回值类型是目标方法的返回值类型的超类 )
 *
 * 目标方法在进行完毕retry后，如果仍然抛出异常, 那么会去定位处理方法, 走处理方法的逻辑，定位处理方法的原则是:
 * - 在同一个类下，寻找和目标方法 具有
 *      相同参数类型(P.S.可能会再参数列表首位多一个异常类参数)、
 *      相同返回值类型
 *      的标记有Recover的方法
 * - 如果存在两个目标方法，他们的参数类型、返回值类型都一样，
 *     这时就需要主动指定对应的处理方法了，
 *     如:@Retryable(recover = "service1Recover")
 *
 * @author JustryDeng
 * @date 2020/2/25 21:40:11
 */
@Component
public class QwerRemoteCall {

    private int times = 0;

    /// --------------------------------------------------------- @Recover基本测试

    @Retryable
    public String methodFour(Integer a, String b) {
        times++;
        throw new RuntimeException("times=" + times + ", 发生的异常是RuntimeException");
    }

    @Recover
    private String justryDeng(Throwable th, Integer a, String b) {
        return "a=" + a + ", b=" + b + "\t" + "异常类是:"
                + th.getClass().getName() + "， 异常信息是:" + th.getMessage();
    }


    ///  如果在@Retryable中指明了异常, 那么在@Recover中可以明确的指明是哪一种异常
    /// @Retryable(RemoteAccessException.class)
    /// public void service() {
    ///     // ... do something
    /// }
    ///
    /// @Recover
    /// public void recover(RemoteAccessException e) {
    ///     // ... panic
    /// }



    /// --------------------------------------------------------- @Retryable指定对应的@Recover方法
    /// 特别注意: @Retryable注解的recover属性, 在spring-retry的较高版本中才得以支持,
    ///          在本人使用的1.2.5.RELEASE版本中还暂不支持

    /// @Retryable(recover = "service1Recover", value = RemoteAccessException.class)
    /// public void service1(String str1, String str2) {
    ///     // ... do something
    /// }
    ///
    /// @Retryable(recover = "service2Recover", value = RemoteAccessException.class)
    /// public void service2(String str1, String str2) {
    ///     // ... do something
    /// }
    ///
    /// @Recover
    /// public void service1Recover(RemoteAccessException e, String str1, String str2) {
    ///     // ... error handling making use of original args if required
    /// }
    ///
    /// @Recover
    /// public void service2Recover(RemoteAccessException e, String str1, String str2) {
    ///     // ... error handling making use of original args if required
    /// }

}
