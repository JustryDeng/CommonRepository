package com.szlzcl.jwttoken.controller;

import com.alibaba.fastjson.JSON;
import com.nimbusds.jose.JOSEException;
import com.szlzcl.jwttoken.model.PayloadDTO;
import com.szlzcl.jwttoken.util.JwtSignatureVerifyException;
import com.szlzcl.jwttoken.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * controller
 *
 * @author JustryDeng
 * @date 2019/7/21 11:03
 */
@Slf4j
@RestController
public class DemoController {

    @Value("${my.jwt.signature.algorithm.secret}")
    private String signatureAlgorithmSecret;

    @Value("${my.jwt.expiration.time}")
    private Long tokenExpirationTime;

    /**
     * 用户登录, 并返回token信息
     *
     * 注:真正使用时，往往除了token，还会返回一些其他的相关信息。
     * 注:真正使用时，token往往放在响应头里面进行返回。
     * 注:真正使用时，用户登录不应用get方法，而是用post方法。
     *
     * @param name 用户名
     * @param password mima
     *
     * @return  返回jwt
     * @date 2019/7/21 11:05
     */
    @GetMapping("/login")
    @SuppressWarnings("all")
    public String login(@RequestParam("name") String name,
                        @RequestParam("password")  String password){
        // 模拟 用户名密码 均正确
        if("邓沙利文".equals(name) && "123xyz".equals(password)) {
            /*
             * 模拟获得有效负载信息
             * 注:根据自己业务的不同，往往这一步会有非常大的不同
             */
            PayloadDTO payloadDTO  = getPayload(name);
            // 生成token
            String payloadJsonString = JSON.toJSONString(payloadDTO);
            String token;
            try {
                token = JwtUtil.generateToken(payloadJsonString, signatureAlgorithmSecret);
            } catch (JOSEException e) {
                log.info("生成token失败！", e);
                return "生成token失败";
            }
            return token;
        }
        return "用户名密码有误！";
    }

    /**
     * 验证请求中的token是否被篡改、是否过期(是否有效)。
     * 如果有效那么返回响应的业务信息。
     *
     * 注:真正使用时，token的验证往往在Filter或AOP中进行。
     * 注:真正使用时，一般将token放在请求头中，以Authorization作为key，以Bearer <token>作为值。
     *
     * @param token json web token（JWT）
     *
     * @return  返回相应信息
     * @date 2019/7/21 11:05
     */
    @GetMapping("/test")
    public String logis(@RequestHeader("Authorization") String token){
        String payloadJsonString;
        // 验证token是否被篡改
        try {
            payloadJsonString = JwtUtil.verifySignature(token, signatureAlgorithmSecret);
        } catch (JwtSignatureVerifyException e) {
            log.info("token签名验证失败， token已经被篡改！", e);
            return "token签名验证失败， token已经被篡改！";
        } catch (JOSEException | ParseException e) {
            log.info("验证token签名时，系统异常！", e);
            return "验证token签名时，系统异常！";
        }

        /// 验证 有效负载中的其他信息 (如:过期时间、权限信息 等等)
        PayloadDTO payloadDTO =JSON.parseObject(payloadJsonString, PayloadDTO.class);
        log.info("token中存放的用户信息是 -> {}", payloadDTO);
        // token生效时间
        long nbf = payloadDTO.getNbf();
        log.info("token的生效时间是 -> {}", nbf);
        // token有效时长
        long exp = payloadDTO.getExp();
        log.info("token的生效时长是 -> {}", exp);
        // 当前时间
        long nowTime = System.currentTimeMillis();
        log.info("当前时间是 -> {}", nowTime);

        boolean isAuthorized = (nowTime - nbf) >= 0 && exp >= (nowTime - nbf);
        log.info("token -> 【{}】是否有效？ {}", token, isAuthorized);

        if (isAuthorized) {
            return "token认证通过！";
        }
        return "token已过期，请重新登录";

    }

    /**
     * 模拟生成 有效负载信息
     *
     * 注:根据自己业务情况的不同，可能会往有效负载中放入不同的信息；
     *    此步骤的逻辑也可能会非常复杂。
     *
     * @date 2019/7/21 16:00
     */
    private PayloadDTO getPayload(String name) {
        return PayloadDTO.builder()
                // 放置过期时长
                .exp(tokenExpirationTime)
                // 放置生效时间
                .nbf(System.currentTimeMillis())
                // 放置用户信息
                .name(name)
                .birthday("1994-02-05")
                .isAdmin(true)
                .build();
    }
}
