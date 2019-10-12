package com.szlzcl.jdk8feature;

import com.szlzcl.jdk8feature.model.Staff;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * stream学习
 *
 * 提示：为了直观的介绍stream功能，在这里就不严格遵循单元测
 *      试的规范走了。本人会在测试代码里使用System.out...
 *
 * @author JustryDeng
 * @date 2019/8/19 9:49
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@SuppressWarnings("unused")
public class StreamTests {

    /// --------------------------------------------------------------- Stream

    /**
     * 获取串行流:
     *     Collection.stream(): 获取串行Stream
     *     Stream.of(T... values): 获取串行Stream
     *     stream.sequential(): 获取串行Stream
     *     Arrays.stream()
     *     ……
     *
     * 获取并行流:
     *     Collection.parallelStream(): 获取并行Stream
     *     stream.parallel(): 获取并行Stream
     *     ……
     *
     * 注:获取Stream实例的方式方式比较多，这里只示例了最基本的几种方式。
     *
     * 相关说明:
     *    1、串行流由单线程进行处理执行， 并行流由多线程进行处理执行。
     *    2、在多核的情况下，并行流效率更高。
     *    3、推荐使用Stream,而不使用显示迭代(如: for循环等)。
     *    4、显示循环、串行流、并行流的性能比较可参考<linked>https://blog.csdn.net/java1856905/article/details/88640557</linked>
     *    5、串行流、并行流的只是介绍可参考<linked>https://www.jianshu.com/p/19ffb2c9438a</linked>
     *    6、在使用并行流时，如果只是用其来处理集合A本身的话，是不会有线程问题的(其内部会自己处理好)；如果在使用并
     *       行流的时候，涉及到了其他变量B，那么可能出现线程不安全的情况。 如:
     *       listA.parallelStream()
     *       .map(x -> {listB.add(x); return x;})
     *       .forEachOrdered(x -> System.out.print(x)
     *       中，listA的并行流中 进行x -> {listB.add(x); return x;}操作，
     *       即: 多线程中调用listB的add方法，如果listB是线程不安全的，那么就可能出现问题。
     */
    @Test
    public void test1() {
        // 数据准备
        List<Staff> tmpList = Lists.newArrayList(
                Staff.builder().name("张三").age(18).staffNo("NO1").build(),
                Staff.builder().name("李四").age(25).staffNo("NO2").build(),
                Staff.builder().name("王五").age(40).staffNo("NO3").build()
        );

        /// 获取串行Stream
        // 常用方式一
        Stream<Staff> stream = tmpList.stream();
        // 常用方式二
        // 这样:
        // Stream<Staff> stream = Stream.of(a, b, c);
        // 或这样:
        // Staff[] tmpArray = {a, b, c};
        // Stream<Staff> stream = Stream.of(tmpArray);
        // 或这样:
        // Arrays.stream()

        /// 获取并行Stream
        // Stream<Staff> stream = tmpList.parallelStream();
        // Stream.of(a, b, c).parallel();
    }


    /**
     * boolean allMatch: 是否【所有元素都满足Predicate】。
     * boolean anyMatch: 是否【存在元素满足Predicate】。
     * boolean noneMatch: 是否【所有元素都不满足Predicate】。
     */
    @Test
    public void test2() {
        // 数据准备
        List<Staff> tmpList = Lists.newArrayList(
                Staff.builder().name("张三").age(18).staffNo("NO1").build(),
                Staff.builder().name("李四").age(25).staffNo("NO2").build(),
                Staff.builder().name("王五").age(40).staffNo("NO3").build()
        );
        Stream<Staff> stream = tmpList.stream();

        // -> Stream.allMatch 测试
        // Predicate<Staff> predicateOne = x -> x.getAge() >= 18;
        // 控制台输出true。 即:stream中的所有Staff均满足predicateOne.
        // System.out.println(stream.allMatch(predicateOne));

        // -> Stream.anyMatch 测试
        // Predicate<Staff> predicateTwo = x -> x.getName() != null &&  x.getName().contains("王");
        // 控制台输出true。 即:stream中存在Staff满足 predicateTwo.
        // System.out.println(stream.anyMatch(predicateTwo));

        // -> Stream.noneMatch 测试
        Predicate<Staff> predicateThree = x -> "JustryDeng".equals(x.getName());
        // 控制台输出true。 即:stream中不存在任何Staff满足 predicateThree.
        System.out.println(stream.noneMatch(predicateThree));
    }

    /**
     * static <T> Stream<T> concat(Stream<? extends T> a, Stream<? extends T> b): 创建一个String<T>，其元
     *     素是：在第一个流的所有元素后再接上第二个流的所有元素。
     */
    @Test
    public void test3() {
        // 基础数据准备
        Staff a = Staff.builder().name("张三").age(18).staffNo("NO1").build();
        Staff c = Staff.builder().name("王五").age(40).staffNo("NO3").build();
        Stream<Staff> streamA = Stream.of(a, c);

        Staff b = Staff.builder().name("李四").age(25).staffNo("NO2").build();
        Stream<Staff> streamB = Stream.of(b);

        // 按前后顺醋，"拼接"两个流
        Stream<Staff> stream = Stream.concat(streamA, streamB);

        // 输出:[Staff(name=张三, age=18, staffNo=NO1), Staff(name=王五, age=40, staffNo=NO3), Staff(name=李四, age=25, staffNo=NO2)]
        System.out.println(Arrays.deepToString(stream.toArray()));
    }

