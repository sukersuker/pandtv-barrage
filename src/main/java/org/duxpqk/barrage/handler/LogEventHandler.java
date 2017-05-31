package org.duxpqk.barrage.handler;

import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogEventHandler implements EventHandler {
	
	private static final transient Log LOG = LogFactory.getLog(LogEventHandler.class);

	private long startTime;
	
	@Override
	public void connect(Channel channel, SocketAddress remoteAddress) throws Exception {
		startTime = System.currentTimeMillis();
		InetSocketAddress remote = (InetSocketAddress) remoteAddress;
		LOG.info("开始连接弹幕服务器 [" + remote.getHostString() + ":" + remote.getPort() + "]");
	}
	
	@Override
	public void connected(Channel channel) throws Exception {
		LOG.info("已登录弹幕服务器，耗时：" + (System.currentTimeMillis() - startTime) + "毫秒");
	}

	@Override
	public void disconnect(Channel channel) throws Exception {
		InetSocketAddress address = (InetSocketAddress) channel.remoteAddress();
		LOG.info("与服务器 [" + address.getHostString() + ":" + address.getPort()  + "] 失去连接，结束弹幕接收！！！");
		channel.close();
	}

	@Override
	public void read(Channel channel, Object message) throws Exception {
		throw new Exception("接收到了弹幕消息，但是有处理！");
	}

}
