package com.pingan.customstarter.service;

import com.pingan.customstarter.config.CustomAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * do some logic
 *
 * @author JustryDeng
 * @date 2020/4/4 14:56:12
 */
public class LogicHandler {

    @Autowired
    private CustomAutoConfiguration.Properties properties;

    public void handle() {
        System.err.println(properties.getName() + "的爱好是\t=>\t" + properties.getMotto());
    }
}
