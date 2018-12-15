package com.aspire.api;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 同步创建节点时，设置ACL权限 示例
 *
 * @author JustryDeng
 * @date 2018/11/21 17:47
 */
public class CreateNodeWithACLDemo implements Watcher {

    private static ZooKeeper zooKeeper;

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) {
            try {
                doSomeThing();
            } catch (KeeperException | InterruptedException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }

    private void doSomeThing() throws KeeperException, InterruptedException, NoSuchAlgorithmException {
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

        // 四个参数分别是: 节点全路径、 节点数据、 ACL权限策略、节点类型
        // 返回新节点路径
        String newNodePath = zooKeeper.create("/acl_demo_node", "我是此临时节点的数据".getBytes(), acls, CreateMode.PERSISTENT);
        System.out.println("新节点路径为: " + newNodePath);
    }

    /**
     * Session创建成功后，会被监视到，走Watcher.process方法
     *
     * @date 2018/11/21 18:07
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper("10.8.109.60:2181", 5000, new CreateNodeWithACLDemo());
        // 由于连接ZooKeeper后(即:创建Session后),会话是被一个异步线程所维护的
        // 所以为了能等待其他线程输出结果，我们要让主线程阻塞一段时间
        TimeUnit.SECONDS.sleep(10);
    }
}
