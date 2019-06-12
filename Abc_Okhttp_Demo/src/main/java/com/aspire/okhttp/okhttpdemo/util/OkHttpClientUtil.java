package com.aspire.okhttp.okhttpdemo.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;


/**
 * OkHttpClient工具类
 *
 * 注: OkHttp官方文档并不建议我们创建多个OkHttpClient，建议使用同一个实例; 所以此工具类采取单例模式
 *
 * 注: 此工具类主要功能是 封装了 获取HTTP、HTTPS(不需验证CA)、HTTPS(需验证CA)请求客户端的实例,
 *     对此客户端的属性设置的比较少，只设置了 【连接超时时间】、【读超时时间】、【写超时时间】、【连接池】，
 *      可根据自己的实际项目情况， 灵活设置这些属性的属性值，还可以添加其它的属性设置
 *
 * @author JustryDeng
 * @date 2019/6/11 20:50
 */
@Slf4j
public class OkHttpClientUtil {

    /**
     * HTTP实例
     * <p>
     * 确保本条指令不会因编译器的优化而省略，且要求每次直接读值
     */
    private static volatile OkHttpClient httpClient;


    /**
     * HTTPS实例(不需要校验CA)
     * <p>
     * 确保本条指令不会因编译器的优化而省略，且要求每次直接读值
     */
    private static volatile OkHttpClient httpsClient;

    /** ssl socket工厂（不需要校验CA） */
    private static SSLSocketFactory sslSocketFactory = null;

    private static X509TrustManager trustManager = null;


    /**
     * HTTPS实例(需要校验CA)
     * <p>
     * 确保本条指令不会因编译器的优化而省略，且要求每次直接读值
     */
    private static volatile OkHttpClient verifyCaHttpsClient;

    /** ssl socket工厂（需要校验CA） */
    private static SSLSocketFactory sslSocketFactoryVerifyCa = null;

    private static X509TrustManager trustManagerVerifyCa = null;


    /**
     * 超时时间等参数设置
     */
    private static final int CONNECT_TIMEOUT = 60;

    private static final int READ_TIMEOUT = 100;

    private static final int WRITE_TIMEOUT = 60;

    /**
     * 获取http客户端
     *
     * @return OkHttpClient客户端实例
     * @date 2019/6/11 20:36
     */
    public static OkHttpClient getHttpClient() {
        if (httpClient == null) {
            synchronized (OkHttpClientUtil.class) {
                if (httpClient == null) {
                    initHttpClient();
                }
            }
        }
        return httpClient;
    }

    /**
     * 获取https客户端(不需要校验证书)
     *
     * @return OkHttpClient客户端实例
     * @date 2019/6/11 20:36
     */
    public static OkHttpClient getHttpsClient() throws Exception {
        if (httpsClient == null) {
            synchronized (OkHttpClientUtil.class) {
                if (httpsClient == null) {
                    initHttpsClient(false, null, null);
                }
            }
        }
        return httpsClient;
    }

    /**
     * 获取https客户端(需要校验证书)
     *
     * @param caInputStream
     *         CA证书(此证书应由要访问的服务端提供)
     * @param cAalias
     *         别名
     *         注意:别名应该是唯一的， 别名不要和其他的别名一样，否者会覆盖之前的相同别名的证书信息。别名即key-value中的key。
     * @return OkHttpClient客户端实例
     * @date 2019/6/11 20:36
     */
    public static OkHttpClient getHttpsClient(InputStream caInputStream, String cAalias) throws Exception {
        if (verifyCaHttpsClient == null) {
            synchronized (OkHttpClientUtil.class) {
                if (verifyCaHttpsClient == null) {
                    // 如果需要校验证书，那么【证书】和【别名】不能为空
                    if (caInputStream == null || cAalias == null) {
                        throw new RuntimeException("[ca] and [alias] must not be null!");
                    }
                    initHttpsClient(true, caInputStream, cAalias);
                }
            }
        }
        return verifyCaHttpsClient;
    }

    /**
     * 初始化HTTP客户端
     *
     * @date 2019/6/11 16:12
     */
    private static void initHttpClient() {
        // 进行数据初始化
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                // 设置读取超时时间
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                // 设置写的超时时间
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                // 设置连接超时时间
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                // 使用连接池
                .connectionPool(pool());
        httpClient = builder.build();
    }

