/**
 * @FileName:Buffer
 * @Package: com.std.nio
 *
 * @author sence
 * @created 3/13/2015 6:07 PM
 *
 * Copyright 2011-2015 Asura
 */
package com.std.nio;

import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 *
 * <p>Buffer学习</p>
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
public class BufferStd {


	@Test
	public void testBuffer(){
		ByteBuffer bb = ByteBuffer.allocate(10);
		bb.put((byte)'H');
		bb.put((byte)'e');
		bb.put((byte)'l');
		bb.put((byte)'l');
		bb.put((byte)'o');
		Assert.assertEquals(10,bb.limit());
		Assert.assertEquals(10,bb.capacity());
		Assert.assertEquals(5,bb.position());
	}

}
