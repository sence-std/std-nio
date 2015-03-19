/**
 * @FileName:SelectorServerChannel
 * @Package: com.std.nio
 *
 * @author sence
 * @created 3/18/2015 10:24 AM
 *
 * Copyright 2011-2015 Asura
 */
package com.std.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * <p>使用选择器来维护服务端</p>
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
public class SelectorServerChannel {

	public void initServer(int port) throws IOException {

		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.bind(new InetSocketAddress("127.0.0.1",port));
		ssc.configureBlocking(false);
		Selector selector = Selector.open();
		ssc.register(selector, SelectionKey.OP_ACCEPT);
		while (true){
			//System.out.println("start select...");
			//返回就绪的IO通道数量
			int select  = selector.select();
			if(select==0){
				//System.out.println("had not selected...");
				continue;
			}
			Set<SelectionKey> keys = selector.selectedKeys();
			//System.out.println("selected key size:"+keys.size());
			Iterator<SelectionKey> iterator = keys.iterator();
			while (iterator.hasNext()){
				//System.out.println("do iterator...");
				SelectionKey key  = iterator.next();
				if(key.isAcceptable()){
					//System.out.println("do accept...");
					ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
					SocketChannel socketChannel = serverSocketChannel.accept();
					//System.out.println("channel:"+socketChannel);
					registerChannel(socketChannel,selector,SelectionKey.OP_READ);
					//作者 这里处理了 socketChannel 比较奇怪
					sayHello(socketChannel);
				}
				if(key.isReadable()){
					//System.out.println("do read...");
					readDataFromSocket(key);
				}
				iterator.remove();
			}
		}
	}

	private static final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);

	public void readDataFromSocket (SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel)key.channel();
		//System.out.println("channel:"+channel);
		int count = 0;
		byteBuffer.clear();
		while((count=channel.read(byteBuffer))>0){
			//System.out.println(count);
			byteBuffer.flip();
			while(byteBuffer.hasRemaining()){
				channel.write(byteBuffer);
			}
			byteBuffer.clear();
		}
		//System.out.println("count:"+count);
		if(count<=0) {
			channel.close();
		}

	}

	private void sayHello (SocketChannel socketChannel) throws IOException {
			String hello = "Hello,胜琦！";
			byteBuffer.clear();
			byteBuffer.put(hello.getBytes("UTF-8"));
			byteBuffer.flip();
			socketChannel.write(byteBuffer);
	}

	private void registerChannel (SelectableChannel channel, Selector selector, int opRead) throws IOException {
		if(channel == null){
			return;
		}
		channel.configureBlocking(false);
		channel.register(selector,opRead);
	}

}
