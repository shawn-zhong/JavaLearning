package com.tseong.learning.opensources.netty._01_Discard;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DiscardServer {

    private int port;

    public DiscardServer(int port) {
        super();
        this.port = port;
    }

    public void run() throws Exception {

        /*
        NioEventLoopGroup是用来处理I/O操作的多线程事件循环器，
        Netty提供了许多不同的EventLoopGroup的实现来处理不同传输协议。在这个例子中我们实现了一个服务端的应用，
        因此会有2个NioEventLoopGroup会被使用。第一个经常被叫做boss，用来接受进来的连接。
        第二个经常被叫做worker，用来处理已经被接受的连接。一旦boss接受到连接，就会把连接信息注册到worker上，
        如何知道多少个线程已经被使用，如何映射到已经创建的channels上都需要依赖于EventLoopGroup的实现，
        并且可以通过构造函数来配置他们的关系
        bossgroup如果服务端开启的是一个端口(大部分都是一个),单线程即可.
        默认线程数是 cpu核心数的2倍
         */

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        System.out.println("now to listen port : " + port);

        try {
            /*
            ServerBootstrap 是一个启动NIO服务的辅助启动类，你可以在这个服务中直接使用channel
             */

            ServerBootstrap b = new ServerBootstrap();

            /* 这一步是必须的，如果没有设置将会抛出异常java.lang.IllegalStateException */
            b = b.group(bossGroup, workerGroup);

            /* ServerSocketChannel以NIO的selector为基础进行实现的，用来接受新的连接
                这里告诉channel如何获取新的连接
             */
            b = b.channel(NioServerSocketChannel.class);

            /*
            这里的事件处理类经常会被用来处理一个最近的已经接受的channel。channelInitializer是一个特殊的处理类，
            他的目的是帮助使用者配置一个新的channel
            也许你想通过增加一些处理类比如NettyServerHandler来配置一个新的channel
            或者其对应的channelPipeline来实现你的网络程序。当你的程序变得复杂时，可能你会增加更多的处理类到pipline上
            然后提取这些匿名类到最顶层的类上
             */
            b = b.childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new DiscardServerHandler());
                    //socketChannel.pipeline().addLast(new )
                }
            });

            /*
            你可以设置这里指定的通道实现的配置参数。我们正在写一个TCP／IP服务器，因此我们被允许设置socket的参数选项
            比如tcpNoDelay和keepAlive。请参考channelOption和详细的channelConfig实现的接口文档，对channelOptions有一个大概的认识
             */
            b = b.option(ChannelOption.SO_BACKLOG, 128);

            /*
            option()是提供给NioServerSocketChannel用来接受进来的连接。
            childOption()是提供给由父管道ServerChannel接受到的连接，在这个例子中也是NioServerSocketChannel
             */
            b = b.childOption(ChannelOption.SO_KEEPALIVE, true);

            /*
            绑定端口并启动去接受进来的连接
             */
            ChannelFuture f = b.bind(port).sync();

            // 这里会一直等待，直到socket被关闭
            f.channel().closeFuture().sync();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
