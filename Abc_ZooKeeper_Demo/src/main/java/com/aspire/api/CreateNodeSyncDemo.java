package com.aspire.api;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 同步创建节点 示例
 *
 * @author JustryDeng
 * @date 2018/11/21 17:47
 */
public class CreateNodeSyncDemo implements Watcher {

    private static ZooKeeper zooKeeper;

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) {
            try {
                doSomeThing();
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void doSomeThing() throws KeeperException, InterruptedException {
        // 四个参数分别是: 节点全路径、 节点数据、 ACL权限策略、节点类型
        // 返回新节点路径
        String newNodePath = zooKeeper.create("/java_node", "我是data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("新节点路径为: " + newNodePath);
    }

    /**
     * Session创建成功后，会被监视到，走Watcher.process方法
     *
     * @date 2018/11/21 18:07
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper("10.8.109.60:2181", 5000, new CreateNodeSyncDemo());
        // 由于连接ZooKeeper后(即:创建Session后),会话是被一个异步线程所维护的
        // 所以为了能等待其他线程输出结果，我们要让主线程阻塞一段时间
        TimeUnit.SECONDS.sleep(10);
    }
}