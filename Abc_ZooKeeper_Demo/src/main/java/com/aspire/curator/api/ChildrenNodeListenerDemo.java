package com.aspire.curator.api;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.RetryNTimes;

import java.util.concurrent.TimeUnit;

/**
 * Curator客户端框架 监听子节点
 *
 * @author JustryDeng
 * @date 2018/11/22 17:24
 */
public class ChildrenNodeListenerDemo {

    /** 重试策略:重试间隔时间为1000ms; 最多重试3次; */
    private static RetryPolicy retryPolicy = new RetryNTimes(3, 1000);

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("10.8.109.60:2181",5000,
                5000, retryPolicy);
        // 启动客户端
        client.start();

        // 最后一个参数为true,表示node contents are cached in addition to the stat
        final PathChildrenCache cache = new PathChildrenCache(client,"/", true);
        // 开始监听
        cache.start();
        // 当目标节点的子节点被创建、子节点被删除、子节点数据内容发生变化等时，会触发监听方法
        cache.getListenable().addListener(new PathChildrenCacheListener() {

            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                System.out.println("线程" + Thread.currentThread().getName() + "说，进入监听方法了!");
                byte[] data;
                switch (event.getType()){
                    case CHILD_ADDED :
                        System.out.println("新增子节点!");
                        System.out.println("子节点path为:" + event.getData().getPath());
                        System.out.println("子节点Stat为:" + event.getData().getStat());
                        data = event.getData().getData();
                        System.out.println("子节点数据内容为:" + (data == null ? null : new String(data)));
                        break;
                    case  CHILD_REMOVED :
                        System.out.println("删除子节点!");
                        System.out.println("子节点path为:" + event.getData().getPath());
                        System.out.println("子节点Stat为:" + event.getData().getStat());
                        data = event.getData().getData();
                        System.out.println("子节点数据内容为:" + (data == null ? null : new String(data)));
                        break;
                    case CHILD_UPDATED :
                        System.out.println("修改子节点数据内容!");
                        System.out.println("子节点path为:" + event.getData().getPath());
                        System.out.println("子节点Stat为:" + event.getData().getStat());
                        data = event.getData().getData();
                        System.out.println("子节点数据内容为:" + (data == null ? null : new String(data)));
                        break;
                    default :
                        System.out.println("default!");
                }
            }
        });

        // 为了看见控制台输出，我们不能让main线程死得太早
        TimeUnit.MILLISECONDS.sleep(100000);

    }

}
