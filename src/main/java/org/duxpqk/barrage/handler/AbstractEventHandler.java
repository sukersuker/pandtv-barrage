package org.duxpqk.barrage.handler;

import io.netty.channel.Channel;

import java.net.SocketAddress;

public abstract class AbstractEventHandler implements EventHandler {
	
	private EventHandler eventHandler;
	
	public AbstractEventHandler(EventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}

	@Override
	public void connect(Channel channel, SocketAddress remoteAddress) throws Exception {
		this.eventHandler.connect(channel, remoteAddress);
	}

	@Override
	public void connected(Channel channel) throws Exception {
		this.eventHandler.connected(channel);
	}

	@Override
	public void disconnect(Channel channel) throws Exception {
		this.eventHandler.disconnect(channel);
	}
	
}
