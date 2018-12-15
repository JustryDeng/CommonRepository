package com.aspire.zkclient.balance.server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * server服务端测试函数
 *
 * @author JustryDeng
 * @date 2018/12/4 15:52
 */
public class ServerRunnerTest {

    private static final int  SIZE = 2;

	/** 连接zookeeper的ip和端口 */
    private static final String  IP_PORT = "10.8.109.60:2181";

    /** /servers节点路径 */
    private static final String  SERVERS_NODE_PATH = "/servers";

	/**
	 * 程序入口
	 */
	public static void main(String[] args) throws InterruptedException {

		List<Thread> threadList = new ArrayList<>(4);

		for(int i = 0; i < SIZE; i++){
			final Integer index = i;
			Thread thread = new Thread(new Runnable() {
				public void run() {
					ServerData serverData = new ServerData();
					serverData.setLoad(0);
					serverData.setHost("127.0.0.1");
					serverData.setPort(6000 + index);
					ServerLogicImpl server = new ServerLogicImpl(IP_PORT, SERVERS_NODE_PATH, serverData);
					server.bind();
				}
			});
			threadList.add(thread);
			thread.start();
		}

		// 为观察其他输出，主线程阻塞一段时间
		TimeUnit.SECONDS.sleep(60);

	}

}