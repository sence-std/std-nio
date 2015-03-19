/**
 * @FileName:ExecutorTest
 * @Package: com.std.nio
 *
 * @author sence
 * @created 3/18/2015 4:23 PM
 *
 * Copyright 2011-2015 Asura
 */
package com.std.nio;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 *
 * <p>并发测试</p>
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
public class ExecutorTest {

	private static int thread_num = 400;
	private static int client_num = 3000;

	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();

		final Semaphore semp = new Semaphore(thread_num);

		for (int index = 0; index < client_num; index++) {
			final int NO = index;
			Runnable run = new Runnable() {
				public void run() {
					try {
						semp.acquire();
						System.out.println("Thread:" + NO);
						ConnectAsync ca = new ConnectAsync();
						ca.initSocket(8080,false);
						semp.release();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			exec.execute(run);
		}
		exec.shutdown();
	}

}
