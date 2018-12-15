package com.aspire.zkclient.subscribe;

import com.alibaba.fastjson.JSON;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;

/**
 * 集群中workerServer服务器 相关逻辑实现
 *
 * 此WorkerServer实例主要做的事有:
 *    1.在调用了start方法后，会在/servers节点下创建对应的临时节点
 *    2.在监听到/config节点数据变化时，会做相应的处理
 *
 * @author JustryDeng
 * @date 2018/11/23 16:19
 */
public class WorkerServer {

    /** ZkClient客户端 */
    private ZkClient zkClient;

    /** 当前WorkerServer的数据内容信息 */
    private WorkerServerParamModel currentWorkerServer;

    /** servers节点路径 */
    private String serversNodePath;

    /** config节点路径 */
    private String configNodePath;

    /** config节点初始化数据 */
    private ConfigPOJO configPOJOData;

    /** 当前workerServer服务器对config节点的数据内容监听器 */
    private IZkDataListener configNodeDataListener;

    /**
     * 构造器
     *
     * @param zkClient
     *            ZkClient客户端
     * @param wsParamModel
     *            当前WorkerServer的数据内容信息
     * @param serversNodePath
     *            servers节点路径
     * @param configNodePath
     *            config节点路径
     * @param initConfig
     *            config节点初始化数据
     * @date 2018/11/28 18:32
     */
    public WorkerServer(ZkClient zkClient, WorkerServerParamModel wsParamModel,
                        String serversNodePath, String configNodePath, ConfigPOJO initConfig) {
        // 初始化赋值
        this.zkClient = zkClient;
        this.currentWorkerServer = wsParamModel;
        this.serversNodePath = serversNodePath;
        this.configNodePath = configNodePath;
        this.configPOJOData = initConfig;

        // 实例化数据监听器
        this.configNodeDataListener = new IZkDataListener() {

            public void handleDataDeleted(String dataPath) {
                // TODO do something
            }

            public void handleDataChange(String dataPath, Object data) {
                // 获取到config节点的最新数据，并将其转换为json字符串
                String jsonString = new String((byte[])data);
                ConfigPOJO newestConfigNodeData = JSON.parseObject(jsonString, ConfigPOJO.class);
                updateConfigData(newestConfigNodeData);
                System.out.println(currentWorkerServer.getServerName() + "监听到config节点数据发生了变化！"
                        + "此时，config节点的数据为:" + newestConfigNodeData.toString());
            }
        };

    }

    /**
     * 启动WorkerServer
     *
     * @author JustryDeng
     * @date 2018/11/28 18:46
     */
    public void start() {
        System.out.println(currentWorkerServer.getServerName() + "开始工作了！");
        // 给当前服务器(严格的说，是给当前客户端)注册对应的临时节点到 /servers下
        registCurrentWorkerServerNodeUnderServersNode();
        // 订阅
        zkClient.subscribeDataChanges(configNodePath, configNodeDataListener);

    }

    /**
     * 停止WorkerServer
     *
     * @author JustryDeng
     * @date 2018/11/28 19:14
     */
    public void stop() {
        System.out.println(currentWorkerServer.getServerName() + "取消了/config节点的数据订阅！");
        zkClient.unsubscribeDataChanges(configNodePath, configNodeDataListener);
    }

    /**
     *  更新此实例的configPOJOData属性，使其为最新的值
     *
     * @author JustryDeng
     * @date 2018/11/28 18:43
     */
    private void updateConfigData(ConfigPOJO newestConfigData){
        this.configPOJOData = newestConfigData;
    }

    /**
     * 在/server节点下 注册 与当前WorkerServer服务器(严格的说，是客户端)对应的 临时节点
     *
     * @author JustryDeng
     * @date 2018/11/28 19:26
     */
    private void registCurrentWorkerServerNodeUnderServersNode() {
        // 节点分割符
        final String NODE_SPLITTER = "/";
        String currWorkerServerNodePath = serversNodePath + NODE_SPLITTER + currentWorkerServer.getServerName();
        try {
            // 创建临时节点，并将数据放入对应节点
            zkClient.createEphemeral(currWorkerServerNodePath, JSON.toJSONString(currentWorkerServer).getBytes());
        } catch (ZkNoNodeException e) {
            // 如果我们要创建的临时节点的某个父节点不存在，那么创建
            // 父节点是持久节点
            // 第二个参数为true,表示如果当前要创建的serversNodePath节点不存在父节点的话，那么直接回顺带创建其父节点
            zkClient.createPersistent(serversNodePath, true);
            registCurrentWorkerServerNodeUnderServersNode();
        } catch (Exception e) {
            System.err.println("registCurrentWorkerServerNodeUnderServersNode发生异常了！" + e.getMessage());
        }
    }
}