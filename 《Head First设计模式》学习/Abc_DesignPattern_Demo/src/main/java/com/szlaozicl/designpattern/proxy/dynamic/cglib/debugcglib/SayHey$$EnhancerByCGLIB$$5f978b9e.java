package com.szlaozicl.designpattern.proxy.dynamic.cglib.debugcglib;
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import com.szlaozicl.designpattern.proxy.dynamic.cglib.SayHey;
import lombok.SneakyThrows;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.cglib.core.Signature;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Factory;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class SayHey$$EnhancerByCGLIB$$5f978b9e extends SayHey implements Factory {
    private boolean CGLIB$BOUND;
    public static Object CGLIB$FACTORY_DATA;
    private static ThreadLocal CGLIB$THREAD_CALLBACKS;
    private static Callback[] CGLIB$STATIC_CALLBACKS;
    private MethodInterceptor CGLIB$CALLBACK_0;
    private static Object CGLIB$CALLBACK_FILTER;
    private static  Method CGLIB$hey$0$Method;
    private static MethodProxy CGLIB$hey$0$Proxy;
    private static Object[] CGLIB$emptyArgs;
    private static Method CGLIB$equals$1$Method;
    private static MethodProxy CGLIB$equals$1$Proxy;
    private static Method CGLIB$toString$2$Method;
    private static MethodProxy CGLIB$toString$2$Proxy;
    private static Method CGLIB$hashCode$3$Method;
    private static MethodProxy CGLIB$hashCode$3$Proxy;
    private static Method CGLIB$clone$4$Method;
    private static MethodProxy CGLIB$clone$4$Proxy;

    static {
        CGLIB$STATICHOOK1();
    }

    public static void CGLIB$STATICHOOK1() {
        CGLIB$THREAD_CALLBACKS = new ThreadLocal();
        CGLIB$emptyArgs = new Object[0];
        Class var0 = null;
        try {
            var0 = Class.forName("com.szlaozicl.designpattern.proxy.dynamic.cglib.debugcglib"
                    + ".SayHey$$EnhancerByCGLIB$$5f978b9e");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Class var1 = null;
        Method[] var10000 = new Method[0];
        try {
            var10000 = ReflectUtils.findMethods(new String[]{"equals", "(Ljava/lang/Object;)Z", "toString", "()Ljava/lang/String;", "hashCode", "()I", "clone", "()Ljava/lang/Object;"}, (var1 = Class.forName("java.lang.Object")).getDeclaredMethods());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        CGLIB$equals$1$Method = var10000[0];
        CGLIB$equals$1$Proxy = MethodProxy.create(var1, var0, "(Ljava/lang/Object;)Z", "equals", "CGLIB$equals$1");
        CGLIB$toString$2$Method = var10000[1];
        CGLIB$toString$2$Proxy = MethodProxy.create(var1, var0, "()Ljava/lang/String;", "toString", "CGLIB$toString$2");
        CGLIB$hashCode$3$Method = var10000[2];
        CGLIB$hashCode$3$Proxy = MethodProxy.create(var1, var0, "()I", "hashCode", "CGLIB$hashCode$3");
        CGLIB$clone$4$Method = var10000[3];
        CGLIB$clone$4$Proxy = MethodProxy.create(var1, var0, "()Ljava/lang/Object;", "clone", "CGLIB$clone$4");
        try {
            CGLIB$hey$0$Method = ReflectUtils.findMethods(new String[]{"hey", "()V"}, (var1 = Class.forName("com.szlaozicl.designpattern.proxy.dynamic.cglib.SayHey")).getDeclaredMethods())[0];
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        CGLIB$hey$0$Proxy = MethodProxy.create(var1, var0, "()V", "hey", "CGLIB$hey$0");
    }

    public final void CGLIB$hey$0() {
        super.hey();
    }

    public final void hey() {
        MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
        if (var10000 == null) {
            CGLIB$BIND_CALLBACKS(this);
            var10000 = this.CGLIB$CALLBACK_0;
        }

        if (var10000 != null) {
            try {
                var10000.intercept(this, CGLIB$hey$0$Method, CGLIB$emptyArgs, CGLIB$hey$0$Proxy);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else {
            super.hey();
        }
    }

    public final boolean CGLIB$equals$1(Object var1) {
        return super.equals(var1);
    }

    @SneakyThrows
    public final boolean equals(Object var1) {
        MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
        if (var10000 == null) {
            CGLIB$BIND_CALLBACKS(this);
            var10000 = this.CGLIB$CALLBACK_0;
        }

        if (var10000 != null) {
            Object var2 = var10000.intercept(this, CGLIB$equals$1$Method, new Object[]{var1}, CGLIB$equals$1$Proxy);
            return var2 == null ? false : (Boolean)var2;
        } else {
            return super.equals(var1);
        }
    }

    public final String CGLIB$toString$2() {
        return super.toString();
    }

    public final String toString() {
        MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
        if (var10000 == null) {
            CGLIB$BIND_CALLBACKS(this);
            var10000 = this.CGLIB$CALLBACK_0;
        }

        try {
            return var10000 != null ? (String)var10000.intercept(this, CGLIB$toString$2$Method, CGLIB$emptyArgs, CGLIB$toString$2$Proxy) : super.toString();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    public final int CGLIB$hashCode$3() {
        return super.hashCode();
    }

    public final int hashCode() {
        MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
        if (var10000 == null) {
            CGLIB$BIND_CALLBACKS(this);
            var10000 = this.CGLIB$CALLBACK_0;
        }

        if (var10000 != null) {
            Object var1 = null;
            try {
                var1 = var10000.intercept(this, CGLIB$hashCode$3$Method, CGLIB$emptyArgs, CGLIB$hashCode$3$Proxy);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return var1 == null ? 0 : ((Number)var1).intValue();
        } else {
            return super.hashCode();
        }
    }

    public final Object CGLIB$clone$4() throws CloneNotSupportedException {
        return super.clone();
    }

    @SneakyThrows
    public final Object clone() throws CloneNotSupportedException {
        MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
        if (var10000 == null) {
            CGLIB$BIND_CALLBACKS(this);
            var10000 = this.CGLIB$CALLBACK_0;
        }

        return var10000 != null ? var10000.intercept(this, CGLIB$clone$4$Method, CGLIB$emptyArgs, CGLIB$clone$4$Proxy) : super.clone();
    }

    public static MethodProxy CGLIB$findMethodProxy(Signature var0) {
        String var10000 = var0.toString();
        switch(var10000.hashCode()) {
            case -1220615463:
                if (var10000.equals("hey()V")) {
                    return CGLIB$hey$0$Proxy;
                }
                break;
            case -508378822:
                if (var10000.equals("clone()Ljava/lang/Object;")) {
                    return CGLIB$clone$4$Proxy;
                }
                break;
            case 1826985398:
                if (var10000.equals("equals(Ljava/lang/Object;)Z")) {
                    return CGLIB$equals$1$Proxy;
                }
                break;
            case 1913648695:
                if (var10000.equals("toString()Ljava/lang/String;")) {
                    return CGLIB$toString$2$Proxy;
                }
                break;
            case 1984935277:
                if (var10000.equals("hashCode()I")) {
                    return CGLIB$hashCode$3$Proxy;
                }
        }

        return null;
    }

    public SayHey$$EnhancerByCGLIB$$5f978b9e() {
        CGLIB$BIND_CALLBACKS(this);
    }

    public static void CGLIB$SET_THREAD_CALLBACKS(Callback[] var0) {
        CGLIB$THREAD_CALLBACKS.set(var0);
    }

    public static void CGLIB$SET_STATIC_CALLBACKS(Callback[] var0) {
        CGLIB$STATIC_CALLBACKS = var0;
    }

    private static final void CGLIB$BIND_CALLBACKS(Object var0) {
        SayHey$$EnhancerByCGLIB$$5f978b9e var1 = (SayHey$$EnhancerByCGLIB$$5f978b9e)var0;
        if (!var1.CGLIB$BOUND) {
            var1.CGLIB$BOUND = true;
            Object var10000 = CGLIB$THREAD_CALLBACKS.get();
            if (var10000 == null) {
                var10000 = CGLIB$STATIC_CALLBACKS;
                if (var10000 == null) {
                    return;
                }
            }

            var1.CGLIB$CALLBACK_0 = (MethodInterceptor)((Callback[])var10000)[0];
        }

    }

    public Object newInstance(Callback[] var1) {
        CGLIB$SET_THREAD_CALLBACKS(var1);
        SayHey$$EnhancerByCGLIB$$5f978b9e var10000 = new SayHey$$EnhancerByCGLIB$$5f978b9e();
        CGLIB$SET_THREAD_CALLBACKS((Callback[])null);
        return var10000;
    }

    public Object newInstance(Callback var1) {
        CGLIB$SET_THREAD_CALLBACKS(new Callback[]{var1});
        SayHey$$EnhancerByCGLIB$$5f978b9e var10000 = new SayHey$$EnhancerByCGLIB$$5f978b9e();
        CGLIB$SET_THREAD_CALLBACKS((Callback[])null);
        return var10000;
    }

    public Object newInstance(Class[] var1, Object[] var2, Callback[] var3) {
        CGLIB$SET_THREAD_CALLBACKS(var3);
        SayHey$$EnhancerByCGLIB$$5f978b9e var10000 = new SayHey$$EnhancerByCGLIB$$5f978b9e();
        switch(var1.length) {
            case 0:
//                var10000.<init>();
                CGLIB$SET_THREAD_CALLBACKS((Callback[])null);
                return var10000;
            default:
                throw new IllegalArgumentException("Constructor not found");
        }
    }

    public Callback getCallback(int var1) {
        CGLIB$BIND_CALLBACKS(this);
        MethodInterceptor var10000;
        switch(var1) {
            case 0:
                var10000 = this.CGLIB$CALLBACK_0;
                break;
            default:
                var10000 = null;
        }

        return var10000;
    }

    public void setCallback(int var1, Callback var2) {
        switch(var1) {
            case 0:
                this.CGLIB$CALLBACK_0 = (MethodInterceptor)var2;
            default:
        }
    }

    public Callback[] getCallbacks() {
        CGLIB$BIND_CALLBACKS(this);
        return new Callback[]{this.CGLIB$CALLBACK_0};
    }

    public void setCallbacks(Callback[] var1) {
        this.CGLIB$CALLBACK_0 = (MethodInterceptor)var1[0];
    }


}
