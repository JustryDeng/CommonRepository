package com.aspire.zkclient.balance.server;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

/**
 * server服务器的注册与注销
 *
 * @author JustryDeng
 * @date 2018/12/4 9:56
 */
public class RegistAndUnregistOperator {

	private String serverNodePath;
	private ZkClient zkClient;
	private ServerData serverData;

	public RegistAndUnregistOperator(String serverNodePath, ZkClient zkClient, ServerData serverData) {
		this.serverNodePath = serverNodePath;
		this.zkClient = zkClient;
		this.serverData = serverData;
	}

	/**
	 * 注册
	 */
	public void regist() {
		try {
			// 创建server对应的 临时节点
			zkClient.createEphemeral(serverNodePath, serverData);
		} catch (ZkNoNodeException e) { // 若serverNodePath的父节点不存在，那么创建持久的父节点
			String parentDir = serverNodePath.substring(0, serverNodePath.lastIndexOf('/'));
			zkClient.createPersistent(parentDir, true);
			regist();
		} catch (ZkNodeExistsException e) {
			System.out.println("该节点已存在！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 注销
	 */
	public void unRegist()  {
		try {
			// 由于我们一般都不会在server节点下创建子节点，所以delete/deleteRecursive都行
			zkClient.delete(serverNodePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}