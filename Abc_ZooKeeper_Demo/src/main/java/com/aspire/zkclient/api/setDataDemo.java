package com.aspire.zkclient.api;

import com.aspire.model.User;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.data.Stat;

/**
 * ZkClient客户端框架 修改节点数据
 *
 * @author JustryDeng
 * @date 2018/11/22 14:25
 */
public class setDataDemo {
    public static void main(String[] args) {
        // SerializableSerializer为序列化器，这样一来，我们在节点存放/读取数据时，就不需要
        // 手动将数据对象转换成自己数组 或 手动将字节数组转换为数据对象了
        ZkClient zkClient = new ZkClient("10.8.109.60:2181", 10000,
                10000, new SerializableSerializer());
        User newData = new User();
        newData.setName("JustryDeng");
        newData.setGender("男");
        newData.setHobby("耍");
        // 因为在获取ZkClient实例时设置了序列化工具， 所以我们在设置节点的数据时，可以
        // 是直接是对象(必须保证这个对象可序列化，即:必须实现Serializable接口)
        Stat stat = zkClient.writeDataReturnStat("/", newData, -1);
        // 还有另外两个可选方法
        // zkClient.writeData("/",newData);
        // zkClient.writeData("/",newData, -1);
        System.out.println("stat状态信息为:" + stat);
    }
}
