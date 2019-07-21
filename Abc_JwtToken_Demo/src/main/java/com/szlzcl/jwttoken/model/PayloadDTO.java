package com.szlzcl.jwttoken.model;

import lombok.*;

import java.io.Serializable;

/**
 * JWT中间部分 有效负载 数据模型
 *
 * @author JustryDeng
 * @date 2019/7/21 15:31
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayloadDTO implements Serializable {

    private static final long serialVersionUID = 5988619597830511341L;

    /**
     * ------------------------------------这部分为 注册声明------------------------------------
     * 这个jwt的身份id
     */
    private String jti;

    /** 签发人 */
    private String iss;

    /** 过期时长(单位ms) */
    private Long exp;

    /** 主题 */
    private String sub;

    /** 受众 */
    private String aud;

    /** 生效时间（1970年1月1日到现在的偏移量） */
    private Long nbf;

    /** 签发时间（1970年1月1日到现在的偏移量） */
    private Long iat;

    /**
     * ------------------------------------这部分为 公开声明------------------------------------
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * ------------------------------------这部分为 私有声明------------------------------------
     * 是否是管理员
     */
    private Boolean isAdmin;
}
