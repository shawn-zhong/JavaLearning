package com.tseong.learning.basic.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class _08_NioTcpClient {

    // 通道管理器
    private Selector selector;

    public static void main(String[] args) throws Exception {
        _08_NioTcpClient client = new _08_NioTcpClient();
        client.initClient("localhost", 8989);
        client.listen();
    }


    // 获取一个socket通道，并对该通道做一些初始化工作
    public void initClient(String ip, int port) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        this.selector = Selector.open();
        // 用channel.finishConnect() 才能完成连接
        channel.connect(new InetSocketAddress(ip, port));
        // 将通道管理器和该通道绑定，并为该通道注册xx事件
        channel.register(selector, SelectionKey.OP_CONNECT);
    }

    // 采用轮询的方式监听selector上是否有需要处理的事件，如果有则进行处理
    public void listen() throws Exception {
        while (true) {
            selector.select();

            Iterator iterator = this.selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) iterator.next();
                iterator.remove();

                if (key.isConnectable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    if (channel.isConnectionPending()) {    // 如果正在连接，则完成连接
                        channel.finishConnect();
                    }
                    channel.configureBlocking(false);   // 设置成非阻塞
                    channel.write(ByteBuffer.wrap(new String("hello from client").getBytes()));
                    channel.register(this.selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    read(key);
                }
            }
        }
    }

    private void read (SelectionKey key) throws Exception {
        SocketChannel channel= (SocketChannel) key.channel();

        // 创建读取的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(10);

        channel.read(buffer);
        byte[] data = buffer.array();

        String msg = new String(data).trim();
        System.out.println(msg);

        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
        channel.write(outBuffer);
    }

}
