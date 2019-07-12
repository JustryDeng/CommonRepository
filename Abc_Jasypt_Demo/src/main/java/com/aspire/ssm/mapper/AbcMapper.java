package com.aspire.ssm.mapper;

import com.aspire.ssm.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 数据访问层
 *
 * @author JustryDeng
 * @date 2018年6月13日下午11:36:37
 */
@Mapper
public interface AbcMapper {

    /**
     * 增------简单增
     *
     * @param user
     *         员工实体模型
     * @return 成功插入的条数
     */
    @Insert("INSERT INTO employee (`name`, `age`, `gender`, `motto`) "
            + " VALUES(#{u.name},#{u.age},#{u.gender},#{u.motto})")
    int abcInsert(@Param("u") User user);

}