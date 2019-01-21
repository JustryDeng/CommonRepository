package com.aspire.model;

import com.aspire.constraints.anno.ConstraintsJustryDeng;
import lombok.Getter;
import lombok.Setter;

/**
 * 自定义注解 测试模型
 *
 * @author JustryDeng
 * @date 2019/1/20 22:38
 */
@Setter
@Getter
public class User {

    @ConstraintsJustryDeng(contains = "我是一只小小鸟", message = "应包含特定信息！")
    private String str;
}
