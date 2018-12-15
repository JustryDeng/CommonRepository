package com.aspire.api;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 创建Session示例
 *
 * @author JustryDeng
 * @date 2018/11/21 17:47
 */
public class CreateSessionDemo {

    public static void main(String[] args) throws IOException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("10.8.109.60:2181", 5000, new MyWatcher());
        // 由于连接ZooKeeper后(即:创建Session后),会话是被一个异步线程所维护的
        // 所以为了能等待其他线程输出结果，我们要让主线程阻塞一段时间
        TimeUnit.SECONDS.sleep(10);
    }

}

/**
 * 创建Session时，需要一个Watcher接口的实现
 *
 * @date 2018/11/21 17:48
 */
class MyWatcher implements Watcher{

    @Override
    public void process(WatchedEvent event) {
        System.out.println("线程" + Thread.currentThread().getName() + "监听到了:" + event);
    }
}
