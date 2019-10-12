package com.szlzcl.jdk8feature;

import com.szlzcl.jdk8feature.model.Staff;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 空指针处理类Optional学习
 *
 * @author JustryDeng
 * @date 2019/7/15 20:49
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@SuppressWarnings("all")
public class OptionalTests {


    /**
     * .get()方法 : 如果 Optional中存在值，则返回值，否则抛出 NoSuchElementException
     *
     * @author JustryDeng
     * @date 2019/7/15 20:49
     */
    @Test(expected = NoSuchElementException.class)
    public void testFour() {
        Optional<?> optional = Optional.empty();
        optional.get();
    }


    /**
     * .of(instance)方法 : 获得持有instance的Optional实例
     *
     * 注:instance不能为null
     *
     * @author JustryDeng
     * @date 2019/7/15 20:49
     */
    @Test
    public void testOne() {
        Staff staff = Staff.builder().name("员工9527").age(123).staffNo("NO.1").build();
        Optional<Staff> optional = Optional.of(staff);
        Assert.assertEquals(optional.get(), staff);
    }

    /**
     * .ofNullable(instance)方法 : 获得持有instance的Optional实例
     *
     * 注:instance可以为null,为null时，获得的就是Optional.empty()
     *
     * @author JustryDeng
     * @date 2019/7/15 20:49
     */
    @Test(expected = NoSuchElementException.class)
    public void testTwo() {
        Optional<Staff> optional = Optional.ofNullable(null);
        // 当optional不持有值的时候，调用 optional.get()方法会抛出NoSuchElementException
        // 注:Optional.empty()方法获得的Optional实例，就是不持有值的Optional实例
        optional.get();
    }

    /**
     * .isPresent()方法 : 判断optional持有的值是否为null(即:判断Optional是否持有值)。
     * 官方的话是 : 如果存在值，则返回 true ，否则为 false 。
     *
     * @author JustryDeng
     * @date 2019/7/15 20:49
     */
    @Test
    public void testThree() {
        Staff staff = Staff.builder().name("员工9527").age(123).staffNo("NO.1").build();
        // 持有值时(即:Optional<T>中，T不为null时)
        Optional<Staff> optionalOne = Optional.ofNullable(staff);
        Assert.assertTrue(optionalOne.isPresent());
        // 不持有值时
        Optional<Staff> optionalTwo = Optional.ofNullable(null);
        Assert.assertFalse(optionalTwo.isPresent());
    }


    class  MyConsumer implements Consumer<Staff>{
        @Override
        public void accept(Staff staff) {
            System.out.println("重新了accept(T t)方法 -> " + staff);
        }
    }

    /**
     * .ifPresent()方法 : 如果存在值，则使用该值调用指定的消费者，否则不执行任何操作。
     *
     * @author JustryDeng
     * @date 2019/7/15 21:06
     */
    @Test
    public void testFive() {
        Staff staff = Staff.builder().name("员工9527").age(123).staffNo("NO.1").build();
        Optional<Staff> optional = Optional.ofNullable(staff);
        optional.ifPresent(new MyConsumer());
        // 此处我们可以不需要显示的实现Consumer接口，而是直接使用lombda表达式来进行优化，如:
        // optional.ifPresent(s -> System.out.println("重新了accept(T t)方法 -> " + s));
        Optional<Staff> emptyOptional = Optional.empty();
        emptyOptional.ifPresent(k -> System.out.println("如果emptyOptional不持有值，那么是不会输出这句话的。"));
    }


    class  MyPredicate implements Predicate<Staff> {
        @Override
        public boolean test(Staff staff) {
            boolean result = staff != null && staff.getAge() > 100;
            log.info(" MyPredicate.test result is -> {}", result);
            return result;
        }
    }

    /**
     * .filter()方法 : 如果一个值存在，并且该值给定的谓词相匹配时(即:filter(Predicate<? super T> predicate)里的，
     *                predicate判定结果为true时)，返回一个 Optional描述的值，否则返回一个空的 Optional 。
     *
     * @author JustryDeng
     * @date 2019/7/15 21:06
     */
    @Test
    public void testSix() {
        Staff staff = Staff.builder().name("员工9527").age(123).staffNo("NO.1").build();
        Optional<Staff> optionalOne = Optional.ofNullable(staff);
        // 匹配Predicate,那么会返回一个 Optional描述的值
        Optional<Staff> newOptionalOne = optionalOne.filter(new MyPredicate());
        // 此时newOptionalOne.isPresent()结果应该为true
        Assert.assertTrue(newOptionalOne.isPresent());

        Optional<Staff> optionalTwo = Optional.ofNullable(null);
        // 不匹配Predicate,那么会返回一个不持有值的Optional实例(即:optional持有的是null)
        Optional<Staff> newOptionalTwo = optionalTwo.filter((s) -> s != null && s.getAge() > 1000);
        // 此时newOptionalOne.isPresent()结果应该为false
        Assert.assertFalse(newOptionalTwo.isPresent());
    }

