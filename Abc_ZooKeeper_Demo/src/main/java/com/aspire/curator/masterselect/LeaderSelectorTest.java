package com.aspire.curator.masterselect;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.utils.CloseableUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LeaderSelectorTest {

    private static final int SIZE = 10;

    private static final String PATH = "/master";

    private static final String IP_PORT = "10.8.109.60:2181";

    private static final int SESSION_TIMEOUT = 10000;

    private static final int CONNECT_TIMEOUT = 10000;


    /** 重试策略:重试间隔时间为1000ms; 最多重试3次; */
    private static RetryPolicy retryPolicy = new RetryNTimes(3, 1000);

    public static void main(String[] args) throws InterruptedException {

        List<CuratorFramework> clients = new ArrayList<>(16);

        List<WorkServer> workServers = new ArrayList<>(16);

        String name = "worker-server-";

        try {
            for (int i = 0; i < SIZE; i++) {
                CuratorFramework client = CuratorFrameworkFactory.newClient(IP_PORT, SESSION_TIMEOUT, CONNECT_TIMEOUT, retryPolicy);
                clients.add(client);
                WorkServer workServer = new WorkServer(client, PATH, name + i);
                workServers.add(workServer);
                // 启动客户端
                client.start();
                // 开始WorkServer
                workServer.start();
            }
        } finally {
            System.out.println("开始关闭！");
            WorkServer workServer;
            for (int i = 0; i < workServers.size(); i++) {
                workServer = workServers.get(i);
                CloseableUtils.closeQuietly(workServer);
                // 为方便观察，这里阻塞几秒
                TimeUnit.SECONDS.sleep(3);
            }
            for (CuratorFramework client : clients) {
                CloseableUtils.closeQuietly(client);
            }
        }
    }
}