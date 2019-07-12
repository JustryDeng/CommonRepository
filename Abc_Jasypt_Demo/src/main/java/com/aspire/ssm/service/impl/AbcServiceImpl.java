package com.aspire.ssm.service.impl;

import com.aspire.ssm.mapper.AbcMapper;
import com.aspire.ssm.model.User;
import com.aspire.ssm.service.AbcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 业务逻辑层
 *
 * @author JustryDeng
 * @date 2019/5/29 17:35
 */
@Slf4j
@Service
public class AbcServiceImpl implements AbcService {

    private final AbcMapper abcMapper;

    public AbcServiceImpl(AbcMapper abcMapper) {
        this.abcMapper = abcMapper;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int insertDemo(User user) {
        log.info("got into AbcServiceImpl -> insertDemo, param is -> {}", user);
        int result = abcMapper.abcInsert(user);
        log.info("result is -> {}", result);
        return result;
    }

}