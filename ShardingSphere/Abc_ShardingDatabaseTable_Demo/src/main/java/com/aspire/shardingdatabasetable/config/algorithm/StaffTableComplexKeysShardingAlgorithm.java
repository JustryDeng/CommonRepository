package com.aspire.shardingdatabasetable.config.algorithm;

import io.shardingjdbc.core.api.algorithm.sharding.ListShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.ShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.complex.ComplexKeysShardingAlgorithm;

import java.util.*;

/**
 * 自定义符合分片策略
 *
 * 用于处理使用多键作为分片键进行分片的场景，包含多个分片键的逻辑较复杂，需要应用开发者自行
 * 处理其中的复杂度。需要配合ComplexShardingStrategy使用。
 *
 * @author JustryDeng
 * @date 2019/5/31 10:11
 */
public class StaffTableComplexKeysShardingAlgorithm implements ComplexKeysShardingAlgorithm {

    /**
     * Sharding.
     *
     * @param availableTargetNames
     *            可用的数据源(库)名称集合 或 可用的真实表名称集合。
     *
     *            提示:数据库源一旦定下来了，那么对应有哪些可用的真实表也就随之定下来了。
     *
     *            注:这里为: 数据源(库)名称集合。
     *               如，本人此次示例中，设置了的数据源有otherDb、youngerDb、olderDb，
     *               那么availableTargetNames输出就应为[otherDb, youngerDb, olderDb]
     *
     *            注:因为是先路由定位库，再路由定位真实表;所以一旦库定下来了，那么真实表的集合就定下来了。
     *               以本人的此项目的库与表进行说明:
     *               otherDb库只有表staff.
     *               youngerDb库有表staff_man。staff_woman.
     *               olderDb库有表staff_man。staff_woman.
     *               在进行表分片之前会先进性数据库(数据源)分片，在数据源分片时，就路由定下来了走
     *               otherDb的话，那么这里定位真实表时，候选的真实表集合里只有staff；如果
     *               在数据源分片时，路由定下来了走youngerDb的话，那么这里定位真实表时，候选的
     *               真实表集合里只有staff_man和staff_woman；
     *
     * @param shardingValues
     *            分片键的值。
     *            如:本人此次示例中，要路由到那个数据库源，是由age为数据源分片键的，age的列类型为int,
     *               所以这里的泛型是Integer。
     *               如果分片键的类型是bigint的话，这里的泛型就应该是Long.
     *               如果分片键的类型是varchar/char的话，这里的泛型就应该是String.
     *            注: ComplexKeysShardingAlgorithm算法，ShardingValue接口的实现是ListShardingValue。
     *                本人这里，shardingValues的toString形如
     *            shardingValues:[
     *                            ListShardingValue(logicTableName=staff, columnName=age, values=[21]),
     *                            ListShardingValue(logicTableName=staff, columnName=gender, values=[女])
     *                            ]
     *
     * @return 路由后的SQL要使用的数据源(库)名字的集合   或   路由后的SQL要使用的真实表名字的集合
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames,
                                         Collection<ShardingValue> shardingValues) {
        List<String> list = new ArrayList<>(4);
        ListShardingValue listShardingValue;
        for (ShardingValue item : shardingValues) {
            // 将item拆箱转换为listShardingValue
            listShardingValue = (ListShardingValue)item;
            System.out.println("逻辑表:" + listShardingValue.getLogicTableName());
            System.out.println("列名:" + listShardingValue.getColumnName());
            System.out.println("改列的值:" + listShardingValue.getValues());
        }

        return list;
    }
}