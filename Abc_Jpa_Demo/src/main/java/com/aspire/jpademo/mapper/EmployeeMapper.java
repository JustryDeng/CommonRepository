package com.aspire.jpademo.mapper;

import com.aspire.jpademo.model.EmployeePO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 直接继承JpaRepository<T, ID>接口即可，其中T代表对应的实体，ID为该表主键对应的字段的数据类型
 *
 * 注:JpaRepository中默认提供了十几种 增、删、改、查(T对应的)表的方法
 *
 * 注:JPA的实现框架会自动将此接口的实现注入到Spring上下文容器中， 我们使用时直接从Spring中获取即可
 *
 * @author JustryDeng
 * @date 2019/4/22 21:14
 */
public interface EmployeeMapper extends JpaRepository<EmployeePO, Long> {
}