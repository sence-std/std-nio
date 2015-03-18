/**
 * @FileName:ChannelAccpet
 * @Package: com.std.nio
 *
 * @author sence
 * @created 3/17/2015 4:02 PM
 *
 * Copyright 2011-2015 Asura
 */
package com.std.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 *
 * <p>设置ServerSocketChannel 阻塞和非阻塞模式</p>
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
public class ChannelAccpet {


	public void initServerSocket(int port,boolean isBlock) throws IOException, InterruptedException {
		ByteBuffer bb = ByteBuffer.wrap("Hello I am server".getBytes("UTF-8"));
		//老的系统一般是serversocket由于阻塞也使用多线程 socket 也使用多线程来做
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.bind(new InetSocketAddress("127.0.0.1",port));
		ssc.configureBlocking(isBlock);
		while (true){
			System.out.println("Waiting for connections...");
			SocketChannel sc = ssc.accept();
			if(sc==null){
				Thread.sleep(10000);
			}else{
				System.out.println("Incomming connection from:"+sc.socket().getRemoteSocketAddress()+":"+sc.getRemoteAddress());
				//这里还是阻塞的
				sc.write(bb);
				bb.rewind();
				sc.close();
				Thread.sleep(10000);
			}
		}
	}

}
