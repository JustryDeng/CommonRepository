package com.szlaozicl.demo.helper;

import com.szlaozicl.demo.annotation.RecordParameters;
import com.szlaozicl.demo.author.JustryDeng;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggerConfiguration;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;

/**
 * 日志辅助器
 *
 * 参考{@link org.springframework.boot.actuate.logging.LoggersEndpoint}
 * @author {@link JustryDeng}
 * @date 2020/6/6 13:09:53
 */
@Component
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class LoggerHelper {
    
    private final LoggingSystem loggingSystem;
    
    /**
     * 获取系统支持的所有日志级别
     * <p>
     * 如:
     * <ul>
     *     <li>TRACE</li>
     *     <li>DEBUG</li>
     *     <li>INFO</li>
     *     <li>WARN</li>
     *     <li>ERROR</li>
     *     <li>FATAL</li>
     *     <li>OFF</li>
     * </ul>
     * @return  统支持的所有日志级别
     */
    public NavigableSet<LogLevel> getSupportedLogLevels() {
        Set<LogLevel> levels = this.loggingSystem.getSupportedLogLevels();
        return new TreeSet<>(levels).descendingSet();
    }
    
    /**
     * 获取当前设置的所有日志级别
     *
     * @return 当前设置的所有日志级别
     */
    public Map<String, LoggerConfiguration> getAllLoggers() {
        Collection<LoggerConfiguration> configurations = this.loggingSystem.getLoggerConfigurations();
        if (configurations == null) {
            return Collections.emptyMap();
        }
        Map<String, LoggerConfiguration> loggers = new LinkedHashMap<>(configurations.size());
        for (LoggerConfiguration configuration : configurations) {
            loggers.put(configuration.getName(), configuration);
        }
        return loggers;
    }
    
    /**
     * 获取指定位置(包/类)下的日志级别
     *
     * @param name
     *            包名 或 全类名
     *
     * @return 指定位置下的日志级别
     */
    public LoggerConfiguration getLogger(String name) {
        Assert.notNull(name, "Name must not be null");
        return this.loggingSystem.getLoggerConfiguration(name);
    }
    
    /**
     * 获取指定位置(包/类)下的日志级别(， 如果没有配置，则查找父辈的日志毕节)
     *
     * <p>
     *  <b>这是因为:</b> 若不显示指定日志级别， 那么就会默认继承(离得最近的)父辈的日志级别
     *
     * @param name
     *            包名 或 全类名
     *
     * @return 指定位置下的日志级别
     */
    public LoggerConfiguration getLoggerSearchParentIfMiss(String name) {
        Assert.notNull(name, "Name must not be null");
        LoggerConfiguration logger = this.loggingSystem.getLoggerConfiguration(name);
        while (logger == null && name.contains(".")) {
            name = name.substring(0, name.lastIndexOf("."));
            logger = this.loggingSystem.getLoggerConfiguration(name);
        }
        if (logger == null) {
            return this.loggingSystem.getLoggerConfiguration(LoggingSystem.ROOT_LOGGER_NAME);
        }
        return this.loggingSystem.getLoggerConfiguration(name);
    }
    
    /**
     * 设置日志级别
     *
     * @param name
     *            包名， 如: com.pingan.victory.controller 或 com.pingan.victory.controller.AbcController
     * @param logLevel
     *            日志级别, 如: org.springframework.boot.logging.LogLevel#DEBUG
     */
    public void setLogLevel(String name, @Nullable LogLevel logLevel) {
        Assert.notNull(name, "Name must not be empty");
        this.loggingSystem.setLogLevel(name, logLevel);
    }
    
    /**
     * 判断是否允许打印日志
     *
     * @param clazz
     *            要打印的日志所属的类
     * @param logLevel
     *            要打印的日志级别
     * @return  是否允许打印
     */
    public boolean isEnableLogging(Class<?> clazz, RecordParameters.LogLevel logLevel) {
        boolean isEnableLogging = true;
        LoggerConfiguration loggerConfiguration = this.getLoggerSearchParentIfMiss(clazz.getName());
        // 如没有任何日志级别配置， 那么不能打印日志
        if (loggerConfiguration == null) {
            return false;
        }
        // fastEnum本次需要打印的日志级别
        int fastEnum = logLevel.getFastEnum();
        // clazz类允许打印的最下日志级别
        LogLevel effectiveLevel = loggerConfiguration.getEffectiveLevel();
        int allowLoggingMinValue;
        switch(effectiveLevel) {
            case TRACE :
                allowLoggingMinValue = 0;
                break;
            case DEBUG :
                allowLoggingMinValue = 1;
                break;
            case INFO :
                allowLoggingMinValue = 2;
                break;
            case WARN :
                allowLoggingMinValue = 3;
                break;
            case ERROR :
                allowLoggingMinValue = 4;
                break;
            case FATAL :
                allowLoggingMinValue = 5;
                break;
            default:
                allowLoggingMinValue = Integer.MAX_VALUE;
        }
        return fastEnum >= allowLoggingMinValue;
    }
}
