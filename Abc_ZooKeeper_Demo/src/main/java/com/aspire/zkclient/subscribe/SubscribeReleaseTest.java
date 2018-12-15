package com.aspire.zkclient.subscribe;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkInterruptedException;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 发布与订阅 --- 测试
 *
 * @author JustryDeng
 * @date 2018/11/27 15:30
 */
public class SubscribeReleaseTest {

    private static final int SIZE = 5;

    private static final String IP_PORT = "10.8.109.60:2181";

    private static final int SESSION_TIMEOUT = 10000;

    private static final int CONNECT_TIMEOUT = 10000;

    private static final String CONFIG_NODE_PATH = "/config";

    private static final String SERVERS_NODE_PATH = "/servers";

    private static final String COMMAND_NODE_PATH = "/command";

    /** manageServer相关ZkClient */
    private static ZkClient managerServerClient;

    private static ManageServer manageServer;

    /** workerServer相关ZkClient */
    private static List<ZkClient> zkClients = new ArrayList<>(16);

    private static List<WorkerServer> workerServers = new ArrayList<>(16);

    /**
     *  程序入口
     */
    public static void main(String[] args) throws InterruptedException {

        try {
            zkClients.clear();
            workerServers.clear();
            // 在WorkerServer开始工作前，先开启manager监听管理
            managerServerClient = new ZkClient(IP_PORT, SESSION_TIMEOUT, CONNECT_TIMEOUT, new BytesPushThroughSerializer());
            manageServer = new ManageServer(managerServerClient, SERVERS_NODE_PATH, COMMAND_NODE_PATH, CONFIG_NODE_PATH);
            manageServer.start();
            // 循环创建WorkerServer
            for (int i = 0; i < SIZE; i++){
                // 创建WorkerServer
                WorkerServerParamModel wsParamModel = new WorkerServerParamModel();
                wsParamModel.setServerId(i);
                wsParamModel.setServerName("worker" + i);
                // 创建初始化配置
                ConfigPOJO initConfig = new ConfigPOJO();
                initConfig.setAgeConfig(100 + i);
                initConfig.setGenderConfig("我是性别" + i);
                initConfig.setMottoConfig("我是座右铭" + i);
                initConfig.setNameConfig("我是姓名" + i);
                ZkClient zkClient = new ZkClient(IP_PORT, SESSION_TIMEOUT, CONNECT_TIMEOUT, new BytesPushThroughSerializer());
                zkClients.add(zkClient);
                WorkerServer workerServer = new WorkerServer(zkClient, wsParamModel, SERVERS_NODE_PATH, CONFIG_NODE_PATH, initConfig);
                workerServers.add(workerServer);
                // 启动zookeeper的某个客户端
                workerServer.start();
            }
            // 为快速测试，直接使用zookeeper命令行客户端，来修改command节点的数据(数据已按要求提前准备好)，
            // 触发manageServer对command节点的监听事件
            // 上述操作需要时间，为了能在控制台观察输出，主线程这里需要阻塞一段时间
            System.out.println("主线程开始sleep,等待JustryDeng大帅哥使用zookeeper命令行客户端[set /command data]!");
            TimeUnit.SECONDS.sleep(60);
        } finally {
            System.out.println("开始退出！");
            try {
                WorkerServer workerServer;
                for (WorkerServer ws : workerServers) {
                    ws.stop();
                }
                for (ZkClient zkClient :zkClients) {
                    zkClient.close();
                }
                manageServer.stop();
                managerServerClient.close();
            } catch (ZkInterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}