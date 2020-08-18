package com.pingan.logbackfilter;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class LogbackFilterApplicationTests {
    
    @Test
    void levelFilterTest() {
        log.debug(" i am debug");
        log.info(" i am info");
        log.warn(" i am warn");
        log.error(" i am error");
    }

    @Test
    void thresholdFilterTest() {
        log.debug(" i am debug");
        log.info(" i am info");
        log.warn(" i am warn");
        log.error(" i am error");
    }
    
    @Test
    void evaluatorFilterTest() {
        log.debug(" i am debug");
        log.info(" i am info, but my content contain debug");
        log.warn(" i am warn");
        log.error(" i am error");
    }
    
    @Test
    void mdcFilterTest() {
        MDC.put("name", "jd_1");
        log.debug(" 1");
        MDC.clear();
        
        MDC.put("name", "jd_2");
        log.debug(" 2");
        MDC.clear();
        
        MDC.put("name", "jd_3");
        log.debug(" 3");
        MDC.clear();
        
        MDC.put("name", "jd_4");
        log.debug(" 4");
        MDC.clear();
    }
    
    @Test
    void customizedFilterTest() {
        log.debug(" i am debug");
        log.info(" i am info");
        log.warn("");
        log.error(" i am error");
    }
    
    @Test
    void customizedTurboFilterTest() {
        log.debug(" i am {}", "debug");
        log.info(" i am {}", "info");
        log.warn("");
        log.error(" i am {}", "error");
    }
}
