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
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 *
 * <p>
 *	   这里还不能使用NIO Channel 因为ObjectOutputStream ObjectInputStream流都是阻塞的，发挥不了异步的优势，使用没有意义
 *     RPC 框架核心
 * </p>
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
		ServerSocket ssc = new ServerSocket();
		SocketAddress socketAddress = new InetSocketAddress(host,port);
		ssc.bind(socketAddress);
		while (true){
			Socket socket = ssc.accept();
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			String methodName = in.readUTF();
			Class<?>[] parameterTypes = (Class<?>[])in.readObject();
			Object[] parameters = (Object[])in.readObject();
			Method method = service.getClass().getMethod(methodName,parameterTypes);
			Object result = method.invoke(service,parameters);
			ObjectOutputStream out  = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(result);
			out.flush();
			socket.close();
		}
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
				Socket socket = null;
				try {
					socket = new Socket();
					socket.connect(new InetSocketAddress(host,port));
					ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
					out.writeUTF(method.getName());
					out.writeObject(method.getParameterTypes());
					out.writeObject(args);
					out.flush();
					ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
					Object obj = in.readObject();
					if(obj instanceof Throwable){
						return obj;
					}
					return obj;
				}catch (Exception  e){
					throw  e;
				}finally {
					if(socket == null){
						socket.close();
					}
				}
			}
		});


	}

}
