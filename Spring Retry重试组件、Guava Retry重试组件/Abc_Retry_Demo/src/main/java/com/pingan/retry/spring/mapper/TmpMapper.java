package com.pingan.retry.spring.mapper;


import com.pingan.retry.spring.model.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (non-javadoc)
 *
 * @author JustryDeng
 * @date 2020/2/26 17:38:44
 */
@Mapper
public interface TmpMapper {

    /**
     * 增
     *
     * @param employee
     *            POJO模型
     * @return  受影响行数
     * @date 2020/2/26 17:39:34
     */
    @Insert("INSERT INTO employee (`name`, `age`, `gender`, `motto`, `birthday`, `hobby`) "
            + " VALUES(#{u.name},#{u.age},#{u.gender},#{u.motto},#{u.birthday},#{u.hobby})")
    int insertOne(@Param("u") Employee employee);

}