package com.aspire.zkclient.subscribe;

import java.io.Serializable;

/**
 * 服务器模型
 * 注:此模型主要用于记录  服务器的基本信息
 * 注:由于涉及到这个模型的ZkClient需要使用SerializableSerializer所以此模型需要实现Serializable接口;
 *    如果不想实现Serializable，那么可以使用BytesPushThroughSerializer,这样一
 *    来设置数据时就需要手动将数据转换为字节数组了
 *
 * @author JustryDeng
 * @date 2018/11/23 15:42
 */
public class WorkerServerParamModel implements Serializable {

    private static final long serialVersionUID = 8895156298220482681L;

    /** 服务器id */
    private int serverId;

    /** 服务器名字 */
    private String serverName;

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    @Override
    public String toString() {
        return "WorkerServerParamModel{serverId=" + serverId + ", serverName='" + serverName + "'}";
    }
}
