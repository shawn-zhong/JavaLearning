package com.tseong.learning.basic.nio;

import java.nio.ByteBuffer;

public class _07_SliceDemo {

    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.allocate(10);

        // 存入数据1～10
        for (int i=0; i<buffer.capacity(); i++) {
            buffer.put((byte)i);
        }

        // 创建一个缓冲区分片(相当于原来缓冲区的一个窗口)
        buffer.position(4); // [position, limit) 是窗口
        buffer.limit(8);    // 实际的操作不会影响limit那个槽（窗口内容为位置4,5,6,7）
        ByteBuffer sub = buffer.slice();
        for (int i=0; i<sub.capacity(); i++) {
            byte b = sub.get(i);
            sub.put((byte)(b*11));
        }

        buffer.position(0);
        buffer.limit(buffer.capacity());

        // 下面证明只有slice的内容受到了修改:
        while (buffer.hasRemaining()) {
            byte b = buffer.get();
            System.out.println(b);
        }


        ByteBuffer readOnly = buffer.asReadOnlyBuffer(); // 您可以读取他们，但是不能向它们写入
        // readOnly.put(1, (byte)11); // 因为是只读缓冲区，这句话会抛异常

        // 直接缓冲区 (系统级别的内存分配，产生的开销在jvm之外，非java堆，查看不出java heap的内存占用，泄漏看不出，)
        ByteBuffer direct = ByteBuffer.allocateDirect(1024);
    }

}
