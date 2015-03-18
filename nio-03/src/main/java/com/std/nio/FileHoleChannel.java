/**
 * @FileName:FileChannel
 * @Package: com.std.nio
 *
 * @author sence
 * @created 3/17/2015 9:57 AM
 *
 * Copyright 2011-2015 Asura
 */
package com.std.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 *
 * <p>文件空洞</p>
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
public class FileHoleChannel {

	/**
	 * 测试FileChannel 绝对写(使用position来写文件，这个是线程安全，意为着可以多线程写文件，极大提高效率)
	 * 注意文件空洞也会增大文件大小
	 *
	 */
	public static void saveFileHole(String fileName) throws IOException {
		File file = new File(fileName);
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		RandomAccessFile raf = new RandomAccessFile(file,"rw");
		FileChannel channel = raf.getChannel();
		ByteBuffer byteBuffer = ByteBuffer.allocate(100);
		putData(0,byteBuffer,channel);
		putData(4000,byteBuffer,channel);
		putData(50000000,byteBuffer,channel);
		/**
		 * force 指将文件的修改强制写入到磁盘，false为不修改文件的metadata(文件所有者，访问权限，最后修改时间等)
		 */
		channel.force(false);
		channel.close();
		raf.close();
	}

	private static void putData (int i, ByteBuffer byteBuffer, FileChannel channel) throws IOException {
		String str = "Hello！世界";
		byteBuffer.clear();
		byteBuffer.put(str.getBytes("UTF-8"));
		byteBuffer.flip();
		channel.position(i);
		channel.write(byteBuffer);
	}

	/**
	 *
	 * @param size
	 */
	public static void truncate(String fileName,Long size) throws IOException {
		System.out.println("target file size is:"+size);
		File file = new File(fileName);
		if(!file.exists() || size==null){
			return;
		}
		RandomAccessFile raf = new RandomAccessFile(file,"rw");
		FileChannel channel = raf.getChannel();
		System.out.println("before truncate file size is:"+channel.size());
		channel.truncate(size);
		System.out.println("after truncate file size is:"+channel.size());
		raf.close();
		channel.close();
	}

	public static String readFile(String fileName,Long position) throws IOException {
		File file = new File(fileName);
		if(!file.exists()){
			return null;
		}
		if(position == null){
			position = 0L;
		}
		RandomAccessFile raf = new RandomAccessFile(file,"r");
		FileChannel channel = raf.getChannel();
		//设置文件锁 共享锁和独占锁 是操作系统文件底层来实现的
		FileLock lock = channel.tryLock(position,channel.size()-position,true);
		if(lock!=null) {
			try{
				ByteBuffer byteBuffer = ByteBuffer.allocate(200);
				byteBuffer.clear();
				int length = channel.read(byteBuffer, position);
				if (length > 0) {
					byteBuffer.flip();
					byte[] bytes = new byte[200];
					byteBuffer.get(bytes, 0, byteBuffer.remaining());
					//字节码文件 UTF-8每个字符站三个字字节位，所以不能使用 char视图
					return new String(bytes, "UTF-8");
				} else {
					return null;
				}
			}catch (Exception e){
				throw new IOException(e.getMessage());
			}finally {
				lock.release();
				channel.close();
				raf.close();
			}

		}else{
			return "文件已经被锁定";
		}

	}
}
