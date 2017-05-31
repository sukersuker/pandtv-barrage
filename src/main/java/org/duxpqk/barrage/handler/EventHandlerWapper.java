package org.duxpqk.barrage.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;

public class EventHandlerWapper extends ChannelDuplexHandler {
	
	private EventHandler eventHandler;

	public EventHandlerWapper(EventHandler eventHandler) {
		if (eventHandler == null) {
			throw new IllegalArgumentException("弹幕处理类不能为空！！！");
		}
		this.eventHandler = eventHandler;
	}

	@Override
	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress,SocketAddress localAddress, ChannelPromise promise) throws Exception {
		super.connect(ctx, remoteAddress, localAddress, promise);
		this.eventHandler.connect(ctx.channel(), remoteAddress);
	}

	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		super.disconnect(ctx, promise);
		this.eventHandler.disconnect(ctx.channel());
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		this.eventHandler.connected(ctx.channel());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		super.channelRead(ctx, msg);
		this.eventHandler.read(ctx.channel(), msg);
	}
	
}
