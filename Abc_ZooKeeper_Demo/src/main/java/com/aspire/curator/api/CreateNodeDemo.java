package com.aspire.curator.api;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;

/**
 * Curator客户端框架 创建节点
 *
 * @author JustryDeng
 * @date 2018/11/22 17:24
 */
public class CreateNodeDemo {

    /** 重试策略:重试间隔时间为1000ms; 最多重试3次; */
    private static RetryPolicy retryPolicy = new RetryNTimes(3, 1000);

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("10.8.109.60:2181",5000,
                5000,retryPolicy);
        // 启动客户端
        client.start();

        // 其中creatingParentsIfNeeded()的作用为:如果要创建的节点的父节点不存在，那么会先创建父节点，再创建该节点
        String newNode = client
                .create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .forPath("/a_node/a_1", "".getBytes());
        System.out.println("新创建的节点是:" + newNode);
    }

}