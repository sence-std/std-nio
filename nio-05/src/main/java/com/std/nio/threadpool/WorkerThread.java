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

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 *
 * <p>工作线程</p>
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
public class WorkerThread extends Thread {

	private ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);

	private SocketsThreadPool.ThreadPool pool;
	private SelectionKey selectionKey;

	/**
	 *
	 * @param pool
	 */
	public WorkerThread(SocketsThreadPool.ThreadPool pool){
		this.pool = pool;
	}

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
	public synchronized void run () {
		System.out.println(this.getName()+"　is running...");
		while(!this.currentThread().isInterrupted()){
			try {
				System.out.println(this.getName()+"　awaiting....");
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
				this.isInterrupted();
			}
			if(selectionKey == null){
				continue;
			}
			System.out.println(this.getName()+"　handing....");
			try {
				drainChannel(selectionKey);
			}catch (Exception e){
				e.printStackTrace();
				try {
					selectionKey.channel().close();
				}catch (Exception ex){
					ex.printStackTrace();
				}
				selectionKey.selector().wakeup();
			}
			selectionKey = null;
			pool.returnWorker(this);
		}
	}

	/**
	 * Called to initiate a unit of work by this worker thread on the
	 * provided SelectionKey object. This method is synchronized, as is the
	 * run( ) method, so only one key can be serviced at a given time.
	 * Before waking the worker thread, and before returning to the main
	 * selection loop, this key's interest set is updated to remove OP_READ.
	 * This will cause the selector to ignore read-readiness for this
	 * channel while the worker thread is servicing it.
	 * */
	synchronized void serviceChannel(SelectionKey key){
		this.selectionKey = key;
		this.selectionKey.interestOps(selectionKey.interestOps() & (~SelectionKey.OP_READ));
		this.notify();
	}

	void drainChannel(SelectionKey key)throws Exception{
		SocketChannel channel = (SocketChannel)key.channel();
		int count=0;
		byteBuffer.clear();
		while((count=channel.read(byteBuffer))>0){
			byteBuffer.flip();
			while(byteBuffer.hasRemaining()){
				channel.write(byteBuffer);
			}
			byteBuffer.clear();
		}
		if(count<=0){
			channel.close();
			return;
		}
		//Resume interest in OP_READ
		key.interestOps(key.interestOps() | SelectionKey.OP_READ);
		// Cycle the selector so this key is active again
		//调用Selector对象的wakeup( )方法将使得 key选择操作立即返回。
		key.selector().wakeup();
	}
}
