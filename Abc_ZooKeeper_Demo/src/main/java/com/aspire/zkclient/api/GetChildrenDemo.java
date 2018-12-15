package com.aspire.zkclient.api;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.List;

/**
 * ZkClient客户端框架 获取子节点
 *
 * @author JustryDeng
 * @date 2018/11/22 14:25
 */
public class GetChildrenDemo {
    public static void main(String[] args) {
        // SerializableSerializer为序列化器，这样一来，我们在节点存放/读取数据时，就不需要
        // 手动将数据对象转换成自己数组 或 手动将字节数组转换为数据对象了
        ZkClient zkClient = new ZkClient("10.8.109.60:2181", 10000,
                10000, new SerializableSerializer());


        List<String> children = zkClient.getChildren("/");
        System.out.println("/节点的子节点集合:" + children);
    }
}
