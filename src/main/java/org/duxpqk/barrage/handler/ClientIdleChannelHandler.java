package org.duxpqk.barrage.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.duxpqk.barrage.obj.Request;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class ClientIdleChannelHandler extends ChannelDuplexHandler {
	
	private static final Log LOG = LogFactory.getLog(ClientIdleChannelHandler.class);

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent e = (IdleStateEvent) evt;
			if (e.state() == IdleState.WRITER_IDLE) {
				LOG.info("开始发送心跳包...");
				ctx.writeAndFlush(Request.buildHearbeatRequest());
				LOG.info("发送心跳包成功！");
			}
		}
		super.userEventTriggered(ctx, evt);
	}
	
}
