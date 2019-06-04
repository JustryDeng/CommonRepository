package com.aspire.shardingdatabasetable.service.impl;

import com.aspire.shardingdatabasetable.mapper.ShardingMapper;
import com.aspire.shardingdatabasetable.model.StaffPO;
import com.aspire.shardingdatabasetable.service.ShardingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 业务逻辑层
 *
 * @author JustryDeng
 * @date 2019/5/29 17:35
 */
@Slf4j
@Service
public class ShardingServiceImpl implements ShardingService {

    private final ShardingMapper shardingMapper;

    public ShardingServiceImpl(ShardingMapper shardingMapper) {
        this.shardingMapper = shardingMapper;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int insertDemo(StaffPO staffPO) {
        log.info("got into ShardingServiceImpl -> insertDemo, param is -> {}", staffPO);
        return shardingMapper.insertData(staffPO);
    }

    @Override
    public List<StaffPO> queryDemo(Integer age) {
        log.info("got into ShardingServiceImpl -> queryDemo, param is -> {}", age);
        return shardingMapper.queryStaffByAge(age);
    }
}
