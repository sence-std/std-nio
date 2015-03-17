/**
 * @FileName:FileHoleChannelTest
 * @Package: com.std.nio
 *
 * @author sence
 * @created 3/17/2015 10:04 AM
 *
 * Copyright 2011-2015 Asura
 */
package com.std.nio;

import org.junit.Test;

import java.io.IOException;

/**
 *
 * <p>文件空洞测试</p>
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
public class FileHoleChannelTest {

	/**
	 *
	 */
	@Test
	public void testCreateHole() throws IOException {
		String fileName = "d:\\bb.txt";
		FileHoleChannel.saveFileHole(fileName);
	}

	@Test
	public void testTruncate() throws IOException {
		String fileName = "d:\\bb.txt";
		FileHoleChannel.truncate(fileName, 4050L);
	}


}
