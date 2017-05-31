package org.duxpqk.barrage.client;

import org.duxpqk.barrage.Room;
import org.duxpqk.barrage.RoomUtils;
import org.duxpqk.barrage.obj.Request;


/**
 * 弹幕抓取
 * @author duxp
 * @date 2017年5月23日 上午11:02:36
 */
public class Crawler { 
	
	public boolean init() throws Exception {
		Room room = RoomUtils.getRoomInfo();
		
		return room != null;
	}
	
	public void start() {
		Room room = RoomUtils.getRoomInfo();
		if (room == null) {
			return ;
		}
		String host = room.getRandomHost();
		if (host != null) {
			String[] hosts = host.split(":");
			if (hosts.length == 2) {
				Client client = new Client();
				boolean conneted = client.connect(hosts[0], Integer.valueOf(hosts[1]));
				if (conneted) {
					client.send(Request.buildRequest(room));
					client.send(Request.buildHearbeatRequest());
					client.waitToClose();
				}
			}
		}
	}
	
}

