package com.aspire.zkclient.idmaker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

/**
 * 分布式ID生成器
 *
 * @author JustryDeng
 * @date 2018/12/15 10:41
 */
public class IDMaker {

    /** ZkClient客户端 */
    private ZkClient zkClient = null;

    /** 客户端要连接的zookeeper地址和端口 */
    private final String IPAndPort;

    /** 创建有序节点的父节点位置 */
    private final String ROOT_NODE_PATH = "/idmarker";

    /** 创建有序节点的前缀名 */
    private final String NODE_PREFIX_NAME = "id-";

    /**
     * 当前客户端的运行状态
     * true  - 正在运行
     * false - 未运行
     */
    private volatile boolean running = false;

    /**
     * 线程池
     * 注:引入此变量的目的是为了:异步删除创建的有序节点，
     * 因为我们只需要得到其名字就行了，并不需要保留创建的节点，
     * 所以在得到名字后，需要删除创建的节点
     */

    private ExecutorService executorService = null;

    /**
     * 枚举定义，删除节点的方式
     */
    public enum DeleteNodeWay {

        /** 不删除 */
        NONE,

        /** 立即删除 */
        IMMEDIATELY,

        /** 使用线程池异步删除(因为异步，所以会有延迟，所以取名为DELAY) */
        DELAY
    }

    /**
     * 构造器
     */
    public IDMaker(String zkServer) {
        this.IPAndPort = zkServer;
    }

    /**
     * start
     */
    public void start() throws Exception {
        if (running)
            throw new Exception("server has stated...");
        running = true;
        init();
    }

    /**
     * stop
     */
    public void stop() throws Exception {
        if (!running)
            throw new Exception("server has stopped...");
        running = false;
        releaseResource();
    }

    /**
     * 生成ID
     */
    public String generateId(DeleteNodeWay removeMethod) throws Exception {
        checkRunning();
        // 节点路径分隔符
        final String SPERATOR = "/";
        final String fullNodePath = ROOT_NODE_PATH.concat(SPERATOR).concat(NODE_PREFIX_NAME);
        final String ourPath = zkClient.createPersistentSequential(fullNodePath, null);
        if (removeMethod.equals(DeleteNodeWay.IMMEDIATELY)) {
            System.out.println("立即删除此次创建的持久有序节点！");
            zkClient.delete(ourPath);
        } else if (removeMethod.equals(DeleteNodeWay.DELAY)) {
            System.out.println("使用线程池异步删除此次创建的持久有序节点！");
            executorService.execute(() -> zkClient.delete(ourPath));
        } else if (removeMethod.equals(DeleteNodeWay.NONE)) {
            System.out.println("不删除此次创建的持久有序节点！");
        }
        return ExtractId(ourPath);
    }
    /**
     * 初始化
     */
    private void init() {
        // 会话超时时间
        final int SESSION_TIMEOUT = 5000;
        // 连接超时时间
        final int CONNECTION_TIMEOUT = 5000;
        zkClient = new ZkClient(IPAndPort, SESSION_TIMEOUT, CONNECTION_TIMEOUT,
                new BytesPushThroughSerializer());
        executorService = Executors.newFixedThreadPool(5);
        try { // 初始化时，创建一个节点;后续会在此节点下创建有序节点
            zkClient.createPersistent(ROOT_NODE_PATH, true);
        } catch (ZkNodeExistsException e) {
            //ignore;
        }
    }

    /**
     * 释放资源
     */
    private void releaseResource() {
        // 关闭线城市
        executorService.shutdown();
        try {
            executorService.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService = null;
        }
        if (zkClient != null) {
            zkClient.close();
            zkClient = null;
        }
    }

    /**
     * 检查当前客户端是否正在运行
     */
    private void checkRunning() throws Exception {
        if (!running)
            throw new Exception("请先调用start");

    }

    /**
     * 将形如“/idmarker/id-0000000009”的节点路径提取出形如“0000000009"”的序号
     */
    private String ExtractId(String str) {
        System.out.println("要提取ID的原始字符串为:" + str);
        int index = str.lastIndexOf(NODE_PREFIX_NAME);
        if (index >= 0) {
            index += NODE_PREFIX_NAME.length();
            return index <= str.length() ? str.substring(index) : "";
        }
        return str;
    }
}
