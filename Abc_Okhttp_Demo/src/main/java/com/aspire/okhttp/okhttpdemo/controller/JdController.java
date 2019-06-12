package com.aspire.okhttp.okhttpdemo.controller;

import com.aspire.okhttp.okhttpdemo.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;

/**
 * controller
 *
 * @author JustryDeng
 * @date 2019/6/10 9:50
 */
@Slf4j
@RestController
@SuppressWarnings("all")
public class JdController {

    private static final String[] GENDER_ARR;

    private static final String[] MOTTO_ARR;

    /** 使用jaskson作为json转换器 */
    private ObjectMapper jacksonMapper = new ObjectMapper();

    /** 使用安全的随机数类 */
    private SecureRandom random = new SecureRandom();

    static {
        GENDER_ARR = new String[]{"男", "女"};
        MOTTO_ARR = new String[]{"我是一只小小小小鸟~嗷嗷！", "蚂蚁牙黑！", "我爱她轰轰烈烈不会忘~"};
    }

    /**
     * 用于get方法测试
     *
     * @date 2019/6/10 9:52
     */
    @GetMapping("/get/test")
    public User getOneController(@RequestParam("name") String name) throws JsonProcessingException {
        int age = random.nextInt(100);
        String gender = GENDER_ARR[random.nextInt(1)];
        String motto = MOTTO_ARR[random.nextInt(2)];
        User user = User.builder().name(name).age(age).gender(gender).motto(motto).build();
        log.info("getOneController - return -> {}", jacksonMapper.writeValueAsString(user));
        return user;
    }

    /**
     * 用于post方法测试one
     *
     * @date 2019/6/10 9:52
     */
    @PostMapping("/post/test/one")
    public String postOneController(@RequestBody User user) {
        log.info("postOneController - return -> {}", user);
        return user.toString();
    }

    /**
     * 用于post方法测试two
     *
     * @date 2019/6/10 9:52
     */
    @PostMapping("/post/test/two")
    public String postTwoController(@RequestParam("platform") String platform, String name, String subject) {
        String result = platform + " ^_^ " +  name + " ^_^ " + subject;
        log.info("postTwoController - return -> {}", result);
        return result;
    }

    /**
     * 用于post方法测试three
     *
     * @date 2019/6/10 9:52
     */
    @PostMapping("/post/test/three")
    public String postThreeController(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        String result = "流转换为字符串后\t" + baos.toString();
        log.info("postThreeController - return -> {}", result);
        return result;
    }

    /**
     * 用于post方法测试four
     *
     * 注:单个文件的话，形参处直接使用MultipartFile即可
     *
     * @date 2019/6/10 9:52
     */
    @PostMapping("/post/test/four")
    public String postFourController(@RequestParam("fileName") MultipartFile[] files) throws IOException {
        StringBuilder sb = new StringBuilder(128);
        for (MultipartFile file : files) {
            sb.append("\nfile.getName()").append(" = ").append(file.getName());
            sb.append("\nfile.getContentType()").append(" = ").append(file.getContentType());
            sb.append("\nfile.getOriginalFilename()").append(" = ").append(file.getOriginalFilename());
            // 文件大小单位默认为字节
            sb.append("\nfile.getSize()").append(" = ").append(file.getSize()).append("B");
            sb.append("\n");
        }
        log.info("postFourController - return -> {}", sb);
        return sb.toString();
    }
}