    class  MyFunction implements Function<String, Staff> {

        /**
         * 将String转换为Staff
         */
        @Override
        public Staff apply(String s) {
            return Staff.builder().name(s).build();
        }
    }

    /**
     * .map()方法 : Optional的泛型(即:持有值 数据类型)转换
     *
     * 注:如Optional<A>.map(Function<? super A, ? extends B> function)后会得到Optional<B>,其
     *    中function的apply方法中定义有从A转换到B的规则
     *
     * 注:会应用function转换的前提是，Optional<A>持有值;
     *
     * 注:如果Optional<A>没持有值，或者Optional<A>持有值，但经过function转换后的Optional<B>不持有值的话，那么
     *    会返回一个一个空的(即:不持有值的)Optional
     *
     * @author JustryDeng
     * @date 2019/7/15 21:06
     */
    @Test
    public void testSeven() {
        Optional<String> optionalOne = Optional.of("蚂蚁牙黑");
        // 将字符串Optional<String>转换为Optional<Staff>，即:将【蚂蚁牙黑】转换为Staff
        Optional<Staff> newOptionalOne = optionalOne.map(new MyFunction());
        Assert.assertEquals("蚂蚁牙黑", newOptionalOne.orElse(Staff.builder().build()).getName());
    }

    class  MyFunctionTwo implements Function<String, Optional<Staff>> {

        /**
         * 将String转换为Optional<Staff>
         */
        @Override
        public Optional<Staff> apply(String s) {
            return Optional.of(Staff.builder().name(s).build());
        }
    }

    /**
     * .flatMap()方法 : Optional泛型(即:持有值 数据类型)转换
     *
     * 注:如Optional<A>.map(Function<? super A, ? extends B> function)后会得到Optional<B>,其
     *    中function的apply方法中定义有从A转换到B的规则
     *
     * 注:会应用function转换的前提是，Optional<A>持有值;
     *
     * 注:如果Optional<A>没持有值，或者Optional<A>持有值，但经过function转换后的Optional<B>不持有值的话，那么
     *    会返回一个一个空的(即:不持有值的)Optional
     *
     * @author JustryDeng
     * @date 2019/7/15 21:06
     */
    @Test
    public void testEight() {
        Optional<String> optionalOne = Optional.of("蚂蚁牙黑");
        // 将String转换为Optional<Staff>，即:将【蚂蚁牙黑】转换为Staff
        Optional<Staff> newOptionalOne = optionalOne.flatMap(new MyFunctionTwo());
        Assert.assertEquals("蚂蚁牙黑", newOptionalOne.orElse(Staff.builder().build()).getName());
    }

    /**
     * public T orElse(T other) 返回值如果存在，否则返回 other
     *
     * @author JustryDeng
     * @date 2019/7/16 9:44
     */
    @Test
    public void testNine() {
        Staff defaultStaff = Staff.builder().name("默认姓名").build();

        Staff staff = Staff.builder().name("邓二洋").build();
        Optional<Staff> optionalOne = Optional.of(staff);
        // 若optionalOne持有值，则返回值；若不持有值(相当于持有的值为null)，则返回defaultStaff
        Staff s1 = optionalOne.orElse(defaultStaff);
        Assert.assertEquals("邓二洋", s1.getName());

        Optional<Staff> optionalTwo = Optional.ofNullable(null);
        // 若optionalTwo持有值，则返回值；若不持有值(相当于持有的值为null)，则返回defaultStaff
        Staff s2 = optionalTwo.orElse(defaultStaff);
        Assert.assertEquals("默认姓名", s2.getName());
    }



    class MySupplier implements Supplier<Staff> {

        @Override
        public Staff get() {
            return Staff.builder().name("李四").build();
        }
    }
    /**
     * public T orElseGet(Supplier<? extends T> other)
     * 若optional持有值，则返回值；若不持有值(相当于持有的值为null)，则调用other并返回该调用的结果。
     *
     * 提示:可使用lombda进行优化
     *
     * @author JustryDeng
     * @date 2019/7/16 9:44
     */
    @Test
    public void testTen() {
        Staff staff = Staff.builder().name("邓二洋").build();
        Optional<Staff> optionalOne = Optional.of(staff);
        // 若optionalOne持有值，则返回值；若不持有值(相当于持有的值为null)，则调用Supplier.get并返回该调用的结果
        Staff s1 = optionalOne.orElseGet(new MySupplier());
        Assert.assertEquals("邓二洋", s1.getName());

        Optional<Staff> optionalTwo = Optional.ofNullable(null);
        // 若optionalTwo持有值，则返回值；若不持有值(相当于持有的值为null)，则调用Supplier.get并返回该调用的结果
        Staff s2 = optionalTwo.orElseGet(new MySupplier());
        Assert.assertEquals("李四", s2.getName());
    }

