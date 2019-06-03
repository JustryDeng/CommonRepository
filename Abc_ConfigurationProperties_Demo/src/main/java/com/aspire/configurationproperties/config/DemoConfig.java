package com.aspire.configurationproperties.config;

import com.aspire.configurationproperties.model.User;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * ConfigurationProperties示例
 *
 * 注意一：@ConfigurationProperties注解的类，的属性，必须要有setter才行
 * 注意二：@ConfigurationProperties注解的类，必须要注入Spring容器中
 * 注意三：如果是将配置信息写在yml文件中的话，那么最好直接写在application.yml或application.yaml
 *        文件中，否者可能获取不到
 *
 * 提示: 匹配时，会自动忽略大小写
 *
 * @author JustryDeng
 * @date 2019/5/31 21:44
 */
@Component
@PropertySource(value = {"classpath:/demo.properties"}, encoding = "gbk")
@ConfigurationProperties(prefix = "prop")
@Data
public class DemoConfig {

    private String normalString;

    private Integer normalNumber;

    private Boolean normalBoolean;

    /** 这里类型采用数组或集合都可以 */
    private String[] myListOrArray;

    private Map<String, Object> myMap;

    private User myUser;

    /** 这里类型采用数组或集合都可以 */
    private List<User> myListOrArrayPlus;

    private Map<String, User> myMapPlus;

    /**
     * 注:此方法不是必须的
     *
     * 虽然此类已经注入了Spring容器中了，但是有时我们为了方便直接获取到属性，
     * 我们可以通过类似以下的方式，将某个属性注入到容器中；
     * 获取时，形如
     *     @Autowired
     *     @Qualifier("initInfoMap")
     *     private Map<String, User> initInfoMap;
     * 这样获取就行
     *
     * @date 2019/6/3 14:48
     */
    @Bean(name = "initInfoMap")
    private Map<String, User> initMap(){
        return this.myMapPlus;
    }
}