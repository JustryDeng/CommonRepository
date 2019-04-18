package com.controller;

import com.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

/**
 * FreeMarker使用示例
 *
 * @author JustryDeng
 * @date 2019/4/17 21:34
 */
@Controller
public class DemoController {

    private static final int LIMIT_MAX = 5;

    /**
     * FreeMarker常用语法示例
     *
     * @param model
     *            SpringMVC 数据模型
     *
     * @return  (无后缀的)模板文件名
     *          注:模板文件所在根目录在配置文件
     * @date 2019/4/17 21:51
     */
    @GetMapping("/grammar/demo")
    public String demoMethod(Model model) {

        // 直接取值示例
        model.addAttribute("testOne", "邓沙利文");

        // 获取对象属性值示例
        User testTwoUser = User.builder()
                               .name("邓二洋")
                               .age(25)
                               .gender("男")
                               .hobby("女")
                               .motto("我是一只小小小小鸟~")
                               .build();
        model.addAttribute("testTwo", testTwoUser);

        // if示例
        User testThreeUser = User.builder().name("邓沙利文").age(25).build();
        model.addAttribute("testThree", testThreeUser);

        // list 示例
        List<User> testFourList = new ArrayList<>(8);
        for (int i = 0; i < LIMIT_MAX; i++) {
            User u = User.builder().name("邓" + i + "洋" ).motto("我是一只小小小小鸟~").build();
            testFourList.add(u);
        }
        model.addAttribute("testFourList", testFourList);

        // map示例
        Map<String, User> testFiveMap = new HashMap<>(8);
        for (int i = 0; i < LIMIT_MAX; i++) {
            User tempUser = User.builder().name("邓" + i + "洋" ).motto("我是一只小小小小鸟~").build();
            testFiveMap.put("key" + i, tempUser);
        }
        model.addAttribute("testFiveMap", testFiveMap);

        // 日期示例
        model.addAttribute("myDate", new Date());
        return "abc/grammar_demo";
    }

    /**
     * 以以下三种中的任意一种模型来封装数据，都是会被FreeMarker解析到的
     *
     * 1、org.springframework.ui.Model
     *
     * 2、org.springframework.ui.ModelMap
     *
     * 3、java.uti.Map
     *
     * @param mode
     *            SpringMVC 数据模型
     *
     * @return  (无后缀的)模板文件名
     *          注:模板文件所在根目录在配置文件
     * @date 2019/4/18 16:41
     */
    @GetMapping("/model/test1")
    public String modelTestOne(Model mode) {
        mode.addAttribute("xyz", "org.springframework.ui.Model也可以作为参数模型！");
        return "abc/model_test";
    }

    @GetMapping("/model/test2")
    public String modelTestTwo(ModelMap modelMap) {
        modelMap.addAttribute("xyz", "org.springframework.ui.ModelMap也可以作为参数模型！");
        return "abc/model_test";
    }

    @GetMapping("/model/test3")
    public String demoMethodThree(Map<String, Object> map) {
        map.put("xyz", "java.util.Map也可以作为参数模型！");
        return "abc/model_test";
    }


    /**
     * 当方法需要传入参数时的 GET测试
     *
     * @param model
     *            SpringMVC 数据模型
     * @param name
     *            用户传入的参数name
     * @param age
     *            用户传入的参数age
     *
     * @return  (无后缀的)模板文件名
     *          注:模板文件所在根目录在配置文件
     * @date 2019/4/18 16:41
     */
    @GetMapping("/hava/param/get/")
    public String paramsTest(Model model, @RequestParam("name") String name, @RequestParam("age")  Integer age) {
        model.addAttribute("myRoot", name + age);
        return "abc/hava_params_test";
    }

    /**
     * 当方法需要传入参数时的 POST测试
     *
     * @param model
     *            SpringMVC 数据模型
     * @param user
     *            用户传入的参数user
     *
     * @return  (无后缀的)模板文件名
     *          注:模板文件所在根目录在配置文件
     * @date 2019/4/18 16:41
     */
    @PostMapping("/hava/param/post/")
    public String paramsTest(Model model, @RequestBody User user) {
        model.addAttribute("myRoot", user.getName() + "都特么" + user.getAge() + "岁了！");
        return "abc/hava_params_test";
    }

}
