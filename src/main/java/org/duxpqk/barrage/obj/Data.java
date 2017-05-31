package org.duxpqk.barrage.obj;

import java.io.Serializable;

public class Data implements Serializable {
	
	private static final long serialVersionUID = 88361842315259335L;

	private From from;
	
	private To to;
	
	private Object content;
	
	public Data() {}

	public From getFrom() {
		return from;
	}

	public void setFrom(From from) {
		this.from = from;
	}

	public To getTo() {
		return to;
	}

	public void setTo(To to) {
		this.to = to;
	}
	
	public  Object getContent() {
		return this.content;
	}
	
	public void setContent(Object content) {
		this.content = content;
	}
	
}