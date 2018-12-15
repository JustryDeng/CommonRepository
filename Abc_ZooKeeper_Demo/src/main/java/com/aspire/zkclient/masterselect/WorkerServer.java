package com.aspire.zkclient.masterselect;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkException;
import org.I0Itec.zkclient.exception.ZkInterruptedException;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 集群中的每一个服务器 启动时，都需要做这些事
 * 注:监听master，并保证群首存在
 * TODO 去调所有的System.out.println
 *
 * @author JustryDeng
 * @date 2018/11/23 16:19
 */
public class WorkerServer {

    /** master临时节点 */
    private final String MASTER_NODE_PATH = "/master";

    /** 主节点服务器(即:群首) */
    private WorkerServerParamModel masterServerParam;

    /** 当前服务器 */
    private WorkerServerParamModel currentServerParam;

    /** 当前服务器是否处于运行中（默认为false） */
    private boolean currentServerIsRunning = false;

    /** 监听器 */
    private IZkDataListener iZkDataListener;

    /**
     *  应对网络抖动的实现
     *  这里使用:定时计划线程池
     */
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private final int DELAY_TIME = 2;

    /** zkClient客户端 */
    private ZkClient zkClient;

    public ZkClient getZkClient() {
        return zkClient;
    }

    public void setZkClient(ZkClient zkClient) {
        this.zkClient = zkClient;
    }

    public WorkerServer(WorkerServerParamModel currentServerParam, ZkClient zkClient) {

        this.zkClient = zkClient;

        this.currentServerParam = currentServerParam;

        iZkDataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println(currentServerParam.getServerName() + "监听到目标节点的数据发生了变化！");
                //do something即可
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println(currentServerParam.getServerName() + "监听到目标节点被删除了！");
                // 如果前一次的群首是自己，那么立即 开始  争抢群首
                if (masterServerParam != null && masterServerParam.getServerId().equals(currentServerParam.getServerId())){
                    tryCreateMasterNode();

                }else{ // 如果前一次的群首不是自己，那么延迟一定时间后才开始  争抢群首
                    scheduledExecutorService.schedule(() -> tryCreateMasterNode(), DELAY_TIME, TimeUnit.SECONDS);
                }
            }
        };
    }

    /**
     * 试着去 创建master临时主节点(即:争抢群首)
     *
     * @author JustryDeng
     * @date 2018/11/26 21:53
     */
    private void tryCreateMasterNode() {
        // 如果当前服务器没有运行着，那么不进行下面的逻辑
        if (!currentServerIsRunning) {
            return;
        }
        try {
            System.out.println(currentServerParam.getServerName() + "试着去创建master临时主节点！");
            // 创建临时主节点， 该节点的数据  要有唯一性(以便后面的区分)
            String path = zkClient.create(MASTER_NODE_PATH, currentServerParam, CreateMode.EPHEMERAL);
            System.out.println(currentServerParam.getServerName() + "创建" + path + "临时主节点成功！");
            //创建成功后，将当前服务器数据赋值给masterServer
            masterServerParam = currentServerParam;
            System.out.println(masterServerParam.getServerName() + " is master! this server id is "
                    + masterServerParam.getServerId());
        } catch (ZkNodeExistsException e) { // 创建时，如果出现master节点存在异常，那么试着读取主节点数据
            // 第二个参数设为true,当节点不存在时，返回null
            WorkerServerParamModel runningServer = zkClient.readData(MASTER_NODE_PATH, true);
            if (runningServer == null) { // 当此时主节点不存在时，重试
                System.out.println("获取主节点数据为null，则此时主节点不存在时，重试tryCreateMasterNode方法");
                // 注:可能出现这种情况:上一步create时，主节点还在;而在下一步readData时，主节点就不在了
                tryCreateMasterNode();
            } else {
                masterServerParam = runningServer;
            }
        } catch (ZkInterruptedException e) { // 出现打断异常时重试
            System.out.println("tryCreateMasterNode()发生ZkInterruptedException异常了！");
            tryCreateMasterNode();
        } catch (Exception e) {
            System.out.println("tryCreateMasterNode()发生Exception异常了！");
        }
    }

    /**
     * 判断当前节点是否是master主节点
     *
     * @author JustryDeng
     * @date 2018/11/27 14:59
     */
    private boolean currentServerIsMasterServer() {
        try {
            WorkerServerParamModel runningServer = zkClient.readData(MASTER_NODE_PATH);
            masterServerParam = runningServer;
            return currentServerParam.getServerId().equals(runningServer.getServerId());
        } catch (ZkNoNodeException e) {
            return false;
        } catch (ZkInterruptedException e) { // 被打断了就进行重试
            return currentServerIsMasterServer();
        } catch (ZkException e) {
            return false;
        }
    }

    /**
     * 如果自己是群首，那么删除主节点
     * 注:由于该节点是一个没有子节点的节点，所以使用delete或deleteRecursive都行;
     *
     * @author JustryDeng
     * @date 2018/11/27 14:59
     */
    private void deleteMasterNode() {
        // 主节点最好是被对应的服务器删除，在实际使用时可考虑加上ACL做限制
        if (currentServerIsMasterServer()) {
            zkClient.delete(MASTER_NODE_PATH);
        }
    }

    /**
     * 对外暴露的启动方法
     *
     * @author JustryDeng
     * @date 2018/11/27 15:12
     */
    public void start() {
        if (currentServerIsRunning) {
            System.out.println("当前服务已经在跑着了！");
            return;
        }
        currentServerIsRunning = true;
        // 监听主节点
        zkClient.subscribeDataChanges(MASTER_NODE_PATH, iZkDataListener);
        tryCreateMasterNode();
    }

    /**
     * 对外暴露的停止方法
     *
     * @author JustryDeng
     * @date 2018/11/27 15:12
     */
    public void stop() {
        if (!currentServerIsRunning) {
            System.out.println("当前服务已经停止了！");
            return;
        }
        // 取消监听
        zkClient.unsubscribeDataChanges(MASTER_NODE_PATH, iZkDataListener);
        currentServerIsRunning = false;
        // 删除主节点
        deleteMasterNode();
        // 关闭线程池
        scheduledExecutorService.shutdown();
    }


}