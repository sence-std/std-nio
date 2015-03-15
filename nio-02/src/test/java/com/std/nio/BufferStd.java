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
import java.nio.CharBuffer;

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


    /**
     * 测试填充缓冲区
     */
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
        bb.put(0,(byte)'M');
	}

    @Test
    public void testBuffer1(){
        ByteBuffer bb = ByteBuffer.allocate(10);
        byte[] bbs = new byte[5];
        bbs[0]=(byte)'H';
        bbs[1]=(byte)'e';
        bbs[2]=(byte)'l';
        bbs[3]=(byte)'l';
        bbs[4]=(byte)'o';
        BufferUtil.fillBuffer(bb,bbs);
        Assert.assertEquals(10,bb.limit());
        Assert.assertEquals(10,bb.capacity());
        Assert.assertEquals(5,bb.position());
    }
    @Test
    public void testBuffer2(){
        CharBuffer bb = CharBuffer.allocate(10);
        BufferUtil.fillBuffrer(bb,"Hello");
        bb.flip();
        BufferUtil.printBuffer(bb);
        Assert.assertEquals(5,bb.limit());
        Assert.assertEquals(10,bb.capacity());
        Assert.assertEquals(0,bb.position());
    }

    @Test
    public void testBuffer3(){
        ByteBuffer bb = ByteBuffer.allocateDirect(10);
        CharBuffer cb = bb.asCharBuffer();
        byte[] bytes = new byte[10];
        bytes[0]=0;
        bytes[1]=(byte)'H';
        bytes[2]=0;
        bytes[3]=(byte)'e';
        bytes[4]=0;
        bytes[5]=(byte)'l';
        bytes[6]=0;
        bytes[7]=(byte)'l';
     //   bb.wrap(new byte[]{'H','E','L','L','O'});
        bb.put(bytes);
        //数据元素视图position 为0 所以不需要flip方法
        BufferUtil.printBuffer(cb);
    }
    @Test
    public void testBuffer4(){
        CharBuffer cb = CharBuffer.allocate(10);
        cb.put('H');
        cb.put('e');
        cb.put('l');
        cb.put('l');
        BufferUtil.printBuffer(cb);
    }

    @Test
    public void testBuffer5(){
        ByteBuffer bb = ByteBuffer.allocateDirect(10);
        bb.put((byte)1);
        bb.put((byte)'H');
        bb.put((byte)1);
        bb.put((byte)1);
        bb.flip();
        int i = bb.getInt();
        System.out.print(i);
    }

}
