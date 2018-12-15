package com.aspire.curator.api;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

import java.util.List;

/**
 * Curator客户端框架 获取子节点
 *
 * @author JustryDeng
 * @date 2018/11/22 17:24
 */
public class GetChildrenDemo {

    /** 重试策略:重试间隔时间为1000ms; 最多重试3次; */
    private static RetryPolicy retryPolicy = new RetryNTimes(3, 1000);

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("10.8.109.60:2181",5000,
                5000, retryPolicy);
        // 启动客户端
        client.start();

        List<String> children = client.getChildren().forPath("/");
        System.out.println("节点“/”的子节点集合:" + children);
    }

}
