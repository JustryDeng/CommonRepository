package com.aspire.ssm;

import com.aspire.ssm.model.User;
import com.aspire.ssm.service.AbcService;
import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricConfig;
import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricStringEncryptor;
import com.ulisesbocchio.jasyptspringboot.util.AsymmetricCryptography;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SsmApplicationTests {

    @Autowired
    private AbcService abcService;

    /**
     * 测试向数据库表增一条数据
     *
     * @date 2019/7/13 5:51
     */
    @Test
    public void contextLoads() {
        User u = User.builder()
                .age(25)
                .gender("男")
                .name("JustryDeng")
                .motto("我是一只小小小小鸟~嗷！嗷！")
                .build();
        int result = abcService.insertDemo(u);
        if (log.isInfoEnabled()) {
            log.info("expected result is -> 1, actual result is -> {}", result);
        }
        Assert.assertEquals(1, result);
    }

    /**
     * 对称加密， 根据密钥，【明文生成密文】示例
     *
     * @date 2019/7/13 5:04
     */
    @Test
    public void symmetricTest() {
        // 基础加密器
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        // 设置对称加密的 加解密 密钥
        textEncryptor.setPassword("hello~JustryDeng!");

        String info = "dengshuai";
        log.info("加密前" + info);
        // 加密
        String result = textEncryptor.encrypt(info);
        log.info("加密后" + result);
        // 解密
        String originInfo = textEncryptor.decrypt(result);
        log.info("解密后" + originInfo);
    }

    /**
     * 非对称加密， 利用公钥，将【明文生成密文】示例
     *
     * @date 2019/7/13 5:04
     */
    @Test
    public void asymmetricTest() {
        SimpleAsymmetricConfig config = new SimpleAsymmetricConfig();
        // 设置密钥类型
        config.setKeyFormat(AsymmetricCryptography.KeyFormat.PEM);
        // 设置用来加密的公钥(注:生成的公钥/私钥可能会有换行，保不保留换行都一样)
        config.setPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCMXf4CXQ/xbZaNSkGfO8NhzbxREWOAhCw35J/Nmz0uiyFV5/iXTN7GZA4Xq1kcAWnIoDuUHt93dvgNRpMP98kwJf4uSIMABueGlxpjoc5kwhTVxlBKshcYliMmqFDUGQX4cNJq+r/rAd1SKYE/Fj20XYxVP4zPCTw5+OSmRa1bFQIDAQAB");
        StringEncryptor encryptor = new SimpleAsymmetricStringEncryptor(config);

        String info = "dengshuai";
        log.info("加密前" + info);
        // 加密
        String result = encryptor.encrypt(info);
        log.info("加密后" + result);
        // 解密
        config.setPrivateKey("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIxd/gJdD/Ftlo1KQZ87w2HNvFERY4CELDfkn82bPS6LIVXn+JdM3sZkDherWRwBacigO5Qe33d2+A1Gkw/3yTAl/i5IgwAG54aXGmOhzmTCFNXGUEqyFxiWIyaoUNQZBfhw0mr6v+sB3VIpgT8WPbRdjFU/jM8JPDn45KZFrVsVAgMBAAECgYAewiX8LJpmxCXediwlEXqB/wxKE25jZhMueEnQSzk/7rryUS+3L+ANRzWTWDfhnCmrDfmgPpenXQmEFzf4osqSDh1TA+wlBXm2iMjVxz01uI0+CES+4I7AjmsVza4rmO8EUyadjoiBFHHDD3+VjWM+o7Pg33L6Hr5dPjEcXU5GrQJBAO8baqlc+1Qvbjb7CHx0U4tx+I204ehFYpQT5vqk89atxqLjMn8QgTA5cFZwQ5V7+uBR+6ZnTlDh3DDrVxZYCOcCQQCWSLoUkN5ljgsaoUMmlQA6vKFVlA/5Oaul574EDpLtiPZHva/u0pdP2fmnPQebe0sX5ThmeD3Aq0v/p/oX/NCjAkEAs+wah9UK3h9OvRqLGTNjhmO9l8xLzb8gXbLYNTUYsytSdFGoNssRm1steC3D/WEst82ZIm9MFDrQuRLuFkcqcwJAXZRx0qaW5bP6dB2gu+CiYPDeoXRuMenYWZmhd9M/aIwVl3ylldgqgn2f+KSHHSk8DGgeo6gSA+xmiY6mq9MwcwJAE03+ZGbTDJoqmSzOlg/P4ScIZH6dDyeycQB2aHKNblRFHEQEzw6+/bEZh5TxSbV4oBWUQGXRCMlkhRAA/A/oHw==");
        String originInfo = encryptor.decrypt(result);
        log.info("解密后" + originInfo);
    }
}
