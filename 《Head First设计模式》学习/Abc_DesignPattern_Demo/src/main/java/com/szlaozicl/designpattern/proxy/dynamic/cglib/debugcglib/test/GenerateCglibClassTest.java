package com.szlaozicl.designpattern.proxy.dynamic.cglib.debugcglib.test;

import com.szlaozicl.designpattern.author.JustryDeng;
import com.szlaozicl.designpattern.proxy.dynamic.cglib.SayHey;
import com.szlaozicl.designpattern.proxy.dynamic.cglib.MethodInterceptor4Proxy;
import org.springframework.cglib.core.DebuggingClassWriter;

/**
 * 获取cglib动态代理生成的相关代理类及FastClass类
 *
 * @author {@link JustryDeng}
 * @date 2020/5/24 12:04:30
 */
public class GenerateCglibClassTest {

    public static void main(String[] args) {
        // 设置需要保存生成的动态代理类文件
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "C:\\Users\\JustryDeng\\Desktop\\temp");
        // 使用一下代理, 使生成代理类, 进而被保存到指定目录下
        SayHey proxy = MethodInterceptor4Proxy.getProxyInstance(SayHey.class);
        proxy.hey();
    }
}
