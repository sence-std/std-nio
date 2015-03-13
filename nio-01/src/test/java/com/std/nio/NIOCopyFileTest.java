/**
 * @FileName:NIOCopyFileTest
 * @Package: com.std.nio
 *
 * @author sence
 * @created 3/13/2015 5:35 PM
 *
 * Copyright 2011-2015 Asura
 */
package com.std.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 *
 * <p>NIO 文件操作</p>
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
public class NIOCopyFileTest {


	@Test
	public void testCopyFile() throws IOException, URISyntaxException {
		NIOCopyFile.useMappedBufferCopy();
	}

}
