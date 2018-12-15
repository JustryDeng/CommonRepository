package com.aspire.zkclient.lock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁的 基本实现
 * 
 * 主要有两个方法:  
 *     releaseLock 释放锁
 *     attemptLock 尝试获取锁
 *
 * 声明:
 *     假设有节点node_c   /node_a/node_b/node_c
 *     那么，node_c节点的名字为node_c; node_c节点的路径为/node_a/node_b/node_c
 *
 * @author JustryDeng
 * @date 2018/12/6 16:13
 */
public class BaseDistributedLock {

    /** ZkClient客户端 */
    private final MyZkClient client;

    /** /locker节点路径 */
    private final String lockerNodePath;

    /**
     *  当前客户端在/locker节点下的子节点 路径
     *  注:创建节点后，此路径并不是创建节点后生成的路径，因为是有序节点，所以会略有不同
     *
     *  如:当前节点路径为为/aspire/abc，那么创建【临时有序】节点后，实际上路径为  /aspire/abc0000000001
     */
    private final String currentNodePath;

    /**
     *  当前客户端在/locker节点下的子节点 的节点名
     *  注:创建节点后，此名字并不是创建节点后生成的名字，因为是有序节点，所以会略有不同
     *
     *  如:当前节点名字为abc，那么创建【临时有序】节点后，实际上名字为   abc0000000001
     */
    @SuppressWarnings("all")
    private final String currentNodeName;

    /** 网络闪断时的 重试次数 */
    private static final Integer MAX_RETRY_COUNT = 10;

    /**
     * 构造器
     */
    public BaseDistributedLock(MyZkClient client, String lockerNodePath, String currentNodeName) {
        this.client = client;
        this.lockerNodePath = lockerNodePath;
        this.currentNodePath = lockerNodePath.concat("/").concat(currentNodeName);
        this.currentNodeName = currentNodeName;
    }

    /**
     * 释放锁
     *
     * @author JustryDeng
     * @date 2018/12/6 17:33
     */
    protected void releaseLock(String nodePath) {
        deleteNode(nodePath);
    }


    /**
     * 尝试获取锁
     *
     * @param time
     *            最大等待时长
     * @param unit
     *            最大等待时长的 时间单位
     *
     * return 成功获取到锁，那么返回 当前客户端创建节点后得到的节点路径
     *        没有获取到锁，那么返回null
     * @date 2018/12/6 17:33
     */
    protected String attemptLock(long time, TimeUnit unit) throws Exception {

        final long startMillis = System.currentTimeMillis();
        final Long millisToWait = (unit != null) && (time != -1) ? unit.toMillis(time) : null;
        String finalCurrentNodePath = null;
        boolean gotTheLock = false;

        boolean isDone = false;
        int retryCount = 0;
        // 首次进入，都会进一次下列代码块儿;但当网络出现闪断时，会进行循环重试
        while (!isDone) {
            isDone = true;
            try {
                try {
                    // 创建临时有序子节点
                    finalCurrentNodePath = createEphemeralSequentialNode(currentNodePath, null);
                } catch (ZkNoNodeException e) {
                    // 如果有父节点不存在，那么先创建父节点,父节点路径即为:lockerNodePath
                    client.createPersistent(lockerNodePath, true);
                    // 再次创建临时有序子节点
                    createEphemeralSequentialNode(currentNodePath, null);
                } catch (ZkNodeExistsException e) {
                    // 由于网络闪断，导致多次进行此步骤的话，那么忽略
                }
                gotTheLock = waitToLock(startMillis, millisToWait, finalCurrentNodePath);
            } catch (ZkNoNodeException e) {
                if (retryCount++ < MAX_RETRY_COUNT) {
                    isDone = false;
                } else {
                    throw e;
                }
            }
        }
        if (gotTheLock) {
            return finalCurrentNodePath;
        }
        return null;
    }

