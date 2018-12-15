package com.aspire.zkclient.subscribe;

import com.alibaba.fastjson.JSON;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import java.util.List;

/**
 * 集群中managerServer服务器 相关逻辑实现
 * <p>
 * 此WorkerServer实例主要做的事有:
 * 1.监听/servers节点的子节点改变事件，并作出相应处理
 * 2.监听/command节点的数据改变事件，并作出相应处理
 *
 * @author JustryDeng
 * @date 2018/11/28 23:31
 */
public class ManageServer {

    /** 节点分割符 */
    private final String NODE_SPLITTER = "/";

    /** ZkClient实例 */
    private ZkClient zkClient;

    /** servers节点路径 */
    private String serversNodePath;

    /** command节点路径 */
    private String commandNodePath;

    /** config节点路径 */
    private String configNodePath;

    /** command节点数据 */
    private CommandPOJO commandNodeData;

    /** 监听servers节点的子节点变化的监听器 */
    private IZkChildListener serversNodeChildListener;

    /** 监听config节点数据变化的监听器 */
    private IZkDataListener commandNodeDataListener;

    /** servers节点下的所有节点(即:WorkerServer集合) */
    private List<String> workerServerList;

    /**
     * 构造器
     *
     * @param zkClient
     *         ZkClient实例
     * @param serversNodePath
     *         servers节点路径
     * @param commandNodePath
     *         command节点路径
     * @param configNodePath
     *         config节点路径
     * @return
     * @throws
     * @date 2018/11/28 23:43
     */
    public ManageServer(ZkClient zkClient, String serversNodePath, String commandNodePath, String configNodePath) {

        this.zkClient = zkClient;
        this.serversNodePath = serversNodePath;
        this.commandNodePath = commandNodePath;
        this.configNodePath = configNodePath;

        // 实例化 serversNodeChildListener监听器
        this.serversNodeChildListener = new IZkChildListener() {

            public void handleChildChange(String parentPath,
                                          List<String> currentWorkerServerList) {
                workerServerList = currentWorkerServerList;
                System.out.println("servers节点的子节点列表发生了变化!");
                execList();
            }
        };

        // 实例化 commandNodeDataListener
        this.commandNodeDataListener = new IZkDataListener() {

            public void handleDataDeleted(String dataPath) {
                // do something
            }

            public void handleDataChange(String dataPath, Object data) {
                String commandNodeDataJsonString = new String((byte[]) data);
                commandNodeData = JSON.parseObject(commandNodeDataJsonString, CommandPOJO.class);
                exeCmd();
            }
        };

    }

    /**
     * 启动ManagerServer
     *
     * @author JustryDeng
     * @date 2018/11/28 23:51
     */
    public void start() {
        // 判断command节点是否存在，不存在则创建
        createCommandNode();
        // 监听(订阅)command节点的数据变化
        zkClient.subscribeDataChanges(commandNodePath, commandNodeDataListener);
        // 监听(订阅)servers节点的子节点变化
        zkClient.subscribeChildChanges(serversNodePath, serversNodeChildListener);
    }

    /**
     * 停止ManagerServer
     *
     * @author JustryDeng
     * @date 2018/11/28 23:51
     */
    public void stop() {
        // 取消 监听(订阅)command节点的数据变化
        zkClient.unsubscribeDataChanges(commandNodePath, commandNodeDataListener);
        // 取消 监听(订阅)servers节点的子节点变化
        zkClient.unsubscribeChildChanges(serversNodePath, serversNodeChildListener);
    }

    /**
     * 创建command节点
     */
    private void createCommandNode() {
        if (!zkClient.exists(commandNodePath)) {
            try {
                // command是持久型节点
                zkClient.createPersistent(commandNodePath);
            } catch (ZkNodeExistsException e) {
                // 如果此时，别的线程恰好刚刚创建了该节点，那么return
                return;
            } catch (ZkNoNodeException e) {
                // 如果父节点不存在，那么同时创建父节点
                String parentNodePath = configNodePath.substring(0, commandNodePath.lastIndexOf(NODE_SPLITTER));
                zkClient.createPersistent(parentNodePath, true);
                createCommandNode();
            } catch (Exception e) {
                System.out.println("发生其他异常了！" + e.getMessage());
            }
        }
    }


    /**
     * 执行command指令
     * <p>
     * 自定义的命令有:
     * list   列出servers节点下的所有子节点
     * create 创建config节点
     * modify 修改config节点数据
     */
    private void exeCmd() {
        String cmd = commandNodeData.getCmd();
        if ("list".equals(cmd)) {
            System.out.println("检测到command中的数据发生了变化，处理相应的list指令");
            execList();
        } else if ("create".equals(cmd)) {
            System.out.println("检测到command中的数据发生了变化，处理相应的create指令！");
            execCreate();
        } else if ("modify".equals(cmd)) {
            System.out.println("检测到command中的数据发生了变化，处理相应的modify指令！");
            execModify();
        } else {
            System.out.println("不存在此指令:" + cmd);
        }

    }

    /**
     * list
     */
    private void execList() {
        System.out.println("此时servers节点下的所有子节点有:" + workerServerList.toString());
    }

    /**
     * create
     */
    private void execCreate() {
        if (!zkClient.exists(configNodePath)) {
            try {
                // config是持久型节点
                zkClient.createPersistent(configNodePath, JSON.toJSONString(commandNodeData.getConfigPOJO()).getBytes());
            } catch (ZkNodeExistsException e) {
                // 如果此时，别的线程恰好刚刚创建了该节点，那么修改该节点的数据
                zkClient.writeData(configNodePath, JSON.toJSONString(commandNodeData.getConfigPOJO()).getBytes());
            } catch (ZkNoNodeException e) {
                // 如果父节点不存在，那么同时创建父节点
                String parentNodePath = configNodePath.substring(0, configNodePath.lastIndexOf(NODE_SPLITTER));
                zkClient.createPersistent(parentNodePath, true);
                execCreate();
            } catch (Exception e) {
                System.out.println("发生其他异常了！" + e.getMessage());
            }
        }
    }

    /**
     * modify
     */
    private void execModify() {
        try {
            zkClient.writeData(configNodePath, JSON.toJSONString(commandNodeData.getConfigPOJO()).getBytes());
        } catch (ZkNoNodeException e) {
            // 节点不存在则创建节点
            execCreate();
        } catch (Exception e) {
            System.out.println("发生其他异常了！" + e.getMessage());
        }
    }

}