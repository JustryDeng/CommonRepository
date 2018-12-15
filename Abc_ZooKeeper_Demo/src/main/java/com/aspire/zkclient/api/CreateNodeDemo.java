package com.aspire.zkclient.api;

import com.aspire.model.User;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;

/**
 * ZkClient客户端框架 创建节点
 *
 * @author JustryDeng
 * @date 2018/11/22 14:25
 */
public class CreateNodeDemo {
    public static void main(String[] args) {
        // SerializableSerializer为序列化器，这样一来，我们在节点存放/读取数据时，就不需要
        // 手动将数据对象转换成自己数组 或 手动将字节数组转换为数据对象了
        ZkClient zkClient = new ZkClient("10.8.109.60:2181", 10000, 10000, new SerializableSerializer());

        User user = new User();
        user.setName("邓沙利文");
        user.setGender("男");
        user.setHobby("女");
        // 因为在获取ZkClient实例时设置了序列化工具， 所以我们在设置节点的数据时，可以
        // 是任意一个Java对象(必须保证这个对象可序列化，即:必须实现Serializable接口)
        String nodeName = zkClient.create("/user_node",user, CreateMode.PERSISTENT);
        System.out.println("创建的新节点为:" + nodeName);
    }
}