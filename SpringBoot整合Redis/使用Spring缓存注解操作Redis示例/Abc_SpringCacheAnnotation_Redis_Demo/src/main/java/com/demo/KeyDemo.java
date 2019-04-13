package com.demo;

import com.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * 测试类 --- 缓存注解的Key属性
 *
 * @author JustryDeng
 * @date 2019/4/13 9:26
 */
@Component
public class KeyDemo {


    /**
     * 方法无参时，默认的key为   SimpleKey []
     *
     * 注:前提条件是 不指定key属性，也无keyGenerator
     */
    @Cacheable(cacheNames = "TestKeySpace")
    public String methodOne() {
        return "methodOne";
    }

    /**
     * 方法只有一个参数时，默认的key为   传入的参数的toString结果
     * 如:调用此方法时，传入的传入的参数为 字符串paramA, 那么key就为paramA
     *
     * 注:前提条件是 不指定key属性，也无keyGenerator
     */
    @Cacheable(cacheNames = "TestKeySpace")
    public String methodTwo(String str) {
        return "methodTwo";
    }

    /**
     * 方法只有一个参数时，默认的key为    传入的参数的toString结果
     * 如:调用此方法时，传入的传如参数为 User对象, 那么就为以 User对象的toString结果作为key
     *
     * 注:前提条件是 不指定key属性，也无keyGenerator
     */
    @Cacheable(cacheNames = "TestKeySpace")
    public String methodThree(User user) {
        return "methodThree";
    }

    /**
     * 方法有多个参数时，默认的key为    SimpleKey [${参数的toString结果},${参数的toString结果}...]
     *
     * 如:调用此方法时传入的参数的toString结果跑分别是:
     *    paramA
     *    1
     *    User(id=null, name=张三, age=18, gender=null, motto=蚂蚁牙黑！)
     *    那么默认的key就为:
     *       SimpleKey [paramA,1,User(id=null, name=张三, age=18, gender=null, motto=蚂蚁牙黑！)]
     *
     * 注:前提条件是 不指定key属性，也无keyGenerator
     */
    @Cacheable(cacheNames = "TestKeySpace")
    public String methodFour(String str, Integer i, User user) {
        return "methodFour";
    }

    /**
     * 若注入有KeyGenerator，当不主动设置注解的key属性时，会默认采用KeyGenerator生成的key
     *
     * 注:前提条件是 设置(注入)有keyGenerator,但不主动指定key属性
     * 提示:本人在com.config包下注入了keyGenerator
     *
     * 如:本人单元测试调用此方法时，传入的参数为字符串“paramA”，如果没注入keyGenerator的话，
     *    key应该是【paramA】，但是本人注入了KeyGenerator，所以这里key
     *    是【com.demo.KeyDemo_methodFive_paramA】
     */
    @Cacheable(cacheNames = "TestKeySpace")
    public String methodFive(String str) {
        return "methodFive";
    }

    /**
     * 说明一: 若主动设置了key属性，那么以主动设置的key属性值为准(无论是否注入有KeyGenerator)
     *
     * 说明二: 如果key为常量的话，需要再使用单引号''引起来
     *
     */
    @Cacheable(cacheNames = "TestKeySpace", key = "'i_am_key'")
    public String methodSix() {
        return "methodSix";
    }

    /**
     * 说明一: 若主动设置了key属性，那么以主动设置的key属性值为准(无论是否注入有KeyGenerator)
     *
     * 说明二: 我们也可以使用Spring Expression Language (SpEL)动态设置key的属性值，
     *        通过  【#形参名】 或 【#p参数索引】来动态获取传入的参数
     *
     *  如: 这里的 key = "#str" 等价于 key = "#p0" 等价于 key = "#a0"
     *      辅助理解:p即params ,   a 即 args
     */
    @Cacheable(cacheNames = "TestKeySpace", key = "#p0")
    public String methodSeven(String str) {
        return "methodSeven";
    }

    /**
     * 说明一: 若主动设置了key属性，那么以主动设置的key属性值为准(无论是否注入有KeyGenerator)
     *
     * 说明二: 我们也可以使用Spring Expression Language (SpEL)动态设置key的属性值，
     *        通过  【#形参名】 或 【#p参数索引】来动态获取传入的参数,
     *        并通过打点的方式对获得的参数进行方法或属性调用
     *
     */
    @Cacheable(cacheNames = "TestKeySpace", key = "#str.hashCode() + '*****' + #p1.name")
    public String methodEight(String str, User user) {
        return "methodEight";
    }


    /**
     * 说明一: 若主动设置了key属性，那么以主动设置的key属性值为准(无论是否注入有KeyGenerator)
     *
     * 说明二: 除了使用Spring Expression Language (SpEL)动动态获取传入的参数外，
     *        我们还可以通过SePL获取Spring为我们提供的隐藏的根对象root
     *
     * 注:#root获取到的其实是CacheExpressionRootObject类的实例，在通过#root打点调用的方式，
     *    可进一步获取到当前环境的一些相关值;
     *
     * 如:这里获取到的key为:
     *       TestKeySpace::com.demo.JustForTest@4f169009--class com.demo.JustForTest--public java.lang.String com.demo.JustForTest.methodNine()--methodNine
     *
     * 再如:#root.args[0]等价于 #p0
     */
    @Cacheable(cacheNames = "TestKeySpace",
               key = "#root.target + '--' + #root.targetClass + '--' + #root.method + '--' + #root.methodName")
    public String methodNine() {
        return "methodNine";
    }
}