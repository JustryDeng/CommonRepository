package com.aspire.zkclient.masterselect;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkInterruptedException;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 群首选举 --- 测试
 *
 * @author JustryDeng
 * @date 2018/11/27 15:30
 */
public class MasterSelectTest {

    private static final int SIZE = 10;

    private static final String IP_PORT = "10.8.109.60:2181";

    private static final int SESSION_TIMEOUT = 10000;

    private static final int CONNECT_TIMEOUT = 100000;

    private static List<ZkClient> zkClients = new ArrayList<>(16);

    private static List<WorkerServer> workerServers = new ArrayList<>(16);

    /**
     *  程序入口
     */
    public static void main(String[] args) throws InterruptedException {

        try {
            zkClients.clear();
            workerServers.clear();
            for (int i = 0; i < SIZE; i++){
                // 创建WorkerServer
                WorkerServerParamModel wsParamModel = new WorkerServerParamModel();
                wsParamModel.setServerId((long)i);
                wsParamModel.setServerName("worker" + i);
                ZkClient zkClient = new ZkClient(IP_PORT, SESSION_TIMEOUT, CONNECT_TIMEOUT, new SerializableSerializer());
                zkClients.add(zkClient);
                WorkerServer workerServer = new WorkerServer(wsParamModel, zkClient);
                workerServers.add(workerServer);
                // 启动zookeeper的某个客户端
                workerServer.start();
            }
        } finally {
            System.out.println("开始退出！");
            try {
                WorkerServer workerServer;
                for (int i = 0; i < workerServers.size(); i++) {
                    workerServer = workerServers.get(i);
                    workerServer.stop();
                    // 这里线程阻塞几秒钟，以便观察 抢占详情
                    TimeUnit.SECONDS.sleep(5);
                }
                for (ZkClient zkClient :zkClients) {
                    zkClient.close();
                }
            } catch (ZkInterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}