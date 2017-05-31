package org.duxpqk.barrage.obj;

import java.io.Serializable;

public class Pc implements Serializable {
	
	private static final long serialVersionUID = -813790726209120365L;

	private String chat;
	
	private String effect;
	
	public Pc() {}

	public String getChat() {
		return chat;
	}

	public void setChat(String chat) {
		this.chat = chat;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}
	
}