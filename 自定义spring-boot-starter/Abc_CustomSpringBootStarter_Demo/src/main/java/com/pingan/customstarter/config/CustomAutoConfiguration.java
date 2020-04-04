package com.pingan.customstarter.config;

import com.pingan.customstarter.service.LogicHandler;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * starter管理类
 *
 * @author JustryDeng
 * @date 2020/4/4 14:48:27
 */
@Configuration
@EnableConfigurationProperties(CustomAutoConfiguration.Properties.class)
@ConditionalOnProperty(value = "coder.info.enabled", havingValue = "true")
public class CustomAutoConfiguration {

    @Bean
    public LogicHandler logicHandler() {
        return new LogicHandler();
    }

    /**
     * 配置信息
     *
     * @author JustryDeng
     * @date 2020/4/4 14:38:05
     */
    @Setter
    @Getter
    @ConfigurationProperties(prefix = "coder.info")
    public static class Properties {

        /** 是否启用 */
        private boolean enabled = false;

        /** 姓名 */
        private String name;

        /** 爱好 */
        private String motto;
    }
}
