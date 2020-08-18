package com.pingan.logbackfilter.filter;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import com.pingan.logbackfilter.author.JustryDeng;

/**
 * 自定义过滤器
 *
 * @author {@link JustryDeng}
 * @since 2020/8/18 11:29:57
 */
public class CustomizedFilter extends Filter<ILoggingEvent> {
    
    
    /**
     * 磁过滤器会过滤掉这些日志:
     *    1. loggerName不为com.pingan.logbackfilter.LogbackFilterApplicationTests的
     *    2. 日志级别小于info的
     *    3. 日志内容为空(或空白)的
     */
    @Override
    public FilterReply decide(ILoggingEvent event) {
        String loggerName = event.getLoggerName();
        if (!"com.pingan.logbackfilter.LogbackFilterApplicationTests".equals(loggerName)) {
            return FilterReply.DENY;
        }
        Level level = event.getLevel();
        if (level.levelInt < Level.INFO_INT) {
            return FilterReply.DENY;
        }
        String formattedMessage = event.getFormattedMessage();
        if (formattedMessage == null || formattedMessage.trim().length() == 0) {
            return FilterReply.DENY;
        }
        return FilterReply.NEUTRAL;
    }
}
