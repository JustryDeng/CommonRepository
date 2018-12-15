package com.aspire.api;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 异步获取子节点 示例
 *
 * @author JustryDeng
 * @date 2018/11/21 17:47
 */
public class GetChildrenASyncDemo implements Watcher {

    private static ZooKeeper zooKeeper;

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) {
            // 两个参数分别是: 要获取的节点、 是否对该节点进行监听、 Children2Callback接口实例、 上下文数据
            // 注:此上下文数据会传递到  Children2Callback接口实例的processResult方法里
            zooKeeper.getChildren("/", false, new MyChildren2Callback(), "我是上下文数据");
        }
    }

    /**
     * Children2Callback实现
     */
    private static class MyChildren2Callback implements AsyncCallback.Children2Callback{

        /**
         * @param rc
         *            create操作的结果码
         * @param path
         *            的节点 路径， 如: "/abc"
         * @param ctx
         *            一步创建时，传递的上下文
         * @param children
         *            获取到的子节点
         * @param stat
         *            状态信息
         */
        @Override
        public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
            System.out.println("进入MyChildren2Callback回调！");
            StringBuilder sb = new StringBuilder(32);
            sb.append("rc = ").append(rc).append("\n");
            sb.append("path = ").append(path).append("\n");
            sb.append("ctx = ").append(ctx).append("\n");
            sb.append("children = ").append(children).append("\n");
            sb.append("stat = ").append(stat);
            System.out.println(sb.toString());
        }
    }

    /**
     * Session创建成功后，会被监视到，走Watcher.process方法
     *
     * @date 2018/11/21 18:07
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper("10.8.109.60:2181", 5000, new GetChildrenASyncDemo());
        // 由于连接ZooKeeper后(即:创建Session后),会话是被一个异步线程所维护的
        // 所以为了能等待其他线程输出结果，我们要让主线程阻塞一段时间
        TimeUnit.SECONDS.sleep(10);
    }
}
