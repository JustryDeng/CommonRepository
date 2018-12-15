package com.aspire.zkclient.balance.client;

import com.aspire.zkclient.balance.server.ServerData;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 负载均衡实现 的封装类
 *
 * @author JustryDeng
 * @date 2018/12/4 13:47
 */
public class LoadBalanceImplProvider<T extends ServerData> {

    /** ip 端口 */
    private final String ipAndPort;

    /** /servers节点路径 */
    private final String serversNodePath;

    /** ZkClient客户端 */
    private final ZkClient zkClient;

    /** 会话超时时间 */
    private static final Integer SESSION_TIME_OUT = 10000;

    /** 连接超时时间 */
    private static final Integer CONNECT_TIME_OUT = 10000;

    /** 节点路径分隔符 */
    private static final String NODE_SEPARATOR = "/";

    /**
     * 构造器
     */
    public LoadBalanceImplProvider(String ipAndPort, String serversNodePath) {
        this.serversNodePath = serversNodePath;
        this.ipAndPort = ipAndPort;
        this.zkClient = new ZkClient(this.ipAndPort, SESSION_TIME_OUT, CONNECT_TIME_OUT, new SerializableSerializer());
    }

    /**
     * 获取列表中的元素  升序排序后的第一个
     */
    protected T balanceAlgorithm(List<T> items) {
        if (items.size() > 0) {
            Collections.sort(items);
            return items.get(0);
        } else {
            return null;
        }
    }


    /**
     * 返回/servers节点下的所有子节点 的 数据 集合
     */
    protected List<T> getBalanceItems() {
        List<T> serverDataList = new ArrayList<>();
        // 获取/servers节点的所有子节点
        List<String> children = zkClient.getChildren(this.serversNodePath);
        for (int i = 0; i < children.size(); i++) {
            // 这里将取出来的数据反序列化为T，那么注意:在网节点里面存数据的时候，对象也应该为T
            T serverData = zkClient.readData(serversNodePath + NODE_SEPARATOR + children.get(i));
            serverDataList.add(serverData);
        }
        return serverDataList;
    }

}