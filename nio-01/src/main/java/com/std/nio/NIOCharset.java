package com.std.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 *
 * @ file_name NIOCopyFile.java
 * @ author liu.sheng.qi (liu.sheng.qi@fesco.com.cn)
 * @ date 2013-9-4下午3:50:40
 * @ description 读取文件并解析成字符串到内存
 * @ reviewed_by 
 */
public class NIOCharset {
	
	
	
	public static String readFile(String filePath) throws URISyntaxException, IOException {
		//读取相对路径下的文件，具体可以查看java.lang.Class API
		File file = new File(NIOCharset.class.getResource("/nio_test_charset.txt").toURI());
		//创建通道(Channel)
		FileInputStream input = new FileInputStream(file);
		FileChannel inChannel = input.getChannel();
		//创建缓冲区
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		byteBuffer.clear();
		//读取数据到缓冲区
		inChannel.read(byteBuffer);
		//必须是调用flip()方法，否则position在最后一个字节的位置上，直接decode为charBuffer会出现charBuffer读不到任何东西
		byteBuffer.flip();
		//转为字符
		Charset charset = Charset.forName( "utf-8" );
		CharBuffer charBuffer = charset.decode(byteBuffer);
		//输出
	    return  new String(charBuffer.array());
	}

}
