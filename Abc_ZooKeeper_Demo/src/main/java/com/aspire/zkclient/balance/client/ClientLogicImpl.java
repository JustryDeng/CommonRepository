package com.aspire.zkclient.balance.client;

import com.aspire.zkclient.balance.server.ServerData;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.List;

/**
 * client客户端的总体逻辑
 *
 * @author JustryDeng
 * @date 2018/12/4 15:46
 */
public class ClientLogicImpl {

	/** 实现负载均衡 方法的封装 */
	private final LoadBalanceImplProvider loadBalanceImplProvider;

	/** netty相关 */
	private EventLoopGroup eventLoopGroup = null;
	private Channel channel = null;
	
	/**
	 * 构造器
	 */
	public ClientLogicImpl(LoadBalanceImplProvider loadBalanceImplProvider) {
		this.loadBalanceImplProvider = loadBalanceImplProvider;
	}


	/**
	 * 连接
	 */
	public void connect(){
		
		try{
			// 获取候选节点 并 负载均衡算出 该用哪一个server
			List<ServerData> candidateServerDataList = loadBalanceImplProvider.getBalanceItems();
			ServerData serverData = loadBalanceImplProvider.balanceAlgorithm(candidateServerDataList);

			System.out.println("connecting to " + serverData.getHost() + ":"+serverData.getPort() + ", it's load:" + serverData.getLoad());
			// 初始化netty
			eventLoopGroup = new NioEventLoopGroup();
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(eventLoopGroup)
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<SocketChannel>() {
	            @Override
	            public void initChannel(SocketChannel ch) throws Exception {
	                ChannelPipeline channelPipeline = ch.pipeline();
	                // 设置信道事件处理器
					channelPipeline.addLast(new ClientHandler());
	            }
	        });
			ChannelFuture channelFuture = bootstrap.connect(serverData.getHost(),serverData.getPort()).syncUninterruptibly();
			channel = channelFuture.channel();
			System.out.println("started success!");
		}catch(Exception e){
			System.out.println("连接异常:" + e.getMessage());
		}
		
	}

	/**
	 * 断开连接
	 */
	public void disConnect(){
		try{
			if (channel!=null) {
				channel.close().syncUninterruptibly();
			}
			eventLoopGroup.shutdownGracefully();
			eventLoopGroup = null;
			System.out.println("disconnected!");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
