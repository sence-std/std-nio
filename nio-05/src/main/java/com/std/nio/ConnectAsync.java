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

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
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

	public static final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);

	public void initSocket(int port,boolean isBlock) throws IOException {

		SocketChannel sc = SocketChannel.open();
		sc.configureBlocking(isBlock);
		sc.connect(new InetSocketAddress("127.0.0.1",port));
		while(!sc.finishConnect()){
			System.out.println("not connect yet...");
		}
		FileInputStream in = new FileInputStream("D:\\std\\x.txt");
		FileChannel channel = in.getChannel();
		channel.transferTo(0,channel.size(),sc);
		int count = 0;
		//如果sc是非阻塞的模式，返回-1才是到了结束了
		while((count = sc.read(byteBuffer))>=0){
			System.out.println("count w:"+count);
			byteBuffer.flip();
			byte[] bytes = new byte[byteBuffer.remaining()];
			byteBuffer.get(bytes,0,byteBuffer.remaining());
			System.out.println("---->"+new String(bytes,"UTF-8"));
			byteBuffer.clear();
		}
		System.out.println("count:"+count);
		sc.close();
	}


}
