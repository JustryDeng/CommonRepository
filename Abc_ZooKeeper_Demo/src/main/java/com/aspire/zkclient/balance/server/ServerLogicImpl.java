package com.aspire.zkclient.balance.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

/**
 * server服务器的总体逻辑
 *
 * @author JustryDeng
 * @date 2018/12/4 15:46
 */
public class ServerLogicImpl {
	
	/** 连接ZooKeeper的ip和端口 */
	private String ipAndPort;

	/** servers节点路径 */
	private String serversNodePath;

	/** 要在servers节点下创建的子节点的全路径 */
	private String currentServerPath;

	/** 当前节点数据 */
	private ServerData currentServerData;

	/** zkclient会话超时时间 */
	private static final Integer SESSION_TIME_OUT = 10000;

	/** zkclient连接超时时间 */
	private static final Integer CONNECT_TIME_OUT = 10000;

	/** zookeeper节点路径分隔符 */
	private final String NODE_SEPARATOR = "/";

	/** zkClient客户端 */
	private final ZkClient zkClient;

	/** server服务器注册与注销的逻辑封装类 */
	private final RegistAndUnregistOperator registAndUnregistOperator;

	/**
	 * netty相关
	 */
	private EventLoopGroup bossGroup = new NioEventLoopGroup();
	private EventLoopGroup workGroup = new NioEventLoopGroup();
	private ServerBootstrap bootStrap = new ServerBootstrap();
	private ChannelFuture channelFuture;
	/** netty相关 之  ServerBootstrap与当前server的端口 的绑定标识*/
	private volatile boolean binded = false;


    /**
     * 构造器
	 * 
	 * @param ipAndPort
	 *            连接ZooKeeper的ip和端口
	 * @param serversNodePath
	 *            当前服务器的节点路径
	 * @param serverData
	 *            当前服务器节点的数据
     * @author JustryDeng
     * @date 2018/12/4 12:26
     */
	public ServerLogicImpl(String ipAndPort, String serversNodePath, ServerData serverData){
		this.ipAndPort = ipAndPort;
		this.serversNodePath = serversNodePath;
		this.currentServerData = serverData;
		// 这里以端口号作为当前服务器对应的节点名，那么全路径节点名
		this.currentServerPath = serversNodePath.concat(NODE_SEPARATOR).concat(serverData.getPort().toString());
		// 根据逻辑需要，这里创建以SerializableSerializer为序列化工具的zkClient
		this.zkClient = new ZkClient(this.ipAndPort, SESSION_TIME_OUT, CONNECT_TIME_OUT, new SerializableSerializer());
		this.registAndUnregistOperator = new RegistAndUnregistOperator(currentServerPath, zkClient, serverData);
	}

	/**
	 * server的逻辑实现
	 *
	 * @author JustryDeng
	 * @date 2018/12/4 12:43
	 */
	public void bind() {
		// 如果当前server对应的端口已经bind过了，那么进行短路
		if (binded){
			return;
		}
		System.out.println(currentServerData.getPort() + ":binding...");
		try {
			// 将当前server,注册到zookeeper
			registAndUnregistOperator.regist();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		// ServerBootstrap负责初始化netty服务器
		bootStrap.group(bossGroup,workGroup)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024)
				// 向pipeline中添加 处理业务的handler
		        .childHandler(new ChannelInitializer<SocketChannel>() {
                	@Override
                	public void initChannel(SocketChannel socketChannel) {
                	    ChannelPipeline channelPipeline = socketChannel.pipeline();
                	    // 我们添加自定义的 ServerHandler来处理相关事件
						channelPipeline.addLast(new ServerHandler(new IncreaseOrDecreaseLoadOperator(currentServerPath,zkClient)));
                	}
                });

		try {
			// 绑定端口，开始监听端口的socket请求；
			// 当这个方法执行后，ServerBootstrap就可以接受指定端口上的socket连接了
			channelFuture =  bootStrap.bind(currentServerData.getPort()).sync();
			binded = true;
			System.out.println(currentServerData.getPort() + ":binded...");
			// 使当前线程进入阻塞,当对应的子线程退出后，再往下执行；类似于CountDownLatch的await方法
			channelFuture.channel().closeFuture().sync();			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
		
	}

}