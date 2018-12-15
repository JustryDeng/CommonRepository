package com.aspire.api;

import org.apache.zookeeper.*;
import org.apache.zookeeper.AsyncCallback.StringCallback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 异步创建节点 示例
 *
 * @author JustryDeng
 * @date 2018/11/21 17:47
 */
public class CreateNodeASyncDemo implements Watcher {

    private static ZooKeeper zooKeeper;

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) {
            // 六个参数分别是: 节点全路径、 节点数据、 ACL权限策略、节点类型 、 StringCallback接口实现、 上下文
            // 注:此上下文会传递到  StringCallback接口实例的processResult方法里
            zooKeeper.create("/java_node_1", "我是data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT , new MyStringCallback(), "我是传输的数据!");
        }
    }


    /**
     * StringCallback实现
     */
    private static class MyStringCallback implements StringCallback{

        /**
         * @param rc
         *            create操作的结果码
         * @param path
         *            我们要创建的节点 路径， 如: "/abc"
         * @param ctx
         *            一步创建时，传递的上下文
         * @param name
         *            最终创建了的 节点路径; 如: "/abc10000000001" 当创建的是有序节点时，path与name不一致
         */
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            System.out.println("进入MyStringCallback回调！");
            StringBuilder sb = new StringBuilder(32);
            sb.append("rc = ").append(rc).append("\n");
            sb.append("path = ").append(path).append("\n");
            sb.append("ctx = ").append(ctx).append("\n");
            sb.append("name = ").append(name);
            System.out.println(sb.toString());
        }
    }

    /**
     * Session创建成功后，会被监视到，走Watcher.process方法
     *
     * @date 2018/11/21 18:07
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper("10.8.109.60:2181", 5000, new CreateNodeASyncDemo());
        // 由于连接ZooKeeper后(即:创建Session后),会话是被一个异步线程所维护的
        // 所以为了能等待其他线程输出结果，我们要让主线程阻塞一段时间
        TimeUnit.SECONDS.sleep(10);
    }
}