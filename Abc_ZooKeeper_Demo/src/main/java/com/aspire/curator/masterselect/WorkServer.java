package com.aspire.curator.masterselect;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

import java.io.Closeable;

/**
 * 集群中的服务器
 *
 * 注:继承LeaderSelectorListenerAdapter类的目的是:
 *       重写takeLeadership方法，需要时还可重写stateChanged方法
 * 注:实现Closeable的目的是:
 *       更优雅地关闭释放资源
 * @author JustryDeng
 * @date 2018/11/28 9:33
 */
public class WorkServer extends LeaderSelectorListenerAdapter implements Closeable {

    /** 服务器的基本属性，主要用来区分是不同的服务器 */
    private final String serverName;

    /** curator提供的监听器 */
    private final LeaderSelector leaderSelector;

    /** takeLeadership方法中设置线程阻塞多长时间，单位ms */
    private final int SLEEP_MILLISECOND = 100000;

    public WorkServer(CuratorFramework client, String path, String serverName) {
        this.serverName = serverName;
        // 传入客户端、监听路径、监听器(这里采用本实例this作为监听器)
        leaderSelector = new LeaderSelector(client, path, this);

        /*
         * By default, when {@link LeaderSelectorListener#takeLeadership(CuratorFramework)} returns,
         * this instance is not requeued. Calling this method puts the leader selector into a mode
         * where it will always requeue itself.
         * 即:当WorkServer从takeLeadership()退出时它就会放弃了Leader身份，
         *    这时Curator会利用Zookeeper再从剩余的WorkServer中选出一个新的Leader。
         *    autoRequeue()方法使放弃Leader身份的WorkServer有机会重新获得Leader身份，
         *    如果不设置的话放弃了的WorkServer是不会再变成Leader的。
         */

        leaderSelector.autoRequeue();
    }

    /**
     * LeaderSelector提供好了的 竞争群首的方法，直接调用即可
     *
     * @author JustryDeng
     * @date 2018/11/28 12:27
     */
    public void start() {
        leaderSelector.start();
        System.out.println("此时" + getServerName() + "开始运行了！");
        // TODO 可在开始后do something
    }

    /**
     * Shutdown this selector and remove yourself from the leadership group
     * 关闭此Server，并从竞争群首组里面移除此Server成员
     *
     * @author JustryDeng
     * @date 2018/11/28 11:37
     */
    @Override
    public void close() {
        leaderSelector.close();
        System.out.println("此时" + getServerName() + "释放资源了！");
        // TODO 可在关闭后do something
    }

    /**
     * 当 当前服务器被选中作为群首Leader时，会调用执行此方法！
     *
     * 注:由于此方法结束后，对应的workerServer就会放弃leader，所以我们不能让此方法马上结束
     *
     * @param client
     *            curator客户端
     * @date 2018/11/28 11:36
     */
    @Override
    public void takeLeadership(CuratorFramework client) {
        try {
            System.out.println("此时" + getServerName() + "是群首！执行到takeLeadership()方法了！");
            Thread.sleep(SLEEP_MILLISECOND);
            // TODO 可do something
        } catch ( InterruptedException e ) {
            // 当此方法未运行完就调用了close()方法时，就会触发此异常
            // 记录一下InterruptedException的发生
            System.err.println(getServerName() + " was interrupted!");
            Thread.currentThread().interrupt();
        } finally {
            // TODO 可do something
        }
    }

    public String getServerName() {
        return serverName;
    }

    public LeaderSelector getLeaderSelector() {
        return leaderSelector;
    }

}
