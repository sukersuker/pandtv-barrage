package org.duxpqk.barrage.obj;

import java.io.Serializable;

public class From implements Serializable {
	
	private static final long serialVersionUID = -8252532972572054018L;

	private String identity;
	
	private String nickName;
	
	private String badge;
	
	private String rid;
	
	private String msgcolor;
	
	private String level;
	
	private String sp_Identity;
	
	private String __plat;
	
	private String userName;
	
	public From() {}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getBadge() {
		return badge;
	}

	public void setBadge(String badge) {
		this.badge = badge;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getMsgcolor() {
		return msgcolor;
	}

	public void setMsgcolor(String msgcolor) {
		this.msgcolor = msgcolor;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getSp_Identity() {
		return sp_Identity;
	}

	public void setSp_Identity(String sp_Identity) {
		this.sp_Identity = sp_Identity;
	}

	public String get__plat() {
		return __plat;
	}

	public void set__plat(String __plat) {
		this.__plat = __plat;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}