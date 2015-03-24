/**
 * @FileName:SocketsThreadPool
 * @Package: com.std.nio.threadpool
 *
 * @author sence
 * @created 3/18/2015 5:14 PM
 *
 * Copyright 2011-2015 Asura
 */
package com.std.nio.threadpool;

import com.std.nio.SelectorServerChannel;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * <p>线程池</p>
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
public class SocketsThreadPool extends SelectorServerChannel {

	public static final int MAX_THREAD_NUM = 4;
	public ThreadPool pool = new ThreadPool(MAX_THREAD_NUM);

	public void readDataFromSocket (SelectionKey key) throws IOException {
		WorkerThread worker = pool.getWorker();
		while(worker==null){
			worker = pool.getWorker();
		}
		worker.serviceChannel(key);
	}


	class ThreadPool{
		List<WorkerThread> idle = new LinkedList<>();

		ThreadPool(int poolSize){
			for(int i=0;i<poolSize;i++){
				WorkerThread workerThread = new WorkerThread(this);
				workerThread.setName("Worker-"+i);
				workerThread.start();
				idle.add(workerThread);
			}
		}

		/**
		 * 得到一个工作线程，由于是多线程访问需要做同步 防止两个线程同时取到用一个工作线程
		 * @return
		 */
		WorkerThread getWorker(){
			WorkerThread worker = null;
			synchronized (idle){
				if(idle.size()>0){
					worker = idle.remove(0);
				}
			}
			return worker;
		}

		/**
		 * 返回工作线程
		 * @param worker
		 */
		void returnWorker(WorkerThread worker){
			synchronized (idle) {
				idle.add(worker);
			}
		}

	}

}
