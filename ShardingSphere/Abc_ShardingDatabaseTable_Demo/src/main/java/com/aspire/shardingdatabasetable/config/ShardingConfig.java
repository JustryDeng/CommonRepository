package com.aspire.shardingdatabasetable.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.aspire.shardingdatabasetable.config.algorithm.DatabaseShardingAlgorithm;
import com.aspire.shardingdatabasetable.config.algorithm.StaffTableComplexKeysShardingAlgorithm;
import com.aspire.shardingdatabasetable.config.algorithm.StaffTableTableShardingAlgorithm;
import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.ComplexShardingStrategyConfiguration;
import io.shardingjdbc.core.api.config.strategy.StandardShardingStrategyConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 分库分表配置
 *
 * @author JustryDeng
 * @date 2019/5/30 9:54
 */
@Configuration
public class ShardingConfig {

    /**
     * 切片配置
     *
     * @return  数据源
     * @date 2019/5/30 21:04
     */
    @Bean
    public DataSource shardingCustomer() throws SQLException {

        // 第一步: 获取众多数据源
        Map<String, DataSource> dataSourceMap = getDatasourceMap();

        // 第二步: 获取总配置类
        ShardingRuleConfiguration shardingRuleConfig = getShardingRuleConfiguration();

        // 第三步: 获取其余配置信息(如果需要的话)
        Properties properties = getProperties();

        // 第四步: 定制指定逻辑表的切片(分库分表)策略
        List<TableRuleConfiguration> allTableRuleConfiguration = getAllTableRuleConfiguration();

        // 第五步: 将定制了自己的切片策略的表的配置规则，加入总配置中
        shardingRuleConfig.getTableRuleConfigs().addAll(allTableRuleConfiguration);

        // 第六步: 创建并返回sharding总数据源,注入容器
        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig,
                new ConcurrentHashMap<>(16), properties);
    }

    /**
     * 对指定逻辑表进行切片(分库分表)个性化配置
     * 注:本人这里只配置了
     *
     * @return 定制的 指定表的分库分表配置
     * @date 2019/6/4 12:01
     */
    private List<TableRuleConfiguration> getAllTableRuleConfiguration() {
        List<TableRuleConfiguration> list = new ArrayList<>(8);
        // 配置staff表切片规则
        TableRuleConfiguration staffTableRuleConfig = new TableRuleConfiguration();
        // 逻辑表名
        staffTableRuleConfig.setLogicTable("staff");
        /*
         * 真实库表名
         *
         * 注：库与表之间使用【.】分割;
         * 注：库表与库表之间使用【,】分割
         * 下述inline表达式的结果即为 otherDb.staff, youngerDb.staff_man, youngerDb.staff_woman, olderDb.staff_man, olderDb.staff_woman
         */
        staffTableRuleConfig.setActualDataNodes("otherDb.staff, ${['youngerDb', 'olderDb']}.staff_${['man', 'woman']}");
        // 设置这张表的 分库策略(本人这里采用：标准分片策略)
        staffTableRuleConfig.setDatabaseShardingStrategyConfig(
                new StandardShardingStrategyConfiguration("age", DatabaseShardingAlgorithm.class.getName()));
        // 设置这张表的 分表策略(本人这里采用：精确分片算法实现的标准分片策略)
        StandardShardingStrategyConfiguration standardShardingStrategyConfiguration = new StandardShardingStrategyConfiguration(
                "gender", StaffTableTableShardingAlgorithm.class.getName(), null);
        staffTableRuleConfig.setTableShardingStrategyConfig(standardShardingStrategyConfiguration);
        list.add(staffTableRuleConfig);
        return list;
    }

    /**
     * 获取其余配置信息
     *
     * @return 其余配置信息
     * @date 2019/6/4 11:24
     */
    private Properties getProperties() {
        Properties properties = new Properties();
        // 打印出分库路由后的sql
        properties.setProperty("sql.show", "true");
        return properties;
    }
    /**
     * 获取切片总配置类
     *
     * @return 总配置类
     * @date 2019/6/4 11:24
     */
    private ShardingRuleConfiguration getShardingRuleConfiguration() {

        // sharding总配置类
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();

        // 设置全局默认库
        shardingRuleConfig.setDefaultDataSourceName("otherDb");

        // 设置全局默认分库的策略(本人这里采用：精确分片算法实现的标准分片策略)
        // 如果某个表，没有指定分库策略的话，那么会默认使用这个策略;如果某个表制定了自己的策略，那么就会走自己的策略不走这个默认策略
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(
                new StandardShardingStrategyConfiguration("age", DatabaseShardingAlgorithm.class.getName()));

        // 设置全局默认分表的策略(本人这里采用：复合分片策略)
        // 如果某个表，没有指定分表策略的话，那么会默认使用这个策略;如果某个表制定了自己的策略，那么就会走自己的策略不走这个默认策略
        ComplexShardingStrategyConfiguration complexShardingStrategyConfiguration = new ComplexShardingStrategyConfiguration(
                "age, gender", StaffTableComplexKeysShardingAlgorithm.class.getName());
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(complexShardingStrategyConfiguration);
        return shardingRuleConfig;
    }

    /**
     * 获取数据源Map
     *
     * @return  获取数据源Map
     * @date 2019/6/4 10:06
     */
    private Map<String, DataSource> getDatasourceMap() {
        // 真实数据源map
        Map<String, DataSource> dataSourceMap = new HashMap<>(4);

        // 配置第一个数据源,对应库other
        DruidDataSource dataSourceDefault = new DruidDataSource();
        dataSourceDefault.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceDefault.setUrl("jdbc:mysql://localhost:3306/other?characterEncoding=utf8&serverTimezone=GMT%2B8");
        dataSourceDefault.setUsername("root");
        dataSourceDefault.setPassword("dengshuai");
        dataSourceMap.put("otherDb", dataSourceDefault);

        // 配置第二个数据源,对应库younger
        DruidDataSource dataSourceYounger = new DruidDataSource();
        dataSourceYounger.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceYounger.setUrl("jdbc:mysql://localhost:3306/younger?characterEncoding=utf8&serverTimezone=GMT%2B8");
        dataSourceYounger.setUsername("root");
        dataSourceYounger.setPassword("dengshuai");
        dataSourceMap.put("youngerDb", dataSourceYounger);

        // 配置第三个数据源,对应库older
        DruidDataSource dataSourceOlder = new DruidDataSource();
        dataSourceOlder.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceOlder.setUrl("jdbc:mysql://localhost:3306/older?characterEncoding=utf8&serverTimezone=GMT%2B8");
        dataSourceOlder.setUsername("root");
        dataSourceOlder.setPassword("dengshuai");
        dataSourceMap.put("olderDb", dataSourceOlder);
        return dataSourceMap;
    }

}