package com.szlaozicl.designpattern.proxy.dynamic.jdk;

import com.szlaozicl.designpattern.author.JustryDeng;
import com.szlaozicl.designpattern.proxy.dynamic.jdk.impl.InvocationHandler4Proxy;
import com.szlaozicl.designpattern.proxy.dynamic.jdk.impl.SayHiImpl;
import org.springframework.lang.NonNull;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * JDK动态代理测试
 *
 * @author {@link JustryDeng}
 * @date 2020/5/24 9:49:22
 */
public class Test {

    public static void main(String[] args) {
        /// 观察 生成的代理类
        // 方式一: 保存生成的动态代理类文件
        // System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        // 方式二: 直接使用ProxyGenerator生成被代理对象(类)的JDK代理类
        generateProxyClass(SayHiImpl.class, "C:\\Users\\JustryDeng\\Desktop\\temp");

        /// 测试
        // 创建一个被代理对象
        SayHi target = new SayHiImpl();
        // (通过被代理对象)获得代理对象
        SayHi proxy = InvocationHandler4Proxy.getProxyInstance(target);
        // 调用代理对象的hi方法
        proxy.hi();
    }

    /**
     * 生成(JDK动态代理)类
     *
     * 提示: System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
     *
     * @param targetClazz
     *         被代理对象的class
     * @param dir
     *         生成的代理类的保存路径
     */
    private static <T> void generateProxyClass(@NonNull Class<T> targetClazz, @NonNull String dir) {
        dir = dir.endsWith(File.separator) ? dir : dir + File.separator;
        try (FileOutputStream out = new FileOutputStream(dir + "$Proxy0.class")) {
            byte[] classFile = ProxyGenerator.generateProxyClass("$Proxy0", targetClazz.getInterfaces());
            out.write(classFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