    /**
     * long count: 返回此Stream中的元素数量。
     */
    @Test
    public void test4() {
        // 数据准备
        List<Staff> tmpList = Lists.newArrayList(
                Staff.builder().name("张三").age(18).staffNo("NO1").build(),
                Staff.builder().name("李四").age(25).staffNo("NO2").build(),
                Staff.builder().name("王五").age(40).staffNo("NO3").build()
        );
        Stream<Staff> stream = tmpList.stream();
        // 输出: 3
        System.out.println(stream.count());
    }

    /**
     * void forEach(Consumer<? super T> action): 遍历元素进行消费。
     *
     * void forEachOrdered(Consumer<? super T> action): 有序遍历元素进行消费。
     *
     * 注:如果是串行流，使用forEach和使用forEachOrdered都能顺序消费。
     *    如果是并行流，使用forEachOrdered能保证有序消费，而forEach不能保证。
     */
    @Test
    public void test6() {
        // 数据准备
        List<Integer> tmpList = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        // 串行流 forEach
        Stream<Integer> sequentialStreamOne = tmpList.stream();
        // 输出: 1	2	3	4	5	6	7	8	9
        sequentialStreamOne.forEach(x -> System.out.print(x + "\t"));
        System.out.print("\n");

        // 串行流 forEachOrdered
        Stream<Integer> sequentialStreamTwo = tmpList.stream();
        // 输出: 1	2	3	4	5	6	7	8	9
        sequentialStreamTwo.forEachOrdered(x -> System.out.print(x + "\t"));
        System.out.print("\n");

        // 并行流 forEach
        Stream<Integer> parallelStreamOne = tmpList.parallelStream();
        // 输出: 6	5	7	8	9	2	1	4	3
        parallelStreamOne.forEach(x -> System.out.print(x + "\t"));
        System.out.print("\n");

        // 并行流 forEachOrdered
        Stream<Integer> parallelStreamTwo = tmpList.parallelStream();
        // 输出: 1	2	3	4	5	6	7	8	9
        parallelStreamTwo.forEachOrdered(x -> System.out.print(x + "\t"));
    }

    /**
     * Stream<T> distinct: 根据equals方法去重。
     */
    @Test
    @SuppressWarnings("all")
    public void test7() {
        String a = "JustryDeng";
        String b = new String("JustryDeng");
        String c = "邓帅";
        String d = new String("邓帅");
        // 数据准备
        List<String> tmpList = Lists.newArrayList(a,b, c,d);
        Stream<String> stream = tmpList.parallelStream();
        // 去重
        stream = stream.distinct();
        stream.forEachOrdered(System.out::println);
    }

    /**
     * Stream<T> filter(Predicate<? super T> predicate): 筛选出(只留下)满足predicate的元素。
     */
    @Test
    public void test8() {
        // 数据准备
        List<Integer> tmpList = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Stream<Integer> stream = tmpList.parallelStream();

        // 筛选条件
        Predicate<Integer> predicate = x -> x != null && x % 2 == 0;

        // 输出: 2	 4	6	8
        stream.filter(predicate).forEachOrdered(x -> System.out.print(x + "\t"));
    }


    /**
     * Optional<T> findFirst: 返回流中的第一个元素的Optional封装。
     *
     * 注: 如果stream是empty，那么会获得一个empty的Optional。
     * 注: 如果筛选出来的第一个元素为null,那么会抛出NPE。
     * 注: If the stream has no encounter order, then any element may be returned.
     */
    @Test
    public void test9() {
        // 数据准备
        List<Integer> tmpList = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Stream<Integer> stream = tmpList.stream();

        Optional<Integer> optional = stream.findFirst();
        optional.ifPresent(x -> System.out.println("\t" + x));
    }


    /**
     * <R> Stream<R> flatMap(Function<? super T,? extends Stream<? extends R>> mapper) :
     *     将Stream<T>中的各个元素转换为对应的Stream<R>,并用新的Stream<R>替代原
     *     来Stream<T>中对应元素的位置，返回一个新的String<R>
     *
     * 注:map与flatMap的都是对原Stream<T>中的元素进行替代。
     *   不过map是以一个Object来替代一个T，而flatMap是用一个Stream对象来替代原来的T。
     *   追注:map与flatMap的主要不同，可以理解为: map只是以一个Object来替代原来的一个T；
     *       而flatMap是以一批(或一个)Object来替代原来的一个T；
     */
    @Test
    public void test10() {
        // 数据准备
        List<Staff> tmpList = Lists.newArrayList(
                Staff.builder().name("张三").age(18).staffNo("NO1").build(),
                Staff.builder().name("李四").age(25).staffNo("NO2").build(),
                Staff.builder().name("王五").age(40).staffNo("NO3").build()
        );

        // 将元素Staff,转换为对应的Stream<String>
        Function<Staff, Stream<String>> function = x -> Arrays.stream(x.getName().split(""));
        // 通过Function<Staff, Stream<String>>，将Stream<Staff>转换为Stream<String>
        Stream<Staff> staffStream = tmpList.parallelStream();
        Stream<String> stringStream = staffStream.flatMap(function);

        // 输出: 张	三	李	四	王	五
        stringStream.forEachOrdered(x -> System.out.print(x + "\t"));
    }

