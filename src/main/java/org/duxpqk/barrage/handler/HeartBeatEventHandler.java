package org.duxpqk.barrage.handler;

import io.netty.channel.Channel;

import java.net.InetSocketAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.duxpqk.barrage.obj.Response;

/**
 * 心跳包处理
 * @author duxp
 * @date 2017年5月26日 上午1:34:48
 */
public class HeartBeatEventHandler extends AbstractEventHandler {
	
	private static final Log LOG = LogFactory.getLog(HeartBeatEventHandler.class); 

	public HeartBeatEventHandler(EventHandler eventHandler) {
		super(eventHandler);
	}

	@Override
	public void read(Channel channel, Object message) throws Exception {
		handleHeartbaetResponse(channel, (Response) message);
	}
	
	private void handleHeartbaetResponse(Channel channel, Response reponse) {
		if (LOG.isInfoEnabled()) {
			InetSocketAddress address = (InetSocketAddress) channel.remoteAddress();
			LOG.info("接收到弹幕服务器 [" + address.getHostString() + ":" + address.getPort() + "] 心跳包...");
		}
	}

}
