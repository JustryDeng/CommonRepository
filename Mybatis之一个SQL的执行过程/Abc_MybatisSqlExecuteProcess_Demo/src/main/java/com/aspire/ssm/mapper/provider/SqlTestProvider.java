package com.aspire.ssm.mapper.provider;

import com.aspire.ssm.mapper.SqlTestMapper;
import com.aspire.ssm.model.Employee;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;

/**
 * sql provider
 *
 * @author JustryDeng
 * @date 2020/2/2 11:45
 */
@SuppressWarnings("all")
public class SqlTestProvider {

    /**
     * sql for {@link SqlTestMapper#selectList_anno}
     */
    public String selectListAnnoSql(@Param("employee") Employee employee) {
        StringBuilder sb = new StringBuilder(64);
        sb.append("SELECT `id`,`name`,`age`,`gender`,`motto`,`birthday`,`hobby` FROM `employee`");
        sb.append(" WHERE 1=1 ");
        if (StringUtils.isNotBlank(employee.getName())) {
            sb.append(" AND `name`=#{employee.name}");
        }
        if (employee.getAge() != null) {
            sb.append(" AND `age`=#{employee.age}");
        }
        if (StringUtils.isNotBlank(employee.getGender())) {
            sb.append(" AND `gender`=#{employee.gender}");
        }
        return sb.toString();
    }

    /// ******************* 使用${}的情况
//    /**
//     * sql for {@link SqlTestMapper#selectList_anno}
//     */
//    public String selectListAnnoSql(@Param("employee") Employee employee) {
//        StringBuilder sb = new StringBuilder(64);
//        sb.append("SELECT `id`,`name`,`age`,`gender`,`motto`,`birthday`,`hobby` FROM `employee`");
//        sb.append(" WHERE 1=1 ");
//        if (StringUtils.isNotBlank(employee.getName())) {
//            sb.append(" AND `name`='${employee.name}'");
//        }
//        if (employee.getAge() != null) {
//            sb.append(" AND `age`=${employee.age}");
//        }
//        if (StringUtils.isNotBlank(employee.getGender())) {
//            sb.append(" AND `gender`='${employee.gender}'");
//        }
//        return sb.toString();
//    }
}
