package com.pingan.retry.spring.service.impl;

import com.pingan.retry.spring.mapper.TmpMapper;
import com.pingan.retry.spring.model.Employee;
import com.pingan.retry.spring.service.OopService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

/**
 * 使用spring-retry注解式时,避免多个AOP可能导致的问题
 *
 * 注:通过@EnableTransactionManagement(order = Ordered.HIGHEST_PRECEDENCE)可以
 *    指定事务AOP优先级为最高，这样一来@Retryable的AOP就会被包在事务AOP内，
 *    就会导致重复数据问题。
 *
 * @author JustryDeng
 * @date 2020/2/25 21:40:11
 */
@Service
@RequiredArgsConstructor
@EnableTransactionManagement(order = Ordered.HIGHEST_PRECEDENCE)
public class OopServiceImpl implements OopService {

    public final TmpMapper tmpMapper;

    int times = 0;

    @Override
    @Retryable
    @Transactional(rollbackFor = Exception.class)
    public void multipleAop() {
        times++;
        tmpMapper.insertOne(Employee.builder().name("邓沙利文").build());
        if (times < 3) {
            throw new RuntimeException();
        }
        System.out.println("第" + times + "次成功了！");
    }

}
