package com.aspire.shardingdatabasetable.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 职工模型， 对应staff表
 *
 * @author JustryDeng
 * @date 2019/6/3 23:26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffPO {

    /** 分布式主键id */
    private String id;

    /** 年龄 */
    private Integer age;

    /** 姓名 */
    private String name;

    /** 性别 */
    private String gender;
}
