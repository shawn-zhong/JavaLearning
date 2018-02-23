package com.tseong.learning.nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class _03_CharsetDemo {

    public static void main(String[] args) throws Exception {

        String inputFile = "/Users/Shawn/Documents/workspace/JavaLearning/target/copy.txt";
        String outputFile = "/Users/Shawn/Documents/workspace/JavaLearning/target/charset.txt";

        RandomAccessFile inf = new RandomAccessFile(inputFile, "r");
        RandomAccessFile outf = new RandomAccessFile(outputFile, "rw");

        long length = new File(inputFile).length();

        FileChannel channelIn = inf.getChannel();
        FileChannel channelOut = outf.getChannel();

        MappedByteBuffer inputData = channelIn.map(FileChannel.MapMode.READ_ONLY, 0, length);   // 将文件映射到内存

        Charset latin1 = Charset.forName("ISO-8859-1");

        // 创建一个解码器（用于读取）和一个编码器（用于写入）
        CharsetDecoder decoder = latin1.newDecoder();
        CharsetEncoder encoder = latin1.newEncoder();

        // 为了将字节数据解码为一组字符，我们把bytebuffer传递给charsetDecoder, 得到一个charBuffer
        CharBuffer cb = decoder.decode(inputData);

        // 如果想要处理字符，我们可以在程序到此处进行，但是这里我们只想无改变地将它写回，所以没什么要做的
        // 要写回数据，我们必须使用CharsetEncoder将它换回字节
        ByteBuffer outputData = encoder.encode(cb);
        channelOut.write(outputData);

        inf.close();
        outf.close();


    }
}


// http://blog.csdn.net/anders_zhuo/article/details/8507237

//  ISO-8859-1(Latin1) 字符集（这是 ASCII 的标准扩展）
//  US-ASCII
//  ISO-8859-1
//  UTF-8
//  UTF-16BE
//  UTF-16LE
//  UTF-16