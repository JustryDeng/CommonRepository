package cn.afterturn.easypoi.test.web.cfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.BeanNameViewResolver;

@Configuration
public class Cfg {

    @Bean
    public BeanNameViewResolver getBeanNameViewResolver() {
        BeanNameViewResolver view = new BeanNameViewResolver();
        view.setOrder(-100);
        return view;
    }

}
