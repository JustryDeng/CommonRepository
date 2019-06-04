package com.aspire.shardingdatabasetable.config.algorithm;

import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

/**
 * 数据源分片策略
 *
 * 注:本人这里采用的是【精确分片算法PreciseShardingAlgorithm】，类似的算法还有
 *    【范围分片算法】
 *    【复合分片算法】
 *    【Hint分片算法】
 *
 * @author JustryDeng
 * @date 2019/5/31 10:11
 */
public class DatabaseShardingAlgorithm implements PreciseShardingAlgorithm<Integer> {

    /**
     * Sharding.
     *
     * @param availableTargetNames
     *            数据源(库)名称集合 或 真实表名称集合。
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
     * @param shardingValue
     *            分片键的值。
     *            如:本人此次示例中，要路由到那个数据库源，是由age为数据源分片键的，age的列类型为int,
     *               所以这里的泛型是Integer。
     *               如果分片键的类型是bigint的话，这里的泛型就应该是Long.
     *               如果分片键的类型是varchar/char的话，这里的泛型就应该是String.
     *
     * @return 路由后的SQL要使用的数据源(库)的名字   或   路由后的SQL要使用的真实表的名字
     *         注:这里为: 路由后的SQL要使用的数据源(库)的名字。
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames,
                             PreciseShardingValue<Integer> shardingValue) {
        double value = shardingValue.getValue() * 1.0 / 50;
        // 年龄在[0, 50)之间的，入youngerDb库
        if (value < 1) {
            return "youngerDb";
        // 年龄在[50, 100)之间的，入olderDb库
        } else if (value < 2) {
            return "olderDb";
        // 年龄在>=100的，入otherDb库
        } else {
            return "otherDb";
        }

        /// 注:如果数据源的名称有规律的话(P.S.该名称是我们自己起的，当然可以起得很有规律)，
        ///    也可以动态将 分片建的值 与 对应 数据源名称关联起来，如
        // for (String each : availableTargetNames) {
        //
        //     if (each.endsWith(shardingValue.getValue() % 2 + "")) {
        //         return each;
        //     }
        // }
        // throw new UnsupportedOperationException();
    }
}