    /**
     * IntStream flatMapToInt(Function<? super T,? extends IntStream> mapper):
     *     将当前Stream<T>中的元素T，以对应的(通过Function转换后得到的)IntStream进行替换，
     *     最终返回一个新的IntStream。
     *
     * LongStream flatMapToLong(Function<? super T,? extends LongStream> mapper):
     *     将当前Stream<T>中的元素T，以对应的(通过Function转换后得到的)LongStream进行替换，
     *     最终返回一个新的LongStream。
     *
     * DoubleStream flatMapToDouble(Function<? super T,? extends DoubleStream> mapper):
     *     将当前Stream<T>中的元素T，以对应的(通过Function转换后得到的)DoubleStream进行
     *     替换，最终返回一个新的DoubleStream。
     *
     */
    @Test
    public void test11() {
        // 数据准备
        List<Staff> tmpList = Lists.newArrayList(
                Staff.builder().name("张三").age(18).workStartTimestamp(1234555L).faceScore(99.9D).build(),
                Staff.builder().name("李四").age(25).workStartTimestamp(2323233L).faceScore(89.2D).build(),
                Staff.builder().name("王五").age(40).workStartTimestamp(8484848L).faceScore(68.7D).build()
        );

        /// flatMapToInt简单示例
        Stream<Staff> staffStreamOne = tmpList.parallelStream();
        Function<Staff, IntStream> functionOne = x -> IntStream.of(x.getAge());
        IntStream intStream = staffStreamOne.flatMapToInt(functionOne);
        // 输出: 18	25	40
        intStream.forEachOrdered(x -> System.out.print(x + "\t"));

        System.out.println("\n");

        /// flatMapToLong简单示例
        Stream<Staff> staffStreamTwo = tmpList.parallelStream();
        Function<Staff, LongStream> functionTwo = x -> LongStream.of(x.getWorkStartTimestamp());
        LongStream longStream = staffStreamTwo.flatMapToLong(functionTwo);
        // 输出: 1234555	2323233	8484848
        longStream.forEachOrdered(x -> System.out.print(x + "\t"));

        System.out.println("\n");

        /// flatMapToDouble简单示例
        Stream<Staff> staffStreamThree = tmpList.parallelStream();
        Function<Staff, DoubleStream> functionThree = x -> DoubleStream.of(x.getFaceScore());
        DoubleStream doubleStream = staffStreamThree.flatMapToDouble(functionThree);
        // 输出: 99.9	89.2	68.7
        doubleStream.forEachOrdered(x -> System.out.print(x + "\t"));
    }

    /**
     * static <T> Stream<T> generate(Supplier<T> s):
     *     返回含有无限多个元素的无序的Stream，其中每个元素由提供的Supplier。
     *
     * 注:对比iterate可知，这里的无序指的是:前一个元素与后一个元素没有任何关系。
     *
     * 注:对于Supplier<T> s，可使用lambda来进行简化书写。
     *
     * 注:此方法一般配合limit(long maxSize)进行使用，否则会有无线多个元素。
     *
     */
    @Test
    public void test12() {
        Stream<Staff> staffStream = Stream.generate(new StaffSupplier()).limit(10);
        staffStream.forEachOrdered(System.out::println);

    }

    class StaffSupplier implements Supplier<Staff> {
        SecureRandom secureRandom = new SecureRandom();
        @Override
        public Staff get() {
            return Staff.builder()
                    .name("张三")
                    .age(secureRandom.nextInt(200))
                    .workStartTimestamp(Math.abs(secureRandom.nextLong()))
                    .faceScore(secureRandom.nextDouble())
                    .build();
        }
    }

    /**
     * static <T> Stream<T> iterate(T seed, UnaryOperator<T> f) :
     *     返回含有无限多个元素的有序的Stream，其中每个元素由提供的Supplier。
     *
     * 注: seed 为生成的Stream的第一个元素，也是f的起始种子。
     *
     * 注:这里的有序指的是:后一个元素是由前一个元素根据一定的逻辑产生的。
     *
     * 注:此方法一般配合limit(long maxSize)进行使用，否则会有无线多个元素。
     *
     * 注:流中的元素是否是同一个对象，取决于UnaryOperator的具体实现了。
     *    一般情况下，最好是使用新的对象。
     */
    @Test
    public void test13() {
        Staff foo = Staff.builder().name("张三").age(18).build();
        Stream<Staff> staffStream = Stream.iterate(foo, new StaffOperatorSupplier()).limit(5);
        // 18	19	20	21	22
        staffStream.forEachOrdered(x -> System.out.print(x.getAge() + "\t"));

    }

    class StaffOperatorSupplier implements UnaryOperator<Staff> {

