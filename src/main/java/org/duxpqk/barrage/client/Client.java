package org.duxpqk.barrage.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.duxpqk.barrage.codec.Decoder;
import org.duxpqk.barrage.codec.Encoder;
import org.duxpqk.barrage.handler.ClientIdleChannelHandler;
import org.duxpqk.barrage.handler.EventHandlerWapper;
import org.duxpqk.barrage.handler.LogEventHandler;
import org.duxpqk.barrage.handler.ResponseEventHandler;
import org.duxpqk.barrage.obj.Request;

/**
 * 客户端
 * 实现连接弹幕服务器获取弹幕数据
 * 无池化，觉得没什么意义，后面在做
 * @author duxp
 * @date 2017年5月25日 下午10:24:07
 */
public class Client {
	
	private static final Log logger = LogFactory.getLog(Client.class);
	
	public static final int CONNECT_TIMEOUT = 5000;
	
	private Bootstrap bootstrap;
	
	private Channel channel;
	
	public boolean connect(final String host, final int port) {
		bootstrap = new Bootstrap();
		bootstrap.group(ClientNioThreadPool.getShareClientNioThreadPool());
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.option(ChannelOption.TCP_NODELAY, true);
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECT_TIMEOUT);
		bootstrap.option(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT);
		bootstrap.handler(new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel ch) throws Exception {
				ch.pipeline().addLast(new IdleStateHandler(0, 60000, 0, TimeUnit.MILLISECONDS), new Encoder(), new Decoder(),  new ClientIdleChannelHandler(), new EventHandlerWapper(new ResponseEventHandler(new LogEventHandler())));
			}
		});
		
		ChannelFuture f = bootstrap.connect(host, port);
		boolean rtn = f.awaitUninterruptibly(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
		if (!f.isSuccess() || !rtn) {
			logger.error("连接弹幕服务器 [" + host + ":" + port + "] 失败，cause：" + f.cause());
			return false;
		}
		this.channel = f.channel();
		this.channel.closeFuture().addListener(new ChannelFutureListener() {
			
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				logger.info("退出弹幕服务器 [" + host + ":" + port + "]");
			}
		});
		
		return true;
	}
	
	public void send(final Request request) {
		if (request.isHeartbeat()) {
			logger.info("开始发送心跳包...");
		} else {
			logger.info("开始发送弹幕请求...");
		}
		ChannelFuture f = this.channel.writeAndFlush(request);
		f.addListener(new ChannelFutureListener() {
			
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if (future.isSuccess()) {
					if (request.isHeartbeat()) {
						logger.info("发送心跳包成功！");
					} else {
						logger.info("发送弹幕请求成功！");
					}
				} else {
					if (request.isHeartbeat()) {
						logger.error("发送心跳包失败！cause：" + future.cause());
					} else {
						logger.error("发送弹幕请求失败！cause：" + future.cause());
					}
					
				}
			}
		});
	}
	
	public void waitToClose() {
		try {
			this.channel.closeFuture().sync();
		} catch (InterruptedException e) {
			logger.info("与弹幕服务器断开连接！！！");
		}
	}
	
	public void close() {
		if (this.channel != null) {
			logger.info("开始停止弹幕接收...");
			this.channel.close();
		}
	}
	
}
