package com.tseong.learning.nio;

import java.nio.FloatBuffer;

public class _02_FloatBuffer {

    public static void main(String[] args) {
        FloatBuffer buffer = FloatBuffer.allocate(10);

        for (int i=0; i<buffer.capacity(); i++) {
            float f = (float) Math.sin((((float)i)/10)*(2*Math.PI));
            buffer.put(f);
        }

        buffer.flip();

        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }
}
