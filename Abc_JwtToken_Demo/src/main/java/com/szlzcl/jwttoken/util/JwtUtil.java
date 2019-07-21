package com.szlzcl.jwttoken.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;

import java.text.ParseException;

/**
 * 对nimbus-jose-jwt类库提供的基本功能 再进行工具类封装
 *
 * @author JustryDeng
 * @date 2019/7/21 13:46
 */
@SuppressWarnings("unused")
public class JwtUtil {

    /**
     * 生成token -- 采用【对称加密】算法HS256验证签名
     *
     * 注:此方法生成的jwt的Header部分为默认的
     *    {
     *      "alg": "HS256",
     *      "typ": "JWT"
     *    }
     *
     * @param payloadJsonString
     *            有效负载JSON字符串
     *
     * @param secret
     *            对称加密/解密密钥
     *            注意:secret.getBytes().length 必须 >=32
     *
     * @return token
     * @throws JOSEException
     *             以下情况会抛出此异常:
     *                 1、密钥长度 secret.getBytes().length < 32时，会抛出此异常
     *                 2、JWS已签名
     *                 3、JWS无法使用指定的签名器
     * @date 2019/7/21 13:54
     */
    public static String generateToken(String payloadJsonString, String secret)
            throws JOSEException {

        // 创建Header(设置以JWSAlgorithm.HS256算法进行签名认证)
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

        // 建立Payload
        Payload payload = new Payload(payloadJsonString);

        /// Signature相关
        // 根据Header以及Payload创建JSON Web Signature (JWS)对象
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        // 使用给的对称加密密钥，创建一个加密器
        JWSSigner jwsSigner = new MACSigner(secret);
        // 将该签名器 与 JSON Web Signature (JWS)对象进行关联
        // 即: 指定JWS使用该签名器进行签名
        jwsObject.sign(jwsSigner);

        // 使用JWS生成JWT(即:使用JWS生成token)
        return jwsObject.serialize();
    }

    /**
     * 校验token是否被篡改，并返回有效负载JSON字符串 -- 采用【对称加密】算法HS256验证签名
     *
     * @param secret
     *            对称加密/解密密钥
     *            注意:secret.getBytes().length 必须 >=32
     *
     * @return  有效负载JSON字符串
     * @throws JwtSignatureVerifyException,JOSEException,ParseException 异常信息
     * @date 2019/7/21 14:08
     */
    public static String verifySignature(String token, String secret)
            throws JwtSignatureVerifyException, JOSEException, ParseException {
        // 解析token，将token转换为JWSObject对象
        JWSObject jwsObject = JWSObject.parse(token);

        // 创建一个JSON Web Signature (JWS) verifier.用于校验签名(即:校验token是否被篡改)
        JWSVerifier jwsVerifier = new MACVerifier(secret);
        // 如果校验到token被篡改(即:签名认证失败)，那么抛出异常
        if(!jwsObject.verify(jwsVerifier)) {
            throw new JwtSignatureVerifyException("Signature verification result is fail!");
        }

        // 获取有效负载
        Payload payload = jwsObject.getPayload();

        // 返回 有效负载JSON字符串
        return payload.toString();
    }


    /**
     * 生成token -- 采用【非对称加密】算法RS256验证签名
     *
     * @param payloadJsonString
     *            有效负载JSON字符串
     *
     * @param rsaKey
     *            非对称加密密钥对
     *            提示:RSAKey示例，可以这么获得
     *            RSAKeyGenerator rsaKeyGenerator = new RSAKeyGenerator(1024 * 3);
     *            RSAKey rsaKey = rsaKeyGenerator.generate();
     *
     * @return token
     * @throws JOSEException 异常信息
     * @date 2019/7/21 13:54
     */
    public static String generateTokenByAsymmetric(String payloadJsonString, RSAKey rsaKey)
            throws JOSEException {

        // Header
        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.RS256)
                                           .keyID(rsaKey.getKeyID())
                                           .build();
        // Payload
        Payload  payload= new Payload(payloadJsonString);

        /// Signature相关
        // 根据Header以及Payload创建JSON Web Signature (JWS)对象
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        // 使用给的对称加密密钥，创建一个加密器
        JWSSigner signer = new RSASSASigner(rsaKey);
        // 将该签名器 与 JSON Web Signature (JWS)对象进行关联
        // 即: 指定JWS使用该签名器进行签名
        jwsObject.sign(signer);

        // 使用JWS生成JWT(即:使用JWS生成token)
        return jwsObject.serialize();
    }

    /**
     * 校验token是否被篡改，并返回有效负载JSON字符串 -- 采用【非对称加密】算法RS256验证签名
     *
     * @param rsaKey
     *          非对称加密密钥对
     *
     * @return  有效负载JSON字符串
     * @throws JwtSignatureVerifyException,JOSEException,ParseException 异常信息
     * @date 2019/7/21 14:08
     */
    public static String verifySignatureByAsymmetric(String token, RSAKey rsaKey)
            throws JwtSignatureVerifyException, JOSEException, ParseException {
        // 根据token获得JSON Web Signature (JWS)对象
        JWSObject jwsObject = JWSObject.parse(token);

        // 获取到公钥
        RSAKey publicRsaKey = rsaKey.toPublicJWK();
        // 根据公钥 获取 Signature验证器
        JWSVerifier jwsVerifier = new RSASSAVerifier(publicRsaKey);

        // 如果校验到token被篡改(即:签名认证失败)，那么抛出异常
        if(!jwsObject.verify(jwsVerifier)) {
            throw new JwtSignatureVerifyException("Signature verification result is fail!");
        }

        // 获取有效负载
        Payload payload = jwsObject.getPayload();

        // 返回 有效负载JSON字符串
        return payload.toString();
    }

//    /**
//     *  main方法测试
//     *
//     *  --------------------------------------下面的为测试代码--------------------------------------
//     *
//     */
//    public static void main(String[] args)
//            throws JOSEException, ParseException, JwtSignatureVerifyException {
//        // 测试对称加密算法 验证签名的JWT
//        testSymmetric();
//        // 测试非对称加密算法 验证签名的JWT
//        testAsymmetric();
//    }
//
//    /**
//     * 测试对称加密算法的token生成与 签名检验
//     */
//    private static void testSymmetric()
//            throws JOSEException, ParseException, JwtSignatureVerifyException {
//        String secret = "adsgfiaughofashdofhjasodhfoasdafisd";
//        String token = JwtUtil.generateToken("{\"name\":\"张三\"}", secret);
//        System.out.println(token);
//        String payloadJsonString = JwtUtil.verifySignature(token, secret);
//        System.out.println(payloadJsonString);
//    }
//
//    /**
//     * 测试对称加密算法的token生成与 签名检验
//     */
//    private static void testAsymmetric()
//            throws JOSEException, ParseException, JwtSignatureVerifyException {
//        RSAKeyGenerator rsaKeyGenerator = new RSAKeyGenerator(1024 * 3);
//        RSAKey rsaKey = rsaKeyGenerator.generate();
//        String token =
//                JwtUtil.generateTokenByAsymmetric("{\"name\":\"张三\"}", rsaKey);
//        System.out.println(token);
//        String payloadJsonString = JwtUtil.verifySignatureByAsymmetric(token, rsaKey);
//        System.out.println(payloadJsonString);
//    }
}
