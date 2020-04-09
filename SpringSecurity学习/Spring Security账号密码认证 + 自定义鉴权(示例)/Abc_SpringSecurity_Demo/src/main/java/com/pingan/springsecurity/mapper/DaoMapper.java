package com.pingan.springsecurity.mapper;

import com.pingan.springsecurity.mapper.sqlprovider.DaoMapperSqlProvider;
import com.pingan.springsecurity.model.ApiResource;
import com.pingan.springsecurity.model.MyUserDetails;
import com.pingan.springsecurity.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * 数据操作层
 *
 * @author JustryDeng
 * @date 2019/12/14 10:10
 */
@Mapper
public interface DaoMapper {

    /**
     * 根据用户账号名，查询用户基本信息
     *
     * @param accountNo
     *            用户账号名
     * @return  用户基本信息
     * @date 2019/12/14 10:28
     */
    @Select("SELECT id, account_no as accountNo, `name`, `password` FROM "
                + " `user` where account_no=#{accountNo} limit 1")
    MyUserDetails selectUserBasicInfoByAccountNo(@Param("accountNo") String accountNo);

    /**
     * 根据用户id,查询其拥有的角色
     *
     * @param userId
     *            用户id
     * @return  该用户的角色集
     * @date 2019/12/14 10:59
     */
    @Select("SELECT r.id,r.`name`,r.description FROM `role` AS r INNER JOIN mid_user_role "
                + " AS ur ON r.id=ur.role_id WHERE ur.user_id=#{userId}")
    List<Role> selectRolesByUserId(@Param("userId") Integer userId);

    /**
     * 根据角色id集合查询对应的可访问api信息
     *
     * @param roleIds
     *            角色id集合
     * @return  权限集合
     * @date 2019/12/14 11:01
     */
    @SelectProvider(type = DaoMapperSqlProvider.class, method = "selectPermissionsByRoleIdsProvider")
    List<ApiResource> selectApiResourcesByRoleIds(@Param("roleIds") List<Integer> roleIds);

    /**
     * 查询所有需要鉴权的path
     *
     * @return  需要鉴权的path集
     * @date 2020/4/8 18:09:57
     */
    @Select("SELECT path FROM `api_resource` AS ar INNER JOIN mid_role_api_resource AS mrar ON "
                + " ar.id=mrar.api_resource_id WHERE role_id IN (SELECT id "
                    + " FROM role WHERE `name` <> 'ANY')")
    List<String> selectNeedAuthPaths();
}
