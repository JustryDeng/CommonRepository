package com.aspire.zkclient.balance.server;

import java.io.Serializable;


/**
 * 服务器数据模型
 * 注:由于其他逻辑需要，这里要保证其可实例化，可比较
 *
 * @author JustryDeng
 * @date 2018/12/4 11:28
 */
public class ServerData implements Serializable,Comparable<ServerData> {

	private static final long serialVersionUID = 4402636779492870030L;

	/** 当前服务器的负载大小 */
	private Integer load;

	/** 当前服务器的地址 */
	private String host;

	/** 当前服务器的端口 */
	private Integer port;

	public Integer getLoad() {
		return load;
	}

	public void setLoad(Integer load) {
		this.load = load;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	/**
	 * A.compareTo(B)
	 * A > B返回  1
	 * A == B返回 0
	 * A < B返回  -1
	 */
	@Override
	public int compareTo(ServerData anotherServerData) {
		return this.getLoad().compareTo(anotherServerData.getLoad());
	}

	@Override
	public String toString() {
		return "ServerData{load=" + load + ", host='" + host + "', port=" + port + '}';
	}
}
