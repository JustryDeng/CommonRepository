//package com.niantou.testcontainer.redis.jedis4cluster;
//
//import com.niantou.testcontainer.ExcludedAllAutoConfiguration;
//import com.niantou.testcontainer.author.JustryDeng;
//import com.niantou.testcontainer.redis.RedisEnvSupport;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
//import org.springframework.context.ApplicationContextInitializer;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.ContextConfiguration;
//import org.testcontainers.containers.FixedHostPortGenericContainer;
//import org.testcontainers.containers.GenericContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * redis集群环境支持  for  jedis4cluster
// *
// * @author {@link JustryDeng}
// * @since 2020/11/13 16:14:18
// */
//@Slf4j
//@Testcontainers
//@Import(value = {ExcludedAllAutoConfiguration.class, RedisAutoConfiguration.class})
//@ContextConfiguration(initializers = RedisClusterEnvSupport.Initializer.class)
//public class RedisClusterEnvSupport implements RedisEnvSupport {
//
//
//    /**
//     * 标准的docker镜像(即${镜像名}:${tag名})
//     * <p>
//     * 提示: image&tag可去https://hub.docker.com/u/library搜索
//     */
//    private static final String DOCKER_IMAGE_NAME = "grokzen/redis-cluster:6.0.7";
//
//    /**
//     * 集群最好用FixedHostPortGenericContainer， 主动避免端口冲突即可
//     */
//    @Container
//    @SuppressWarnings("deprecation")
//    public static GenericContainer<?> redisContainer = new FixedHostPortGenericContainer<>(DOCKER_IMAGE_NAME)
//            .withFixedExposedPort(7000, 7000)
//            .withFixedExposedPort(7001, 7001)
//            .withFixedExposedPort(7002, 7002)
//            .withFixedExposedPort(7003, 7003)
//            .withFixedExposedPort(7004, 7004)
//            .withFixedExposedPort(7005, 7005);
//
//    /**
//     * init application context
//     *
//     * @author {@link JustryDeng}
//     * @since 2020/11/14 19:22:23
//     */
//    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
//        @Override
//        public void initialize(@SuppressWarnings("NullableProblems") ConfigurableApplicationContext configurableApplicationContext) {
//            String redisIpAddress = redisContainer.getContainerIpAddress();
//            String nodesInfo = redisIpAddress + ":" + 7000
//                    + "," + redisIpAddress + ":" + 7001
//                    + "," + redisIpAddress + ":" + 7002
//                    + "," + redisIpAddress + ":" + 7003
//                    + "," + redisIpAddress + ":" + 7004
//                    + "," + redisIpAddress + ":" + 7005;
//            log.info("spring.redis.cluster.nodes is [{}]", nodesInfo);
//            System.setProperty("spring.redis.cluster.nodes", nodesInfo);
//
//            try {
//                // 睡眠几秒， 给容器足够的时间去初始化相关信息(否则，可能导致环境支持存在缺陷)
//                TimeUnit.SECONDS.sleep(10);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//}