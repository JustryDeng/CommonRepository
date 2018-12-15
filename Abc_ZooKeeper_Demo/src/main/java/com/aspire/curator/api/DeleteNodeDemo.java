package com.aspire.curator.api;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

/**
 * Curator客户端框架 删除节点
 *
 * @author JustryDeng
 * @date 2018/11/22 17:24
 */
public class DeleteNodeDemo {

    /** 重试策略:重试间隔时间为1000ms; 最多重试3次; */
    private static RetryPolicy retryPolicy = new RetryNTimes(3, 1000);

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("10.8.109.60:2181",5000,
                5000,retryPolicy);
        // 启动客户端
        client.start();

        // 删除(无子节点的)节点
        //client.delete().forPath("/a_node");
        // 删除指定版本的(无子节点的)节点
        //client.delete().withVersion(4).forPath("/a_node");
        // 删除节点(如果有子节点，也一并删除)
        client.delete().deletingChildrenIfNeeded().forPath("/a_node");
        // 删除指定版本的节点(如果有子节点，也一并删除)
        //client.delete().deletingChildrenIfNeeded().withVersion(4).forPath("/a_node");
        // 只要Session有效，那么就会对指定节点进行持续删除，知道删除成功; 这是一种保障机制
        //client.delete().guaranteed().deletingChildrenIfNeeded().withVersion(4).forPath("/a_node");
    }

}
