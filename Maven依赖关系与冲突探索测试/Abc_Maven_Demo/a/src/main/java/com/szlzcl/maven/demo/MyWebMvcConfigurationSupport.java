package com.szlzcl.maven.demo;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义部分MVC配置
 *
 * @author JustryDeng
 * @date 2019/7/17 12:29
 */
@Component
public class MyWebMvcConfigurationSupport extends WebMvcConfigurationSupport {

    /**
     * 使用fastjson作为JSON消息转换器
     * 使用StringHttpMessageConverter作为String消息转换器
     * <p>
     * 注: 一般来讲，这两个消息消息转换器就是用于绝大部分情况了;
     * 如果业务环境情况等比较特殊，需要其他的消息转换器，
     * 那么再追加新的转换器即可
     * <p>
     * 注:JSON消息转换器需要引入fastjson依赖
     * StringHttpMessageConverter消息转换器需要引入springframework依赖
     *
     * @date 2019/7/18 16:21
     */
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        /// -> JSON消息转换器(采用阿里的fastjson)
        // 创建一个转换器对象;
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();

        // 个性化配置转换特性
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        // 配置:要格式化返回的json数据
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        // 配置:把空的值的key也返回
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue);
        // 字段如果为null,输出为false,而非null
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteNullBooleanAsFalse);
        // 数值字段如果为null,输出为0,而非null
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteNullNumberAsZero);
        // List字段如果为null,输出为[],而非null;
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteNullListAsEmpty);
        // 字符类型字段如果为null,输出为”“,而非null
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteNullStringAsEmpty);
        // 设置日期格式化输出
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteDateUseDateFormat);
        // 输出key时是否使用双引号,默认为true
        /// fastJsonConfig.setSerializerFeatures(SerializerFeature.QuoteFieldNames);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);

        // 处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>(4);
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);

        // 将convert添加到converters当中.
        converters.add(fastJsonHttpMessageConverter);

        /// -> String消息转换器
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        stringConverter.setDefaultCharset(Charset.forName("UTF-8"));
        stringConverter.setSupportedMediaTypes(fastMediaTypes);

        // 将convert添加到converters当中.
        converters.add(fastJsonHttpMessageConverter);
        converters.add(stringConverter);
        super.configureMessageConverters(converters);
    }

    /**
     * 跨域配置
     *
     * @date 2019/7/23 18:51
     */
    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("access-control-allow-headers", "access-control-allow-methods",
                        "access-control-allow-origin", "access-control-max-age", "X-Frame-Options")
                .allowCredentials(false).maxAge(3600);
        super.addCorsMappings(registry);
    }

}