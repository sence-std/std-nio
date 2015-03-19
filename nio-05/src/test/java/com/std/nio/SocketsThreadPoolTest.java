/**
 * @FileName:SocketsThreadPoolTest
 * @Package: com.std.nio
 *
 * @author sence
 * @created 3/19/2015 4:15 PM
 *
 * Copyright 2011-2015 Asura
 */
package com.std.nio;

import com.std.nio.threadpool.SocketsThreadPool;
import org.junit.Test;

import java.io.IOException;

/**
 *
 * <p>多线程</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sence
 * @since 1.0
 * @version 1.0
 */
public class SocketsThreadPoolTest {

	@Test
	public void test() throws IOException {
		SocketsThreadPool socketsThread = new SocketsThreadPool();
		socketsThread.initServer(8080);
	}
}
