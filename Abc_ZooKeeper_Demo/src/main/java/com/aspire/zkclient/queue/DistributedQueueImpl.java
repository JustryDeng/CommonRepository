package com.aspire.zkclient.queue;


import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 分布式队列 的 实现
 *
 * @author JustryDeng
 * @date 2018/12/12 14:03
 */
public class DistributedQueueImpl<T> {

    /** ZkClient客户端 */
    private final ZkClient zkClient;

    /** queue节点路径 */
    private final String QUEUE_NODE_PATH;

    /** 节点分隔符 */
    private static final String NODE_SEPARATOR = "/";

    /** 子节点名称前缀 */
    private static final String CHILD_NAME_PREFIX = "msg_";

    /**
     * 构造器
     */
    public DistributedQueueImpl(ZkClient zkClient, String queueNodePath) {
        this.zkClient = zkClient;
        this.QUEUE_NODE_PATH = queueNodePath;
    }

    /**
     * 消息生产者 --- 推送消息
     *
     * @return 创建后的 该节点的真实路径
     * @date 2018/12/12 14:45
     */
    public String push(T data) {
        String childNodePath = QUEUE_NODE_PATH.concat(NODE_SEPARATOR).concat(CHILD_NAME_PREFIX);
        try {
            childNodePath = zkClient.createPersistentSequential(childNodePath, data);
        } catch (ZkNoNodeException e) { // 如果父路径不存在，则创建
            zkClient.createPersistent(QUEUE_NODE_PATH, true);
            push(data);
        }
        return childNodePath;
    }


    /**
     * 消息消费者 --- 消费消息
     * <p>
     * 提示: T必须可序列化，且创建ZkClient时,必须用SerializableSerializer序列化器
     *
     * @throws NodeDataIsNullException
     *         节点数据为null异常
     * @date 2018/12/12 14:45
     */
    public T pull() throws NodeDataIsNullException {
        List<String> list = zkClient.getChildren(QUEUE_NODE_PATH);
        if (list.size() == 0) {
            return null;
        }
        list.sort(Comparator.comparing(String::valueOf));
        String childNodePath;
        for (String childNodeName : list) {
            childNodePath = QUEUE_NODE_PATH.concat(NODE_SEPARATOR).concat(childNodeName);
            try {
                // 如果该节点没有数据，那么读取出来的即为null
                T childNodeData = zkClient.readData(childNodePath);
                zkClient.delete(childNodePath);
                if (childNodeData == null) {
                    throw new NodeDataIsNullException("节点" + childNodePath + "数据为null,已经删除该节点！");
                }
                return childNodeData;
            } catch (ZkNoNodeException e) {
                // 如果在执行delete操作时，发现该节点不存在(即:已经被别的消费者删除了)
                // 那么消费节点名次大的节点
            }
        }
        // QUEUE_NODE_PATH下没有任何子节点的话，返回null
        return null;
    }

    /**
     * 消息消费者 --- 消费消息
     *
     * @author JustryDeng
     * @date 2018/12/12 14:45
     */
    public T pullUntilGotMessage() throws Exception {
        while (true) {
            // 倒计时锁,长度设置为1
            final CountDownLatch latch = new CountDownLatch(1);
            // 监听器
            final IZkChildListener childListener = new IZkChildListener() {
                public void handleChildChange(String parentPath, List<String> currentChilds) {
                    System.out.println("父节点" + parentPath + "的子节点发生了变化！countDown!");
                    // 节点到子节点发生变化时，latch.countDown()
                    latch.countDown();
                }
            };
            // 订阅QUEUE_NODE_PATH节点的自及诶单改变事件
            zkClient.subscribeChildChanges(QUEUE_NODE_PATH, childListener);
            try {
                T node = pull();
                if (node != null) {
                    return node;
                } else {
                    // 说明QUEUE_NODE_PATH节点下此时没有任何子节点，那么继续等待，直到能消费到消息
                    latch.await();
                }
            } catch (NodeDataIsNullException e) { // 说明小得到了消息，不对该消息对应节点的数据本身就为null
                e.printStackTrace();
                return null;
            } finally {
                // 取消订阅
                zkClient.unsubscribeChildChanges(QUEUE_NODE_PATH, childListener);
            }
        }
    }

    /**
     * /queue子节点个数
     *
     * @author JustryDeng
     * @date 2018/12/12 14:06
     */
    public int size() {
        return zkClient.getChildren(QUEUE_NODE_PATH).size();
    }

    /**
     * /queue是否存在子节点
     *
     * @author JustryDeng
     * @date 2018/12/12 14:06
     */
    public boolean isEmpty() {
        return zkClient.getChildren(QUEUE_NODE_PATH).size() == 0;
    }

}