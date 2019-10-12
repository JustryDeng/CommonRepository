package com.szlzcl.jdk8feature;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.function.Predicate;

/**
 * 函数式接口之Predicate<T>学习
 *
 * 提示一:Predicate<T>的功能是:使用test(T t)方法，判断参数t是否满
 *       足Predicate实例所代表的表达式
 *
 * 提示二:相似的还有:
 *       BiPredicate<T, U>: 使用 test(T t, U u)方法，判断参数t、u是否
 *                          满足BiPredicate实例所代表的表达式
 *       DoublePredicate: 对于基本数据类型double的Predicate
 *       LongPredicate: 对于基本数据类型int的Predicate
 *       IntPredicate: 对于基本数据类型long的Predicate
 *
 * 提示三:本文主要学习Predicate，学会了Predicate，那么自然就学会了
 *       BiPredicate<T, U>、DoublePredicate、IntPredicate、LongPredicate
 *
 * @author JustryDeng
 * @date 2019/8/19 10:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PredicateTest {

    /**
     * test(T t): 判断t，是否满足Predicate实例所代表的表达式
     */
    @Test
    public void test1() {

        // 形参x的数据类型，由Predicate<T>的泛型T指定
        // 定义一个 用于判断的表达式(这里为 x >= 1)
        Predicate<Integer> predicate = x -> x >= 1;

        // 判断参数是否满足 predicate代表的表达式
        boolean resultOne = predicate.test(0);
        boolean resultTwo = predicate.test(1);

        // 输出结果为： false
        System.out.println(resultOne);
        // 输出结果为： true
        System.out.println(resultTwo);
    }

    /**
     * isEqual(Object targetRef): 判断targetRef，是否与Predicate实例所代表的对象相等
     */
    @Test
    public void test2() {

        /*
         * 等价于:
         *  Predicate<Object> predicate = x -> {
         *      if (x == objA) {
         *         return true;
         *      }
         *      return x.equals(objA);
         *  };
         */
        Object objA = new Object();
        Predicate<Object> predicate = Predicate.isEqual(objA);


        // 判断参数是否满足 predicate代表的表达式
        boolean resultOne = predicate.test(objA);
        boolean resultTwo = predicate.test(null);
        boolean resultThree = predicate.test(new Object());

        // 输出结果为： true
        System.out.println(resultOne);
        // 输出结果为： false
        System.out.println(resultTwo);
        // 输出结果为： false
        System.out.println(resultThree);
    }

    /**
     * and(Predicate<? super T> other): 对两个Predicate实例取&&，得到新的Predicate实例
     */
    @Test
    public void test3() {

        // 定义两个 用于判断的表达式
        Predicate<Integer> predicateOne = x -> x >= 1;
        Predicate<Integer> predicateTwo = x -> x <= 2;

        /*
         * 对两个表达式取 &&
         *
         * 等价于：Predicate<Integer> predicateThree = x -> x >= 1 && x <= 2;
         */
        Predicate<Integer> predicateThree = predicateOne.and(predicateTwo);

        // 判断参数是否满足 predicateThree代表的表达式
        boolean resultOne = predicateThree.test(0);
        boolean resultTwo = predicateThree.test(1);
        boolean resultThree = predicateThree.test(2);
        boolean resultFour = predicateThree.test(3);

        // 输出结果为： false
        System.out.println(resultOne);
        // 输出结果为： true
        System.out.println(resultTwo);
        // 输出结果为： true
        System.out.println(resultThree);
        // 输出结果为： false
        System.out.println(resultFour);
    }

    /**
     * or(Predicate<? super T> other): 对两个Predicate实例取||，得到新的Predicate实例
     */
    @Test
    public void test4() {

        // 定义两个 用于判断的表达式
        Predicate<Integer> predicateOne = x -> x >= 1;
        Predicate<Integer> predicateTwo = x -> x <= -1;

        /*
         * 对两个表达式取 ||
         *
         * 等价于：Predicate<Integer> predicateThree = x -> x >= 1 || x <= -1;
         */
        Predicate<Integer> predicateThree = predicateOne.or(predicateTwo);

        // 判断参数是否满足 predicateThree代表的表达式
        boolean resultOne = predicateThree.test(-2);
        boolean resultTwo = predicateThree.test(-1);
        boolean resultThree = predicateThree.test(0);
        boolean resultFour = predicateThree.test(1);
        boolean resultFive = predicateThree.test(2);

        // 输出结果为： true
        System.out.println(resultOne);
        // 输出结果为： true
        System.out.println(resultTwo);
        // 输出结果为： false
        System.out.println(resultThree);
        // 输出结果为： true
        System.out.println(resultFour);
        // 输出结果为： true
        System.out.println(resultFive);
    }

    /**
     * negate(): 对当前Predicate实例取!，得到新的Predicate实例
     */
    @Test
    public void test5() {

        // 定义两个 用于判断的表达式
        Predicate<Integer> predicateOne = x -> x >= 1;

        /*
         * 等价于：Predicate<Integer> predicate = x -> !(x >= 1);
         * 等价于：Predicate<Integer> predicate = x -> x < 1;
         */
        Predicate<Integer> predicate = predicateOne.negate();

        // 判断参数是否满足 predicateThree代表的表达式
        boolean resultOne = predicate.test(0);
        boolean resultTwo = predicate.test(1);
        boolean resultThree = predicate.test(2);

        // 输出结果为： true
        System.out.println(resultOne);
        // 输出结果为： false
        System.out.println(resultTwo);
        // 输出结果为： false
        System.out.println(resultThree);
    }
}