        @Override
        public Staff apply(Staff staff) {
            // System.err.println(staff.hashCode());
            Integer age = staff.getAge();
            age = age == null ? 0 : age + 1;
            return Staff.builder().age(age).build();
        }

    }


    /**
     * Stream<T> limit(long maxSize) : 截取Stream前面的maxSize个元素。
     *
     * 注：若maxSize大于流的实际长度大小，那么截取到的元素个数就是流的实际长度大小。
     */
    @Test
    public void test14() {
        Stream<Integer> parallelStreamOne = Stream.of(1, 2, 3, 4, 5).parallel();
        // 输出: 123
        parallelStreamOne.limit(3).forEachOrdered(System.out::print);

        Stream<Integer> parallelStreamTwo = Stream.of(1, 2, 3, 4, 5).parallel();
        // 输出: 12345
        parallelStreamTwo.limit(8).forEachOrdered(System.out::print);
    }


    /**
     * <R> Stream<R> map(Function<? super T,? extends R> mapper) :
     *     将Stream<T>中的各个元素转换为对应的Object,并用新的Object替代原
     *     来Stream<T>中对应元素的位置(类型O代替T)，返回一个新的String<O>。
     *
     * 注:map与flatMap的都是对原Stream<T>中的元素进行替代。
     *   不过map是以一个Object来替代一个T，而flatMap是用一个Stream对象来替代原来的T。
     *   追注:map与flatMap的主要不同，可以理解为: map只是以一个Object来替代原来的一个T；
     *       而flatMap是以一批(或一个)Object来替代原来的一个T；
     */
    @Test
    public void test15() {
        // 数据准备
        List<Staff> tmpList = Lists.newArrayList(
                Staff.builder().name("JustryDeng").age(18).staffNo("NO1").build(),
                Staff.builder().name("邓沙利文").age(25).staffNo("NO2").build(),
                Staff.builder().name("邓二洋").age(40).staffNo("NO3").build()
        );

        // 将元素Staff,转换为对应的String
        Function<Staff, String> function = x -> x.getName() + "都特么" + x.getAge() + "岁了！";
        // 通过Function<Staff, String>，将Stream<Staff>转换为Stream<String>
        Stream<Staff> staffStream = tmpList.parallelStream();
        Stream<String> stringStream = staffStream.map(function);

        // 输出: JustryDeng都特么18岁了！	邓沙利文都特么25岁了！	邓二洋都特么40岁了！
        stringStream.forEachOrdered(x -> System.out.print(x + "\t"));
    }


    /**
     * IntStream mapToInt(ToIntFunction<? super T> mapper):
     *     将当前Stream<T>中的元素T，以对应的(通过Function转换后得到的)IntStream进行替换，
     *     最终返回一个新的IntStream。
     *
     * LongStream mapToLong(ToLongFunction<? super T> mapper):
     *     将当前Stream<T>中的元素T，以对应的(通过Function转换后得到的)LongStream进行替换，
     *     最终返回一个新的LongStream。
     *
     * DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper):
     *     将当前Stream<T>中的元素T，以对应的(通过Function转换后得到的)DoubleStream进行
     *     替换，最终返回一个新的DoubleStream。
     */
    @Test
    public void test16() {
        // 数据准备
        List<Staff> tmpList = Lists.newArrayList(
                Staff.builder().name("张三").age(18).workStartTimestamp(1234555L).faceScore(99.9D).build(),
                Staff.builder().name("李四").age(25).workStartTimestamp(2323233L).faceScore(89.2D).build(),
                Staff.builder().name("王五").age(40).workStartTimestamp(8484848L).faceScore(68.7D).build()
        );

        /// mapToInt简单示例
        Stream<Staff> staffStreamOne = tmpList.parallelStream();
        ToIntFunction<Staff> functionOne = Staff::getAge;
        IntStream intStream = staffStreamOne.mapToInt(functionOne);
        // 输出: 18	25	40
        intStream.forEachOrdered(x -> System.out.print(x + "\t"));

        System.out.println("\n");

        /// mapToLong简单示例
        Stream<Staff> staffStreamTwo = tmpList.parallelStream();
        ToLongFunction<Staff> functionTwo = Staff::getWorkStartTimestamp;
        LongStream longStream = staffStreamTwo.mapToLong(functionTwo);
        // 输出: 1234555	2323233	8484848
        longStream.forEachOrdered(x -> System.out.print(x + "\t"));

        System.out.println("\n");

        /// mapToDouble简单示例
        Stream<Staff> staffStreamThree = tmpList.parallelStream();
        ToDoubleFunction<Staff> functionThree = Staff::getFaceScore;
        DoubleStream doubleStream = staffStreamThree.mapToDouble(functionThree);
        // 输出: 99.9	89.2	68.7
        doubleStream.forEachOrdered(x -> System.out.print(x + "\t"));
    }


