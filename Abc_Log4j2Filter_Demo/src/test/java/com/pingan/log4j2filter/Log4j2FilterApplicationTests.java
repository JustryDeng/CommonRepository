package com.pingan.log4j2filter;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class Log4j2FilterApplicationTests {
    
    @Test
    void contextLoads() {
        log.info("asdf");
    }
    
}
