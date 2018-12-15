package com.aspire.curator.api;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.retry.RetryUntilElapsed;

import java.util.concurrent.TimeUnit;

/**
 * Curator客户端框架 创建Session
 *
 * @author JustryDeng
 * @date 2018/11/22 17:24
 */
public class CreateSessionDemo {

    /** 重试策略One:重试间隔基础时间为1000ms，随着重试的次数增大，时间间隔会逐渐增大; 最多重试3次; */
    private static RetryPolicy retryPolicyOne = new ExponentialBackoffRetry(1000, 3);

    /** 重试策略Two:重试间隔时间为1000ms; 最多重试3次; */
    private static RetryPolicy retryPolicyTwo = new RetryNTimes(3, 1000);

    /** 重试策略Three:重试间隔时间为500ms; 重试总时间不得10000ms */
    private static RetryPolicy retryPolicyThree = new RetryUntilElapsed(10000, 500);

    public static void main(String[] args) throws InterruptedException {
        createSessionFirstWay();
        TimeUnit.SECONDS.sleep(30);
    }

    public static void createSessionFirstWay() {
        // 说明:最后一个参数retryPolicy为重试策略;zookeeper客户端的某些操作可能会失败，我们可以对那些失败的操作进行重试
        CuratorFramework client = CuratorFrameworkFactory.newClient("10.8.109.60:2181", 5000, 5000, retryPolicyOne);
        // 启动客户端
        client.start();
    }

    public static void createSessionSecondWay() {
        // 说明:最后一个参数retryPolicy为重试策略;zookeeper客户端的某些操作可能会失败，我们可以对那些失败的操作进行重试
        CuratorFramework client = CuratorFrameworkFactory
                .builder()
                .connectString("10.8.109.60:2181")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicyThree)
                .build();
        // 启动客户端
        client.start();
    }
}
