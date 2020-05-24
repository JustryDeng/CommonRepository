package com.szlaozicl.designpattern.proxy.dynamic.cglib.debugcglib.test;

import com.szlaozicl.designpattern.author.JustryDeng;
import com.szlaozicl.designpattern.proxy.dynamic.cglib.debugcglib.SayHey$$EnhancerByCGLIB$$5f978b9e;
import com.szlaozicl.designpattern.proxy.dynamic.cglib.debugcglib.editor.MethodProxyEditor;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * debug学习CGLIB原理
 *
 * @author {@link JustryDeng}
 * @date 2020/5/24 16:18:42
 */
public class CglibDebugTest {

    public static void main(String[] args) {
        SayHey$$EnhancerByCGLIB$$5f978b9e  proxy = new SayHey$$EnhancerByCGLIB$$5f978b9e();
        proxy.setCallback(0, new MethodInterceptor() {
            @Override
            public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                /*
                 * 使用编辑器，暴力修改methodProxy中的属性值, 使用我们反编译出来的
                 * SayHey$$EnhancerByCGLIB$$5f978b9e、SayHey$$EnhancerByCGLIB$$5f978b9e$$FastClassByCGLIB$$790599f2、SayHey$$FastClassByCGLIB$$6fa9a659
                 *
                 * 然后只需要在这三个类中的对应方法里面打断点, 即可观察学习cglib代理的实现过程了
                 *
                 */
                MethodProxyEditor.edit(methodProxy);
                System.err.println("前戏");
                Object object = methodProxy.invokeSuper(proxy, args);
                System.err.println("后戏");
                return object;
            }
        });
        proxy.hey();
    }
}
