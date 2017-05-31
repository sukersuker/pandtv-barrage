package org.duxpqk.barrage.obj;

import java.io.Serializable;

public class To implements Serializable {
	
	private static final long serialVersionUID = 5335590481224960033L;
	
	private String toroom;
	
	public To() {}

	public String getToroom() {
		return toroom;
	}

	public void setToroom(String toroom) {
		this.toroom = toroom;
	}
	
}