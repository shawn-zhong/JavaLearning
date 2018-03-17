package com.tseong.learning.basic.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/*
scatter/gather指的在多个缓冲区上实现一个简单的I/O操作，比如从通道中读取数据到多个缓冲区，或从多个缓冲区中写入数据到通道；
scatter（分散）：指的是从通道中读取数据分散到多个缓冲区Buffer的过程，该过程会将每个缓存区填满，直至通道中无数据或缓冲区没有空间；
gather（聚集）：指的是将多个缓冲区Buffer聚集起来写入到通道的过程，该过程类似于将多个缓冲区的内容连接起来写入通道；
 */
public class _06_GatherScatterDemo {

    static private final int HEADER_LENGTH = 5;
    static private final int BODY_LENGTH = 10;

    public static void main(String[] args) throws Exception {
        int port = 8080;
        ServerSocketChannel ssc = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(port);
        ssc.socket().bind(address);

        int messageLentgh = HEADER_LENGTH + BODY_LENGTH;

        ByteBuffer[] buffers = new ByteBuffer[2];
        buffers[0] = ByteBuffer.allocate(HEADER_LENGTH);    // header buffer
        buffers[1] = ByteBuffer.allocate(BODY_LENGTH);      // body buffer

        SocketChannel sc = ssc.accept();

        int byteRead = 0;
        while (byteRead < messageLentgh) {
            long r = sc.read(buffers);          // scatter 读取
            byteRead += r;

            System.out.println("r " + r);
            for (int i=0; i<buffers.length; i++) {
                ByteBuffer bb = buffers[i];
                System.out.println("b" + i + " position : " + bb.position());
            }
        }

        // process message

        // flip buffer
        for (int i=0; i<buffers.length; i++) {
            ByteBuffer bb = buffers[i];
            bb.flip();
        }

        // scatter write
        long byteWrite = 0;
        while (byteWrite < messageLentgh) {
            long r = sc.write(buffers);         // gather 写入
            byteWrite += r;
        }

        // clear buffers
        for (int i=0; i<buffers.length; i++) {
            ByteBuffer bb = buffers[i];
            bb.clear();
        }

        System.out.println(byteRead + " " + byteWrite + " " + messageLentgh);
    }
}



/*

public interface ScatteringByteChannel extends ReadableByteChannel
{
    public long read(ByteBuffer[] dsts) throws IOException;

    public long read(ByteBuffer[] dsts, int offset, int length) throws IOException;
}

public interface GatheringByteChannel extends WritableByteChannel
{
    public long write(ByteBuffer[] srcs) throws IOException;

    public long write(ByteBuffer[] srcs, int offset, int length) throws IOException;
}

提醒下，带offset和length参数的read( ) 和write( )方法可以让我们只使用缓冲区数组的子集，注意这里的offset指的是缓冲区数组索引，而不是Buffer数据的索引，length指的是要使用的缓冲区数量；

如下代码，将会往通道写入第二个、第三个、第四个缓冲区内容；
int bytesRead = channel.write (fiveBuffers, 1, 3);
注意，无论是scatter还是gather操作，都是按照buffer在数组中的顺序来依次读取或写入的；



ByteBuffer header = ByteBuffer.allocate(128);
ByteBuffer body   = ByteBuffer.allocate(1024);

//write data into buffers

ByteBuffer[] bufferArray = { header, body };
channel.write(bufferArray);

以上代码会将header和body这两个缓冲区的数据写入到通道；
这里要特别注意的并不是所有数据都写入到通道，写入的数据要根据position和limit的值来判断，只有position和limit之间的数据才会被写入；
举个例子，假如以上header缓冲区中有128个字节数据，但此时position=0，limit=58；那么只有下标索引为0-57的数据才会被写入到通道中；

 */