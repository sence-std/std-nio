/**
 * @FileName:SocketThread
 * @Package: com.std.nio.threadpool
 *
 * @author sence
 * @created 3/18/2015 5:42 PM
 *
 * Copyright 2011-2015 Asura
 */
package com.std.nio.threadpool;

import java.nio.channels.SelectionKey;

/**
 *
 * <p>TODO</p>
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
public class SocketThread implements Runnable {


	private SelectionKey selectionKey;

	/**
	 * When an object implementing interface <code>Runnable</code> is used
	 * to create a thread, starting the thread causes the object's
	 * <code>run</code> method to be called in that separately executing
	 * thread.
	 * <p>
	 * The general contract of the method <code>run</code> is that it may
	 * take any action whatsoever.
	 *
	 * @see     Thread#run()
	 */
	@Override
	public void run () {

	}
}
