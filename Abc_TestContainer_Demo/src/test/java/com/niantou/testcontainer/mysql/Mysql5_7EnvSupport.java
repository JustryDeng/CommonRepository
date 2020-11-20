package com.niantou.testcontainer.mysql;

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
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Mysql5.7环境支持
 *
 * @author {@link JustryDeng}
 * @since 2020/11/17 14:14:43
 */
@Slf4j
@ContextConfiguration(initializers = Mysql5_7EnvSupport.Initializer.class)
@Import(value = {ExcludedAllAutoConfiguration.class, DataSourceAutoConfiguration.class})
public class Mysql5_7EnvSupport implements MysqlEnvSupport{
    
    /** 
     * 标准的docker镜像(即${镜像名}:${tag名})
     * <p>
     * 提示: image&tag可去https://hub.docker.com/u/library搜索
     */
    private static final String DOCKER_IMAGE_NAME = "mysql:5.7.32";
    
    /** 数据库 */
    private static final String DATABASE = "mine_database";
    
    /** 连接池类型 */
    private static final Class<?> POOL_TYPE_CLASS = HikariDataSource.class;
    
    @ClassRule
    public static MySQLContainer<?> mySqlContainer = new MySQLContainer<>(DockerImageName.parse(DOCKER_IMAGE_NAME))
            .withDatabaseName(DATABASE)
            // 初始化脚本
            .withInitScript("mysql/init_mysql.sql")
            /// 配置文件
            ///.withConfigurationOverride("mysql/config")
            ;
    
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
            mySqlContainer.start();
            
            String jdbcUrl = mySqlContainer.getJdbcUrl();
            int endIndex = jdbcUrl.indexOf("?");
            String additionalSetting = "?characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";
            if (endIndex < 0) {
                jdbcUrl = jdbcUrl + additionalSetting;
            } else {
                jdbcUrl = jdbcUrl.substring(0, endIndex) + additionalSetting;
            }
            System.setProperty("spring.datasource.url", jdbcUrl);
            System.setProperty("spring.datasource.username", mySqlContainer.getUsername());
            System.setProperty("spring.datasource.password", mySqlContainer.getPassword());
            System.setProperty("spring.datasource.driver-class-name", mySqlContainer.getDriverClassName());
            System.setProperty("spring.datasource.type", POOL_TYPE_CLASS.getName());
        }
    }
    
}
