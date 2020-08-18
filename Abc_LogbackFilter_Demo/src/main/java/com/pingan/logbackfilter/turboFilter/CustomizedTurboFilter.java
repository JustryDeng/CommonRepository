package com.pingan.logbackfilter.turboFilter;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;
import com.pingan.logbackfilter.author.JustryDeng;
import org.slf4j.Marker;
import org.slf4j.helpers.MessageFormatter;

/**
 * 自定义过滤器
 *
 * @author {@link JustryDeng}
 * @since 2020/8/18 11:54:16
 */
public class CustomizedTurboFilter extends TurboFilter {
    
    /**
     * 进行过滤决策
     *
     * @param marker
     *            站位标志
     * @param logger
     *            日志器
     * @param level
     *            日志级别
     * @param message
     *            未进行相应填充的字符串
     *            <p/>
     *            如: <code>log.debug(" i am {}", "debug");</> 那么这里message值为:  i am {}】
     *            <p/>
     *            注： 可通过{@link MessageFormatter#arrayFormat(String, Object[]).getMessage()}
     *                 或{@link MessageFormatter#arrayFormat(String, Object[], Throwable).getMessage()}
     *                 得到格式化后最终的日志信息formattedMessage
     * @param params
     *            message中对应{}的实际参数数组
     * @param t
     *            错误信息t
     * @return  过滤结果状态
     */
    @Override
    public FilterReply decide(Marker marker, Logger logger, Level level, String message, Object[] params, Throwable t) {
        String loggerName = logger.getName();
        if (!"com.pingan.logbackfilter.LogbackFilterApplicationTests".equals(loggerName)) {
            return FilterReply.DENY;
        }
        if (level.levelInt < Level.INFO_INT) {
            return FilterReply.DENY;
        }
        // 格式化后的结果
        String formattedMessage = MessageFormatter.arrayFormat(message, params, t).getMessage();
        System.err.println("格式化后的结果: " + formattedMessage);
        if (formattedMessage == null || formattedMessage.trim().length() == 0) {
            return FilterReply.DENY;
        }
        return FilterReply.NEUTRAL;
    }
}
