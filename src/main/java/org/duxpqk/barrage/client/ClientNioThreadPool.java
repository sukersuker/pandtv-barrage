package org.duxpqk.barrage.client;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 共享线程池
 * @author duxp
 * @date 2017年5月25日 下午10:27:40
 */
public class ClientNioThreadPool {

	private static final EventLoopGroup SHARE_CLIENT_WORKER_GROUP = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2, new ThreadFactory() {
		
		private AtomicLong THREAD_COUNTER = new AtomicLong(1);
		
		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setName("PandTV-NIO-" + THREAD_COUNTER.get());
			t.setDaemon(true);
			return t;
		}
	});
	
	public static EventLoopGroup getShareClientNioThreadPool() {
		return SHARE_CLIENT_WORKER_GROUP;
	}
	
}
