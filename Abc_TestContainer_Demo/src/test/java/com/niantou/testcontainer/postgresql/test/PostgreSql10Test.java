package com.niantou.testcontainer.postgresql.test;

import com.niantou.testcontainer.author.JustryDeng;
import com.niantou.testcontainer.postgresql.PostgreSql10EnvSupport;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 测试
 *
 * @author {@link JustryDeng}
 * @since 2020/11/13 16:36:21
 */
@Slf4j
@SpringBootTest
public class PostgreSql10Test extends PostgreSql10EnvSupport {
    
    @Resource
    TestMapper testMapper;
    
    @Test
    void one() {
        System.err.println(testMapper.count());
        System.err.println(testMapper.selectAll());
    }
    
    @Mapper
    public interface TestMapper {
        
        @Select("select count(*) from employee")
        int count();
        
        @Select("select * from employee")
        List<Map<String, Object>> selectAll();
    }
    
}

