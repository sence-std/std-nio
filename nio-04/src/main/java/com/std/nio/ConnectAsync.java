/**
 * @FileName:ConnectAsync
 * @Package: com.std.nio
 *
 * @author sence
 * @created 3/17/2015 4:39 PM
 *
 * Copyright 2011-2015 Asura
 */
package com.std.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 *
 * <p>同步和异步socketChannel</p>
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
public class ConnectAsync {

	public void initSocket(int port,boolean isBlock) throws IOException {

		SocketChannel sc = SocketChannel.open();
		sc.configureBlocking(isBlock);
		sc.connect(new InetSocketAddress("127.0.0.1",port));
		while(!sc.finishConnect()){
			System.out.println("not connect yet...");
		}
		System.out.println("yes you are connect");
		sc.close();
	}

}
