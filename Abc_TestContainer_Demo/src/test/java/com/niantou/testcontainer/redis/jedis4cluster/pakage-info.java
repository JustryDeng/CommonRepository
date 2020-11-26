package com.niantou.testcontainer.redis.jedis4cluster;


/**
 * 高版本的spring-boot都是使用的lettuce而不使用jedis了。
 *
 * 我们在{@link com.niantou.testcontainer.redis.RedisClusterEnvSupport}的redis-cluster支持，也是基于lettuce的，
 * 那如果想支持jedis操作redis-cluster呢， 应该怎样使用test-containers生成呢, 这个包就作实现演示
 *
 * P.S. 为了避免编译报错，现在把相关代码和pom注释掉了的，如果需要测试使用jedis操作redis-cluster，那么请把相关代码和pom中的对应依赖放开即可。
 */