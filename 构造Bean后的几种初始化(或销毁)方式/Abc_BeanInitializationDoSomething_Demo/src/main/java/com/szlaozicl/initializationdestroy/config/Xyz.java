package com.szlaozicl.initializationdestroy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 测试@PostConstruct、@PreDestroy
 *
 * @author JustryDeng
 * @date 2019/8/18 10:42
 */
@Configuration
public class Xyz {

    /**
     * 注:在这里通过@Bean将Abc的声明周期交由Spring进行管理，同时指定其初始化方法、销毁方法。
     *
     * 注:如果注入Abc时，采用的是懒加载，那么只有当程序第一次用到Abc时，才会走其构造以及后面紧接着的相关初始化方法。
     */
    @Bean(initMethod = "myInit", destroyMethod = "myDestroy")
    public Abc abc() {
        return new Abc();
    }
}


