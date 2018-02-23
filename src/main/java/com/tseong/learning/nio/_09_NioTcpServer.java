package com.tseong.learning.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class _09_NioTcpServer {

    private Selector selector;

    public static void main(String[] args) throws Exception {
        _09_NioTcpServer server = new _09_NioTcpServer();
        server.initServer(8989);
        server.listen();
    }

    public void initServer(int port) throws Exception {

        // 获取一个serverSocket通道
        ServerSocketChannel serverchannel = ServerSocketChannel.open();

        // 设置通道为非阻塞
        serverchannel.configureBlocking(false);

        // 将该通道对于的serverSocket绑定到port端口
        serverchannel.socket().bind(new InetSocketAddress(port));

        selector = Selector.open();

        // 将通道管理器和该通道绑定，并为该通道注册selectionKey.OP_ACCEPT事件
        // 注册该事件后，当事件到达的时候，selector.select()会返回，
        // 如果事件没有到达selector.select()会一直阻塞

        serverchannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    // 采用轮询的方式监听selector上是否有需要处理的时间，如果有，进行处理
    public void listen() throws Exception {
        System.out.println("start server");

        // 轮询访问selector
        while (true) {
            // 当注册事件到达时，方法会返回，否则该方法会一直阻塞
            selector.select();
            Iterator iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) iterator.next();
                // 删除已选的key 以防重复处理
                iterator.remove();
                // 客户端请求连接时间
                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    // 获取和客户端连接的通道
                    SocketChannel channel = server.accept();
                    // 设置成非阻塞
                    channel.configureBlocking(false);
                    // 这里可以发送消息给客户端
                    channel.write(ByteBuffer.wrap(new String("hello from server").getBytes()));
                    // 在客户端连接成功之后，为了可以接受到客户端的信息，需要给通道设置读的权限
                    channel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    read(key);
                }
            }
        }
    }

    private void read(SelectionKey key) throws Exception {
        // 服务器可读消息，得到时间发生的socket通道
        SocketChannel channel = (SocketChannel)key.channel();
        // 创建读取的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(10);

        channel.read(buffer);
        byte[] data = buffer.array();
        String msg = new String(data).trim();
        System.out.println("server receive from client: " + msg);

        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
        channel.write(outBuffer);
    }
}
