package com.aspire.ssm;

import com.aspire.ssm.mapper.SqlTestMapper;
import com.aspire.ssm.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class SsmApplicationTests {

    @Autowired
    private SqlTestMapper mapper;

    @Test
    public void testOne() {
        List<Employee> list = mapper.selectAll_xml();
        list.forEach(System.err::println);
    }


    @Test
    public void testTwo() {
        List<Employee> list = mapper.selectListByName_xml("JustryDeng");
        list.forEach(System.err::println);
    }

    @Test
    public void testThree() {
        Employee e = Employee.builder()
                .name("JustryDeng")
                .gender("男")
                .build();
        List<Employee> list = mapper.selectList_xml(e);
        list.forEach(System.err::println);
    }

    @Test
    public void testFour() {
        List<Employee> list = mapper.selectAll_anno();
        list.forEach(System.err::println);
    }

    @Test
    public void testFive() {
        List<Employee> list = mapper.selectListByName_anno("JustryDeng");
        list.forEach(System.err::println);
    }

    @Test
    public void testSix() {
        Employee e = Employee.builder()
                .name("JustryDeng")
                .age(25)
                .gender("男")
                .build();
        List<Employee> list = mapper.selectList_anno(e);
        list.forEach(System.err::println);
    }

}
