package com.aspire.jpademo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 员工实体类模型
 *
 *
 * 这里 @Data、@Builder、@NoArgsConstructor、@AllArgsConstructor  是: lombok的注解，与JPA无
 * 关， 引入lombok主要是为了快速开发
 * 注: 若使用了@Builder注解、建议一定要使用 @NoArgsConstructor、@AllArgsConstructor注解，因
 *     为@Builder注解是建造者模式，如果只使用了@Builder，而不使用@NoArgsConstructor
 *     、@AllArgsConstructor注解的话，可能他会导致fastjson、jpa等出现异常
 *
 * 这里 @Entity  的作用是: 表明这是一个JPA实体
 * 这里 @Table  的作用是:  指明这个JPA实体 对应 那一张表
 *
 * @author JustryDeng
 * @date 2019/4/22 20:41
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class EmployeePO implements Serializable {

    /**
     * 这里 @Id 注解的作用是: 指明此字段对应的列是相关表的主键
     * 这里 @GeneratedValue 注解的作用是: 指定主键生成策略， 默认是AUTO的策略,也就是主键序列化,
     *     由于mysql是不支持序列化的， 所以我们需要给他指定其他的策略，这里
     *     GenerationType.IDENTITY表示 使用此数据库服务器端自己的策略
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 姓名
     *
     * 这里@Column注解的作用是: 1、指明该字段对应表中的哪一列
     *                       2、指明此字段可以为null（默认即为true）
     * 注:@Column注解还有很多属性，如: 约束是否唯一、是否插入、是否更新、指定长度等等，
     *    可详见源码 或 查阅相关资料文档
     */
    @Column(name = "name", nullable = true)
    private String name;

    /** 年龄 */
    private Integer age;

    /**
     * 性别
     *
     * 这里@Column注解的length属性作用是: 指明该字段最大长度为1
     */
    @Column(length = 1)
    private String gender;

    /** 座右铭 */
    private String motto;

}
