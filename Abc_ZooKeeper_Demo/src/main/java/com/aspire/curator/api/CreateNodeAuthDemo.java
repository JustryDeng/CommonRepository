package com.aspire.curator.api;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Curator客户端框架 创建需要权限的节点
 *
 * @author JustryDeng
 * @date 2018/11/22 17:24
 */
public class CreateNodeAuthDemo {

    /** 重试策略:重试间隔时间为1000ms; 最多重试3次; */
    private static RetryPolicy retryPolicy = new RetryNTimes(3, 1000);

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("10.8.109.60:2181",5000,
                5000,retryPolicy);
        // 启动客户端
        client.start();

        // ID白名单模式,给ip为10.8.109.60的客户端分配所有权限
        ACL aclIdOne = new ACL(ZooDefs.Perms.ALL, new Id("ip", "10.8.109.60"));
        // ID白名单模式,给ip为10.8.109.76的客户端分配 读 的权限
        ACL aclIdTwo = new ACL(ZooDefs.Perms.READ, new Id("ip", "10.8.109.76"));
        // ID白名单模式,给ip为10.8.109.60的客户端分配 读和写 的权限
        ACL aclIdThree = new ACL(ZooDefs.Perms.READ | ZooDefs.Perms.WRITE, new Id("ip", "10.8.109.44"));
        // 用户名密码模式,设置userOne用户,有所有权限
        ACL aclDigestOne = new ACL(ZooDefs.Perms.ALL, new Id("digest", DigestAuthenticationProvider.generateDigest("userOne:123")));
        // 用户名密码模式,设置userTwo用户,有 读 权限
        ACL aclDigestTwo = new ACL(ZooDefs.Perms.READ, new Id("digest", DigestAuthenticationProvider.generateDigest("userTwo:123")));
        // 用户名密码模式,设置userThree用户,有 读和写 权限
        ACL aclDigestThree = new ACL(ZooDefs.Perms.READ | ZooDefs.Perms.WRITE, new Id("digest", DigestAuthenticationProvider.generateDigest("userThree:123")));
        List<ACL> acls = new ArrayList<>(8);
        acls.add(aclIdOne);
        acls.add(aclIdTwo);
        acls.add(aclIdThree);
        acls.add(aclDigestOne);
        acls.add(aclDigestTwo);
        acls.add(aclDigestThree);

        // 其中creatingParentsIfNeeded()的作用为:如果要创建的节点的父节点不存在，那么会先创建父节点，再创建该节点
        String newNode = client
                .create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .withACL(acls)
                .forPath("/auth_node", "".getBytes());
        System.out.println("新创建的节点是:" + newNode);
    }

}
