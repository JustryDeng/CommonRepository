package com.pingan.retry.spring.service.impl;

import com.pingan.retry.spring.mapper.TmpMapper;
import com.pingan.retry.spring.model.Employee;
import com.pingan.retry.spring.service.OopService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

/**
 * 将重试机制那部分代码，单独放在一个类里面，避免多个AOP作用于同一个切点
 *
 * @author JustryDeng
 * @date 2020/2/25 21:40:11
 */
@Service
@RequiredArgsConstructor
@EnableTransactionManagement(order = Ordered.HIGHEST_PRECEDENCE)
public class OopServiceImplPlus implements OopService {

    public final TmpMapper tmpMapper;

    public final OopRetrySupport oopRetrySupport;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void multipleAop() {
        tmpMapper.insertOne(Employee.builder().name("邓沙利文").build());
        oopRetrySupport.multipleAopRetry();
    }

    /**
     * 专门拿一个内部类来存放重试机制相关的代码，将@Retryable写在这个类里面的方法上。
     *
     * 注: 这样可最大程度避免重试机制对其他功能的影响。
     *
     * @author JustryDeng
     * @date 2020/2/26 18:58:57
     */
    @Component
    public static class OopRetrySupport {

        int times = 0;

        @Retryable
        public void multipleAopRetry() {
            times++;
            if (times < 3) {
                throw new RuntimeException();
            }
            System.out.println("第" + times + "次成功了！");
        }
    }
}
