package com.aspire.api;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 异步删除节点 示例
 *
 * @author JustryDeng
 * @date 2018/11/21 17:47
 */
public class DeleteNodeASyncDemo implements Watcher {

    private static ZooKeeper zooKeeper;

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) {
            // 两个参数分别是: 节点全路径、 要校验的版本信息(-1表示不校验版本)、 VoidgCallback接口实现、 上下文
            // 注:此上下文会传递到  VoidgCallback接口实例的processResult方法里
            zooKeeper.delete("/java_node", -1, new MyVoidCallback(), "我是上下文数据");
        }
    }

    /**
     * VoidCallback实现
     */
    private static class MyVoidCallback implements AsyncCallback.VoidCallback {

        /**
         * @param rc
         *            create操作的结果码
         * @param path
         *            我们要创建的节点 路径， 如: "/abc"
         * @param ctx
         *            一步创建时，传递的上下文
         */
        @Override
        public void processResult(int rc, String path, Object ctx) {
            System.out.println("进入MyVoidCallback回调！");
            StringBuilder sb = new StringBuilder(16);
            sb.append("rc = ").append(rc).append("\n");
            sb.append("path = ").append(path).append("\n");
            sb.append("ctx = ").append(ctx);
            System.out.println(sb.toString());
        }
    }

    /**
     * Session创建成功后，会被监视到，走Watcher.process方法
     *
     * @date 2018/11/21 18:07
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper("10.8.109.60:2181", 5000, new DeleteNodeASyncDemo());
        // 由于连接ZooKeeper后(即:创建Session后),会话是被一个异步线程所维护的
        // 所以为了能等待其他线程输出结果，我们要让主线程阻塞一段时间
        TimeUnit.SECONDS.sleep(10);
    }
}
