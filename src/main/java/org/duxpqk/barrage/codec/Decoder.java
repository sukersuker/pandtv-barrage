package org.duxpqk.barrage.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.ArrayList;
import java.util.List;

import org.duxpqk.barrage.StringUtils;

public class Decoder extends ByteToMessageDecoder {
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		Object decoded = decode(ctx, in);
        if (decoded != null) {
            out.add(decoded);
        }
	}

	private Object decode(ChannelHandlerContext ctx, ByteBuf in) {
		if (in.readableBytes() < 4) {
			return null;
		}
		
		int readerIndex = in.readerIndex();
		
		byte[] headers = new byte[4];
		in.readBytes(headers);
		
		int frameLength = 0;
		if (isData(headers)) {
			// 跳过7个无用字节
			in.skipBytes(7); 
			// 读取4个字节，得到数据长度
	        for (int i = 3; i >= 0; i--) {
	            int n = in.readByte();
	            if (n < 0) {
	            	n += 256;
				}
	            frameLength += n * Math.pow(16, 2 * i);
	        }
	        if (in.readableBytes() < frameLength) {
	        	in.readerIndex(readerIndex); // 半包重置读索引
				return null;
			}
	        byte[] danmu = new byte[frameLength];
	        in.readBytes(danmu);
	        
	        byte[] bytes = new byte[4];
	        System.arraycopy(danmu, 8, bytes, 0, 4);
	        
	        List<String> result = new ArrayList<String>();
	        // 可能有多条弹幕数据
	        for (int i = 0; i < frameLength;) {
	        	if (danmu[i] == bytes[0] && danmu[i+1] == bytes[1] && danmu[i+2] == bytes[2] && danmu[i+3] == bytes[3]) {
	        		i += 4;
	        		int length = 0;
	        		for (int j = 0, k = 3; j < 4; j++, k--) {
	        			int n = danmu[i + j];
	        			if (n < 0) {
	        				n += 256;
	        			}
	        			length += n * Math.pow(16, 2 * k);
	        		 }
	        		 i += 4;
	        		 byte[] data = new byte[length];
	        		 if ((danmu.length - i) < length) {
	        			 System.err.println("##########");
	        		 }
	        		 System.arraycopy(danmu, i, data, 0, length);
	        		 result.add(StringUtils.unicode2String(new String(data)));
	        		 i += length;
	        	} else {
	        		i++;
	        	}
	        }
	        return result;
		} else if(isNullData(headers)) {
			// 读取2个字节，得到数据长度
	        for (int i = 1; i >= 0; i--) {
	            int b = in.readByte();
	            frameLength += b * Math.pow(16, 2 * i);
	        }
	        in.skipBytes(frameLength);
		} else if(isHeartbeatData(headers)) {
			
		}
		return null;
	}
	
	private boolean isData(byte[] headers) {
		for (int i = 0; i < headers.length; i++) {
			if (headers[i] != DATA_HEADER[i]) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isNullData(byte[] headers) {
		for (int i = 0; i < headers.length; i++) {
			if (headers[i] != NULL_HEADER[i]) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isHeartbeatData(byte[] headers) {
		for (int i = 0; i < headers.length; i++) {
			if (headers[i] != HERATBEAT_HEADER[i]) {
				return false;
			}
		}
		return true;
	}
	
	private static final byte[] DATA_HEADER = new byte[] {0x00, 0x06, 0x00, 0x03};
	private static final byte[] NULL_HEADER = new byte[] {0x00, 0x06, 0x00, 0x06};
	private static final byte[] HERATBEAT_HEADER = new byte[] {0x00, 0x06, 0x00, 0x01};
	
}
