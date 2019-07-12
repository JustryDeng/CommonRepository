package com.aspire.ssm.service;

import com.aspire.ssm.model.User;

/**
 * Service层
 *
 * @author JustryDeng
 * @date 2019/6/21 16:49
 */
public interface AbcService {

    /**
     * 插入测试
     *
     * @param user
     *            用户模型
     * @return  受影响行数
     * @date 2019/6/21 16:49
     */
    int insertDemo(User user);
}