    class MySupplierTwo implements Supplier<RuntimeException> {

        @Override
        public RuntimeException get() {
            return null;
        }
    }

    /**
     * public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier)
     * throws X extends Throwable
     * 返回包含的值（如果存在），否则抛出由提供的供应商创建的异常。
     *
     * 提示:可使用lombda进行优化
     *
     * @author JustryDeng
     * @date 2019/7/16 9:59
     */
    @Test(expected = RuntimeException.class)
    public void testEleven() {
        Staff staff = Staff.builder().name("邓二洋").build();
        Optional<Staff> optionalOne = Optional.of(staff);
        // 若optionalOne持有值，则返回值；若不持有值(相当于持有的值为null)，则抛出由提供的供应商创建的异常。
        Staff s1 = optionalOne.orElseThrow(new MySupplierTwo());
        Assert.assertEquals("邓二洋", s1.getName());

        Optional<Staff> optionalTwo = Optional.ofNullable(null);
        // 若optionalTwo持有值，则返回值；若不持有值(相当于持有的值为null)，则抛出由提供的供应商创建的异常。
        Staff s2 = optionalTwo.orElseThrow(new MySupplierTwo());
    }

    /**
     * Optional重写了equals方法，会比较Optional持有对象的值
     */
    @Test
    public void testTwelve() {
        Staff staffOne = Staff.builder().name("邓二洋").age(25).build();
        Staff staffTwo = Staff.builder().name("邓二洋").age(25).build();
        Staff staffThree = Staff.builder().name("JustryDeng").age(25).build();

        // 第一个Optional
        Optional<Staff> optionalOne = Optional.of(staffOne);
        // 第二个Optional
        Optional<Staff> optionalTwo = Optional.of(staffTwo);
        // 第三个Optional
        Optional<Staff> optionalThree = Optional.of(staffThree);
        // 第四个Optional
        Optional<Staff> optionalFour = Optional.ofNullable(null);
        // 第五个Optional
        Optional<Staff> optionalFive = Optional.ofNullable(null);

        // true
        boolean resultOne = optionalOne.equals(optionalTwo);
        Assert.assertTrue(resultOne);
        // false
        boolean resultTwo = optionalOne.equals(optionalThree);
        Assert.assertFalse(resultTwo);
        // true（若两个Optional都不持有对象，那么这两个Optional进行equals的结果为true）
        boolean resultThree = optionalFour.equals(optionalFive);
        Assert.assertTrue(resultThree);
        // false
        boolean resultFour = optionalOne.equals(optionalFour);
        Assert.assertFalse(resultFour);
    }

    /**
     * Optional.hashCode
     * 返回当前值的哈希码值（如果有的话），如果没有值，则返回0（零）。
     */
    @Test
    public void testThirteen() {
        Staff staffOne = Staff.builder().name("邓二洋").age(25).build();
        Staff staffTwo = Staff.builder().name("邓二洋").age(25).build();
        Staff staffThree = Staff.builder().name("JustryDeng").age(25).build();

        // 第一个Optional
        Optional<Staff> optionalOne = Optional.of(staffOne);
        // 第二个Optional
        Optional<Staff> optionalTwo = Optional.of(staffTwo);
        // 第三个Optional
        Optional<Staff> optionalThree = Optional.of(staffThree);
        // 第四个Optional
        Optional<Staff> optionalFour = Optional.ofNullable(null);
        int resultOne = optionalOne.hashCode();
        log.info(" resultOne is -> 【{}】！", resultOne);
        int resultTwo = optionalTwo.hashCode();
        log.info(" resultTwo is -> 【{}】！", resultTwo);
        // 求Optional对象的hashCode,实际上求的是Optional持有的对象的hashCode
        Assert.assertEquals(resultOne, resultTwo);

        int resultThree = optionalThree.hashCode();
        Assert.assertNotEquals(resultOne, resultThree);

        int resultFour = optionalFour.hashCode();
        // 若Optional不持有值，那么hashCode值为0
        Assert.assertEquals(0, resultFour);
    }

    /**
     * Optional.toString
     * 返回此可选的非空字符串表示，适用于调试
     */
    @Test
    public void testFourteen() {

        Staff staffOne = Staff.builder().name("邓二洋").age(25).build();
        Optional<Staff> optionalOne = Optional.of(staffOne);

        Staff staffTwo = Staff.builder().name("JustryDeng").age(25).build();
        Optional<Staff> optionalTwo = Optional.of(staffTwo);

        Optional<Staff> optionalThree = Optional.ofNullable(null);
        // Optional[T.toString]
        log.info(" optionalOne is -> 【{}】！", optionalOne);
        // Optional[T.toString]
        log.info(" optionalTwo is -> 【{}】！", optionalTwo);
        // 若不持有值，那么tiString结果为 Optional.empty
        log.info(" optionalThree is -> 【{}】！", optionalThree);
    }
}
