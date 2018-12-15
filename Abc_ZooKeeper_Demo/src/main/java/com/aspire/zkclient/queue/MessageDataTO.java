package com.aspire.zkclient.queue;

import java.io.Serializable;

/**
 * 数据封装类
 *
 * 注:由于创建zookeeper客户端ZkClient时，使用的序列化器是SerializableSerializer，
 *    所以此类需要可序列化，即:需要实现功能性接口Serializable
 *
 * @author JustryDeng
 * @date 2018/12/12 16:33
 */
public class MessageDataTO implements Serializable {

	private static final long serialVersionUID = -3251695575975145393L;

	/** 信息ID */
	private long id;

	/** 信息名字 */
	private String name;

	/**
	 * 无参构造
	 */
	public MessageDataTO() {
	}

	/**
	 * 全参构造
	 */
	public MessageDataTO(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "MessageDataTO{id=" + id + ", name='" + name + "'}'";
	}
}
