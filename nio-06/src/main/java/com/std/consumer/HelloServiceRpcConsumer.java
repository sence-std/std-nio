/**
 * @FileName:HelloServiceRpcConsumer
 * @Package: com.std.consumer
 *
 * @author sence
 * @created 4/9/2015 4:28 PM
 *
 * Copyright 2011-2015 Asura
 */
package com.std.consumer;

import com.std.entity.Hello;
import com.std.rpc.RpcFramework;
import com.std.service.IHelloService;

import java.io.IOException;

/**
 *
 * <p>Rpc消费者</p>
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
public class HelloServiceRpcConsumer {

	/**
	 * 执行消费
	 */
	public void doConsumer() throws IOException {
		IHelloService helloService = RpcFramework.refer(IHelloService.class, "127.0.0.1", 8888);
		Hello hello = helloService.sayHello("sence","hello");
	}

}
