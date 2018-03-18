package com.tseong.learning.basic.container.blockingQueue.Methods;

import java.util.concurrent.ArrayBlockingQueue;

public class Bootstrap {

    public static void main(String[] args) {
        // 创建一个有界的blocking queue, 看超出界限之后会怎么样

        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

        for (int i=0; i<11; i++) {
            try {
                //queue.put(i);                                 // 到第十一个之后，会一直阻塞
                //queue.offer(i, 3, TimeUnit.SECONDS);          // 到第十一个之后，会超时并返回false
                //queue.offer(i);                               // 到第十一个之后, 会立即返回false
                queue.add(i);                                   // 到第十一个之后，会立即抛出异常
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("added all");
    }
}

/* blocking queue 的操作

 	    抛异常	    特定值	    阻塞	        超时
插入	    add(o)	    offer(o)	put(o)	    offer(o, timeout, timeunit)
移除	    remove(o)	poll(o)	    take(o)	    poll(timeout, timeunit)
检查	    element(o)	peek(o)

注意抛出的异常是runtime exception

 */
