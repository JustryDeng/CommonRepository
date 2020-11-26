//package com.niantou.testcontainer.redis.jedis4cluster.test;
//
//import com.niantou.testcontainer.author.JustryDeng;
//import com.niantou.testcontainer.redis.jedis4cluster.RedisClusterEnvSupport;
//import com.niantou.testcontainer.redis.jedis4cluster.helper.JedisClusterHelper4Test;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.jedis.JedisClusterConnection;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.util.Assert;
//import redis.clients.jedis.JedisCluster;
//
///**
// * 测试
// *
// * @author {@link JustryDeng}
// * @since 2020/11/13 16:36:21
// */
//@Slf4j
//@SpringBootTest
//public class RedisClusterTest extends RedisClusterEnvSupport {
//
//    @Autowired
//    RedisTemplate<Object, Object> redisTemplate;
//
//    /**
//     * 是否已经处理了连接信息
//     * <p>
//     * 注: 由于这里是由test-container引入的redis-集群环境支持， 所以这里为了兼容，得这么处理一下
//     */
//    private boolean hasRedirectAddress2Localhost;
//
//    @BeforeEach
//    void initJedisCluster() {
//        if (hasRedirectAddress2Localhost) {
//            return;
//        }
//        synchronized (this) {
//            if (hasRedirectAddress2Localhost) {
//                return;
//            }
//            RedisConnectionFactory redisConnectionFactory = redisTemplate.getConnectionFactory();
//            Assert.notNull(redisConnectionFactory, "redisConnectionFactory cannot be null");
//            JedisClusterConnection jedisClusterConnection = (JedisClusterConnection)redisConnectionFactory.getClusterConnection();
//            JedisCluster jedisCluster = jedisClusterConnection.getNativeConnection();
//            // 将connectionHandler中对redis的节点连接地址指向localhost，使之能与test-containers关联的docker通信，否者无法直接通信
//            JedisClusterHelper4Test.handleNodeAddress2Localhost(jedisCluster);
//            hasRedirectAddress2Localhost = true;
//        }
//    }
//
//    @Test
//    void one() {
//        System.err.println(redisTemplate.opsForValue().get("k123"));
//        redisTemplate.opsForValue().set("k123", "v123");
//        System.err.println(redisTemplate.opsForValue().get("k123"));
//    }
//
//}
