package com.aspire.api;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 同步获取子节点 示例
 *
 * @author JustryDeng
 * @date 2018/11/21 17:47
 */
public class GetChildrenSyncDemo implements Watcher {

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
        // 两个参数分别是: 要获取的节点、 是否对该节点进行监听
        // 返回子节点路径集合
        List<String> childrenList = zooKeeper.getChildren("/", false);
        System.out.println("子节点路径有:" + childrenList);
    }

    /**
     * Session创建成功后，会被监视到，走Watcher.process方法
     *
     * @date 2018/11/21 18:07
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper("10.8.109.60:2181", 5000, new GetChildrenSyncDemo());
        // 由于连接ZooKeeper后(即:创建Session后),会话是被一个异步线程所维护的
        // 所以为了能等待其他线程输出结果，我们要让主线程阻塞一段时间
        TimeUnit.SECONDS.sleep(10);
    }
}