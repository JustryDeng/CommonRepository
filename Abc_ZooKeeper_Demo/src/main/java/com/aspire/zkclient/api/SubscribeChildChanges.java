package com.aspire.zkclient.api;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * ZkClient客户端框架 订阅 子节点发生变化事件
 *
 * @author JustryDeng
 * @date 2018/11/22 16:32
 */
public class SubscribeChildChanges {

    public static class MyIZkChildListener implements IZkChildListener {

        @Override
        public void handleChildChange(String parentPath, List<String> currentChilds) {
            String threadName = Thread.currentThread().getName();
            System.out.println("线程" + threadName + "说，进入监听方法了！");
            System.out.println("线程" + threadName + "说，parentPath:" + parentPath);
            System.out.println("线程" + threadName + "说，currentChilds:" + currentChilds);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // SerializableSerializer为序列化器，这样一来，我们在节点存放/读取数据时，就不需要
        // 手动将数据对象转换成自己数组 或 手动将字节数组转换为数据对象了
        ZkClient zkClient = new ZkClient("10.8.109.60:2181", 10000,
                10000, new SerializableSerializer());

        /* 订阅“/”节点的 子节点发生变化事件
         * 能监听到的事件有:
         *      1.节点“/”本身被创建的事件
         *      2.节点“/”本身被删除的事件
         *      3.节点“/”下 新创建子节点事件
         *      4.节点“/”下 删除子节点事件
         */
        zkClient.subscribeChildChanges("/", new MyIZkChildListener());

        // 主线程休眠一段时间，以便我们能观察到监听输出
        TimeUnit.SECONDS.sleep(60);
    }
}
