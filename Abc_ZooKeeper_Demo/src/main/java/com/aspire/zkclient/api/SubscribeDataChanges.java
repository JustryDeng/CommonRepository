package com.aspire.zkclient.api;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

import java.util.concurrent.TimeUnit;

/**
 * ZkClient客户端框架 订阅 节点数据内容变化事件
 *
 * @author JustryDeng
 * @date 2018/11/22 16:32
 */
public class SubscribeDataChanges {

    public static class MyIZkDataListener implements IZkDataListener {

        @Override
        public void handleDataChange(String dataPath, Object data) throws Exception {
            String threadName = Thread.currentThread().getName();
            System.out.println("线程" + threadName + "说，进入handleDataChange监听方法了！");
            System.out.println("线程" + threadName + "说，节点“" + dataPath + "”被修改了了！新的数据内容是:" + data);
        }

        @Override
        public void handleDataDeleted(String dataPath) throws Exception {
            String threadName = Thread.currentThread().getName();
            System.out.println("线程" + threadName + "说，进入handleDataDeleted监听方法了！");
            System.out.println("线程" + threadName + "说，节点“" + dataPath + "”被删除了！");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 因为我们这里涉及到zookeeper服务器数据传输给监听函数，所以我们这里用SerializableSerializer就不合适了，此时
        // 我们需要使用BytesPushThroughSerializer处理器，点开源码会发现,此处理器什么都没有做；我们也不需要其做什么
        ZkClient zkClient = new ZkClient("10.8.109.60:2181", 10000,
                10000, new BytesPushThroughSerializer());

        /* 订阅“/”节点的 子节点发生变化事件
         * 能监听到的事件有:
         *      1.节点“/”本身被创建的事件
         *      2.节点“/”本身被删除的事件
         *      3.节点“/”下 新创建子节点事件
         *      4.节点“/”下 删除子节点事件
         */
        zkClient.subscribeDataChanges("/", new MyIZkDataListener());

        // 主线程休眠一段时间，以便我们能观察到监听输出
        TimeUnit.SECONDS.sleep(60);
    }
}
