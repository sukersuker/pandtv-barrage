package org.duxpqk.barrage.obj;

import java.io.Serializable;

public class Response implements Serializable{

	private static final long serialVersionUID = 2690100323903261795L;

	private String type;
	
	private String time;
	
	private Data data;
	
	private String unlock_time;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
	
	public String getUnlock_time() {
		return unlock_time;
	}

	public void setUnlock_time(String unlock_time) {
		this.unlock_time = unlock_time;
	}
}