    /**
     * Optional<T> max(Comparator<? super T> comparator): 根据提供的Comparator, 返回此流的最大元素。
     *
     * Optional<T> min(Comparator<? super T> comparator): 根据提供的Comparator, 返回此流的最小元素。
     *
     * 注:对于比较器等，常用lambda表达式进行简写优化。
     *
     */
    @Test
    public void test17() {
        // 数据准备
        List<Staff> tmpList = Lists.newArrayList(
                Staff.builder().name("JustryDeng").age(12).staffNo("NO1").build(),
                Staff.builder().name("邓沙利文").age(125).staffNo("NO2").build(),
                Staff.builder().name("邓二洋").age(40).staffNo("NO3").build()
        );

        /// max示例
        Optional<Staff>  maxOpt= tmpList.stream().parallel().max(new MaxMinStaffComparator());
        // 输出: Staff(name=邓沙利文, age=125, staffNo=NO2, faceScore=null, workStartTimestamp=null)
        maxOpt.ifPresent(System.out::println);

        /// min示例
        Optional<Staff>  minOpt= tmpList.stream().parallel().min(new MaxMinStaffComparator());
        // 输出: Staff(name=JustryDeng, age=12, staffNo=NO1, faceScore=null, workStartTimestamp=null)
        minOpt.ifPresent(System.out::println);
    }

    class MaxMinStaffComparator implements Comparator<Staff> {

        @Override
        public int compare(Staff o1, Staff o2) {
            Objects.requireNonNull(o1);
            Objects.requireNonNull(o2);
            Integer ageOne = o1.getAge();
            Integer ageTwo = o2.getAge();
            Objects.requireNonNull(ageOne);
            Objects.requireNonNull(ageTwo);
            // 注意:这里比较的顺序，会对结果有影响。一般的，都是o1.compareTo(o2);
            //      如果交换比较顺序，那么结果会相反。
            return ageOne.compareTo(ageTwo);
        }
    }


    /**
     * Stream<T> peek(Consumer<? super T> action): 对stream中的元素进行处理，然后返回此Stream。
     *
     * 注:原Stream中的元素对象reference没变。 但是对象的属性可能会发生变化。
     *
     * 即:这是一个中间处理操作。
     *
     * 注:对于Consumer<T>接口等的实现，可使用lambda。
     */
    @Test
    public void test18() {
        // 数据准备
        List<Staff> tmpList = Lists.newArrayList(
                Staff.builder().name("JustryDeng").age(12).staffNo("NO1").build(),
                Staff.builder().name("邓沙利文").age(125).staffNo("NO2").build(),
                Staff.builder().name("邓二洋").age(40).staffNo("NO3").build()
        );
        Stream<Staff> stream = tmpList.stream().parallel();
        // 对stream中的元素进行处理
        stream = stream.peek(new PeekConsumer());
        // 输出:
        //     peek:JustryDeng
        //     peek:邓沙利文
        //     peek:邓二洋
        stream.forEachOrdered(x -> System.out.println(x.getName()));
    }

    class PeekConsumer implements  Consumer<Staff> {

        @Override
        public void accept(Staff staff) {
            staff.setName("peek:" + staff.getName());
        }
    }


    /**
     * Optional<T> reduce(BinaryOperator<T> accumulator): 归并。通过实现BinaryOperator<T>，Stream<T>中的所有
     *                                                    元素，最终归并为一个被Optional封装的T。
     *
     * 假设:Stream<T>中的元素分别是 T1, T2, T3.
     * 那么:先通过BinaryOperator<T>将T1, T2归并为 T', 然后再通过BinaryOperator<T>将
     *      前面的归并结果T' 和 T3归并为 T", 最终将T"封装进Optional进行返回。
     *
     * 注:推荐使用lambda表达式对BinaryOperator<Staff>进行实现。
     */
    @Test
    public void test19() {
        // 数据准备
        List<Staff> tmpList = Lists.newArrayList(
                Staff.builder().name("JustryDeng").build(),
                Staff.builder().name("邓沙利文").build(),
                Staff.builder().name("邓二洋").build()
        );
        Stream<Staff> stream = tmpList.stream().parallel();
        Optional<Staff> opt = stream.reduce(new MyBinaryOperator());
        // 输出: JustryDeng & 邓沙利文 & 邓二洋
        opt.ifPresent(x -> System.out.println(x.getName()));
    }

    class MyBinaryOperator implements BinaryOperator<Staff> {

        @Override
        public Staff apply(Staff s1, Staff s2) {
            return Staff.builder()
                        .name(s1.getName() + " & " + s2.getName())
                        .build();
        }
    }

    /**
     * 【串行流】下的T reduce(T identity, BinaryOperator<T> accumulator): 使用identity与第一个元素
     *   进行归并得到归并结果accumulatorT，然后再用accumulatorT与后面的元素进行归并。
     *
     * 假设:identity是T0， Stream<T>中的元素分别是 T1, T2, T3.
     *
     * 那么,第一步:先将T0, T1归并为 tmpT1, 然后再将前面的归并结果(首次无)与tmpT1归并为accumulatorT1。
     *        注:因为第一次归并，所以此时accumulatorT1即为tmpT1。
     *     第二步:将accumulatorT1与T2归并为accumulatorT2。
     *     第三步:将accumulatorT2与T3归并为accumulatorT3。
     *     第四步:返回accumulatorT3。
     *           注:accumulatorT3的类型为T。
     */
    @Test
    public void test20_0() {
        // 数据准备
        List<Staff> tmpList = Lists.newArrayList(
                Staff.builder().name("JustryDeng").build(),
                Staff.builder().name("邓沙利文").build(),
                Staff.builder().name("邓二洋").build()
        );
        Stream<Staff> stream = tmpList.stream();

        Staff identityStaff = Staff.builder().name("苗苗").build();
        Staff staff = stream.reduce(identityStaff, (s1, s2) -> Staff.builder()
                .name(s1.getName() + " & " + s2.getName())
                .build());
        /// 输出: 苗苗 & JustryDeng & 邓沙利文 & 邓二洋
        //  说明: 第一步，accumulatorT1的getName()值为【苗苗 & JustryDeng】
        //       第二步，accumulatorT2的getName()值为【苗苗 & JustryDeng & 邓沙利文】
        //       第三步、第四步，accumulatorT3的getName()值为【苗苗 & JustryDeng & 邓沙利文 & 邓二洋】
        System.out.println(staff.getName());
    }

