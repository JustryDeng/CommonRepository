package com.aspire.curator.api;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.data.Stat;

/**
 * Curator客户端框架 获取节点数据
 *
 * @author JustryDeng
 * @date 2018/11/22 17:24
 */
public class GetDataDemo {

    /** 重试策略:重试间隔时间为1000ms; 最多重试3次; */
    private static RetryPolicy retryPolicy = new RetryNTimes(3, 1000);

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("10.8.109.60:2181",5000,
                5000, retryPolicy);
        // 启动客户端
        client.start();

        // 获取节点的数据
        // byte[] data = client.getData().forPath("/");

        // 获取节点的数据的同时，并将节点状态信息放入传入的stat实例中
        Stat stat = new Stat();
        byte[] data = client.getData().storingStatIn(stat).forPath("/");
        System.out.println("节点“/”的数据内容为:" + new String(data));
        System.out.println("节点“/”的stat状态信息为:" + stat);
    }

}
