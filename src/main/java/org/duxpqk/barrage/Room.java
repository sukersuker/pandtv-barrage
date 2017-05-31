package org.duxpqk.barrage;

import java.util.List;
import java.util.Random;

/**
 * 房间签名
 * @author duxp
 * @date 2017年5月25日 上午9:37:56
 */
public class Room {

	// 房间号
	private String roomid;
	
	// 随机id
	private String rid;
	
	// 签名
	private String sign;
	
	// 时间戳
	private String ts;
	
	// 错误码
	private String errno;
	
	// 错误消息
	private String errmsg;
	
	private String appid;
	
	// 认证方式
	private String authType;
	
	// 服务器地址列表
	private List<String> chatAddrList;

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getErrno() {
		return errno;
	}

	public void setErrno(String errno) {
		this.errno = errno;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
	public String sign() {
		return "[" + "roomid: " + roomid + ", rid: " + rid + ", sign: " + sign + ", ts: " + ts + "]";
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public List<String> getChatAddrList() {
		return chatAddrList;
	}

	public void setChatAddrList(List<String> chatAddrList) {
		this.chatAddrList = chatAddrList;
	}
	
	public String getRandomHost() {
		if (chatAddrList != null) {
			int size = chatAddrList.size();
			if (size > 0) {
				return chatAddrList.get(new Random().nextInt(size));
			}
		}
		return null;
	}
	
	public final String K = "1";
	
	public final String T = "300";
	
	public String buildDanmuMsg() {
		return "u:" + rid + "@" + appid + "\n"
				+ "k:" + K + "\n"
				+ "t:" + T + "\n"
				+ "ts:" + ts + "\n"
				+ "sign:" + sign + "\n"
				+ "authtype:" + authType;
	}
}
