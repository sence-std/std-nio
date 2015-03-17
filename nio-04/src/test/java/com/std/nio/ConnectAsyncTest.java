/**
 * @FileName:ConnectAsyncTest
 * @Package: com.std.nio
 *
 * @author sence
 * @created 3/17/2015 4:44 PM
 *
 * Copyright 2011-2015 Asura
 */
package com.std.nio;

import org.junit.Test;

import java.io.IOException;

/**
 *
 * <p>异步连接</p>
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
public class ConnectAsyncTest {

	/**
	 * 非阻塞模式
	 * @throws java.io.IOException
	 * @throws InterruptedException
	 */
	@Test
	public void testNoBlock() throws IOException, InterruptedException {
		ConnectAsync ca = new ConnectAsync();
		ca.initSocket(8080,false);
	}

	/**
	 * 阻塞模式
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	public void testBlock() throws IOException, InterruptedException {
		ConnectAsync ca = new ConnectAsync();
		ca.initSocket(8080,true);
	}

}
