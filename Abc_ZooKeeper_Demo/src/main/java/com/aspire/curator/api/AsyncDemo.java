package com.aspire.curator.api;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.TimeUnit;

/**
 * Curator客户端框架 异步调用示例(以创建节点数据示例)
 *
 * @author JustryDeng
 * @date 2018/11/22 17:24
 */
public class AsyncDemo {

    /** 重试策略:重试间隔时间为1000ms; 最多重试3次; */
    private static RetryPolicy retryPolicy = new RetryNTimes(3, 1000);

    /**
     * 实现异步调用接口
     */
    private static class MyBackgroundCallback implements BackgroundCallback {

        @Override
        public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
            System.out.println("进入回调方法了！执行此方法的线程是:" + Thread.currentThread().getName());
            StringBuilder sb = new StringBuilder(64);
            sb.append("操作事件类型CuratorEventType为:").append(event.getType()).append("\n");
            sb.append("ACL为:").append(event.getACLList()).append("\n");
            sb.append("子节点集合为:").append(event.getChildren()).append("\n");
            sb.append("操作时传入的上下文数据:").append(event.getContext()).append("\n");
            sb.append("所操作节点(操作进行前)为:").append(event.getPath()).append("\n");
            byte[] data = event.getData();
            sb.append("所操作节点数据内容为:").append(data == null ? null : new String(data)).append("\n");
            // 当节点类型为  有序节点时，path与name不一致
            sb.append("所操作节点(操作进行后)为:").append(event.getName()).append("\n");
            // 操作成功的结果码为0
            sb.append("操作结果码为:").append(event.getResultCode()).append("\n");
            sb.append("所操作节点stat为:").append(event.getStat()).append("\n");
            sb.append("WatchedEvent为:").append(event.getWatchedEvent());
            System.out.println(sb.toString());
        }
    }

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("10.8.109.60:2181",5000,
                5000, retryPolicy);
        // 启动客户端
        client.start();


        /*
         异步创建节点
         注:inBackground有多个重载方法;根据实际情况选择适合的即可
         注:执行回调函数的是一个单独的线程;如果同一时刻回调函数较多的话，我们就可以考虑引入线程池(不引入的话，频繁的创建销毁线程会比较耗性能)
            inBackground(BackgroundCallback callback, Object context, Executor executor);
        */
        client.create()
                .withMode(CreateMode.PERSISTENT_SEQUENTIAL)
                .inBackground(new MyBackgroundCallback(), "我是上下文Object数据")
                .forPath("/abc", "异步创建节点".getBytes());


        // 为了看见控制台输出，我们不能让main线程死得太早
        TimeUnit.MILLISECONDS.sleep(10000);
    }

}