package com.aspire.api;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 异步获取节点数据 示例
 *
 * @author JustryDeng
 * @date 2018/11/21 17:47
 */
public class GetDataASyncDemo implements Watcher {

    private static ZooKeeper zooKeeper;

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) {
            // 两个参数分别是: 要获取的节点、 是否对该节点进行监听、 StringCallback接口实现、 上下文
            // 注:此上下文会传递到  DataCallback接口实例的processResult方法里
            zooKeeper.getData("/", false, new MyDataCallback(), "我是上下文数据");
        }
    }

    /**
     * Children2Callback实现
     */
    private static class MyDataCallback implements AsyncCallback.DataCallback{

        /**
         * @param rc
         *            create操作的结果码
         * @param path
         *            的节点 路径， 如: "/abc"
         * @param ctx
         *            一步创建时，传递的上下文
         * @param data
         *            获取到的 字节数组 数据
         * @param stat
         *            该节点的状态信息
         */
        @Override
        public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
            System.out.println("进入MyDataCallback回调！");
            StringBuilder sb = new StringBuilder(32);
            sb.append("rc = ").append(rc).append("\n");
            sb.append("path = ").append(path).append("\n");
            sb.append("ctx = ").append(ctx).append("\n");
            sb.append("data = ").append(new String(data)).append("\n");
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
        zooKeeper = new ZooKeeper("10.8.109.60:2181", 5000, new GetDataASyncDemo());
        // 由于连接ZooKeeper后(即:创建Session后),会话是被一个异步线程所维护的
        // 所以为了能等待其他线程输出结果，我们要让主线程阻塞一段时间
        TimeUnit.SECONDS.sleep(10);
    }
}

