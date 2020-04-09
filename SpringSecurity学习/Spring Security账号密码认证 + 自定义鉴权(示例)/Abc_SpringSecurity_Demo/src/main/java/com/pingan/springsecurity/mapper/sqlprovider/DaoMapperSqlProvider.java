package com.pingan.springsecurity.mapper.sqlprovider;

import com.pingan.springsecurity.mapper.DaoMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.util.Assert;

import java.util.List;

/**
 * provider
 *
 * @author JustryDeng
 * @date 2020/4/8 16:31:02
 */
@SuppressWarnings("unused")
public class DaoMapperSqlProvider {

    /**
     * {@link DaoMapper#selectApiResourcesByRoleIds}方法sql的Provider
     */
    public String selectPermissionsByRoleIdsProvider(@Param("roleIds") List<Integer> roleIds) {
        Assert.notEmpty(roleIds, "roleIds cannot be empty!");
        StringBuilder sb = new StringBuilder(16);
        sb.append("SELECT ar.id,ar.pid,ar.`name`,ar.path,ar.request_method AS requestMethod,")
          .append(" ar.description FROM `api_resource` AS ar INNER JOIN ")
          .append(" mid_role_api_resource AS mrar ON ar.id=mrar.api_resource_id ")
          .append(" WHERE mrar.role_id IN (");
        for (int i = 0; i < roleIds.size(); i++) {
            sb.append(" #{roleIds[").append(i).append("]}");
            if (i < roleIds.size() - 1) {
                sb.append(",");
            }
        }
        sb.append(" )");
        return sb.toString();
    }
}
