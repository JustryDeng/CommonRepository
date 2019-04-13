package com.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * 定制化CachingConfigurer
 *
 * @author JustryDeng
 * @date 2019/4/11 16:26
 */
@Configuration
public class MyCachingConfigurer extends CachingConfigurerSupport {

    /**
     * 定制化key生成器
     *
     * 设置  全限定类名 + 方法名 + 参数名 共同组成 key
     *
     * @return key生成器
     * @date 2019/4/12 14:09
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (Object target, Method method, Object... params) -> {
            StringBuilder sb = new StringBuilder(16);
            sb.append(target.getClass().getName());
            sb.append("_");
            sb.append(method.getName());
            sb.append("_");
            for (int i = 0; i < params.length; i++) {
                sb.append(params[i]);
                if (i < params.length - 1) {
                    sb.append(",");
                }
            }
            return sb.toString();
        };
    }
}

