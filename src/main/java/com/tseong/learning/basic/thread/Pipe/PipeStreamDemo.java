package com.tseong.learning.basic.thread.Pipe;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

class ThreadRead extends Thread {

    private PipedInputStream input;

    public ThreadRead(PipedInputStream input) {
        super();
        this.input = input;
    }


    @Override
    public void run() {
        this.readMethod(input);
    }

    public void readMethod(PipedInputStream input) {
        try {
            System.out.println("read :");
            byte[] byteArray = new byte[20];
            int readLength = input.read(byteArray);
            while(readLength != -1) {
                String newData = new String(byteArray, 0, readLength);
                System.out.print(newData);
                readLength = input.read(byteArray);
            }
            System.out.println();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ThreadWrite extends Thread {

    private PipedOutputStream out;

    public ThreadWrite(PipedOutputStream out) {
        this.out = out;
    }

    @Override
    public void run() {
        this.writeMethod(out);
    }

    public void writeMethod(PipedOutputStream out) {
        try {
            System.out.println("write :");
            for (int i=0; i< 30; i++) {
                String outdata = " " + (i + 1);
                out.write(outdata.getBytes());
                System.out.print(outdata);
            }
            System.out.println();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

public class PipeStreamDemo {

    public static void main(String[] args) throws InterruptedException {
        try (PipedOutputStream outputStream = new PipedOutputStream();
             PipedInputStream inputStream = new PipedInputStream()){

            // nputStream.connect(outputStream); // 效果相同
            outputStream.connect(inputStream);

            Thread t1 = new ThreadRead(inputStream);
            Thread t2 = new ThreadWrite(outputStream);
            t1.start();
            t2.start();

            t1.join();
            t2.join();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



/*

　PipedInputStream和PipedOutputStream的实现原理类似于”生产者-消费者”原理，PipedOutputStream是生产者，PipedInputStream是消费者。
    在PipedInputStream中，有一个buffer字节数组，默认大小为1024，作为缓冲区，存放”生产者”生产出来的东西。此外，还有两个变量in和out
    —— in用来记录”生产者”生产了多少，out是用来记录”消费者”消费了多少，in为-1表示消费完了，in==out表示生产满了。当消费者没东西可消费的时候，
    也就是当in为-1的时候，消费者会一直等待，直到有东西可消费。
　　
　　在 Java 的 JDK 中，提供了四个类用于线程间通信：

字节流：PipedInputStream 和 PipedOutputStream;
字符流: PipedWriter 和 PipedReader


 */