    /**
     * 创建临时有序节点
     */
    private String createEphemeralSequentialNode(final String path, final Object data) {
        return client.createEphemeralSequential(path, data);
    }

    /**
     * 删除节点
     */
    private void deleteNode(String nodePath) {
        client.delete(nodePath);
    }

    /**
     * 等待获取锁
     *
     * @param startMillis
     *            等待开始时间
     * @param millisToWait
     *            最大等待时长
     * @param finalCurrentNodePath
     *            当前客户端对应的 在zookeeper上创建的节点 路径
     * @return  是否成功获取到锁
     * @throws  Exception
     *
     * @date 2018/12/6 18:14
     */
    private boolean waitToLock(long startMillis, Long millisToWait, String finalCurrentNodePath) throws Exception {
        boolean gotTheLock = false;
        boolean doDelete = false;
        try {
            while (!gotTheLock) {
                // 获取到/locker节点下的 按照 节点名 排序后的所有子节点
                List<String> children = getSortedChildren();
                // 获取当前客户端对应的节点的 节点名称
                String sequenceNodeName = finalCurrentNodePath.substring(lockerNodePath.length() + 1);
                // 获取当前客户端对应的节点 所在集合中的位置
                int ourIndex = children.indexOf(sequenceNodeName);
                if (ourIndex < 0) { // 如果集合中不存在该节点，那么抛出异常
                    throw new ZkNoNodeException("节点没有找到: " + sequenceNodeName);
                }
                // 当  当前客户端对应的节点  排在集合开头时，表示该此客户端获得锁
                boolean shouldGetTheLock = ourIndex == 0;
                // 当前客户端 应该监视的节点的名字
                String nodeNameToWatch = shouldGetTheLock ? null : children.get(ourIndex - 1);
                if (shouldGetTheLock) {
                    gotTheLock = true;
                } else {
                    // 组装当前客户端 应该监视的节点的路径
                    String previousSequencePath = lockerNodePath.concat("/").concat(nodeNameToWatch);
                    // 倒计时锁
                    final CountDownLatch latch = new CountDownLatch(1);
                    // 创建监听器
                    final IZkDataListener previousListener = new IZkDataListener() {
                        public void handleDataDeleted(String dataPath) {
                            latch.countDown();
                        }
                        public void handleDataChange(String dataPath, Object data) {
                            // ignore
                        }
                    };

                    try {
                        // 如果节点不存在会出现异常
                        client.subscribeDataChanges(previousSequencePath, previousListener);

                        if (millisToWait != null) {// 如果设置了等待时间，那么最多只等这么长时间
                            millisToWait -= (System.currentTimeMillis() - startMillis);
                            startMillis = System.currentTimeMillis();
                            if (millisToWait <= 0) { // 如果等待已经超时，那么需要删除当前客户端对应的临时有序节点
                                doDelete = true;
                                break;
                            }
                            // CountDownLatch#await
                            latch.await(millisToWait, TimeUnit.MICROSECONDS);
                        } else { // 如果没有设置等待时间，那么一直等待，知道获取到锁
                            // CountDownLatch#await
                            latch.await();
                        }
                    } catch (ZkNoNodeException e) {
                        //ignore
                    } finally {
                        client.unsubscribeDataChanges(previousSequencePath, previousListener);
                    }
                }
            }
        } catch (Exception e) {
            // 发生异常需要删除节点
            doDelete = true;
            throw e;
        } finally {
            // 如果需要删除节点
            if (doDelete) {
                deleteNode(finalCurrentNodePath);
            }
        }
        return gotTheLock;
    }


    /**
     * 按照子节点名字，升序排序
     *
     * @date 2018/12/6 18:14
     */
    private List<String> getSortedChildren() {
        try {
            List<String> children = client.getChildren(lockerNodePath);
            children.sort(Comparator.comparing(String::valueOf));
            return children;
        } catch (ZkNoNodeException e) {
            client.createPersistent(lockerNodePath, true);
            return getSortedChildren();
        }
    }

}