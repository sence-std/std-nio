/**
 * @FileName:MappedHttp
 * @Package: com.std.nio
 *
 * @author sence
 * @created 3/17/2015 2:32 PM
 *
 * Copyright 2011-2015 Asura
 */
package com.std.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * <p>MappedBuffer Scatter Gather相结合</p>
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
public class MappedHttp {


	/**HTTP 分隔符*/
	private static final String HTTP_LINE_SEP ="\r\n";
	/**服务器ID*/
	private static final String SERVER_ID = "Server:Sence Dummary Server";

	public static final String HTTP_DHR = "HTTP/1.1 200 OK"+HTTP_LINE_SEP+SERVER_ID+HTTP_LINE_SEP;

	public static final String HTTP_404_DHR = "HTTP/1.1 404 Not Found"+HTTP_LINE_SEP+SERVER_ID+HTTP_LINE_SEP;
	/**查找不到*/
	public static final String MSG_404="Page not found:";



	public void httpResponse(String filePath,String outFilePath)
			throws FileNotFoundException, UnsupportedEncodingException {
		ByteBuffer header = ByteBuffer.wrap(HTTP_DHR.getBytes("UTF-8"));
		ByteBuffer dnyHeader = ByteBuffer.allocate(128);
		ByteBuffer[] gather = {header,dnyHeader,null};
		String contentType="unknow/unkonw";
		long contentLength = -1L;
		File file = new File(filePath);
		FileInputStream in = null;
		FileChannel channel = null;
		try{
			in = new FileInputStream(file);
			channel = in.getChannel();
			MappedByteBuffer mbb = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
			gather[2] = mbb;
			contentLength = channel.size();
			contentType = URLConnection.guessContentTypeFromName(filePath);
		}catch (IOException e){
				gather[0] = ByteBuffer.wrap(HTTP_404_DHR.getBytes("UTF-8"));
		 		String msg404 = MSG_404+e+HTTP_LINE_SEP;
				gather[2] = ByteBuffer.wrap(msg404.getBytes("UTF-8"));
				contentLength=msg404.length();
				contentType="text/plain";
		}finally {
			try {
				if(in!=null) {
					in.close();
				}
				if(channel!=null) {
					channel.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileOutputStream out = null;
		FileChannel outChannel=null;
		try {
		    StringBuffer sb = new StringBuffer();
			sb.append("CONTENT-TYPE:"+contentType);
			sb.append(HTTP_LINE_SEP);
			sb.append("COTENT-LENGTH:"+contentLength);
			sb.append(HTTP_LINE_SEP);
			sb.append(HTTP_LINE_SEP);
			//返回了new Buffer 所以文件内不会输出
			//dnyHeader.wrap(sb.toString().getBytes("UTF-8"));
			dnyHeader.put(sb.toString().getBytes("UTF-8"));
			dnyHeader.flip();
			File outFile = new File(outFilePath);
			if(!outFile.getParentFile().exists()){
				outFile.getParentFile().mkdirs();
			}
			out = new FileOutputStream(outFilePath);
			outChannel = out.getChannel();
			while(outChannel.write(gather)>0){
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			try {
				if(out!=null){
					out.close();
				}
				if(outChannel!=null) {
					outChannel.close();
				}
			}catch (IOException e){
				e.printStackTrace();
			}

		}

	}

}
