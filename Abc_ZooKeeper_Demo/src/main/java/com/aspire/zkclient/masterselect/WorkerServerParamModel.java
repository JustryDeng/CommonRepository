package com.aspire.zkclient.masterselect;

import java.io.Serializable;

/**
 * 服务器模型
 * 注:此模型主要用于记录  服务器的基本信息
 * 注:由于此信息要被写入节点，所以需要能够序列化，即:需要实现Serializable功能性接口
 *
 * @author JustryDeng
 * @date 2018/11/23 15:42
 */
public class WorkerServerParamModel implements Serializable {

    private static final long serialVersionUID = -4983450409669715087L;

    /** 服务器id */
    private Long serverId;

    /** 服务器名字 */
    private String serverName;

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
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
