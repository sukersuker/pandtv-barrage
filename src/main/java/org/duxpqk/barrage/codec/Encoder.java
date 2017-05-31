package org.duxpqk.barrage.codec;

import java.io.IOException;

import org.duxpqk.barrage.obj.Request;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码器
 * @author duxp
 * @date 2017年5月25日 上午10:58:50
 */
public class Encoder extends MessageToByteEncoder<Request>{
	
	public static final byte[] HEARTBEAT = new byte[]{0x00, 0x06, 0x00, 0x00};
	
	public static final byte[] REQUEST_HEADERS = new byte[]{0x00, 0x06, 0x00, 0x02, 0x00};

	@Override
	protected void encode(ChannelHandlerContext ctx, Request request, ByteBuf out) throws Exception {
		if (request.isHeartbeat()) {
			out.writeBytes(HEARTBEAT);
		} else {
			out.writeBytes(REQUEST_HEADERS);
			out.writeByte(request.getValue().length());
			encodyBody(out, request.getValue());
		}
	}

	private void encodyBody(ByteBuf out, String msg) throws IOException {
		ByteBufOutputStream streamOut = new ByteBufOutputStream(out);
		streamOut.writeBytes(msg);
		streamOut.flush();
		streamOut.close();
	}

}
