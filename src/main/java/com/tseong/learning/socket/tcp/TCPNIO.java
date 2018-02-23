package com.tseong.learning.socket.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class TCPNIO {

    private Selector selector = null;
    private static final int BUF_LENGTH = 1024;

    public void start() throws IOException {
        if (selector != null) {
            selector = Selector.open();
        }

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ServerSocket serverSocket = ssc.socket();
        serverSocket.bind(new InetSocketAddress(80));
        ssc.configureBlocking(false);
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        try {
            while (true) {
                int nKeys = selector.select();

                if (nKeys > 0) {
                    Iterator it = selector.selectedKeys().iterator();
                    while (it.hasNext()) {
                        SelectionKey key = (SelectionKey) it.next();
                        if (key.isAcceptable()) {
                            ServerSocketChannel server = (ServerSocketChannel)key.channel();
                            SocketChannel channel = server.accept();
                            if (channel == null){
                                continue;
                            }
                            channel.configureBlocking(false);
                            channel.register(selector, SelectionKey.OP_READ);
                        }

                        if (key.isReadable()) {
                            readDataFromSocket(key);
                        }

                        it.remove();
                    }
                }
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void readDataFromSocket(SelectionKey key) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(BUF_LENGTH);
        SocketChannel sc = (SocketChannel)key.channel();

        int readBytes = 0;
        int ret;

        try {
            while ((ret = sc.read(buf)) >0) {
                readBytes += ret;
            }
        } finally {
            buf.flip();
        }
    }
}


// http://blog.csdn.net/xymyeah/article/details/3714667

/*
从这段程序，我们基本可以了解到NIO网络编程的一些特点，创建一个SocketServer的方式已经发生了变化，需要指定非阻塞模式，需要创建一个 Channel然后注册到Selector中去，同样，建立一个网络连接过程也是一样的模式,然后就是有条件的选择(Readiness Selection).
这样，我们的每一个线程只需要处理一类型的网络选择。在代码上，我们发现处理的方式和阻塞完全不一样了，我们需要完全重新考虑如何编写网络通信的模块了：
        1.持久连接的超时问题(Timeout)，因为API没有直接的支持timeout的参数设置功能，因此需要我们自己实现一个这样功能。
        2.如何使用Selector，由于每一个Selector的处理能力是有限的，因此在大量链接和消息处理过程中，需要考虑如何使用多个Selector.
        3.在非阻塞情况下，read和write都不在是阻塞的，因此需要考虑如何完整的读取到确定的消息；如何在确保在网络环境不是很好的情况下，一定将数据写进IO中。
        4.如何应用ByteBuffer，本身大量创建ByteBuffer就是很耗资源的；如何有效的使用ByteBuffer？同时ByteBuffer的操作需要仔细考虑，因为有position()、mark()、limit()、capacity等方法。
        5.由于每一个线程在处理网络连接的时候，面对的都是一系列的网络连接，需要考虑如何更好的使用、调度多线程。在对消息的处理上，也需要保证一定的顺序，比方说，登录消息最先到达，只有登录消息处理之后，才有可能去处理同一个链路上的其他类型的消息。
        6.在网络编程中可能出现的内存泄漏问题。
        在NIO的接入处理框架上，大约有两种并发线程：
        1.Selector线程，每一个Selector单独占用一个线程，由于每一个Selector的处理能力是有限的，因此需要多个Selector并行工作。
        2.对于每一条处于Ready状态的链路，需要线程对于相应的消息进行处理；对于这一类型的消息，需要并发线程共同工作进行处理。在这个过程中，不断可能需要消息的完整性；还要涉及到，每个链路上的消息可能有时序，因此在处理上，也可能要求相应的时序性。
        当前社区的开源NIO框架实现有MINA、Grizzly、nio framework、QuickServer、xSocket等，其中MINA和Grizzly最为活跃，而且代码的质量也很高。他们俩在实现的方法上也完全大不一样。(大部分Java的开源服务器都已经用NIO重写了网络部分。 )
        不管是我们自己实现NIO的网络编程框架，还是基于MINA、Grizzly等这样的开源框架进行开发，都需要理解确定的了解NIO带来的益处和NIO编程需要解决的众多类型的问题。充足、有效的单元测试，是我们写好NIO代码的好助手:)
*/