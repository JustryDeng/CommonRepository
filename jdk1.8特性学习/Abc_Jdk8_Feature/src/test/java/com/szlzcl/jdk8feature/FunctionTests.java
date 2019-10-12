package com.szlzcl.jdk8feature;

import com.szlzcl.jdk8feature.model.Staff;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Function 及 相关接口 使用示例。
 *
 * 注:本文按以下顺序进行介绍示例:
 *    Function<T, R>
 *    IntFunction<R>
 *    DoubleFunction<R>
 *    LongFunction<R>
 *    ToIntFunction<T>
 *    ToLongFunction<T>
 *    ToDoubleFunction<T>
 *    IntToDoubleFunction
 *    IntToLongFunction
 *    LongToDoubleFunction
 *    LongToIntFunction
 *    DoubleToIntFunction
 *    DoubleToLongFunction
 *    BiFunction<T, U, R>
 *    ToIntBiFunction<T, U>
 *    ToLongBiFunction<T, U>
 *    ToDoubleBiFunction<T, U>
 *
 *
 * @author JustryDeng
 * @date 2019/9/12 17:22
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FunctionTests {


    /// ------------------------------------------------------------------- Function<T, R>
    /**
     *  R apply(T t): 输入T类型的参数，运行相关逻辑后，返回R类型的结果。
     */
    @Test
    public void test1() {
        Function<String, Staff> function = x -> {
            Staff s = new Staff();
            s.setName(x + ", 咿呀咿呀哟!");
            return s;
        };

        Staff staff = function.apply("邓某");
        // 输出: 邓某, 咿呀咿呀哟!
        System.out.println(staff.getName());
    }

    /**
     * <V> Function<V, R> compose(Function<? super V, ? extends T> before):由两个旧的Function得到一个新的Function。
     *
     * 假设：现有式子functionC = functionB.compose(functionA)，
     *      functionA泛型为<P1, R1>， functionB泛型为<R1, R2>。
     *
     * 那么，上述式子的逻辑是:先运行functionA<P1, R1>的apply方法，然后将其返回值
     *                     作为functionB<R1, R2>的apply方法的入参，执行
     *                     functionB<R1, R2>的apply方法，返回一个泛型为
     *                     <P1, R2>的functionC<P1, R2>。
     */
    @Test
    public void test2() {

        Function<String, Integer> functionA = x -> {
            Objects.requireNonNull(x, "参数不能为空");
            x = x.replace(" ", "");
            return x.length();
        };

        Function<Integer, Staff> functionB = x -> {
            Objects.requireNonNull(x, "参数不能为空");
            Staff s = new Staff();
            s.setAge(x);
            return s;
        };

        Function<String, Staff> functionC = functionB.compose(functionA);
        Staff staff = functionC.apply(" 我 是 参 数 ！ ");
        // 输出: Staff(name=null, age=5, staffNo=null)
        System.out.println(staff);
    }

    /**
     * <V> Function<T, V> andThen(Function<? super R, ? extends V> after):由两个旧的Function得到一个新的Function。
     *
     * 假设：现有式子functionC = functionA.andThen(functionB),
     *      functionA泛型为<P1, R1>， functionB泛型为<R1, R2>。
     *
     * 那么，上述式子的逻辑是:先运行functionA<P1, R1>的apply方法，然后将其返回值
     *                     作为functionB<R1, R2>的apply方法的入参，执行
     *                     functionB<R1, R2>的apply方法，返回一个泛型为
     *                     <P1, R2>的functionC<P1, R2>。
     *
     * 注: functionA.andThen(functionB)是先执行functionA，再执行functionB；
     *     而functionA.compose(functionB)是先执行functionB，再执行functionA。
     */
    @Test
    public void test3() {

        Function<String, Integer> functionA = x -> {
            Objects.requireNonNull(x, "参数不能为空");
            x = x.replace(" ", "");
            return x.length();
        };

        Function<Integer, Staff> functionB = x -> {
            Objects.requireNonNull(x, "参数不能为空");
            Staff s = new Staff();
            s.setAge(x);
            return s;
        };

        Function<String, Staff> functionC = functionA.andThen(functionB);
        Staff staff = functionC.apply(" 我 是 参 数 ！ ");
        // 输出: Staff(name=null, age=5, staffNo=null)
        System.out.println(staff);
    }

    /**
     * static <T> Function<T, T> identity(): 将输入的参数进行返回，即: return t -> t。
     *
     * 说明: 在某些情景下，使用Function.identity()，会让代码更优雅。
     */
    @Test
    public void test4() {
        /*
         * 使用普通的lambda表达式
         */
        Map<Integer, String> mapOne = Stream.of("a", "ab", "abc", "abcd", "abcde").collect(
                Collectors.toMap(String::length, param -> param)
        );
        // 输出: {1=a, 2=ab, 3=abc, 4=abcd, 5=abcde}
        System.out.println(mapOne);

        /*
         * 使用Function.identity()无疑更优雅
         */
        Map<Integer, String> mapTwo = Stream.of("a", "ab", "abc", "abcd", "abcde").collect(
                Collectors.toMap(String::length, Function.identity())
        );
        // 输出: {1=a, 2=ab, 3=abc, 4=abcd, 5=abcde}
        System.out.println(mapTwo);
    }

    /// ------------------------------------------------------------------- IntFunction<R>
    /**
     * R apply(int value): 入参类型必须为int, (运行相关逻辑后)返回R类型的数据。
     */
    @Test
    public void test5() {
        IntFunction<Staff> intFunction = x -> {
            Staff staff = new Staff();
            staff.setAge(x);
            return staff;
        };

        Staff res = intFunction.apply(100);
        // 输出: Staff(name=null, age=100, staffNo=null)
        System.out.println(res);
    }

    /// ------------------------------------------------------------------- DoubleFunction<R>
    /**
     * R apply(double value): 入参类型必须为double, (运行相关逻辑后)返回R类型的数据。
     */
    @Test
    public void test6() {
        DoubleFunction<String> doubleFunction = x -> (x + "").replace(".", "_");
        String res = doubleFunction.apply(10.01);
        // 输出: 10_01
        System.out.println(res);
    }

    /// ------------------------------------------------------------------- LongFunction<R>
    /**
     * R apply(long value): 入参类型必须为long, (运行相关逻辑后)返回R类型的数据。
     */
    @Test
    public void test7() {
        LongFunction<String> longFunction = x -> (x + "").replace("4", "  8484884  ").trim();
        String res = longFunction.apply(484);
        // 输出: 8484884  8  8484884
        System.out.println(res);
    }

    /// ------------------------------------------------------------------- ToIntFunction<T>
    /**
     * int applyAsInt(T value): 入参类型为T, (运行相关逻辑后)返回类型必为int。
     */
    @Test
    public void test8() {
        ToIntFunction<String> toIntFunction = x -> x == null ? 0 : x.length();
        int res = toIntFunction.applyAsInt("蚂蚁呀~嘿！嘿！");
        // 输出: 8
        System.out.println(res);
    }

    /// ------------------------------------------------------------------- ToLongFunction<T>
    /**
     * long applyAsLong(T value): 入参类型为T, (运行相关逻辑后)返回类型必为long。
     */
    @Test
    public void test9() {
        ToLongFunction<String> toLongFunction = x -> x == null ? 0 : new SecureRandom(x.getBytes()).nextLong();
        long res = toLongFunction.applyAsLong("蚂蚁呀~嘿！嘿！");
        // 输出: 2677168598702751372
        System.out.println(res);
    }

    /// ------------------------------------------------------------------- ToDoubleFunction<T>
    /**
     * double applyAsDouble(T value): 入参类型为T, (运行相关逻辑后)返回类型必为double。
     */
    @Test
    public void test10() {
        ToDoubleFunction<Float> toDoubleFunction = x -> x == null ? 0.0 : x;
        double res = toDoubleFunction.applyAsDouble(123.4F);
        // 输出: 123.4000015258789 (注:精度问题不在本文的讨论范围内)
        System.out.println(res);
    }

    /// ------------------------------------------------------------------- IntToDoubleFunction
    /**
     * double applyAsDouble(int value): 入参类型必须为int, (运行相关逻辑后)返回类型必为double。
     */
    @Test
    public void test11() {
        IntToDoubleFunction intToDoubleFunction = x -> x + 88.8;
        double res = intToDoubleFunction.applyAsDouble(12300);
        // 输出: 12388.8
        System.out.println(res);
    }

    /// ------------------------------------------------------------------- IntToLongFunction
    /**
     * long applyAsLong(int value): 入参类型必须为int, (运行相关逻辑后)返回类型必为long。
     */
    @Test
    public void test12() {
        IntToLongFunction intToLongFunction = x -> x + 1000L;
        long res = intToLongFunction.applyAsLong(12345);
        // 输出: 13345
        System.out.println(res);
    }

    /// ------------------------------------------------------------------- LongToDoubleFunction
    /**
     * double applyAsDouble(long value): 入参类型必须为long, (运行相关逻辑后)返回类型必为double。
     */
    @Test
    public void test13() {
        LongToDoubleFunction longToDoubleFunction = x -> x + 1000000D;
        double res = longToDoubleFunction.applyAsDouble(12345L);
        // 输出: 1012345.0
        System.out.println(res);
    }

    /// ------------------------------------------------------------------- LongToIntFunction
    /**
     * int applyAsInt(long value): 入参类型必须为long, (运行相关逻辑后)返回类型必为int。
     */
    @Test
    public void test14() {
        LongToIntFunction longToIntFunction = x -> (int)(x / 1000);
        int res = longToIntFunction.applyAsInt(12345L);
        // 输出: 12
        System.out.println(res);
    }

    /// ------------------------------------------------------------------- DoubleToIntFunction
    /**
     * int applyAsInt(double value): 入参类型必须为double, (运行相关逻辑后)返回类型必为int。
     */
    @Test
    public void test15() {
        DoubleToIntFunction doubleToIntFunction = x -> (int)x;
        int res = doubleToIntFunction.applyAsInt(123.45);
        // 输出: 123
        System.out.println(res);
    }

    /// ------------------------------------------------------------------- DoubleToLongFunction
    /**
     * long applyAsLong(double value): 入参类型必须为double, (运行相关逻辑后)返回类型必为long。
     */
    @Test
    public void test16() {
        DoubleToLongFunction doubleToLongFunction = x -> (long)x;
        long res = doubleToLongFunction.applyAsLong(112233.4455);
        // 输出: 112233
        System.out.println(res);
    }

    /// ------------------------------------------------------------------- BiFunction<T, U, R>
    /**
     *  R apply(T t, U u): 输入T类型、U类型的参数，运行相关逻辑后，返回R类型的结果。
     */
    @Test
    public void test17() {
        BiFunction<Integer, String, Staff> biFunction = (x, y) -> {
            Staff s = new Staff();
            s.setAge(x);
            s.setName(y);
            return s;
        };

        Staff staff = biFunction.apply(25, "单身邓");
        // 输出: 单身邓, 25岁！
        System.out.println(staff.getName() + ", " + staff.getAge() + "岁！");
    }

    /**
     * <V> BiFunction<T, U, V> andThen(Function<? super R, ? extends V> after):
     *     由一个旧的BiFunction以及一个旧的Function得到一个新的BiFunction<T, U, V>。
     *
     * 假设：现有式子biFunctionC = biFunctionA.andThen(functionB),
     *      biFunctionA泛型为<P1, T1, R1>， functionB泛型为<R1, R2>。
     *
     * 那么，上述式子的逻辑是:先运行biFunctionA<P1, T1, R1>的apply方法，然后将其返回值
     *                     作为functionB<R1, R2>的apply方法的入参，执行
     *                     functionB<R1, R2>的apply方法，返回一个泛型为
     *                     <P1, T1, R2>的biFunctionC<P1, T1, R2>。
     */
    @Test
    public void test18() {
        BiFunction<Integer, String, Staff> biFunctionA = (x, y) -> {
            Staff s = new Staff();
            s.setAge(x);
            s.setName(y);
            return s;
        };

        Function<Staff, Map<String, Staff>> functionB = x -> {
            Map<String, Staff> map = new HashMap<>(4);
            map.put(x.getName(), x);
            return map;
        };

        BiFunction<Integer, String,  Map<String, Staff>> biFunctionC = biFunctionA.andThen(functionB);

        Map<String, Staff> map = biFunctionC.apply(25, "单身邓");
        // 输出: {单身邓=Staff(name=单身邓, age=25, staffNo=null)}
        System.out.println(map);
    }

    /// ------------------------------------------------------------------- ToIntBiFunction<T, U>
    /**
     * int applyAsInt(T t, U u): 入参类型为T和U, (运行相关逻辑后)返回类型必为int。
     */
    @Test
    public void test19() {
        ToIntBiFunction<String, Double> toIntBiFunction = (x, y) -> Integer.parseInt(x) + y.intValue();
        int res = toIntBiFunction.applyAsInt("123000", 456.789);
        // 输出: 123456
        System.out.println(res);
    }

    /// ------------------------------------------------------------------- ToLongBiFunction<T, U>
    /**
     * long applyAsLong(T t, U u): 入参类型为T和U, (运行相关逻辑后)返回类型必为long。
     */
    @Test
    public void test20() {
        ToLongBiFunction<String, Double> toLongBiFunction = (x, y) -> Integer.parseInt(x) + y.intValue();
        long res = toLongBiFunction.applyAsLong("123000", 456.789);
        // 输出: 123456
        System.out.println(res);
    }

    /// ------------------------------------------------------------------- ToDoubleBiFunction<T, U>
    /**
     * double applyAsDouble(T t, U u): 入参类型为T和U, (运行相关逻辑后)返回类型必为double。
     */
    @Test
    public void test21() {
        ToDoubleBiFunction<String, Double> toDoubleBiFunction = (x, y) -> Integer.parseInt(x) + y;
        double res = toDoubleBiFunction.applyAsDouble("123000", 456.789);
        // 输出: 123456.789
        System.out.println(res);
    }

}