package com.szlaozicl.resolver.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户模型
 *
 * @author JustryDeng
 * @date 2019/8/18 1:54
 */
@Data
public class Person implements Serializable {

    private static final long serialVersionUID = 6854385056568469475L;

    /** 姓名 */
    private String name;

    /** 年龄 */
    private Integer age;

    /** 座右铭 */
    private String motto;

    /**
     * 出生日期
     *
     * 注:@DateTimeFormat是spring的一个注解， 其功能是:
     *   【保证前端传入给后端的(指定日期时间格式的)字符串，能正常转换为Date类型的属性值】
     *
     * 注:@JsonFormat是SpringBoot自带的消息转换器jackson的一个注解， 其功能是:
     *   【保证后端返回给前端的此字段的值，是指定格式的字符串】
     *    追注:不同的消息转换器，使用的注解不一样
     */

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;
}