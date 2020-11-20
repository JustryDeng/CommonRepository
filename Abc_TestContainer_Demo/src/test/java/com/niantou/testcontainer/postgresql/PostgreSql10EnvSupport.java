package com.niantou.testcontainer.postgresql;

import com.niantou.testcontainer.ExcludedAllAutoConfiguration;
import com.niantou.testcontainer.author.JustryDeng;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * PostgreSql10环境支持
 *
 * @author {@link JustryDeng}
 * @since 2020/11/17 21:14:29
 */
@Slf4j
@ContextConfiguration(initializers = PostgreSql10EnvSupport.Initializer.class)
@Import(value = {ExcludedAllAutoConfiguration.class, DataSourceAutoConfiguration.class})
public class PostgreSql10EnvSupport implements PostgreSqlEnvSupport {
    
    /** 
     * 标准的docker镜像(即${镜像名}:${tag名})
     * <p>
     * 提示: image&tag可去https://hub.docker.com/u/library搜索
     */
    private static final String DOCKER_IMAGE_NAME = "postgres:10.15";
    
    /** 数据库 */
    private static final String DATABASE = "mine_database";
    
    /** 连接池类型 */
    private static final Class<?> POOL_TYPE_CLASS = HikariDataSource.class;
    
    @ClassRule
    public static PostgreSQLContainer<?> pgSqlContainer = new PostgreSQLContainer<>(DockerImageName.parse(DOCKER_IMAGE_NAME))
            .withDatabaseName(DATABASE)
            // 初始化脚本
            .withInitScript("postgresql/init_postgresql.sql");
    
    /**
     * init application context
     *
     * @author {@link JustryDeng}
     * @since 2020/11/14 19:22:23
     */
    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(@SuppressWarnings("NullableProblems") ConfigurableApplicationContext configurableApplicationContext) {
            // start
            pgSqlContainer.start();
            
            System.setProperty("spring.datasource.url", pgSqlContainer.getJdbcUrl());
            System.setProperty("spring.datasource.username", pgSqlContainer.getUsername());
            System.setProperty("spring.datasource.password", pgSqlContainer.getPassword());
            System.setProperty("spring.datasource.driver-class-name", pgSqlContainer.getDriverClassName());
            System.setProperty("spring.datasource.type", POOL_TYPE_CLASS.getName());
        }
    }
    
}
