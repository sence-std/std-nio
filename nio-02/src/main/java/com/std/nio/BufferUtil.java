package com.std.nio;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * @ file_name BufferUtil
 * @ author  sence (lovesenceqi@gmail.com)
 * @ date 2015/3/14 17:08
 * @ description
 * @ review by
 */
public class BufferUtil {


    /**
     *
     * @param buffer
     * @param bytes
     * @return
     */
    public static Buffer fillBuffer(ByteBuffer buffer,byte[] bytes){
        for(byte b:bytes){
            buffer.put(b);
        }
        return buffer;
    }

    /**
     *
     * @param buffer
     * @param str
     * @return
     */
    public static Buffer fillBuffrer(ByteBuffer buffer,String str){
       for(int i=0;i<str.length();i++){
           Character ch = str.charAt(i);
           buffer.put((byte)ch.charValue());
       }
        return buffer;
    }

    /**
     *
     * @param buffer
     * @param str
     * @return
     */
    public static Buffer fillBuffrer(CharBuffer buffer,String str){
        int i = Math.min(buffer.remaining(),str.length());
        buffer.put(str.toCharArray(),0,i);
//        for(int i=0;i<str.length();i++){
//            Character ch = str.charAt(i);
//            buffer.put(ch.charValue());
//        }
        return buffer;
    }

    /**
     *
     * @param buffer
     * @param chs
     * @return
     */
    public static Buffer fillBuffrer(CharBuffer buffer,Character[] chs){
        for(Character ch:chs){
            buffer.put(ch);
        }
        return buffer;
    }

    /**
     *
     * @param buffer
     */
    public static void drainBuffer(CharBuffer buffer){
        while(buffer.hasRemaining()){
            buffer.get();
        }
    }

    /**
     *
     * @param buffer
     */
    public static void drainBuffer(ByteBuffer buffer){
        while(buffer.hasRemaining()){
            buffer.get();
        }
    }

    /**
     *
     * @param charBuffer
     */
    public static void printBuffer(CharBuffer charBuffer){
        charBuffer.mark();
//        while (charBuffer.hasRemaining()){
//            System.out.print("-"+charBuffer.get());
//        }
        int remain = charBuffer.remaining();
        char[] ch = new char[remain];
        charBuffer.get(ch,0,remain);
        System.out.print(new String(ch));
        charBuffer.reset();
    }


}
