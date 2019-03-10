package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author JustryDeng
 * @date 2019/2/15 9:43
 */
@SpringBootApplication
public class KafkaApplication {

    public static void main(String[] args) {
        systemPropertisConfig();
        SpringApplication.run(KafkaApplication.class, args);
    }

    /**
     * 系统环境属性 --- 设置
     *
     * 注:因为是系统参数，多出地方都要使用；所以直接写在启动类里面
     *
     * 注:设置系统环境属性 的 方式较多，这只是其中的一种
     *
     * @author JustryDeng
     * @date 2019/2/24 10:31
     */
    private static void systemPropertisConfig(){
        System.setProperty("java.security.auth.login.config",
                           "C:/Users/JustryDeng/Desktop/kerberos/kafka_client_jaas.conf");
    }

}