package com.szlzcl.jdk8feature.model;

import lombok.*;

import java.io.PipedOutputStream;
import java.io.Serializable;

/**
 * 员工实体模型
 *
 * @author JustryDeng
 * @date 2019/7/15 19:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Staff implements Serializable {

    /** 姓名 */
    private String name;

    /** 年龄 */
    private Integer age;

    /** 工号 */
    private String staffNo;

    /** 颜值(满分100) */
    private Double faceScore;

    /** 工作起始时间点(时间戳) */
    private Long workStartTimestamp;
}
