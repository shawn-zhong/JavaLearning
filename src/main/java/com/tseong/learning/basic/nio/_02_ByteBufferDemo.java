package com.tseong.learning.basic.nio;

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
                buffer.clear(); // 将position置为0，limit和capacity置为1024（注意不是1023）
                int n = chlIn.read(buffer);

                // 从通道中读完之后，position变为index＝读的字节数，limit和capacity不变

                if (n == -1) {
                    break;
                }

                /*
                byte[] data = buffer.array();
                String part = new String(data);
                System.out.println(part);*/


                buffer.flip();
                // filp的效果：
                // 1 把limit设置为当前position
                // 2 posotion置为0, 这样才能从buffer中读出
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


/*

https://blog.csdn.net/hemeinvyiqiluoben/article/details/82861355

position：指定了下一个将要被写入或者读取的元素索引，它的值由get()/put()方法自动更新，在新创建一个Buffer对象时，position被初始化为0。
limit：指定还有多少数据需要取出(在从缓冲区写入通道时)，或者还有多少空间可以放入数据(在从通道读入缓冲区时)。
capacity：指定了可以存储在缓冲区中的最大数据容量，实际上，它指定了底层数组的大小，或者至少是指定了准许我们使用的底层数组的容量

以上三个属性值之间有一些相对大小的关系：0 <= position <= limit <= capacity。如果我们创建一个新的容量大小为10的ByteBuffer对象，在初始化的时候，position设置为0，limit和 capacity被设置为10，在以后使用ByteBuffer对象过程中，capacity的值不会再发生变化，而其它两个个将会随着使用而变化。三个属性值分别如图所示：

现在我们可以从通道中读取一些数据到缓冲区中，注意从通道读取数据，相当于往缓冲区中写入数据。如果读取4个自己的数据，则此时position的值为4，即下一个将要被写入的字节索引为4，而limit仍然是10，如下图所示：

下一步把读取的数据写入到输出通道中，相当于从缓冲区中读取数据，在此之前，必须调用flip()方法，该方法将会完成两件事情：

1. 把limit设置为当前的position值
2. 把position设置为0
由于position被设置为0，所以可以保证在下一步输出时读取到的是缓冲区中的第一个字节，而limit被设置为当前的position，可以保证读取的数据正好是之前写入到缓冲区中的数据，如下图所示：

现在调用get()方法从缓冲区中读取数据写入到输出通道，这会导致position的增加而limit保持不变，但position不会超过limit的值，所以在读取我们之前写入到缓冲区中的4个自己之后，position和limit的值都为4，如下图所示：

在从缓冲区中读取数据完毕后，limit的值仍然保持在我们调用flip()方法时的值，调用clear()方法能够把所有的状态变化设置为初始化时的值，如下图所示：


*/