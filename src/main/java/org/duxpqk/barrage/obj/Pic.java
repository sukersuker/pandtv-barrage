package org.duxpqk.barrage.obj;

import java.io.Serializable;

public class Pic implements Serializable {
	
	private static final long serialVersionUID = 8926050092877515668L;
	
	private Pc pc;
	
	private String m_icon;
	
	public Pic() {}

	public Pc getPc() {
		return pc;
	}

	public void setPc(Pc pc) {
		this.pc = pc;
	}

	public String getM_icon() {
		return m_icon;
	}

	public void setM_icon(String m_icon) {
		this.m_icon = m_icon;
	}
	
}