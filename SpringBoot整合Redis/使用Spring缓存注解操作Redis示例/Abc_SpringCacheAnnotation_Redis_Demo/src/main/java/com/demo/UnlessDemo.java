package com.demo;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * unless属性测试
 *
 * @author JustryDeng
 * @date 2019/4/13 12:06
 */
@Component
public class UnlessDemo {

    /**
     * unless的作用时机: 目标方法运行后
     *                  注: 如果(因为直接从缓存中获取到了数据，而导致)目标方法没有被执行，那么unless字段不生效
     *
     * unless的功能: 是否  令注解（在方法执行后的功能）不生效;
     *              注:unless的结果为true，则(方法执行后的功能)不生效;
     *                 unless的结果为false，则(方法执行后的)功能生效.
     *
     *  举例说明一: 对于@Cacheable注解，在执行目标方法前，如果从缓存中查询到了数据，那么直接返回缓存中的数据；
     *            如果从 缓存中没有查询到数据，那么执行目标方法，目标方法执行完毕之后，判断unless的结果，
     *            若unless的结果为true，那么不缓存方法的返回值；
     *            若unless的结果为false，那么缓存方法的返回值。
     *
     *  举例说明二: 对于@CachePut注解，在目标方法执行完毕之后，判断unless的结果，
     *            若unless的结果为true，那么不缓存方法的返回值；
     *            若unless的结果为false，那么缓存方法的返回值。
     *
     * 注:unless默认为""，即相当于默认为false.
     *
     * 注:因为unless的作用时机是在方法运行完毕后，所以我们可以用SpEL表达式   #result   来获取方法的返回值
     */
    @Cacheable(cacheNames = "TestUnlessSpace", key = "#p0", unless = "#result < 5000")
    public Integer methodTwo(Integer i) {
        System.out.println("执行方法了， 说明【指定cacheNames下不存在对应key的缓存】!");
        return i;
    }
}
