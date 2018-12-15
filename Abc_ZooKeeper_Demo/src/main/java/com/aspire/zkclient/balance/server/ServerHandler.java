
package com.aspire.zkclient.balance.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 创建一个处理器，来处理信道事件
 *
 * @author JustryDeng
 * @date 2018/12/4 11:02
 */
public class ServerHandler extends ChannelHandlerAdapter{

    /** 增加/减少 负载大小的实现逻辑封装 */
	private final IncreaseOrDecreaseLoadOperator loadOperator;

	/** 步进，即:server负载因子增长步进 */
	private static final Integer BALANCE_STEP = 1; 

    public ServerHandler(IncreaseOrDecreaseLoadOperator loadOperator){
    	this.loadOperator = loadOperator;
    	
    } 

    public IncreaseOrDecreaseLoadOperator getLoadOperator() {
		return loadOperator;
	}	
	
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	System.out.println("one client connect...");
        loadOperator.increaseLoad(BALANCE_STEP);
    }
	
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        loadOperator.decreaseLoad(BALANCE_STEP);
    }

	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable throwable) {
        throwable.printStackTrace();
        ctx.close();
    }

}