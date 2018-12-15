package com.aspire.curator.api;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

/**
 * Curator客户端框架 获取权限节点数据
 *
 * @author JustryDeng
 * @date 2018/11/22 17:24
 */
public class GetDataAuthDemo {

    /** 重试策略:重试间隔时间为1000ms; 最多重试3次; */
    private static RetryPolicy retryPolicy = new RetryNTimes(3, 1000);

    public static void main(String[] args) throws Exception {
        // 说明:最后一个参数retryPolicy为重试策略;zookeeper客户端的某些操作可能会失败，我们可以对那些失败的操作进行重试
        CuratorFramework client = CuratorFrameworkFactory
                .builder()
                .connectString("10.8.109.60:2181")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                // 当前客户端的ip不在节点锁设置的白名单的话，那么必须通过digest认证了;这里将用户名密码放入Session中，才能认证通过
                .authorization("digest","userOne:123".getBytes())
                .build();
        // 启动客户端
        client.start();

        // 获取节点的数据
        byte[] data = client.getData().forPath("/auth_node");
        System.out.println("节点“/auth_node”的数据内容为:" + new String(data));
    }

}
