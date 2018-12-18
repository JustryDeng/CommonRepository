package com.aspire.controller;
import com.alibaba.fastjson.JSON;
import com.aspire.annotation.AdviceOne;
import com.aspire.model.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * aop测试
 *
 * @author JustryDeng
 * @date 2018/12/3 19:42
 */
@RestController
public class AopController {

    @AdviceOne
    @RequestMapping(value = "/test", method = {RequestMethod.POST})
    public Map<String, Object> test(@RequestBody User user) {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " -> " + JSON.toJSONString(user));
        Map<String, Object> map = new HashMap<>(8);
        map.put("params", JSON.toJSONString(user));
        map.put("code", "0000");
        map.put("message", "成功");
        return map;
    }

}
