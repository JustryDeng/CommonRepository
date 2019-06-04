package com.aspire.shardingdatabasetable.service;

import com.aspire.shardingdatabasetable.model.StaffPO;

import java.util.List;

/**
 * Service层
 *
 * @author JustryDeng
 * @date 2019/5/29 17:35
 */
public interface ShardingService {

    /**
     * 插入测试
     *
     * @param staffPO
     *            职工模型
     * @return  受影响行数
     * @date 2019/6/3 23:42
     */
    int insertDemo(StaffPO staffPO);

    /**
     * 查询测试
     *
     * @param age
     *            年龄条件
     * @return  满足要求的职工信息集合
     * @date 2019/6/4 21:55
     */
    List<StaffPO> queryDemo(Integer age);
}
