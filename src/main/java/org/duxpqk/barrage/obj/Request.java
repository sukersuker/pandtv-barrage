package org.duxpqk.barrage.obj;

import java.io.Serializable;

import org.duxpqk.barrage.Room;

/**
 * 弹幕请求
 * @author duxp
 * @date 2017年5月25日 上午10:59:44
 */
public class Request implements Serializable {
	
	private static final long serialVersionUID = 4318855297840034277L;

	private String value;
	
	private boolean isHeartbeat;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isHeartbeat() {
		return isHeartbeat;
	}

	public void setHeartbeat(boolean isHeartbeat) {
		this.isHeartbeat = isHeartbeat;
	}
	
	public static Request buildRequest(Room room) {
		Request request = new Request();
		request.setHeartbeat(false);
		request.setValue(room.buildDanmuMsg());
		return request;
	}
	
	public static Request buildHearbeatRequest() {
		Request request = new Request();
		request.setHeartbeat(true);
		return request;
	}
	
}