    /**
     * 【并行流】下的T reduce(T identity, BinaryOperator<T> accumulator):
     *       在并行多线程时，在每个线程内部先使用identity与流的当前元素通过accumulator进行归并，
     *       每个线程都会得到临时结果resultT，
     *       然后再通过accumulator归并各个线程的resultT，最终归并为一个T。
     *
     * 假设:identity是T0， Stream<T>中的元素分别是 T1, T2, T3.
     *
     * 那么,第一步:线程内部的归并，T0, T1归并为 tmpT1, T0, T2归并为 tmpT2, T0, T3归并为 tmpT3。
     *     第二步:依次归并tmpT1、tmpT2、tmpT3。 返回类型为T的结果。
     */
    @Test
    public void test20_1() {
        // 数据准备
        List<Staff> tmpList = Lists.newArrayList(
                Staff.builder().name("JustryDeng").build(),
                Staff.builder().name("邓沙利文").build(),
                Staff.builder().name("邓二洋").build()
        );
        Stream<Staff> stream = tmpList.stream().parallel();

        Staff identityStaff = Staff.builder().name("苗苗").build();
        Staff staff = stream.reduce(identityStaff, (s1, s2) -> Staff.builder()
                                                                    .name(s1.getName() + " & " + s2.getName())
                                                                    .build());
        /// 输出: 苗苗 & JustryDeng & 苗苗 & 邓沙利文 & 苗苗 & 邓二洋
        //  说明: 第一步，线程内部的归并，得到【苗苗 & JustryDeng】、【苗苗 & 邓沙利文】、【苗苗 & 邓二洋】
        //       第二步，归并各个线程结果， 得到【苗苗 & JustryDeng & 苗苗 & 邓沙利文 & 苗苗 & 邓二洋】
        System.out.println(staff.getName());
    }

    /**
     * 【串行流】下的 <U> U reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner):
     *       先使用identity(类型为U)与流的第一个元素(类型为T)通过accumulator进行归并，得到临时结果resultU(类型为U)，
     *       然后再通过accumulator归并前面的归并结果resultU(类型为U)与流后面的元素(类型为T)逐个归并。最终归并为一个U。
     *
     * 假设:identity是U0， Stream<T>中的元素分别是 T1, T2, T3.
     *
     * 那么,第一步:将U0, T1通过accumulator归并为 resultU1。
     *     第二步:将resultU1, T2通过accumulator归并为 resultU2。
     *     第二步:将resultU2, T3通过accumulator归并为 resultU3。
     *     第四步:返回resultU3。
     *
     * 注:流的类型为T。 返回的类型U可以是任何类型(包括类型T)。
     *
     * 注:串行流下，第三个参数combiner不生效。
     */
    @Test
    public void test21_0() {
        // 数据准备
        List<Staff> tmpList = Lists.newArrayList(
                Staff.builder().name("JustryDeng").build(),
                Staff.builder().name("邓沙利文").build(),
                Staff.builder().name("邓二洋").build()
        );
        Stream<Staff> stream = tmpList.stream();

        Staff identityStaff = Staff.builder().name("苗苗").build();
        Staff staff = stream.reduce(identityStaff,
                (p1, p2) -> Staff.builder().name(p1.getName() + " | " + p2.getName()).build(),
                (s1, s2) -> Staff.builder().name(s1.getName() + " & " + s2.getName()).build());
        /// 输出: 苗苗 | JustryDeng | 邓沙利文 | 邓二洋
        //  说明: 第一步，resultU1的getName()值为【苗苗 | JustryDeng】
        //       第二步，resultU2的getName()值为【苗苗 | JustryDeng | 邓沙利文】
        //       第三步、第四步，resultU3的getName()值为【苗苗 | JustryDeng | 邓沙利文 | 邓二洋】
        System.out.println(staff.getName());
    }

