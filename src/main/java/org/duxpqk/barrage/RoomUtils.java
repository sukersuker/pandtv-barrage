package org.duxpqk.barrage;

import java.io.IOException;
import java.net.URI;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 房间信息帮助工具
 * @author duxp
 * @date 2017年5月25日 上午9:23:11
 */
public class RoomUtils {
	
	private static final Log LOG = LogFactory.getLog(RoomUtils.class);
	
	private static Properties config = new Properties();
	
	static {
		try {
			if (LOG.isInfoEnabled()) {
				LOG.info("开始加载配置文件...");
			}
			config.load(RoomUtils.class.getResourceAsStream("/room.properties"));
			LOG.info("配置文件加载成功");
		} catch (IOException ex) {
			if (LOG.isErrorEnabled()) {
				LOG.error("配置文件加载失败");
			}
			ex.printStackTrace();
		}
	}

	private RoomUtils() {}
	
	public static String getRoomId() {
		return config.getProperty("roomId");
	}
	
	public static Room getRoomInfo() {
		Room sign = getSign();
		if (sign != null) {
			try {
				if (LOG.isInfoEnabled()) {
					LOG.info("开始获取弹幕服务器地址...");
				}
				HttpClient client = HttpClients.createDefault();
				URI uri = new URIBuilder(SERVER_URL).setParameter("rid", sign.getRid()).setParameter("roomid", sign.getRoomid()).setParameter("retry", "0").setParameter("sign", sign.getSign()).setParameter("ts", sign.getTs()).setParameter("_", System.currentTimeMillis() + "").build();
				HttpGet httpGet = new HttpGet(uri);
				sign = client.execute(httpGet, new RoomResponseHandler());
				if (sign != null && "0".equals(sign.getErrno())) {
					if (LOG.isInfoEnabled()) {
						LOG.info("获取弹幕服务器成功，地址列表：" + sign.getChatAddrList());
					}
				} else {
					if (LOG.isErrorEnabled()) {
						LOG.error("获取弹幕服务器失败，[errCode: " + sign.getErrno() + "errMsg: " + sign.getErrmsg() + "]");
					}
				}
				return sign;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private static Room getSign() {
		if (LOG.isInfoEnabled()) {
			LOG.info("开始获取房间签名信息...");
		}
		String roomId = getRoomId();
		try {
			HttpClient client = HttpClients.createDefault();
			URI uri = new URIBuilder(SING_URL).setParameter("roomid", roomId).setParameter("_", System.currentTimeMillis() + "").build();
			HttpGet httpGet = new HttpGet(uri);
			Room sign = client.execute(httpGet, new RoomResponseHandler());
			if (sign != null && "0".equals(sign.getErrno())) {
				if (LOG.isInfoEnabled()) {
					LOG.info("获取房间签名信息成功，sign：" + sign.sign());
				}
			} else {
				if (LOG.isErrorEnabled()) {
					LOG.error("获取房间签名信息失败，[errCode: " + sign.getErrno() + "errMsg: " + sign.getErrmsg() + "]");
				}
			}
			return sign;
		} catch (Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.error("获取房间签名信息失败！" + e.getMessage());
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.err.println(new Random().nextInt(3) + 1);
		}
	}
	
	private static final String SING_URL = "http://www.panda.tv/ajax_chatroom";
	
	private static final String SERVER_URL = "http://api.homer.panda.tv/chatroom/getinfo";
}

class RoomResponseHandler implements ResponseHandler<Room> {
	
	@Override
	public Room handleResponse(HttpResponse response) {
		try {
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				String result = EntityUtils.toString(entity,"utf-8");
				JSONObject json = JSONObject.parseObject(result);
				String errmsg = json.getString("errmsg");
				String errno = json.getString("errno");
				JSONObject data = json.getJSONObject("data");
				Room info = null;
				if (data != null) {
					info = data.toJavaObject(Room.class);
				} else {
					info = new Room();
				}
				info.setErrmsg(errmsg);
				info.setErrno(errno);
				return info;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
