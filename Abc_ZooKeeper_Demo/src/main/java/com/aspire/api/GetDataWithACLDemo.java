package com.aspire.api;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 认证权限并获取节点数据 示例
 *
 * @author JustryDeng
 * @date 2018/11/21 17:47
 */
public class GetDataWithACLDemo implements Watcher {

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

        // 将 用户名:密码 加入认证凭证信息
        zooKeeper.addAuthInfo("digest", "userOne:123".getBytes());
        // 两个参数分别是: 要获取的节点、 是否对该节点进行监听、 该节点的State
        // 注:该节点的状态信息会放入 传入的Stat实例里面
        // 返回字节数组
        Stat stat = new Stat();
        byte[] bytes = zooKeeper.getData("/acl_demo_node", false, stat);
        System.out.println(new String(bytes));
        System.out.println(stat.toString());
    }

    /**
     * Session创建成功后，会被监视到，走Watcher.process方法
     *
     * @date 2018/11/21 18:07
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper("10.8.109.60:2181", 5000, new GetDataWithACLDemo());
        // 由于连接ZooKeeper后(即:创建Session后),会话是被一个异步线程所维护的
        // 所以为了能等待其他线程输出结果，我们要让主线程阻塞一段时间
        TimeUnit.SECONDS.sleep(10);
    }
}
