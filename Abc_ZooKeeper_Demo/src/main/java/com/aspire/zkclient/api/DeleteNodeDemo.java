package com.aspire.zkclient.api;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

/**
 * ZkClient客户端框架 删除节点
 * 注:个人感觉deleteRecursive功能更加强大
 *
 * @author JustryDeng
 * @date 2018/11/22 14:25
 */
public class DeleteNodeDemo {
    public static void main(String[] args) {
        // SerializableSerializer为序列化器，这样一来，我们在节点存放/读取数据时，就不需要
        // 手动将数据对象转换成自己数组 或 手动将字节数组转换为数据对象了
        ZkClient zkClient = new ZkClient("10.8.109.60:2181", 10000,
                10000, new SerializableSerializer());
        // -> delete方法只能没有子节点的节点
        // 如果目标节点不存在，那么返回false;目标节点存在子节点，会出异常
        boolean delResultOne = zkClient.delete("/abc_node");
        System.out.println("删除/abc_node节点，" + (delResultOne ? "" : "不") + "成功！");

        // -> deleteRecursive方法无论目标节点有无子节点，都能删除
        // 递归删除节点(该节点内存在子节点);如果目标节点不存在，那么返回true;
        boolean delResultTwo = zkClient.deleteRecursive("/parent_node");
        System.out.println("递归删除/parent_node节点，" + (delResultTwo ? "" : "不") + "成功！");
    }
}
