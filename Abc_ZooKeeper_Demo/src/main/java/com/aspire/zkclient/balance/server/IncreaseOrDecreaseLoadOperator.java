package com.aspire.zkclient.balance.server;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkBadVersionException;
import org.apache.zookeeper.data.Stat;

/**
 * 服务节 负载大小的增/减 实现逻辑
 *
 * @author JustryDeng
 * @date 2018/12/4 9:55
 */
public class IncreaseOrDecreaseLoadOperator {

	/** 服务器节点路径 */
	private String serverNodePath;

	/** zkClient客户端, 此类中的代码逻辑要求:此客户端必须是以SerializableSerializer序列化器创建的实例 */
	private ZkClient zkClient;

	public IncreaseOrDecreaseLoadOperator(String serverNodePath, ZkClient zkClient) {
		this.serverNodePath = serverNodePath;
		this.zkClient = zkClient;
	}

	/**
	 * 增加负载 --- 记数
	 *
	 * @author JustryDeng
	 * @date 2018/12/4 11:25
	 */
	public boolean increaseLoad(Integer step) {
		Stat stat = new Stat();
		ServerData serverData;
		while (true) {
			try {
				serverData = zkClient.readData(this.serverNodePath, stat);
				serverData.setLoad(serverData.getLoad() + step);
				// 为避免并发出错，修改数据时，要校验版本号
				zkClient.writeData(this.serverNodePath, serverData, stat.getVersion());
				return true;
			} catch (ZkBadVersionException e) {
				// 版本不对则重试
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	/**
	 * 降低负载 --- 记数
	 *
	 * @author JustryDeng
	 * @date 2018/12/4 11:25
	 */
	public boolean decreaseLoad(Integer step) {
		Stat stat = new Stat();
		ServerData serverData;
		while (true) {
			try {
				serverData = zkClient.readData(this.serverNodePath, stat);
				final Integer currentLoad = serverData.getLoad();
				serverData.setLoad(currentLoad > step ? currentLoad - step : 0);
				// 为避免并发出错，修改数据时，要校验版本号
				zkClient.writeData(this.serverNodePath, serverData, stat.getVersion());
				return true;
			} catch (ZkBadVersionException e) {
				// 版本不对则重试
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	}

}
