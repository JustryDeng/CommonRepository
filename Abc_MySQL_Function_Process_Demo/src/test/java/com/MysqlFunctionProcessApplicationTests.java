package com;

import com.aspire.mapper.JustryDengMapper;
import com.aspire.model.ProcedureModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MysqlFunctionProcessApplicationTests {

    @Autowired
    JustryDengMapper mapper;

    /**
     * 测试数据库连通性
     *
     * @author JustryDeng
     * @date 2019/1/27 10:24
     */
    @Test
    public void mysqlConnectionTest() {
        System.out.println(mapper.testMysqlConnection());
    }

    /**
     * java程序使用MySQL存储过程示例
     *
     * @author JustryDeng
     * @date 2019/1/27 10:24
     */
    @Test
    public void useProcedureDemo() {
        final String database = "demo";
        final String procedureType = "PROCEDURE";

        // -> 判断存储过程ifElseProcedure是否存在
        boolean exist = mapper.procedureOrFunctionIsExist("ifElseProcedure",database, procedureType);

        // -> 不存在则创建存储过程
        if (!exist) {
            // 注:在java程序中创建MySQL存储过程时，直接写创建指令即可
            String procedureSql = ("CREATE PROCEDURE ifElseProcedure ( IN paramA INT, IN paramB INT, "
                                   + "OUT paramC VARCHAR(40) ) ")
                                  .concat("BEGIN ")
                                  .concat("  IF paramA = 1 THEN set paramC = '参数paramA的值为1'; ")
                                  .concat(" ELSEIF paramA = 2 THEN set paramC = '参数paramA的值为2' ; ")
                                  .concat(" ELSEIF paramA = paramB THEN set  paramC = '参数paramA的值 = 参数paramB的值' ; ")
                                  .concat(" ELSE set  paramC = '参数有误！' ; ")
                                  .concat(" END IF; ")
                                  .concat("END");
            mapper.createProcedureOrFunction(procedureSql);

        }

        // -> 组装参数、(以map为参数容器)调用存储过程
        Map<String, Object> map = new HashMap<>(4);
        map.put("keyOne", 3);
        map.put("keyTwo", 3);
        // 执行调用
        mapper.callProcedureByMap(map);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }

        // -> 组装参数、(以model为参数容器)调用存储过程
        ProcedureModel procedureModel = new ProcedureModel();
        procedureModel.setParamA(4);
        procedureModel.setParamB(5);
        // 执行调用
        mapper.callProcedureByModel(procedureModel);
        System.out.println(procedureModel);
    }

    /**
     * java程序使用MySQL自定义函数示例
     *
     * @author JustryDeng
     * @date 2019/1/27 10:24
     */
    @Test
    public void useFunctionDemo() {
        final String database = "demo";
        final String functionType = "FUNCTION";

        // -> 判断自动以函数myFunction是否存在
        boolean exist = mapper.procedureOrFunctionIsExist("myFunction", database, functionType);

        // -> 不存在则创建函数
        if (!exist) {
            // 注:在java程序中创建函数时，直接写创建指令即可
            String functionSql = "CREATE FUNCTION myFunction(paramA INT, paramB INT) "
                                  .concat("RETURNS VARCHAR(50) ")
                                  .concat("BEGIN")
                                  .concat("  DECLARE diffValue INT;")
                                  .concat("  DECLARE returnValue varchar(20) DEFAULT '';")
                                  .concat("  set diffValue = paramA - paramB;")
                                  .concat("  set returnValue = CONCAT(paramA,' - ', paramB, ' = ', diffValue);")
                                  .concat("  RETURN returnValue;")
                                  .concat("END");
            mapper.createProcedureOrFunction(functionSql);

        }

        // -> 调用函数
        System.out.println(mapper.invokeFunction(321, 123));
    }

}

