package com.aspire.curator.api;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.data.Stat;

/**
 * Curator客户端框架 修改节点数据
 *
 * @author JustryDeng
 * @date 2018/11/22 17:24
 */
public class SetDataDemo {

    /** 重试策略:重试间隔时间为1000ms; 最多重试3次; */
    private static RetryPolicy retryPolicy = new RetryNTimes(3, 1000);

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("10.8.109.60:2181",5000,
                5000, retryPolicy);
        // 启动客户端
        client.start();

        // 设置节点数据
        // client.setData().forPath("/", "我是修改后的节点数据内容".getBytes());

        // 先获取到节点的状态
        Stat statOne = new Stat();
        client.getData().storingStatIn(statOne).forPath("/");
        System.out.println("修改节点“/”的数据后，数据版本为:" + statOne.getVersion());
        // 设置节点数据(校验版本信息)
        Stat statTwo = client.setData().withVersion(statOne.getVersion()).forPath("/", "我是修改后的节点数据内容".getBytes());
        System.out.println("修改节点“/”的数据后，数据版本为:" + statTwo.getVersion());
    }

}