    /**
     * 【并行流】下的 <U> U reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner):
     *       在并行多线程时，在每个线程内部先使用identity(类型为U)与流的当前元素(类型为T)通过accumulator进行归并，
     *       每个线程都会得到临时结果resultU(类型为U)，
     *       然后再通过combiner归并各个线程的resultU(类型为U)，最终归并为一个U。
     *
     * 假设:identity是U0， Stream<T>中的元素分别是 T1, T2, T3.
     *
     * 那么,第一步:线程内部的归并，U0, T1归并为 tmpU1, U0, T2归并为 tmpU2, U0, T3归并为 tmpU3。
     *     第二步:依次归并tmpU1、tmpU2、tmpU3。 返回类型为U的结果。
     *
     * 注:流的类型为T。 返回的类型U可以是任何类型(包括类型T)。
     *
     * 注:并行流下，第三个参数combiner生效，用于归并各个线程的结果。
     */
    @Test
    public void test21_1() {
        // 数据准备
        List<Staff> tmpList = Lists.newArrayList(
                Staff.builder().name("JustryDeng").build(),
                Staff.builder().name("邓沙利文").build(),
                Staff.builder().name("邓二洋").build()
        );
        Stream<Staff> stream = tmpList.stream().parallel();

        Staff identityStaff = Staff.builder().name("苗苗").build();
        Staff staff = stream.reduce(identityStaff,
                (p1, p2) -> Staff.builder().name(p1.getName() + " | " + p2.getName()).build(),
                (s1, s2) -> Staff.builder().name(s1.getName() + " & " + s2.getName()).build());
        /// 输出: 苗苗 | JustryDeng & 苗苗 | 邓沙利文 & 苗苗 | 邓二洋
        //  说明: 第一步，线程内部的归并，得到【苗苗 | JustryDeng】、【苗苗 | 邓沙利文】、【苗苗 | 邓二洋】
        //       第二步，归并各个线程结果， 得到【苗苗 | JustryDeng & 苗苗 | 邓沙利文 & 苗苗 | 邓二洋】
        System.out.println(staff.getName());
    }

    /**
     * 示例
     * <U> U reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner)方法中，
     * U与T类型不同的情况。
     */
    @Test
    public void test21_2() {
        // 数据准备
        List<Staff> tmpList = Lists.newArrayList(
                Staff.builder().name("JustryDeng").build(),
                Staff.builder().name("邓沙利文").build(),
                Staff.builder().name("邓二洋").build()
        );

        /// -> 串行流下 U与T类型不同的情况 (以List<String>为U， 以Staff为T)示例
        Stream<Staff> streamOne = tmpList.stream();
        List<String> identityOne = new ArrayList<>(3);
        // 串行流下，线程安全，引入“第三方”时，可不考虑其是否线程安全(如:这里直接使用ArrayList的add方法)。
        List<String> resultOne = streamOne.reduce(
                identityOne,
                (List<String> p1, Staff p2) -> {
                    p1.add(p2.getName());
                    return p1;
                },
                (x1, x2) -> x1);
        /// 输出: [JustryDeng, 邓沙利文, 邓二洋]
        System.out.println(resultOne);


        /// -> 并行流下 U与T类型不同的情况 (以List<String>为U， 以Staff为T)示例
        Stream<Staff> streamTwo = tmpList.stream().parallel();
        // 涉及到多线程时，相关位置注意要使用线程安全的类。
        List<String> identityTwo = new CopyOnWriteArrayList<>();
        // 或者: List<String> identityTwo = Collections.synchronizedList(new ArrayList<>(3));
        List<String> resultTwo = streamTwo.reduce(
                identityTwo,
                // 此时p1类型实际为CopyOnWriteArrayList，在多线程情况下，是安全的。
                (List<String> p1, Staff p2) -> {
                    p1.add(p2.getName());
                    return p1;
                },
                // 在并发的情况下，我们已经使用了CopyOnWriteArrayList保证线程安全，且在第二个参数处做了累加操作，
                // 那么在这里(第三个参数)即可什么也不作。 当然也可以让累加操作在这儿离进行
                (List<String> x1, List<String> x2) -> x2);
        /// 输出: [邓沙利文, 邓二洋, JustryDeng]
        System.out.println(resultTwo);
    }

    /**
     * Stream<T> skip(long n): 地球前面n个元素， 返回剩下的元素流。
     *
     * 注：若流的元素个数 <= n，那么丢弃后，将返回一个空的流。
     */
    @Test
    public void test22() {
        Stream<Integer> parallelStreamOne = Stream.of(1, 2, 3, 4, 5).parallel();
        Stream<Integer> remainStreamOne = parallelStreamOne.skip(3L);
        // 输出:  > 4 > 5
        remainStreamOne.forEachOrdered(x -> System.out.print(" > " + x));


        Stream<Integer> parallelStreamTwo = Stream.of(1, 2, 3, 4, 5).parallel();
        Stream<Integer> remainStreamTwo = parallelStreamTwo.skip(5L);
        // 输出:
        // 注；流为空，都不会进入循环，所以不会输出任何东西
        remainStreamTwo.forEachOrdered(x -> System.out.print(" > " + x));


        Stream<Integer> parallelStreamThree = Stream.of(1, 2, 3, 4, 5).parallel();
        Stream<Integer> remainStreamThree = parallelStreamThree.skip(6L);
        // 输出:
        // 注；流为空，都不会进入循环，所以不会输出任何东西
        remainStreamThree.forEachOrdered(x -> System.out.print(" > " + x));
    }