    /**
     * 初始化HTTPS客户端
     *
     * @param needVerifyCa
     *         是否需要检验CA证书(即:是否需要检验服务器的身份)
     * @param caInputStream
     *         CA证书。(若不需要检验证书，那么此处传null即可)
     * @param cAalias
     *         别名。(若不需要检验证书，那么此处传null即可)
     *         注意:别名应该是唯一的， 别名不要和其他的别名一样，否者会覆盖之前的相同别名的证书信息。别名即key-value中的key。
     * @date 2019/6/11 16:12
     */
    private static void initHttpsClient(boolean needVerifyCa, InputStream caInputStream, String cAalias)
            throws CertificateException, NoSuchAlgorithmException, KeyStoreException,
                   KeyManagementException, IOException {
        // 先调用helper方法，初始化
        httpsHelper(needVerifyCa, caInputStream, cAalias);
        // 进行数据初始化
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                // 设置读取超时时间
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                // 设置写的超时时间
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                // 设置连接超时时间
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                // 使用连接池
                .connectionPool(pool());
        // 需要校验证书
        if (needVerifyCa) {
            builder.sslSocketFactory(sslSocketFactoryVerifyCa, trustManagerVerifyCa);
            /*
             * 情况一: 证书中预设有 hostname
             *    证书中会预设一些hostname(即: 预设一些ip、域名)， 只有当请求url中的ip/域名，是包含在预设的那些hostname中的时，
             * 才会认证通过，否者会报【The certificate hostname does not match】之类的问题。如果证书中预设有一些hostname
             * 的话，我们这里就不需要在再设置hostname校验规则了，会走默认的hostname校验。
             *
             *
             * 情况二: 证书中没有预设 hostname
             *    如果连证书中都没有预设hostname的话，即:服务端认为不需要验证hostname, 这时，我们作为客户端，需要重写hostname校验规则，
             * 不让其走默认的校验规则(因为默认会对hostname进行校验)。即:不论是什么，直接返回true即可，表示所有的hostname都成功，
             * 在此处代码里，只需要builder.hostnameVerifier((String hostname, SSLSession session) -> true)即可达到设
             * 置不作hostname校验的效果。
             *    当然，如果你非要校验，你也可以自己在客户端通过builder.hostnameVerifier(HostnameVerifier hostnameVerifier)
             * 设置hostname校验规则。
             */
            verifyCaHttpsClient = builder.build();
            return;
        }
        // 不需要校验证书
        builder.sslSocketFactory(sslSocketFactory, trustManager);
        // 不校验 url中的hostname(直接返回true，表示不校验hostname)
        // 注:hostname 指 ip/域名
        builder.hostnameVerifier((String hostname, SSLSession session) -> true);
        httpsClient = builder.build();
    }

    /**
     * 使用连接池，复用HTTP/HTTPS连接
     * <p>
     * Sets the connection pool used to recycle HTTP and HTTPS connections.
     *
     * @date 2019/6/11 19:35
     */
    private static ConnectionPool pool() {
        return new ConnectionPool(100, 5, TimeUnit.MINUTES);
    }

    /**
     * HTTPS辅助方法, 为HTTPS请求 创建SSLSocketFactory实例、TrustManager实例
     *
     * @param needVerifyCa
     *         是否需要检验CA证书(即:是否需要检验服务器的身份)
     * @param caInputStream
     *         CA证书。(若不需要检验证书，那么此处传null即可)
     * @param cAalias
     *         别名。(若不需要检验证书，那么此处传null即可)
     *         注意:别名应该是唯一的， 别名不要和其他的别名一样，否者会覆盖之前的相同别名的证书信息。别名即key-value中的key。
     * @throws NoSuchAlgorithmException
     *         异常信息
     * @throws CertificateException
     *         异常信息
     * @throws KeyStoreException
     *         异常信息
     * @throws IOException
     *         异常信息
     * @throws KeyManagementException
     *         异常信息
     * @date 2019/6/11 19:52
     */
    private static void httpsHelper(boolean needVerifyCa, InputStream caInputStream, String cAalias)
            throws CertificateException, NoSuchAlgorithmException, KeyStoreException,
                   IOException, KeyManagementException {
        // https请求，需要校验证书
        if (needVerifyCa) {
            KeyStore keyStore = getKeyStore(caInputStream, cAalias);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            trustManagerVerifyCa = (X509TrustManager) trustManagers[0];
            // 这里传TLS或SSL其实都可以的
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManagerVerifyCa}, new SecureRandom());
            sslSocketFactoryVerifyCa = sslContext.getSocketFactory();
            return;
        }
        // https请求，不作证书校验
        trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) {
                // 不验证
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
        sslSocketFactory = sslContext.getSocketFactory();
    }

    /**
     * 获取(密钥及证书)仓库
     * 注:该仓库用于存放 密钥以及证书
     *
     * @param caInputStream
     *         CA证书(此证书应由要访问的服务端提供)
     * @param cAalias
     *         别名
     *         注意:别名应该是唯一的， 别名不要和其他的别名一样，否者会覆盖之前的相同别名的证书信息。别名即key-value中的key。
     * @return 密钥、证书 仓库
     * @throws KeyStoreException 异常信息
     * @throws CertificateException 异常信息
     * @throws IOException 异常信息
     * @throws NoSuchAlgorithmException 异常信息
     * @date 2019/6/11 18:48
     */
    private static KeyStore getKeyStore(InputStream caInputStream, String cAalias)
            throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        // 证书工厂
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        // 秘钥仓库
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null);
        keyStore.setCertificateEntry(cAalias, certificateFactory.generateCertificate(caInputStream));
        return keyStore;
    }
}