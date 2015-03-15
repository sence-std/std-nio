package com.std.nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @ file_name ChannelCopy
 * @ author  sence (lovesenceqi@gmail.com)
 * @ date 2015/3/15 22:18
 * @ description
 * @ review by
 */
public class ChannelCopyTest {

    @Test
    public void test() throws IOException {
        ReadableByteChannel source = Channels.newChannel(System.in);
        WritableByteChannel dest = Channels.newChannel(System.out);
        ChannelCopy.copyChannel(source,dest);
        source.close();
        dest.close();;
    }

}
