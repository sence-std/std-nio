/**
 * @FileName:RpcTest
 * @Package: com.std
 *
 * @author sence
 * @created 4/13/2015 9:38 AM
 *
 * Copyright 2011-2015 Asura
 */
package com.std;

import com.std.entity.Hello;
import com.std.rpc.RpcFramework;
import com.std.service.IHelloService;
import com.std.service.impl.SimpleHelloServiceImpl;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * <p>测试类</p>
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
public class RpcTest {


	@Test
	public void testProvider()
			throws ClassNotFoundException, NoSuchMethodException, IOException, IllegalAccessException,
			InvocationTargetException {
		IHelloService helloService = new SimpleHelloServiceImpl();
		RpcFramework.export("127.0.0.1", 8888, helloService);
	}

	@Test
	public void testConsumer() throws IOException {
		IHelloService helloService = RpcFramework.refer(IHelloService.class, "127.0.0.1", 8888);
		Hello hello = helloService.sayHello("sence","hello");
		System.out.print(hello);
	}

}
