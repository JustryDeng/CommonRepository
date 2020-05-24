package com.szlaozicl.designpattern.proxy.dynamic.cglib.debugcglib.editor;

import com.szlaozicl.designpattern.author.JustryDeng;
import com.szlaozicl.designpattern.proxy.dynamic.cglib.SayHey;
import com.szlaozicl.designpattern.proxy.dynamic.cglib.debugcglib.SayHey$$EnhancerByCGLIB$$5f978b9e;
import com.szlaozicl.designpattern.proxy.dynamic.cglib.debugcglib.SayHey$$EnhancerByCGLIB$$5f978b9e$$FastClassByCGLIB$$790599f2;
import com.szlaozicl.designpattern.proxy.dynamic.cglib.debugcglib.SayHey$$FastClassByCGLIB$$6fa9a659;
import org.springframework.cglib.core.Signature;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * 暴力修改MethodProxy中的属性、字段
 *
 * @author {@link JustryDeng}
 * @date 2020/5/24 15:13:11
 */
public class MethodProxyEditor {

    public static void edit(MethodProxy methodProxy) throws Exception {
        Class<MethodProxy> methodProxyClass = MethodProxy.class;
        Field createInfoField = methodProxyClass.getDeclaredField("createInfo");
        Field fastClassInfoField = methodProxyClass.getDeclaredField("fastClassInfo");
        createInfoField.setAccessible(true);
        fastClassInfoField.setAccessible(true);

        /// 改变org.springframework.cglib.proxy.MethodProxy.createInfo
        Class<?> createInfoClass = ClassLoader.getSystemClassLoader().loadClass(
                "org.springframework.cglib.proxy.MethodProxy$CreateInfo");
        Constructor<?> createInfoConstructor = createInfoClass.getDeclaredConstructor(Class.class, Class.class);
        createInfoConstructor.setAccessible(true);
        Object createInfo = createInfoConstructor.newInstance(SayHey.class, SayHey$$EnhancerByCGLIB$$5f978b9e.class);
        // do
        createInfoField.set(methodProxy, createInfo);

        /// 改变org.springframework.cglib.proxy.MethodProxy.fastClassInfo
        Class<?> fastClassInfoClass = ClassLoader.getSystemClassLoader().loadClass(
                "org.springframework.cglib.proxy.MethodProxy$FastClassInfo");
        Constructor<?> fastClassInfoConstructor = fastClassInfoClass.getDeclaredConstructor();
        fastClassInfoConstructor.setAccessible(true);
        Object fastClassInfo = fastClassInfoConstructor.newInstance();

        Field f1Field = fastClassInfoClass.getDeclaredField("f1");
        f1Field.setAccessible(true);
        SayHey$$FastClassByCGLIB$$6fa9a659 normalFastClass = new SayHey$$FastClassByCGLIB$$6fa9a659(SayHey.class);
        f1Field.set(fastClassInfo, normalFastClass);

        Field f2Field = fastClassInfoClass.getDeclaredField("f2");
        f2Field.setAccessible(true);
        SayHey$$EnhancerByCGLIB$$5f978b9e$$FastClassByCGLIB$$790599f2 cglibFastClass =
                new SayHey$$EnhancerByCGLIB$$5f978b9e$$FastClassByCGLIB$$790599f2(SayHey$$EnhancerByCGLIB$$5f978b9e.class);
        f2Field.set(fastClassInfo, cglibFastClass);

        Field i1Field = fastClassInfoClass.getDeclaredField("i1");
        i1Field.setAccessible(true);
        i1Field.set(fastClassInfo, normalFastClass.getIndex(new Signature("hey", "()V")));

        Field i2Field = fastClassInfoClass.getDeclaredField("i2");
        i2Field.setAccessible(true);
        i2Field.set(fastClassInfo, cglibFastClass.getIndex(new Signature("CGLIB$hey$0", "()V")));
        // do
        fastClassInfoField.set(methodProxy, fastClassInfo);
    }

}
