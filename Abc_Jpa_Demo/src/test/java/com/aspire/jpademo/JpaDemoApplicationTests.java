package com.aspire.jpademo;

import com.aspire.jpademo.mapper.EmployeeMapper;
import com.aspire.jpademo.model.EmployeePO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaDemoApplicationTests {

    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 增
     */
    @Test
    public void insertTest() {
        // 注:如果在增数据时，指定了主键，且数据库中已经存在有该主键对应的数据了，那么执行后，原数据会被覆盖
        EmployeePO employeePO = EmployeePO.builder().name("张三").age(25).motto("我是座右铭").build();
        employeeMapper.save(employeePO);
    }

    /**
     * 删
     */
    @Test
    public void deleteTest() {
        // 将要删除的数据 的 id封装进模型中，然后调用 删除方法即可
        // 注: 删除是以键为准进行删除的， 即:不论模型中的其他属性是否与表中数据对应的
        //     上，只要主键对应的上，那么就会删除表中对应行的数据
        EmployeePO employeePO = EmployeePO.builder().id(130L).build();
        employeeMapper.delete(employeePO);
    }

    /**
     * 改 (通过覆盖式新增的方式，实现改)
     */
    @Test
    public void updateTest() {
        // 如果存在， 则进行修改
        boolean exist = employeeMapper.existsById(14L);
        if (exist) {
            // 通过覆盖式新增的方式，实现改
            EmployeePO employeePO = EmployeePO.builder().id(14L).name("邓沙利文").build();
            employeeMapper.save(employeePO);
        }
    }

    /**
     * 查
     */
    @Test
    public void selectTest() {
        List<EmployeePO> list = employeeMapper.findAll();
        System.out.println(list);
    }
}
