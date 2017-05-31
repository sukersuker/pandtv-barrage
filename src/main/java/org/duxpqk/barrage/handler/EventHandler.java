package org.duxpqk.barrage.handler;

import io.netty.channel.Channel;

import java.net.SocketAddress;

public interface EventHandler {

	void connect(Channel channel, SocketAddress remoteAddress) throws Exception;
	
	void connected(Channel channel) throws Exception;
	
	void disconnect(Channel channel) throws Exception;
	
	void read(Channel channel, Object message) throws Exception;
}
