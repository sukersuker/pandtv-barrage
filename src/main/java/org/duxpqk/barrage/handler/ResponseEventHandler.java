package org.duxpqk.barrage.handler;

import io.netty.channel.Channel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.duxpqk.barrage.obj.Content;
import org.duxpqk.barrage.obj.Response;

import com.alibaba.fastjson.JSONObject;

/**
 * 弹幕处理
 * @author duxp
 * @date 2017年5月26日 上午1:24:25
 */
public class ResponseEventHandler extends AbstractEventHandler {
	
	private ExecutorService executor;

	public ResponseEventHandler(EventHandler eventHandler) {
		super(eventHandler);
		executor = Executors.newSingleThreadExecutor();
	}

	@Override
	public void read(final Channel channel, final Object message) throws Exception {
		executor.execute(new Runnable() {
			
			@Override
			public void run() {
				processMsg(channel, message);
			}

		});
	}

	@SuppressWarnings("unchecked")
	private void processMsg(Channel channel, Object msg) {
		if (msg != null && msg instanceof List) {
			List<String> datas = (List<String>) msg;
			for (String value : datas) {
				if (value != null && !"".equals(value)) {
					try {
						JSONObject json = JSONObject.parseObject(value);
						Response response = json.toJavaObject(Response.class);
						
						if ("1".equals(response.getType())) {
							System.err.println("[" + response.getData().getFrom().getNickName() + "]: " + response.getData().getContent());
						} else {
							Object object = response.getData().getContent();
							if (object instanceof JSONObject) {
								JSONObject contentJson = (JSONObject) object;
								response.getData().setContent(contentJson.toJavaObject(Content.class));
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						System.err.println(value);
					}
				}
			}
		}
	}
	
}
