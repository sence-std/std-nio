/**
 * @FileName:HelloServiceRpcProvider
 * @Package: com.std.provider
 *
 * @author sence
 * @created 4/10/2015 10:22 AM
 *
 * Copyright 2011-2015 Asura
 */
package com.std.provider;

import com.std.rpc.RpcFramework;
import com.std.service.IHelloService;
import com.std.service.impl.SimpleHelloServiceImpl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * <p>Rpc提供者</p>
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
public class HelloServiceRpcProvider {

	/**
	 * 执行暴露服务
	 */
	public void doExport()
			throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException,
			IOException {
		IHelloService helloService = new SimpleHelloServiceImpl();
		RpcFramework.export("127.0.0.1",8888,helloService);
	}

}
