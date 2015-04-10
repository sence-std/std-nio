/**
 * @FileName:RpcFramework
 * @Package: com.std.rpc
 *
 * @author sence
 * @created 4/9/2015 4:19 PM
 *
 * Copyright 2011-2015 Asura
 */
package com.std.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * <p>RPC 框架核心</p>
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
public class RpcFramework {

	/**
	 * 暴露提供者服务
	 * @param host
	 * @param port
	 * @param service
	 */
	public static void export(String host,int port,Object service)
			throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		if(host == null || host.length()==0){
			throw new IllegalArgumentException("host == null");
		}
		if(port<0 || port>65535){
			throw new IllegalArgumentException("port must between 0 to 65535");
		}
		if(service == null){
			throw new IllegalArgumentException("service is null");
		}
		ServerSocketChannel ssc = ServerSocketChannel.open();
		SocketAddress socketAddress = new InetSocketAddress(host,port);
		ssc.bind(socketAddress);
		ssc.configureBlocking(true);
		Selector selector = Selector.open();
		ssc.register(selector, SelectionKey.OP_ACCEPT);
		while (true){
			int select = selector.select();
			if(select==0){
				continue;
			}
			Set<SelectionKey> keys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = keys.iterator();
			while (iterator.hasNext()){
				SelectionKey key = iterator.next();
				if(key.isAcceptable()){
					ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
					SocketChannel socketChannel = serverSocketChannel.accept();
					key.cancel();
					selector.selectNow();
					registerSocketChannel(socketChannel,selector,SelectionKey.OP_READ);
				}
				if(key.isReadable()){
					readDataDoService(service, key);
				}
				iterator.remove();
			}
		}
	}

	/**
	 *
	 * @param service
	 * @param key
	 */
	private static void readDataDoService (Object service, SelectionKey key)
			throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
			IllegalAccessException {
		SocketChannel socketChannel = null;
		Socket socket = null;
		ObjectInputStream in = null;
		ObjectOutputStream out = null;
		try {
			socketChannel = (SocketChannel) key.channel();
			socket = socketChannel.socket();
		    in = new ObjectInputStream(socket.getInputStream());
			String methodName = in.readUTF();
			Class<?>[] parameterTypes = (Class<?>[])in.readObject();
			Object[] parameters = (Object[])in.readObject();
			Method method = service.getClass().getMethod(methodName,parameterTypes);
			Object result = method.invoke(service,parameters);
			out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(result);
		}catch (Exception e){
			throw e;
		}finally {
			if(in != null){
				in.close();
			}
			if(out != null){
				out.close();
			}
			if(socket != null){
				socket.close();
			}
			if(socketChannel != null){
				socketChannel.close();
			}
		}
	}

	/**
	 * 注册socketchannel
	 * @param channel
	 * @param selector
	 * @param opRead
	 * @throws ClosedChannelException
	 */
	private static void registerSocketChannel (SocketChannel channel, Selector selector,int opRead) throws IOException {
		if(channel == null){
			return;
		}
		channel.configureBlocking(true);
		channel.register(selector,opRead);
	}

	/**
	 * 引用服务
	 * @return
	 */
	public static <T> T refer(Class<T> interfaceClass, final String host, final int port) throws IOException {
		if(host == null || host.length()==0){
			throw new IllegalArgumentException("host == null");
		}
		if(port<0 || port>65535){
			throw new IllegalArgumentException("port must between 0 to 65535");
		}
		if(interfaceClass==null ){
			throw new IllegalArgumentException("interfaceClass is null");
		}
		if(!interfaceClass.isInterface()){
			throw new IllegalArgumentException("interfaceClass is not a interface");
		}
		return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(),new Class<?>[]{interfaceClass},new InvocationHandler() {
			@Override
			public Object invoke (Object proxy, Method method, Object[] args) throws Throwable {
				SocketChannel sc = null;
				ObjectOutputStream out = null;
				ObjectInputStream input = null;
				try {
					sc = SocketChannel.open();
					sc.configureBlocking(true);
					sc.connect(new InetSocketAddress(host,port));
					while(!sc.finishConnect()){
					}
					Socket socket = sc.socket();
					out = new ObjectOutputStream(socket.getOutputStream());
					out.writeUTF(method.getName());
					out.writeObject(method.getParameterTypes());
					out.writeObject(args);
					input = new ObjectInputStream(socket.getInputStream());
					Object obj = input.readObject();
					if(obj instanceof Throwable){
						throw (Throwable)obj;
					}
					return obj;
				}catch (Exception  e){
					throw  e;
				}finally {
					if(out == null){
						out.close();
					}
					if(input == null){
						input.close();
					}
					if(sc == null){
						sc.close();
					}
				}
			}
		});


	}

}
