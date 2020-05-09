package com.aspire.ssm.plugins;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 自定义Mybatis插件
 *
 * @author JustryDeng
 * @date 2020/1/31 22:47
 */
@Slf4j
@Component
@Order(MyExecutorPlugin.HIGH_ORDER)
@Intercepts(value = {
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
@ConditionalOnProperty(value = "my-plugins.enable", havingValue = "true")
public class MyExecutorPlugin implements Interceptor {

    /** 高优先级 */
    public static final int HIGH_ORDER = 0;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info(" MyExecutorPlugin#intercept, pre logic");
        Object result = invocation.proceed();
        log.info(" MyExecutorPlugin#intercept, post logic");
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // ignore
    }
}
