package com.aspire.mapper;

import com.aspire.model.ProcedureModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * mapper注解
 *
 * 说明:灵活运用@Select等注解即可
 *
 *
 * @author JustryDeng
 * @date 2019/1/25 21:10
 */
@Mapper
public interface JustryDengMapper {

    /**
     * mysql连接通畅性 测试
     *
     * @return  数据map
     * @date 2019/1/25 21:17
     */
    @Select("SELECT e_gender,e_age,id,e_name FROM employee WHERE id=3")
    @Options(statementType = StatementType.CALLABLE)
    LinkedHashMap<String, Object> testMysqlConnection();

    /**
     * 查询 存储过程/函数 是否存在
     *
     * 注:mysql.proc记录的是所有数据库中的存储过程名，查询时最好指定数据库名;
     *    当然如果能保证所有数据库中的存储过程的唯一性的话，也可以不指定数据库名
     *
     * @param name
     *            存储过程/函数  的 名字
     * @param database
     *            数据库
     * @param type
     *            类型，其值只能为   PROCEDURE   或者  FUNCTION
     *
     * @return  是否存在
     * @date 2019/1/27 10:16
     */
    @Select("SELECT IF (COUNT(*)=0,FALSE,TRUE) FROM mysql.proc WHERE db=#{database} "
            + " AND `type`=#{type} AND `name`=#{name}")
    boolean procedureOrFunctionIsExist (@Param("name") String name,
                                        @Param("database") String database,
                                        @Param("type") String type);


    /**
     * 创建 存储过程/函数  的SQL语句
     *
     * @param procedureOrFunction
     *            存储过程/函数  的 创建语句
     *
     * @date 2019/1/25 21:17
     */
    @Select("${procedureOrFunction}")
    void createProcedureOrFunction (@Param("procedureOrFunction") String procedureOrFunction);


    /**
     * 调用存储过程(以map作为参数容器)
     *
     * 注:存储过程是将结果，放在OUT/INOUT参数里面，所以这里的返回值用void即可；
     *    就算是用Object接收参数，接受到的也是null
     *
     * 注:就算传入map中原本就没有OUT对应的key(这里即为keyThree),那么执行存
     *    储过程后，也会也会自动put进去
     *
     * @param paramsMap
     *            map容器，容纳 IN 、 OUT 、 INOUT参数
     *
     * @date 2019/1/27 10:35
     */
    @Select("CALL ifElseProcedure ("
            + " #{map.keyOne, mode = IN, jdbcType = NUMERIC}, "
            + " #{map.keyTwo, mode = IN, jdbcType = NUMERIC},"
            + " #{map.keyThree, mode = OUT, jdbcType = VARCHAR}"
            + ")")
    @Options(statementType = StatementType.CALLABLE)
    void callProcedureByMap (@Param("map") Map<String, Object> paramsMap);

    /**
     * 调用存储过程(以对象作为参数容器)
     *
     * 注:存储过程是将结果，放在OUT/INOUT参数里面，所以这里的返回值用void即可；
     *    就算是用Object接收参数，接受到的也是null
     *
     * 注:模型必须有相应的字段，且必须有setter、getter方法
     *
     * @param pm
     *            对象容器，容纳 IN 、 OUT 、 INOUT参数
     *
     * @date 2019/1/27 10:35
     */
    @Select("CALL ifElseProcedure ("
            + " #{paramA, mode = IN, jdbcType = NUMERIC}, "
            + " #{paramB, mode = IN, jdbcType = NUMERIC},"
            + " #{paramC, mode = OUT, jdbcType = VARCHAR}"
            + ")")
    @Options(statementType = StatementType.CALLABLE)
    void callProcedureByModel (ProcedureModel pm);

    /**
     * 调用函数
     *
     * 注:调用函数时的传参方式，与普通增删改查一样，返回值的接收也是一样的
     *
     * @param paramA
     *            函数参数
     * @param paramB
     *            函数参数
     *
     * @return 函数结果
     * @date 2019/1/27 10:35
     */
    @Select("SELECT myFunction(#{a}, #{b})")
    String invokeFunction (@Param("a") Integer paramA, @Param("b") Integer paramB);
}
