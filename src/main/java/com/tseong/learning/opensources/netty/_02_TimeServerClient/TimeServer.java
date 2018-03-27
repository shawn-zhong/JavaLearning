package com.tseong.learning.opensources.netty._02_TimeServerClient;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TimeServer {

    public static void main(String[] args) throws Exception {

        int port = 8866;
        if (args.length > 0) {
             port = Integer.parseInt(args[0]);
        }

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        System.out.println("now to listen port : " + port);

        try {

            ServerBootstrap b = new ServerBootstrap();
            b = b.group(bossGroup, workerGroup);
            b = b.channel(NioServerSocketChannel.class);
            b = b.childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new TimeEncoder()).addLast(new TimerServerHandler());
                    //socketChannel.pipeline().addLast(new )
                }
            });

            b = b.option(ChannelOption.SO_BACKLOG, 128);
            b = b.childOption(ChannelOption.SO_KEEPALIVE, true);

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