    /**
     * Stream<T> sorted(): 对流元素进行自然排序(ASC2码排序)。
     */
    @Test
    public void test23() {
        /*
         * b 的ASC2为 98
         * # 的ASC2为 35
         * a 的ASC2为 97
         * % 的ASC2为 37
         * ! 的ASC2为 33
         */
        Stream<Character> parallelStreamOne = Stream.of('b', '#', 'a', '%', '!').parallel();
        parallelStreamOne = parallelStreamOne.sorted();
        // 输出: > ! > # > % > a > b
        parallelStreamOne.forEachOrdered(x -> System.out.print(" > " + x));

        /*
         * 赵 的ASC2为 36213
         * 钱 的ASC2为 38065
         * 孙 的ASC2为 23385
         * 李 的ASC2为 26446
         */
        System.out.println();
        Stream<String> parallelStreamTwo = Stream.of("赵", "钱", "孙", "李").parallel();
        parallelStreamTwo = parallelStreamTwo.sorted();
        // 输出: > 孙 > 李 > 赵 > 钱
        parallelStreamTwo.forEachOrdered(x -> System.out.print(" > " + x));

        // ASC2码值
        // System.out.println((int) "赵".charAt(0));
        // System.out.println((int) "钱".charAt(0));
        // System.out.println((int) "孙".charAt(0));
        // System.out.println((int) "李".charAt(0));
    }

    /**
     * Stream<T> sorted(Comparator<? super T> comparator): 根据比较器，对流元素进行排序。
     */
    @Test
    public void test24() {
        Stream<String> parallelStream = Stream.of("李d", "钱b", "孙c", "赵a").parallel();
        parallelStream = parallelStream.sorted((String a, String b) -> {
            char aChar = a.charAt(a.length() - 1);
            char bChar = b.charAt(b.length() - 1);
            return Character.compare(aChar, bChar);
        });
        // 输出:  > 赵a > 钱b > 孙c > 李d
        parallelStream.forEachOrdered(x -> System.out.print(" > " + x));
    }

    /**
     * Object[] toArray(): 流转Object数组。
     */
    @Test
    public void test25() {
        // 数据准备
        List<Staff> tmpList = Lists.newArrayList(
                Staff.builder().name("JustryDeng").build(),
                Staff.builder().name("邓沙利文").build(),
                Staff.builder().name("邓二洋").build()
        );
        Object[] array = tmpList.parallelStream().toArray();
        // 输出: [
        //       Staff(name=JustryDeng, age=null, staffNo=null, faceScore=null, workStartTimestamp=null),
        //       Staff(name=邓沙利文, age=null, staffNo=null, faceScore=null, workStartTimestamp=null),
        //       Staff(name=邓二洋, age=null, staffNo=null, faceScore=null, workStartTimestamp=null)
        //       ]
        System.out.println(Arrays.deepToString(array));
    }

    /**
     * <A> A[] toArray(IntFunction<A[]> generator): 流转指定类型的数组。
     */
    @Test
    public void test26() {
        // 数据准备
        List<Staff> tmpList = Lists.newArrayList(
                Staff.builder().name("JustryDeng").build(),
                Staff.builder().name("邓沙利文").build(),
                Staff.builder().name("邓二洋").build()
        );
        Stream<Staff> stream = tmpList.parallelStream();
        // 流转指定类型的数组
        IntFunction<Staff[]> generator = Staff[]::new;
        Staff[] array = tmpList.stream().toArray(generator);
        System.out.println(Arrays.deepToString(array));
    }

//    /**
//     * collect(Collector<? super T,A,R> collector):
//     */
//    @Test
//    public void test27_0() {
//        // 数据准备
//        List<Staff> tmpList = Lists.newArrayList(
//                Staff.builder().name("张三").age(18).workStartTimestamp(1234555L).faceScore(99.9D).build(),
//                Staff.builder().name("李四").age(25).workStartTimestamp(2323233L).faceScore(89.2D).build(),
//                Staff.builder().name("王五").age(40).workStartTimestamp(8484848L).faceScore(68.7D).build()
//        );
//        Stream<Staff> stream = tmpList.parallelStream().collect(Collectors.to);
//    }
//
//    /**
//     * collect(Supplier<R> supplier, BiConsumer<R,? super T> accumulator, BiConsumer<R,R> combiner)
//     */
//    @Test
//    public void test28() {
//    }
//    /// --------------------------------------------------------------- IntStream
//
//
//    /// --------------------------------------------------------------- LongStream
//
//    /// --------------------------------------------------------------- DoubleStream
//
//    /// --------------------------------------------------------------- 相关简单拓展
//
//    /**
//     * 利用Stream, 获取文件内容、处理文件内容
//     */
//    @Test
//    public void other1() throws IOException {
//        // 获取文件数据行的stream
//        Path path = Paths.get("C:\\Users\\JustryDeng\\Desktop\\abc.txt");
//        Stream<String> line = Files.lines(path, StandardCharsets.UTF_8).parallel();
//        // 注意: 因为这里用的是并行流，所以这里map中的式子，一定要保证线程安全
//        line.map(x -> {
//            x = x.replace("夹杂中文", "【夹杂JustryDeng中文】");
//            return x;
//        }).forEachOrdered(System.out::println);
//    }

}