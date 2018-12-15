package com.aspire.zkclient.queue;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 队列测试
 *
 * @author JustryDeng
 * @date 2018/12/12 13:28
 */
public class QueueTest {

    /** 程序入口 */
    public static void main(String[] args) throws InterruptedException {
        // 测试:如果没有消息,那么返回null
        // pushAndPullTest();
        //测试:如果没有消息,那么进行等待,直到消费到了消息
        pushAndPullUntilGotMessageTest();
    }


    /**
     * 测试 队列实现方式一:
     *    如果没有消息，那么返回null
     */
    private static void pushAndPullTest() {

        // zookeeper地址端口
        final String IP_PORT = "10.8.109.32:2181";

        // 会话超时时间
        final int SESSION_TIMEOUT = 5000;

        // 连接超时时间
        final int CONNECTION_TIMEOUT = 5000;

        // 会话超时时间
        final String QUEUE_PATH = "/queue";

        // zookeeper客户端
        ZkClient zkClient = new ZkClient(IP_PORT, SESSION_TIMEOUT, CONNECTION_TIMEOUT, new SerializableSerializer());

        // 分布式队列示例
        DistributedQueueImpl<MessageDataTO> queueImpl = new DistributedQueueImpl<>(zkClient, QUEUE_PATH);

        // 数据
        final MessageDataTO messageDeng = new MessageDataTO(1L, "邓消息");
        final MessageDataTO messageLi = new MessageDataTO(2L, "李消息");
        try {
            // 消息生产者 生产(推送)两个消息
            String pathDeng = queueImpl.push(messageDeng);
            System.out.println("消息messageDeng对应的节点路径为" + pathDeng);
            String pathLi = queueImpl.push(messageLi);
            System.out.println("消息messageLi对应的节点路径为" + pathLi);

            Thread.sleep(1000);

            // 消息消费者 消费两个消息
            MessageDataTO gotMessageOne;
            try {
                gotMessageOne = queueImpl.pull();
            } catch (NodeDataIsNullException e) {
                // 说明消费到了消息，不过该消息本身九尾null
                gotMessageOne = new MessageDataTO();
            }
            MessageDataTO gotMessageTwo;
            try {
                gotMessageTwo = queueImpl.pull();
            } catch (NodeDataIsNullException e) {
                // 说明消费到了消息，不过该消息本身九尾null
                gotMessageTwo = new MessageDataTO();
            }

            System.out.println("理论上，得到的第一个消息应该是:" + messageDeng + "\t实际上，得到的第一个消息是:"
                               + gotMessageOne);
            System.out.println("理论上，得到的第二个消息应该是:" + messageLi + "\t实际上，得到的第一个消息是:"
                    + gotMessageTwo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试 队列实现方式二:
     *    如果没有消息，那么进行等待，直到消费到了消息
     */
    private static void pushAndPullUntilGotMessageTest() throws InterruptedException {

        // zookeeper地址端口
        final String IP_PORT = "10.8.109.32:2181";

        // 会话超时时间
        final int SESSION_TIMEOUT = 5000;

        // 连接超时时间
        final int CONNECTION_TIMEOUT = 5000;

        // 会话超时时间
        final String QUEUE_PATH = "/queue";

        // zookeeper客户端
        ZkClient zkClient = new ZkClient(IP_PORT, SESSION_TIMEOUT, CONNECTION_TIMEOUT, new SerializableSerializer());

        // 分布式队列示例
        DistributedQueueImpl<MessageDataTO> queueImpl = new DistributedQueueImpl<>(zkClient, QUEUE_PATH);


        // 创建 调度线程池
        ScheduledExecutorService delayExector = Executors.newScheduledThreadPool(1);
        // 延迟时间
        final int delayTime = 5;

        // 数据
        final MessageDataTO messageDeng = new MessageDataTO(1L, "邓消息");
        final MessageDataTO messageLi = new MessageDataTO(2L, "李消息");
        try {

            delayExector.schedule(() -> {
                    try {
                        System.out.println("---> 开始推送消息！");
                        // 消息生产者 生产(推送)两个消息
                        String pathDeng = queueImpl.push(messageDeng);
                        System.out.println("消息messageDeng对应的节点路径为" + pathDeng);
                        String pathLi = queueImpl.push(messageLi);
                        System.out.println("消息messageLi对应的节点路径为" + pathLi);
                        System.out.println("---> 推送消息完成！");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }, delayTime , TimeUnit.SECONDS);
            System.out.println("---> 开始试着拽取消息。。。");
            // 消息消费者 消费两个消息
            MessageDataTO gotMessageOne = queueImpl.pullUntilGotMessage();
            System.out.println("拽取(消费)到了消息:" + gotMessageOne);
            MessageDataTO gotMessageTwo = queueImpl.pullUntilGotMessage();
            System.out.println("拽取(消费)到了消息:" + gotMessageTwo);
            System.out.println("---> 拽取消息完成！");
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            // 关闭线程池,释放资源
            delayExector.shutdown();
            // 阻塞当前线程一段时间
            // 如果在这段时间结束之后，所有tasks还没有执行完毕，那么当前线程不再阻塞，往下执行
            // 如果在这段时间结束之前，所有tasks都执行完毕，那么不论这段时间还剩多久，都不再阻塞，往下执行
            delayExector.awaitTermination(10, TimeUnit.SECONDS);
        }
    }

}
