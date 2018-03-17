package com.tseong.learning.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class _02_ByteBufferDemo {

    public static void main(String[] args) throws IOException {

        try{
            // 创建输入输出流
            FileInputStream ins = new FileInputStream(new File("/Users/Shawn/Documents/workspace/JavaLearning/target/copy.txt"));
            FileOutputStream out = new FileOutputStream(new File("/Users/Shawn/Documents/workspace/JavaLearning/target/paste.txt"));

            // 从输入输出流中获取channel
            FileChannel chlIn = ins.getChannel();
            FileChannel chlOut = out.getChannel();

            // 创建一个buffer, 大小为1024
            ByteBuffer buffer = ByteBuffer.allocate(1024);  // 使用静态方法allocate来分配缓冲区 (会自动释放的堆外内存－》allocateDirect)

            // 另外一种方法创建ByteBuffer :
            /*
            byte array[] = new byte[1024];
            ByteBuffer buffer2 = ByteBuffer.wrap(array);
            */

            while(true) {
                buffer.clear();
                int n = chlIn.read(buffer);
                if (n == -1) {
                    break;
                }

                /*
                byte[] data = buffer.array();
                String part = new String(data);
                System.out.println(part);*/


                buffer.flip();  // // 把limit设置为当前position并把posotion置为0
                chlOut.write(buffer);
            }

            chlOut.close();
            System.out.println("copy/paste over");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


// http://blog.csdn.net/anders_zhuo/article/details/8497634
