package com.szlaozicl.mavenprofile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

/**
 * 启动类
 *
 * @author JustryDeng
 * @date 2019/9/3 14:33
 */
@SpringBootApplication
public class MavenProfileApplication {

    public static void main(String[] args) {
//        SpringApplication.run(MavenProfileApplication.class, args);
        osInfo();

    }


    /**
     * 获取操作系统信息
     *
     * @date 2019/9/4 11:21
     */
    private static void osInfo() {
        Properties osProperties = System.getProperties();
        // name-操作系统名，如【Windows 10】
        System.out.println(osProperties.get("os.name"));

        // family-操作系统隶属， 如【windows】、【unix】, 可根据name来获取
        System.out.println(
                osProperties.get("os.name").toString().startsWith("Window") ? "windows" : "unix"
        );

        // arch-操作系统的体系结构，如【amd64】
        System.out.println(osProperties.get("os.arch"));

        // version-操作系统版本号，如【10.0】
        System.out.println(osProperties.get("os.version"));
    }

}
