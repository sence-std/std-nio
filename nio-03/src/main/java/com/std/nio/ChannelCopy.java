package com.std.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @ file_name ChannelCopy
 * @ author  sence (lovesenceqi@gmail.com)
 * @ date 2015/3/15 22:02
 * @ description
 * @ review by
 */
public class ChannelCopy {

    public static  void copyChannel(ReadableByteChannel rbc,WritableByteChannel wbc) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        while(rbc.read(byteBuffer)!=-1){
            byteBuffer.flip();
            while(byteBuffer.hasRemaining()) {
                wbc.write(byteBuffer);
            }
            byteBuffer.clear();
        }

    }

}
