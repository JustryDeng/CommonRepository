package com.szlaozicl.eventlistener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义线程池Executor。
 *
 * 注: 我们常用的ExecutorService就继承自Executor。
 *
 * 注:关于线程池的各个参数的介绍、各个参数的关系，可详见<linked>https://blog.csdn.net/justry_deng/article/details/89331199</linked>
 *
 * @author JustryDeng
 * @date 2019/11/25 11:05
 */
@Configuration
public class SyncExecutor {

    /** 核心线程数 */
    private static final int CORE_POOL_SIZE = 5;

    /** 最大线程数 */
    private static final int MAX_POOL_SIZE = 100;

    /** 阻塞队列容量 */
    private static final int QUEUE_CAPACITY = 20;

    @Bean
    public Executor myAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setThreadNamePrefix("JustryDeng-Executor-");
        // 设置，当任务满额时将新任务(如果有的话)，打回到原线程去执行。
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}