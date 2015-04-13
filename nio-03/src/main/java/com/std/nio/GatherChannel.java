/**
 * @FileName:GatherStd
 * @Package: com.std.nio
 *
 * @author sence
 * @created 3/16/2015 11:59 AM
 *
 * Copyright 2011-2015 Asura
 */
package com.std.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * <p>
 *     NIO gather 聚合多个Buffer
 * 	   写出
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
public class GatherChannel {

	private static String[] col1 = {"sence mile","duka lio","stake kila","lisa mike","chris nbone","huke curs"};
	private static String[] col2 = {"microsoft","apple","google","alibaba","adobe","oracle"};
	private static String[] col3 = {"washington","new york","landon","北京"};

	public static void gatherWrite(ByteBuffer[] byteBuffers,String file) throws IOException {

		FileOutputStream fos = new FileOutputStream(file);
		GatheringByteChannel gatherChannel = fos.getChannel();
		while(gatherChannel.write(byteBuffers)>0){
		}
		System.out.println("全部写入文件:"+file);
		gatherChannel.close();
		fos.close();

	}

	public static void gatherWrite (String[] strs,String file) throws IOException {

		FileOutputStream fos = new FileOutputStream("d:\\aa.txt");
		GatheringByteChannel gatherChannel = fos.getChannel();
		ByteBuffer[] byteBuffers = utterBS();
		while(gatherChannel.write(byteBuffers)>0){
		}
		System.out.println("全部写入文件:"+file);
		fos.close();

	}

	private static ByteBuffer[] utterBS () throws UnsupportedEncodingException {
		List<ByteBuffer> byteBufferList = new ArrayList<>();
		for(int i=0;i<5;i++){
			byteBufferList.add(pickRandom(col1,"#"));
			byteBufferList.add(pickRandom(col2,"#"));
			byteBufferList.add(pickRandom(col3,"#"));
		}
		ByteBuffer[] byteBuffers = new ByteBuffer[byteBufferList.size()];
		byteBufferList.toArray(byteBuffers);
		return byteBuffers;
	}
	private static Random rand = new Random( );
	private static ByteBuffer pickRandom (String[] strs, String suffix) throws UnsupportedEncodingException {
		String string = strs [rand.nextInt (strs.length)];
		int total = string.length() + suffix.length( );
		ByteBuffer buf = ByteBuffer.allocate (total*3);
		buf.put (string.getBytes ("UTF-8"));
		buf.put (suffix.getBytes ("UTF-8"));
		buf.flip( );
		return (buf);
	}

}
