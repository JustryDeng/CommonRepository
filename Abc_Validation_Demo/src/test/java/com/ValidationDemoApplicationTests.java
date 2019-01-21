package com;

import com.aspire.model.ValidationBeanModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidationDemoApplicationTests {

    private Validator validator;


    @Before
    public void initValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    /**
     * 在myAssertTrue属性上加@AssertTrue注解
     * <p>
     * 程序输出:  com.aspire.model.ValidationBeanModel$AbcAssertTrue类的myAssertTrue属性 -> 只能为true
     */
    @Test
    public void testAssertTrue() {
        ValidationBeanModel.AbcAssertTrue vm = new ValidationBeanModel().new AbcAssertTrue();
        vm.setMyAssertTrue(false);
        fa(vm);
    }

    /**
     * 在myAssertFalse属性上加@AssertFalse注解
     * <p>
     * 程序输出:  com.aspire.model.ValidationBeanModel$AbcAssertFalse类的myAssertFalse属性 -> 只能为false
     */
    @Test
    public void testAssertFalse() {
        ValidationBeanModel.AbcAssertFalse vm = new ValidationBeanModel().new AbcAssertFalse();
        vm.setMyAssertFalse(true);
        fa(vm);
    }


    /**
     * 在myDecimalMax属性上加@DecimalMax(value = "12.3")注解
     * <p>
     * 程序输出:  com.aspire.model.ValidationBeanModel$AbcDecimalMax类的myDecimalMax属性 -> 必须小于或等于12.3
     */
    @Test
    public void testDecimalMax() {
        ValidationBeanModel.AbcDecimalMax vm = new ValidationBeanModel().new AbcDecimalMax();
        vm.setMyDecimalMax("123");
        fa(vm);
    }

    /**
     * 在myDecimalMin属性上加@DecimalMin(value = "10.3")注解
     * <p>
     * 程序输出:  com.aspire.model.ValidationBeanModel$AbcDecimalMin类的myDecimalMin属性 -> 必须大于或等于10.3
     */
    @Test
    public void testDecimalMin() {
        ValidationBeanModel.AbcDecimalMin vm = new ValidationBeanModel().new AbcDecimalMin();
        vm.setMyDecimalMin("1.23");
        fa(vm);
    }

    /**
     * 在myDigits属性上加@Digits(integer = 5, fraction = 3)注解
     * <p>
     * 程序输出:  com.aspire.model.ValidationBeanModel$AbcDigits类的myDigits属性 -> 数字的值超出了允许范围(只允许在5位整数和3位小数范围内)
     */
    @Test
    public void testDigits() {
        ValidationBeanModel.AbcDigits vm = new ValidationBeanModel().new AbcDigits();
        vm.setMyDigits(1000738);
        fa(vm);
    }

    /**
     * 在myEmail属性上加@Email注解
     * <p>
     * 程序输出:  com.aspire.model.ValidationBeanModel$AbcEmail类的myEmail属性 -> 不是一个合法的电子邮件地址
     */
    @Test
    public void testEmail() {
        ValidationBeanModel.AbcEmail vm = new ValidationBeanModel().new AbcEmail();
        vm.setMyEmail("asd@.com");
        fa(vm);
    }

    /**
     * 在myFuture属性上加@Future注解
     * <p>
     * 程序输出:  com.aspire.model.ValidationBeanModel$AbcFuture类的myFuture属性 -> 需要是一个将来的时间
     */
    @Test
    public void testFuture() {
        ValidationBeanModel.AbcFuture vm = new ValidationBeanModel().new AbcFuture();
        vm.setMyFuture(new Date(10000L));
        fa(vm);
    }

    /**
     * 在myLength属性上加@Length(min = 5, max = 10)注解
     * <p>
     * 程序输出:  com.aspire.model.ValidationBeanModel$AbcLength类的myLength属性 -> 长度需要在5和10之间
     */
    @Test
    public void testLength() {
        ValidationBeanModel.AbcLength vm = new ValidationBeanModel().new AbcLength();
        vm.setMyLength("abcd");
        fa(vm);
    }

    /**
     * 在myMax属性上加@Max(value = 200)注解
     * <p>
     * 程序输出:  com.aspire.model.ValidationBeanModel$AbcMax类的myMax属性 -> 最大不能超过200
     */
    @Test
    public void testMax() {
        ValidationBeanModel.AbcMax vm = new ValidationBeanModel().new AbcMax();
        vm.setMyMax(201L);
        fa(vm);
    }

    /**
     * 在myMin属性上加@Min(value = 200)注解
     * <p>
     * 程序输出:  com.aspire.model.ValidationBeanModel$AbcMin类的myMin属性 -> 最小不能小于100
     */
    @Test
    public void testMin() {
        ValidationBeanModel.AbcMin vm = new ValidationBeanModel().new AbcMin();
        vm.setMyMin(99L);
        fa(vm);
    }

    /**
     * 在myStringNotBlank属性上加@NotBlank注解
     * 在myObjNotBlank属性上加@NotBlank注解
     *
     * 注:如果属性值为null 或者 .trim()后等于""，那么会提示 不能为空
     * <p>
     * 程序输出:   com.aspire.model.ValidationBeanModel$AbcNotBlank类的myObjNotBlank属性 -> 不能为空
     *            com.aspire.model.ValidationBeanModel$AbcNotBlank类的myStringNotBlank属性 -> 不能为空
     */
    @Test
    public void testNotBlank() {
        ValidationBeanModel.AbcNotBlank vm = new ValidationBeanModel().new AbcNotBlank();
        vm.setMyObjNotBlank(null);
        vm.setMyStringNotBlank(" ");
        fa(vm);
    }

    /**
     * 在myStringNotEmpty属性上加@NotEmpty注解
     * 在myNullNotEmpty属性上加@NotEmpty注解
     * 在myMapNotEmpty属性上加@NotEmpty注解
     * 在myListNotEmpty属性上加@NotEmpty注解
     * 在myArrayNotEmpty属性上加@NotEmpty注解
     *
     * 注:String可以是.trim()后等于""的字符串，但是不能为null
     * 注:MAP、Collection、Array既不能是空，也不能是null
     *
     * <p>
     * 程序输出: com.aspire.model.ValidationBeanModel$AbcNotEmpty类的myNullNotEmpty属性 -> 不能为空
     *           com.aspire.model.ValidationBeanModel$AbcNotEmpty类的myListNotEmpty属性 -> 不能为空
     *           com.aspire.model.ValidationBeanModel$AbcNotEmpty类的myArrayNotEmpty属性 -> 不能为空
     *           com.aspire.model.ValidationBeanModel$AbcNotEmpty类的myMapNotEmpty属性 -> 不能为空
     */
    @Test
    public void testNotEmpty() {
        ValidationBeanModel.AbcNotEmpty vm = new ValidationBeanModel().new AbcNotEmpty();
        vm.setMyStringNotEmpty(" ");
        vm.setMyNullNotEmpty(null);
        vm.setMyMapNotEmpty(new HashMap<>(0));
        vm.setMyListNotEmpty(new ArrayList<>(0));
        vm.setMyArrayNotEmpty(new String[]{});
        fa(vm);
    }

    /**
     * 在myStringNotNull属性上加@NotNull注解
     * 在myNullNotNull属性上加@NotNull注解
     * 在myMapNotNull属性上加@NotNull注解
     *
     * 注:属性值可以是空的， 但是就是不能为null
     * <p>
     * 程序输出:   com.aspire.model.ValidationBeanModel$AbcNotNull类的myNullNotNull属性 -> 不能为null
     */
    @Test
    public void testNotNull() {
        ValidationBeanModel.AbcNotNull vm = new ValidationBeanModel().new AbcNotNull();
        vm.setMyStringNotNull("   ");
        vm.setMyNullNotNull(null);
        vm.setMyMapNotNull(new HashMap<>(0));
        fa(vm);
    }

    /**
     * 在myStringNull属性上加@Null注解
     * 在myMapNotNull属性上加@Null注解
     *
     * 注:属性值必须是null， 是空都不行
     * <p>
     * 程序输出:   com.aspire.model.ValidationBeanModel$AbcNull类的myMapNull属性 -> 必须为null
     *            com.aspire.model.ValidationBeanModel$AbcNull类的myStringNull属性 -> 必须为null
     */
    @Test
    public void testNull() {
        ValidationBeanModel.AbcNull vm = new ValidationBeanModel().new AbcNull();
        vm.setMyStringNull("   ");
        vm.setMyMapNull(new HashMap<>(0));
        fa(vm);
    }

    /**
     * 在myPast属性上加@Past注解
     *
     * <p>
     * 程序输出:   com.aspire.model.ValidationBeanModel$AbcPast类的myPast属性 -> 需要是一个过去的时间
     */
    @Test
    public void testPast() {
        ValidationBeanModel.AbcPast vm = new ValidationBeanModel().new AbcPast();
        vm.setMyPast(new Date(20000000000000000L));
        fa(vm);
    }

    /**
     * 在myPattern属性上加@Pattern(regexp = "\\d+")注解
     *
     * <p>
     * 程序输出:   com.aspire.model.ValidationBeanModel$AbcPattern类的myPattern属性 -> 需要匹配正则表达式"\d"
     */
    @Test
    public void testPattern() {
        ValidationBeanModel.AbcPattern vm = new ValidationBeanModel().new AbcPattern();
        vm.setMyPattern("ABC");
        fa(vm);
    }

    /**
     * 在myRange属性上加@Range(min = 100, max = 100000000000L)注解
     *
     * <p>
     * 程序输出:   com.aspire.model.ValidationBeanModel$AbcRange类的myRange属性 -> 需要在100和100000000000之间
     */
    @Test
    public void testRange() {
        ValidationBeanModel.AbcRange vm = new ValidationBeanModel().new AbcRange();
        vm.setMyRange(32222222222222222222222222222222.323);
        fa(vm);
    }

    /**
     * 在mySize属性上加@Size(min = 3, max = 5)注解
     *
     * <p>
     * 程序输出:   com.aspire.model.ValidationBeanModel$AbcSize类的mySize属性 -> 个数必须在3和5之间
     */
    @Test
    public void testSize() {
        ValidationBeanModel.AbcSize vm = new ValidationBeanModel().new AbcSize();
        List<Integer> list = new ArrayList<>(4);
        list.add(0);
        list.add(1);
        vm.setMySize(list);
        fa(vm);
    }

    /**
     * 在myURL属性上加@URL注解
     *
     * <p>
     * 程序输出:   com.aspire.model.ValidationBeanModel$AbcURL类的myURL属性 -> 需要是一个合法的URL
     */
    @Test
    public void testURL() {
        ValidationBeanModel.AbcUrl vm = new ValidationBeanModel().new AbcUrl();
        vm.setMyURL("www.baidu.xxx");
        fa(vm);
    }

    private <T> void fa(T obj) {
        Set<ConstraintViolation<T>> cvSet = validator.validate(obj);
        for (ConstraintViolation<T> cv : cvSet) {
            System.err.println(cv.getRootBean().getClass().getName() + "类的"
                    + cv.getPropertyPath() + "属性 -> " + cv.getMessage());
        }
    }


}

