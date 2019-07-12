package com.aspire.ssm.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA公钥/私钥 工具类
 *
 * 注:此工具类摘录自某个网友，具体的我忘记了，这是之前去网上找的，当时忘记把链接记下来了，深感抱歉。
 * @author JustryDeng
 * @date 2019/7/12 13:19
 */
@SuppressWarnings("unused")
public class KeypairUtil {

    private static final String KEY_ALGORITHM = "RSA";

    private static final String PUBLIC_KEY = "RSAPublicKey";

    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 获取公钥
     *
     * @param keyMap 公钥/私钥信息map
     * @return 公钥字符串
     */
    public static String getPublicKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return encryptBase64(key.getEncoded());
    }

    /**
     * 获取私钥
     *
     * @param keyMap 公钥/私钥信息map
     * @return 私钥字符串
     */
    public static String getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return encryptBase64(key.getEncoded());
    }

    /**
     * 将byte[]型的密钥转换为String类
     *
     * @param key 公钥/私钥字节数组
     * @return 公钥/私钥字符串
     */
    private static String encryptBase64(byte[] key) {
        return new BASE64Encoder().encodeBuffer(key);
    }

    /**
     * 将String类型的密钥转换为byte[]
     *
     * @param key 公钥/私钥字符串
     * @return 公钥/私钥字节数组
     */
    public static byte[] decryptBase64(String key) throws Exception {
        return new BASE64Decoder().decodeBuffer(key);
    }

    /**
     * RSA是目前最有影响力的公钥加密算法，该算法基于一个十分简单的数论事实：将两个大素数相乘十分容易，
     * 但那时想要对其乘积进行因式分解却极其困难，因此可以将乘积公开作为加密密钥，即公钥，而两个大素数
     * 组合成私钥。公钥是可发布的供任何人使用，私钥则为自己所有，供解密之用。
     * <p>
     * 解密者拥有私钥，并且将由私钥计算生成的公钥发布给加密者。加密都使用公钥进行加密，并将密文发送到
     * 解密者，解密者用私钥解密将密文解码为明文。
     * <p>
     * 以甲要把信息发给乙为例，首先确定角色：甲为加密者，乙为解密者。首先由乙随机确定一个KEY，称之为
     * 密匙，将这个KEY始终保存在机器B中而不发出来；然后，由这个 KEY计算出另一个KEY，称之为公匙。这
     * 个公钥的特性是几乎不可能通过它自身计算出生成它的私钥。接下来通过网络把这个公钥传给甲，甲收到公
     * 钥后，利用公钥对信息加密，并把密文通过网络发送到乙，最后乙利用已知的私钥，就对密文进行解码了。
     * 以上就是RSA算法的工作流程。
     * <p>
     * 算法实现过程为：
     * 1. 随意选择两个大的质数p和q，p不等于q，计算N=pq。
     * 2. 根据欧拉函数，不大于N且与N互质的整数個数為(p-1)(q-1)。
     * 3. 选择一个整数e与(p-1)(q-1)互质，并且e小于(p-1)(q-1)。
     * 4. 用以下这个公式计算d：d× e ≡ 1 (mod (p-1)(q-1))。
     * 5. 将p和q的记录销毁。
     * <p>
     * 以上内容中，(N,e)是公钥，(N,d)是私钥。
     * <p>
     * RSA算法的应用。
     * 1. RSA的公钥和私钥是由KeyPairGenerator生成的，获取KeyPairGenerator的实例后还需要设置其密钥位数。
     *    设置密钥位数越高，加密过程越安全，一般使用1024位。     *
     *    KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
     *    keyPairGen.initialize(1024);
     * 2.公钥和私钥可以通过KeyPairGenerator执行generateKeyPair()后生成密钥对KeyPair，
     *   通过KeyPair.getPublic()和KeyPair.getPrivate()来获取。
     *   动态生成密钥对，这是当前最耗时的操作，一般要2s以上。
     *   KeyPair keyPair = keyPairGen.generateKeyPair();
     *   公钥
     *   PublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
     *   私钥
     *   PrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
     * <p>
     * byte[] publicKeyData = publicKey.getEncoded();
     * byte[] privateKeyData = publicKey.getEncoded();
     * 公钥和私钥都有它们自己独特的比特编码，可以通过getEncoded()方法获取，返回类型为byte[]。
     * 通过byte[]可以再度将公钥或私钥还原出来。
     */
    public static Map<String, Object> initKey() throws Exception {
        // 获取密钥对生成器
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        // 设置密钥位数
        keyPairGen.initialize(1024);
        // 生成密钥对
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<>(4);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    ///
//    public static void main(String[] args) throws Exception {
//        Map<String, Object> map = initKey();
//        System.out.println(getPublicKey(map) + ".");
//        System.out.println(getPrivateKey(map) + ".");
//    }
}