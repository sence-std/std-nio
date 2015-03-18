/**
 * @FileName:MappedHttpTest
 * @Package: com.std.nio
 *
 * @author sence
 * @created 3/17/2015 3:06 PM
 *
 * Copyright 2011-2015 Asura
 */
package com.std.nio;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

/**
 *
 * <p>MappedBuffer Gather 结合使用</p>
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
public class MappedHttpTest {

	@Test
	public  void test() throws FileNotFoundException, UnsupportedEncodingException {
		String fileName="D:\\std\\c.txt";
		String fileName2="D:\\std\\x.txt";
		MappedHttp mappedHttp = new MappedHttp();
		mappedHttp.httpResponse(fileName,fileName2);
	}

}
