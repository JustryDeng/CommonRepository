package com.controller;

import com.model.PersonInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 测试Controller
 *
 * @author JustryDeng
 * @date 2019/3/19 14:14
 */
@RestController
@Slf4j
public class TestController {

    @PostMapping("/test")
    public PersonInfoVO test(@RequestBody PersonInfoVO personInfoVO,
                             @RequestHeader("Authorization") String authorization) {
        log.info("TestController -> test -> @RequestBody got -> {}", personInfoVO);
        log.info("TestController -> test -> @RequestHeader got -> {}", authorization);
        personInfoVO.setAge(100);
        personInfoVO.setName("咿呀咿呀哟~");
        personInfoVO.setGender("其他");
        personInfoVO.setBorthDate(new Date());
        return personInfoVO;
    }
}
