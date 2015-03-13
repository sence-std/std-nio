package com.std.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ file_name NIOCopyFile.java
 * @ author liu.sheng.qi (liu.sheng.qi@fesco.com.cn)
 * @ date 2013-9-5上午9:46:22
 * @ description
 * @ reviewed_by 
 */
public class NIOCopyFile {
	
	
	
	
	/**
	 * 
	 * @throws java.net.URISyntaxException
	 * @throws java.io.IOException
	 * @ void useMappedBufferCopy(NIOCopyFile)
	 * @ author liu.sheng.qi (liu.sheng.qi@fesco.com.cn)
	 * @ date 2013-9-5上午9:47:03
	 * @ description 使用内存映射直接Copy File
	 * @ reviewed_by
	 */
	public static void useMappedBufferCopy() throws URISyntaxException, IOException{
		//得到文件
		URI uri = NIOCopyFile.class.getResource("/nio_test_copy.txt").toURI();
		File readFile = new File(uri);
		//由于不对文件内容做任何操作，故使用RandomAccessFile效率更高
		//读取的文件
		RandomAccessFile inputAccessFile = new RandomAccessFile(readFile, "r");
		FileChannel inputFileChannel = inputAccessFile.getChannel();
		MappedByteBuffer mappedByteBuffer =  inputFileChannel.map(FileChannel.MapMode.READ_ONLY,0, inputFileChannel.size());
		
		//写入的文件
		File writeFile = new File("D:\\nio_test_copy_1.txt");
		RandomAccessFile outputFile = new RandomAccessFile(writeFile, "rw");
		FileChannel outputFileChannel = outputFile.getChannel();
		outputFileChannel.write(mappedByteBuffer);
		inputAccessFile.close();
		inputFileChannel.close();
		outputFile.close();
		outputFileChannel.close();
		
	}
	/**
	 * 
	 * @throws java.net.URISyntaxException
	 * @throws java.io.IOException
	 * @ void useChannerToChannel(NIOCopyFile)
	 * @ author liu.sheng.qi (liu.sheng.qi@fesco.com.cn)
	 * @ date 2013-9-5上午9:49:25
	 * @ description 使用Channel to Channel方式
	 * @ reviewed_by
	 */
	public static void useChannerToChannelCopy() throws URISyntaxException, IOException{
		URI uri = NIOCopyFile.class.getResource("/nio_test_copy.txt").toURI();
		File inputFile = new File(uri);
		//read 模式
		RandomAccessFile inputAccessFile = new RandomAccessFile(inputFile, "r"); 
		FileChannel inputFileChannel = inputAccessFile.getChannel();
		
		File writeFile = new File("D:\\nio_test_copy_2.txt");
		RandomAccessFile outputFile = new RandomAccessFile(writeFile, "rw");
		FileChannel outputFileChannel = outputFile.getChannel();
		inputFileChannel.transferTo(0, outputFileChannel.size(), outputFileChannel);
		
		inputAccessFile.close();
		inputFileChannel.close();
		outputFile.close();
		outputFileChannel.close();
	}
	/**
	 * 
	 * @throws java.net.URISyntaxException
	 * @throws java.io.IOException
	 * @ void userReadToWrite(NIOCopyFile)
	 * @ author liu.sheng.qi (liu.sheng.qi@fesco.com.cn)
	 * @ date 2013-9-5上午9:50:07
	 * @ description 使用常规方式 Copy File
	 * @ reviewed_by
	 */
	public static void userReadToWriteCopy() throws URISyntaxException, IOException{
		URI uri = NIOCopyFile.class.getResource("/nio_test_copy.txt").toURI();
		File inputFile = new File(uri);
		//read 模式
		RandomAccessFile inputAccessFile = new RandomAccessFile(inputFile, "r"); 
		FileChannel inputFileChannel = inputAccessFile.getChannel();
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		
		File writeFile = new File("D:\\nio_test_copy_3.txt");
		RandomAccessFile outputFile = new RandomAccessFile(writeFile, "rw");
		FileChannel outputFileChannel = outputFile.getChannel();
		int r = 0;
		while (r!=-1) {
			//情况缓存区，重设position limit
			byteBuffer.clear();
			//读取
			r = inputFileChannel.read(byteBuffer);
			//调用flip()重新设置position为零 limit为数据的大小相当于重设之前的position
			byteBuffer.flip();
			outputFileChannel.write(byteBuffer);
		}
		inputAccessFile.close();
		inputFileChannel.close();
		outputFile.close();
		outputFileChannel.close();
	}

}
