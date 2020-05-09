package com.aspire.ssm.plugins;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.sql.Statement;
import java.util.Properties;

/**
 * 自定义Mybatis插件
 *
 * @author JustryDeng
 * @date 2020/1/31 22:47
 */
@Slf4j
@Component
@Intercepts(value = {
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})
})
@ConditionalOnProperty(value = "my-plugins.enable", havingValue = "true")
public class MyResultSetHandlerPlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info(" MyResultSetHandlerPlugin#intercept, pre logic");
        Object result = invocation.proceed();
        log.info(" MyResultSetHandlerPlugin#intercept, post logic");
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
