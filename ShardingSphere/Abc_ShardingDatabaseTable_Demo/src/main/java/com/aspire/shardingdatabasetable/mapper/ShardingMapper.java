package com.aspire.shardingdatabasetable.mapper;

import com.aspire.shardingdatabasetable.model.StaffPO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 持久层
 *
 * @author JustryDeng
 * @date 2019/5/29 17:36
 */
@Mapper
public interface ShardingMapper {

    /**
     * 向逻辑表staff中插入数据
     *
     * @param staffPO
     *            职工模型
     * @return  受影响行数
     * @date 2019/6/3 23:38
     */
    @Insert({"insert into staff (`id`, `age`, `name`, `gender`) values (#{staffPO.id},",
             " #{staffPO.age}, #{staffPO.name}, #{staffPO.gender})"})
    int insertData(@Param("staffPO") StaffPO staffPO);

    /**
     * 根据年龄大于等于给定age的职工信息
     *
     * @param age
     *            年龄条件
     * @return  符合要求的职工集合
     * @date 2019/6/4 21:52
     */
    @Select("select id, name, age, gender from staff where age >= #{age}")
    List<StaffPO> queryStaffByAge(Integer age);
}
