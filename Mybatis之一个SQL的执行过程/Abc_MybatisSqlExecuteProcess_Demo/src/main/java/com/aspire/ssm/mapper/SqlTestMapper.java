package com.aspire.ssm.mapper;

import com.aspire.ssm.mapper.provider.SqlTestProvider;
import com.aspire.ssm.model.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * 数据访问层
 *
 * @author JustryDeng
 * @date 2018年6月13日下午11:36:37
 */
@Mapper
@SuppressWarnings("all")
public interface SqlTestMapper {

    /// *********************** xml的形式 写sql ***********************

    /**
     * 普通SQL(无参数) - XML的形式
     */
    List<Employee> selectAll_xml();

    /**
     * 普通SQL(有参数) - XML的形式
     */
    List<Employee> selectListByName_xml(@Param("name") String name);

    /**
     * 动态SQL - XML的形式
     */
    List<Employee> selectList_xml(@Param("employee") Employee employee);



    /// *********************** 注解的形式 写sql ***********************

    /**
     * 普通SQL(无参数) - 注解的形式
     */
    @Select("SELECT `id`,`name`,`age`,`gender`,`motto`,`birthday`,`hobby` FROM `employee`")
    List<Employee> selectAll_anno();

    /**
     * 普通SQL(有参数) - 注解的形式
     */
    @Select("SELECT `id`,`name`,`age`,`gender`,`motto`,`birthday`,`hobby` FROM `employee` WHERE name=#{name}")
    List<Employee> selectListByName_anno(@Param("name") String name);

    /**
     * 动态SQL - 注解的形式
     */
    @SelectProvider(type = SqlTestProvider.class, method = "selectListAnnoSql")
    List<Employee> selectList_anno(@Param("employee") Employee employee);


    /// ******************* 使用${}的情况
//    /**
//     * 普通SQL(有参数) - 注解的形式
//     */
//    @Select("SELECT `id`,`name`,`age`,`gender`,`motto`,`birthday`,`hobby` FROM `employee` WHERE name='${name}'")
//    List<Employee> selectListByName_anno(@Param("name") String name);
}