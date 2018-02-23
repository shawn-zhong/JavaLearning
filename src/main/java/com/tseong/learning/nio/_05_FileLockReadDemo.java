package com.tseong.learning.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class _05_FileLockReadDemo {

    public static void main(String[] args) throws InterruptedException {


        // 运行04 再运行05 结果成功，不知道是不是和操作系统有关，等待查询

        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.submit(()->{

            try {
                System.out.println("ThreadId: " + Thread.currentThread().getId());
                String path = "/Users/Shawn/Documents/workspace/JavaLearning/target/newfile.txt";
                File file = new File(path);

                FileInputStream ins = new FileInputStream(file);
                FileChannel fIns = ins.getChannel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);

                while (true) {
                    buffer.clear();
                    int r = fIns.read(buffer);
                    if (r == -1) {
                        break;
                    }
                    buffer.flip();
                    printout(buffer);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        Thread.sleep(4000);
        pool.shutdown();
    }

    public static void printout(ByteBuffer buffer) throws CharacterCodingException {
        /*
        for (int i=0; i<buffer.limit(); i++) {
            System.out.println(buffer.get());
        }*/

        Charset charset = Charset.forName("UTF-8");
        CharsetDecoder decoder = charset.newDecoder();

        // 用这个的化，只能输出来一次结果，第二次显示为空
        // CharBuffer charBuffer = decoder.decode(buffer);

        CharBuffer charBuffer = decoder.decode(buffer.asReadOnlyBuffer());
        String strOut = charBuffer.toString();

        System.out.println(strOut);
    }
}


/*
Exception in thread "main" java.io.IOException: 另一个程序已锁定文件的一部分，进程无法访问。
        at sun.nio.ch.FileDispatcherImpl.read0(Native Method)
        at sun.nio.ch.FileDispatcherImpl.read(Unknown Source)
        at sun.nio.ch.IOUtil.readIntoNativeBuffer(Unknown Source)
        at sun.nio.ch.IOUtil.read(Unknown Source)
        at sun.nio.ch.FileChannelImpl.read(Unknown Source)
        at com.anders.javanio.filelock.ReadFile.main(ReadFile.java:23)
*/